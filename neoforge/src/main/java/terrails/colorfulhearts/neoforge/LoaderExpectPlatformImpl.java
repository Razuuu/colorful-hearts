package terrails.colorfulhearts.neoforge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModLoader;
import net.neoforged.neoforge.common.NeoForge;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.drawing.Heart;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.neoforge.event.NeoHeartSingleRenderEvent;
import terrails.colorfulhearts.api.neoforge.event.NeoHeartUpdateEvent;
import terrails.colorfulhearts.api.neoforge.event.NeoHeartRegistryEvent;
import terrails.colorfulhearts.api.neoforge.event.NeoHeartRenderEvent;

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

    public static HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, Player player, int x, int y, int maxHealth, int currentHealth, int displayHealth, int absorption, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        NeoHeartRenderEvent.Pre event = new NeoHeartRenderEvent.Pre(guiGraphics, player, x, y, maxHealth, currentHealth, displayHealth, absorption, blinking, hardcore, overlayHeart);
        NeoForge.EVENT_BUS.post(event);
        return event.getEvent();
    }

    public static void postRenderEvent(GuiGraphics guiGraphics, Player player, int x, int y, int maxHealth, int currentHealth, int displayHealth, int absorption, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        NeoForge.EVENT_BUS.post(new NeoHeartRenderEvent.Post(guiGraphics, player, x, y, maxHealth, currentHealth, displayHealth, absorption, blinking, hardcore, overlayHeart));
    }

    public static void heartRegistryEvent(HeartRegistry registry) {
        ModLoader.get().postEvent(new NeoHeartRegistryEvent(registry));
    }

    public static void singleRenderEvent(Heart heart, GuiGraphics guiGraphics, int index, int x, int y, boolean hardcore, boolean blinking, boolean blinkingHeart) {
        NeoForge.EVENT_BUS.post(new NeoHeartSingleRenderEvent(heart, guiGraphics, index, x, y, hardcore, blinking, blinkingHeart));
    }

    public static void heartUpdateEvent() {
        NeoForge.EVENT_BUS.post(new NeoHeartUpdateEvent());
    }
}
