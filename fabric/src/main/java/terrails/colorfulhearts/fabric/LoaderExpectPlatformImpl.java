package terrails.colorfulhearts.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.event.HeartSingleRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.Heart;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.fabric.ColorfulHeartsApi;
import terrails.colorfulhearts.api.fabric.event.FabHeartEvents;

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

    public static HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, Player player, int x, int y, int maxHealth, int currentHealth, int displayHealth, int absorption, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        HeartRenderEvent.Pre event = new HeartRenderEvent.Pre(guiGraphics, player, x, y, maxHealth, currentHealth, displayHealth, absorption, blinking, hardcore, overlayHeart);
        FabHeartEvents.PRE_RENDER.invoker().accept(event);
        return event;
    }

    public static void postRenderEvent(GuiGraphics guiGraphics, Player player, int x, int y, int maxHealth, int currentHealth, int displayHealth, int absorption, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        HeartRenderEvent.Post event = new HeartRenderEvent.Post(guiGraphics, player, x, y, maxHealth, currentHealth, displayHealth, absorption, blinking, hardcore, overlayHeart);
        FabHeartEvents.POST_RENDER.invoker().accept(event);
    }

    public static void singleRenderEvent(Heart heart, GuiGraphics guiGraphics, int index, int x, int y, boolean hardcore, boolean blinking, boolean blinkingHeart) {
        HeartSingleRenderEvent event = new HeartSingleRenderEvent(heart, guiGraphics, index, x, y, hardcore, blinking, blinkingHeart);
        FabHeartEvents.SINGLE_RENDER.invoker().accept(event);
    }

    public static void heartRegistryEvent(HeartRegistry registry) {
        FabricLoader.getInstance().getEntrypointContainers("colorfulhearts", ColorfulHeartsApi.class).forEach(entryPoint -> {
            String modId = entryPoint.getProvider().getMetadata().getId();
            try {
                CColorfulHearts.LOGGER.info("Loading ColorfulHeartsApi implementation of mod {}", modId);
                ColorfulHeartsApi api = entryPoint.getEntrypoint();
                api.registerHearts(registry);
                CColorfulHearts.LOGGER.debug("Loaded ColorfulHeartsApi implementation of mod {}", modId);
            } catch (Throwable e) {
                CColorfulHearts.LOGGER.error("Could not load ColorfulHeartsApi implementation of mod {}", modId, e);
            }
        });

        FabHeartEvents.HEART_REGISTRY.invoker().accept(registry);
    }

    public static void heartUpdateEvent() {
        FabHeartEvents.UPDATE.invoker().run();
    }
}
