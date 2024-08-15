package terrails.colorfulhearts;

import net.minecraft.client.gui.GuiGraphics;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

public interface PlatformProxy {

    /**
     * Returns the lowercase name of current modloader
     * @return modloader name
     */
    String getLoader();

    /**
     * Applies changes to night-config's FileConfig
     * Technically not needed if autosave were to be enabled
     */
    void applyConfig();

    /**
     * A way for other mods to force usage of hardcore hearts
     * Currently only possible with Fabric via ObjectShare
     * @return if hardcore textures should be used even if not in a hardcore world
     */
    boolean forcedHardcoreHearts();

    /**
     * Event to register custom hearts. Current use is for overlay type hearts
     * @param registry heart registry
     */
    void heartRegistryEvent(HeartRegistry registry);

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
    HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart);

    /**
     * Called after health renderer finishes drawing
     * @param guiGraphics used by renderer
     * @param x final position of the hearts
     * @param y final position of the hearts
     * @param blinking eligible hearts blink
     * @param hardcore hearts are of hardcore type
     * @param overlayHeart type of overlay heart, null otherwise
     */
    void postRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart);

    /**
     * Just an empty event used to notify about in-game changes from the Config Screen
     */
    void heartUpdateEvent();
}
