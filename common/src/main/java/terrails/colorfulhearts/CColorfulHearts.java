package terrails.colorfulhearts;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CColorfulHearts {

    public static final String MOD_ID = "colorfulhearts";
    public static final String MOD_NAME = "Colorful Hearts";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static ResourceLocation location(String path) {
        return new ResourceLocation(CColorfulHearts.MOD_ID, path);
    }
}
