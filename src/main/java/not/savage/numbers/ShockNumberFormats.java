package not.savage.numbers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatCommandUnsigned;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShockNumberFormats extends JavaPlugin implements PacketListener {

    private final HashMap<String, Integer> suffixMap = new HashMap<>();

    private final Pattern numberFinder = Pattern.compile("(?<!\\d)(\\d+(?:\\.\\d+)?)([KkMmBbTtQqSsOoNnDdUu])");

    @Override
    public void onEnable() {
        fillMap();
        PacketEvents.getAPI().getEventManager().registerListener(this, PacketListenerPriority.LOW);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CHAT_COMMAND_UNSIGNED) {
            WrapperPlayClientChatCommandUnsigned packet = new WrapperPlayClientChatCommandUnsigned(event);
            packet.setCommand(replaceNumbers(packet.getCommand()));
            event.markForReEncode(true);
        }
    }

    private String replaceNumbers(String command) {
        Matcher matcher = numberFinder.matcher(command);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            BigDecimal base = new BigDecimal(matcher.group(1));
            String suffix = matcher.group(2);
            int exponent = suffixMap.getOrDefault(suffix, -1);
            if (exponent != -1) {
                BigDecimal full =  base.multiply(BigDecimal.TEN.pow(exponent));
                String fullString = full.toPlainString();
                if (fullString.contains("E")) {
                    fullString = full.toBigInteger().toString();
                }
                matcher.appendReplacement(result, fullString);
            }
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private void fillMap() {
        suffixMap.put("K", 3); suffixMap.put("k", 3);
        suffixMap.put("M", 6); suffixMap.put("m", 6);
        suffixMap.put("B", 9); suffixMap.put("b", 9);
        suffixMap.put("T", 12); suffixMap.put("t", 12);
        suffixMap.put("Q", 15); suffixMap.put("q", 15);
        suffixMap.put("S", 18); suffixMap.put("s", 18);
        suffixMap.put("O", 21); suffixMap.put("o", 21);
        suffixMap.put("N", 24); suffixMap.put("n", 24);
        suffixMap.put("D", 27); suffixMap.put("d", 27);
        suffixMap.put("U", 30); suffixMap.put("u", 30);
    }
}
