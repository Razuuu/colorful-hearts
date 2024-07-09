package terrails.colorfulhearts.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ObjectShare;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.fabric.config.FabConfig;

public class ColorfulHearts implements ClientModInitializer {

    public static FabConfig CONFIG;

    @Override
    public void onInitializeClient() {
        CColorfulHearts.setup(new PlatformProxyImpl());
        CONFIG = new FabConfig();
        setupObjectShare();
    }

    private static void setupObjectShare() {
        final ObjectShare objectShare = FabricLoader.getInstance().getObjectShare();

        // Absorption
        // keep this for now in case some mods depended on it
        objectShare.putIfAbsent("colorfulhearts:absorption_over_health", false);

        // Allows other mods to force use of hardcore heart textures
        // Default vanilla behaviour (hardcore world) if false
        objectShare.putIfAbsent("colorfulhearts:force_hardcore_hearts", false);
    }
}
