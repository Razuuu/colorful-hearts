package terrails.colorfulhearts.neoforge;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.fml.ModLoader;
import net.neoforged.neoforge.common.NeoForge;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;
import terrails.colorfulhearts.neoforge.api.event.NeoHeartUpdateEvent;
import terrails.colorfulhearts.neoforge.api.event.NeoHeartRegistryEvent;
import terrails.colorfulhearts.neoforge.api.event.NeoHeartRenderEvent;

import static terrails.colorfulhearts.CColorfulHearts.LOGGER;

public class LoaderExpectPlatformImpl {

    public static String getLoader() {
        return "neoforge";
    }

    public static void applyConfig() {
        ColorfulHearts.CONFIG_SPEC.save();
        LOGGER.debug("Successfully saved changes to {} config file.", CColorfulHearts.MOD_ID + ".toml");
    }

    public static boolean forcedHardcoreHearts() {
        return false;
    }

    public static HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
        NeoHeartRenderEvent.Pre event = new NeoHeartRenderEvent.Pre(guiGraphics, x, y, blinking, hardcore, effectHeart);
        NeoForge.EVENT_BUS.post(event);
        return event.getEvent();
    }

    public static void postRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
        NeoForge.EVENT_BUS.post(new NeoHeartRenderEvent.Post(guiGraphics, x, y, blinking, hardcore, effectHeart));
    }

    public static void heartRegistryEvent(HeartRegistry registry) {
        ModLoader.postEvent(new NeoHeartRegistryEvent(registry));
    }

    public static void heartUpdateEvent() {
        NeoForge.EVENT_BUS.post(new NeoHeartUpdateEvent());
    }
}
