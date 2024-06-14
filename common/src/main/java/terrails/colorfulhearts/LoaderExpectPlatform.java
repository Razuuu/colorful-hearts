package terrails.colorfulhearts;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.Contract;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

public class LoaderExpectPlatform {

    /**
     * Returns the lowercase name of current modloader
     * @return modloader name
     */
    @Contract
    @ExpectPlatform
    public static String getLoader() { throw new AssertionError(); }

    /**
     * Applies changes to night-config's FileConfig
     * Technically not needed if autosave were to be enabled
     */
    @Contract
    @ExpectPlatform
    public static void applyConfig() { throw new AssertionError(); }

    /**
     * A way for other mods to force usage of hardcore hearts
     * Currently only possible with Fabric via ObjectShare
     * @return if hardcore textures should be used even if not in a hardcore world
     */
    @Contract
    @ExpectPlatform
    public static boolean forcedHardcoreHearts() { throw new AssertionError(); }

    /**
     * Event to register custom hearts. Current use is for overlay type hearts
     * @param registry heart registry
     */
    @Contract
    @ExpectPlatform
    public static void heartRegistryEvent(HeartRegistry registry) {
        throw new AssertionError();
    }

    /**
     * Called before health renderer draws anything on screen
     * @param guiGraphics used by the renderer
     * @param x position of the hearts
     * @param y position of the hearts
     * @param blinking eligible hearts should blink
     * @param hardcore hearts should be of hardcore type
     * @param overlayHeart type of overlay heart, null otherwise
     * @return the event with modified values
     */
    @Contract
    @ExpectPlatform
    public static HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        throw new AssertionError();
    }

    /**
     * Called after health renderer finishes drawing
     * @param guiGraphics used by renderer
     * @param x final position of the hearts
     * @param y final position of the hearts
     * @param blinking eligible hearts blink
     * @param hardcore hearts are of hardcore type
     * @param overlayHeart type of overlay heart, null otherwise
     */
    @Contract
    @ExpectPlatform
    public static void postRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        throw new AssertionError();
    }

    /**
     * Just an empty event used to notify about in-game changes from the Config Screen
     */
    @Contract
    @ExpectPlatform
    public static void heartUpdateEvent() {
        throw new AssertionError();
    }
}
