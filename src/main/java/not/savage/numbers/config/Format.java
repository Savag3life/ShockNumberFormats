package not.savage.numbers.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ConfigSerializable
@NoArgsConstructor @Data
public class Format {

    private List<String> suffixes = new ArrayList<>();
    private int exponent = 0;

}
