package terrails.colorfulhearts.api.neoforge.event;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.bus.api.Event;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

import java.util.Optional;

public class NeoHeartRenderEvent<E extends HeartRenderEvent> extends Event {

    public static class Pre extends NeoHeartRenderEvent<HeartRenderEvent.Pre> {

        public Pre(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart effectHeart) {
            super(new HeartRenderEvent.Pre(guiGraphics, x, y, blinking, hardcore, effectHeart));
        }

        public void setCancelled(boolean cancel) {
            event.setCancelled(cancel);
        }
        public boolean isCancelled() {
            return event.isCancelled();
        }

        public void setX(int x) {
            event.setX(x);
        }
        public void setY(int y) {
            event.setY(y);
        }

        public void setBlinking(boolean blinking) {
            event.setBlinking(blinking);
        }
        public void setHardcore(boolean hardcore) {
            event.setHardcore(hardcore);
        }
        public void setEffectHeart(OverlayHeart heart) {
            event.setEffectHeart(heart);
        }
    }

    public static class Post extends NeoHeartRenderEvent<HeartRenderEvent.Post> {

        public Post(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart effectHeart) {
            super(new HeartRenderEvent.Post(guiGraphics, x, y, blinking, hardcore, effectHeart));
        }
    }

    final E event;

    public NeoHeartRenderEvent(E event) {
        this.event = event;
    }

    public E getEvent() {
        return this.event;
    }

    public GuiGraphics getGuiGraphics() {
        return event.getGuiGraphics();
    }

    public int getX() {
        return event.getX();
    }
    public int getY() {
        return event.getY();
    }

    public boolean isBlinking() {
        return event.isBlinking();
    }
    public boolean isHardcore() {
        return event.isHardcore();
    }

    public Optional<OverlayHeart> getOverlayHeart() {
        return event.getOverlayHeart();
    }
}
