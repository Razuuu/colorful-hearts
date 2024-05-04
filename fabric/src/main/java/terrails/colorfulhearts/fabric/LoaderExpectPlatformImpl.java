package terrails.colorfulhearts.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphics;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;
import terrails.colorfulhearts.fabric.api.event.FabHeartEvents;

public class LoaderExpectPlatformImpl {

    public static String getLoader() {
        return "fabric";
    }

    public static void applyConfig() {
        ColorfulHearts.CONFIG.save();
    }

    public static boolean forcedHardcoreHearts() {
        if (FabricLoader.getInstance().getObjectShare().get("colorfulhearts:force_hardcore_hearts") instanceof Boolean forced) {
            return forced;
        } else return false;
    }

    public static HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
        HeartRenderEvent.Pre event = new HeartRenderEvent.Pre(guiGraphics, x, y, blinking, hardcore, effectHeart);
        FabHeartEvents.PRE_RENDER.invoker().accept(event);
        return event;
    }

    public static void postRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
        HeartRenderEvent.Post event = new HeartRenderEvent.Post(guiGraphics, x, y, blinking, hardcore, effectHeart);
        FabHeartEvents.POST_RENDER.invoker().accept(event);
    }

    public static void heartRegistryEvent(HeartRegistry registry) {
        FabHeartEvents.HEART_REGISTRY.invoker().accept(registry);
    }

    public static void heartUpdateEvent() {
        FabHeartEvents.UPDATE.invoker().run();
    }
}
