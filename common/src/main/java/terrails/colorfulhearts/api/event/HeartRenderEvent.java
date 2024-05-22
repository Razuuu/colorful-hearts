package terrails.colorfulhearts.api.event;

import net.minecraft.client.gui.GuiGraphics;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

import java.util.Optional;

/**
 * A set of events useful to control the health renderer and render overlays
 */
public class HeartRenderEvent {

    /**
     * Called before health renderer draws anything on screen
     * Can be used to do any tweaks to the rendering
     */
    public static class Pre extends HeartRenderEvent {

        private boolean cancelled = false;

        public Pre(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
            super(guiGraphics, x, y, blinking, hardcore, effectHeart);
        }

        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }
        public boolean isCancelled() {
            return this.cancelled;
        }

        public void setX(int x) {
            this.x = x;
        }
        public void setY(int y) {
            this.y = y;
        }

        public void setBlinking(boolean blinking) {
            this.blinking = blinking;
        }
        public void setHardcore(boolean hardcore) {
            this.hardcore = hardcore;
        }
        public void setEffectHeart(StatusEffectHeart heart) {
            this.effectHeart = heart;
        }
    }

    /**
     * Called after health renderer finishes drawing
     * Can be used to render additional overlays on top of health
     */
    public static class Post extends HeartRenderEvent {

        public Post(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
            super(guiGraphics, x, y, blinking, hardcore, effectHeart);
        }
    }

    protected final GuiGraphics guiGraphics;
    protected int x, y;
    protected boolean blinking, hardcore;
    protected StatusEffectHeart effectHeart;

    public HeartRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, StatusEffectHeart effectHeart) {
        this.guiGraphics = guiGraphics;
        this.x = x;
        this.y = y;
        this.blinking = blinking;
        this.hardcore = hardcore;
        this.effectHeart = effectHeart;
    }

    public GuiGraphics getGuiGraphics() {
        return guiGraphics;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean isBlinking() {
        return blinking;
    }
    public boolean isHardcore() {
        return hardcore;
    }

    public Optional<StatusEffectHeart> getEffectHeart() {
        return Optional.ofNullable(effectHeart);
    }
}