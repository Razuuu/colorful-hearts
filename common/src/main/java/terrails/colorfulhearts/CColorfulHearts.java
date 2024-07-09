package terrails.colorfulhearts;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CColorfulHearts {

    public static final String MOD_ID = "colorfulhearts";
    public static final String MOD_NAME = "Colorful Hearts";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static PlatformProxy PROXY;

    public static final ResourceLocation SPRITE_NAME = new ResourceLocation(CColorfulHearts.MOD_ID, "colored_hearts");

    public static ResourceLocation location(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static void setup(PlatformProxy proxy) {
        PROXY = proxy;
    }
}
