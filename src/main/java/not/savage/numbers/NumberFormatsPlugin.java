package not.savage.numbers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketEvent;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import not.savage.numbers.config.Config;
import not.savage.numbers.listener.CommandPacketIntercept;
import not.savage.numbers.utility.ConfigBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NumberFormatsPlugin extends JavaPlugin {

    @Getter private Config settings;
    private boolean internalPacketEvents = false;
    private PacketListenerCommon packetListener;

    @Override
    public void onLoad() {
        getLogger().info("Initializing ShockAirHeads Plugin!");
        try {
            Class.forName("not.savage.numbers.shade.com.github.retrooper.packetevents.PacketEventsAPI");
            getLogger().info("Using shaded PacketEvents.");
            internalPacketEvents = true;
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            PacketEvents.getAPI().load();
        } catch (ClassNotFoundException ignored) {
            getLogger().info("Using non-shaded PacketEvents (server dependency).");
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading Shock Number Formats...");
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        if (!new File(getDataFolder(), "config.yml").exists()) {
            getLogger().info("Config not found, creating default config...");
            ConfigBuilder<Config> configBuilder = new ConfigBuilder<>(Config.class);
            configBuilder.forFile(new File(getDataFolder(), "config.yml"));
            this.settings = Config.getDefault();
            configBuilder.save(this.settings);
        } else {
            getLogger().info("Config found, loading...");
            this.settings = new ConfigBuilder<>(Config.class)
                    .forFile(new File(getDataFolder(), "config.yml"))
                    .build();
        }

        getLogger().info("Setting up PacketEvents...");
        PacketEvents.getAPI().init();
        packetListener = PacketEvents.getAPI().getEventManager().registerListener(new CommandPacketIntercept(this), PacketListenerPriority.LOW);
    }

    @Override
    public void onDisable() {
        if (internalPacketEvents) {
            getLogger().info("Shutting down PacketEvents...");
            PacketEvents.getAPI().terminate();
        } else {
            PacketEvents.getAPI().getEventManager().unregisterListener(packetListener);
        }
    }
}
