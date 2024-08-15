package terrails.colorfulhearts;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.render.RenderUtils;

public class CColorfulHearts {

    public static final String MOD_ID = "colorfulhearts";
    public static final String MOD_NAME = "Colorful Hearts";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static PlatformProxy PROXY;

    public static final ResourceLocation HEALTH_ICONS_LOCATION = new ResourceLocation(MOD_ID, "textures/health.png");
    public static final ResourceLocation ABSORPTION_ICONS_LOCATION = new ResourceLocation(MOD_ID, "textures/absorption.png");
    public static final ResourceLocation HALF_HEART_ICONS_LOCATION = new ResourceLocation(MOD_ID, "textures/half_heart.png");
    public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

    public static void setupCommon(PlatformProxy proxy) {
        PROXY = proxy;
        Hearts.CONTAINER = new HeartDrawing() {

            private static final ResourceLocation ID = new ResourceLocation("colorfulhearts", "container");

            @Override
            public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                if (half) {
                    RenderSystem.setShaderTexture(0, HALF_HEART_ICONS_LOCATION);
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, 0, highlight ? 9 : 0);
                    RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
                } else {
                    RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, 16 + (highlight ? 9 : 0), hardcore ? 45 : 0);
                }
            }

            @Override
            public ResourceLocation getId() {
                return ID;
            }
        };
    }
}
