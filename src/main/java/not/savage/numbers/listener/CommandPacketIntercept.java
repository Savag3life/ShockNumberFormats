package not.savage.numbers.listener;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatCommandUnsigned;
import not.savage.numbers.config.Format;
import not.savage.numbers.NumberFormatsPlugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandPacketIntercept implements PacketListener {

    private static final String DETECTION_REGEX = "(?<!\\d)(\\d+(?:\\.\\d+)?)([%s])";

    private final List<String> ignoredCommands;
    private final HashMap<String, Integer> suffixMap = new HashMap<>();
    private final Pattern detector;

    public CommandPacketIntercept(final NumberFormatsPlugin plugin) {
        final StringBuilder regex = new StringBuilder();
        for (String key : plugin.getSettings().getFormats().keySet()) {
            final Format format = plugin.getSettings().getFormats().get(key);
            for (String suffix : format.getSuffixes()) {
                suffixMap.put(suffix, format.getExponent());
            }
            regex.append(format.getSuffixes().stream().map(Pattern::quote).collect(Collectors.joining("|")));
        }
        detector = Pattern.compile(String.format(DETECTION_REGEX, regex));
        ignoredCommands = plugin.getSettings().getIgnoreCommands();
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CHAT_COMMAND_UNSIGNED) {
            WrapperPlayClientChatCommandUnsigned packet = new WrapperPlayClientChatCommandUnsigned(event);
            String command = packet.getCommand();
            // Only handle commands which aren't blacklisted
            if (ignoredCommands.stream().anyMatch(command::startsWith)) {
                return;
            }
            packet.setCommand(replaceNumbers(command));
            // Mark the packet for re-encoding to ensure the changes take effect
            event.markForReEncode(true);
        }
    }

    private String replaceNumbers(final String command) {
        final Matcher matcher = detector.matcher(command);
        final StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            final BigDecimal base = new BigDecimal(matcher.group(1));
            final String suffix = matcher.group(2);
            int exponent = suffixMap.getOrDefault(suffix, -1);
            if (exponent != -1) {
                // If the exponent is valid, calculate the full number
                BigDecimal full =  base.multiply(BigDecimal.TEN.pow(exponent));
                String fullString = full.toPlainString();
                // If the number is in scientific notation, convert it to a plain string
                if (fullString.contains("E")) {
                    fullString = full.toBigInteger().toString();
                }
                matcher.appendReplacement(result, fullString);
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
