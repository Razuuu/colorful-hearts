package terrails.colorfulhearts.api.forge.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.eventbus.api.Event;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

import java.util.Optional;

public class ForgeHeartRenderEvent<E extends HeartRenderEvent> extends Event {

    public static class Pre extends ForgeHeartRenderEvent<HeartRenderEvent.Pre> {

        public Pre(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
            super(new HeartRenderEvent.Pre(guiGraphics, x, y, blinking, hardcore, overlayHeart));
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
        public void setOverlayHeart(OverlayHeart heart) {
            event.setOverlayHeart(heart);
        }
    }

    public static class Post extends ForgeHeartRenderEvent<HeartRenderEvent.Post> {

        public Post(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
            super(new HeartRenderEvent.Post(guiGraphics, x, y, blinking, hardcore, overlayHeart));
        }
    }

    final E event;

    public ForgeHeartRenderEvent(E event) {
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
