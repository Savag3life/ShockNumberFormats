package not.savage.numbers.config;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class Config {

    @Getter private List<String> ignoreCommands = List.of("help");
    @Getter private HashMap<String, Format> formats = new HashMap<>();

    public static Config getDefault() {
        final Config config = new Config();
        config.formats.put("thousand",    new Format(Arrays.asList("k", "K"), 3));
        config.formats.put("million",     new Format(Arrays.asList("m", "M"), 6));
        config.formats.put("billion",     new Format(Arrays.asList("b", "B"), 9));
        config.formats.put("trillion",    new Format(Arrays.asList("t", "T"), 12));
        config.formats.put("quadrillion", new Format(Arrays.asList("q", "Q"), 15));
        config.formats.put("sextillion",  new Format(Arrays.asList("s", "S"), 18));
        config.formats.put("octillion",   new Format(Arrays.asList("o", "O"), 21));
        config.formats.put("nonillion",   new Format(Arrays.asList("n", "N"), 24));
        config.formats.put("decillion",   new Format(Arrays.asList("d", "D"), 27));
        config.formats.put("undecillion", new Format(Arrays.asList("u", "U"), 30));
        return config;
    }

}
