package terrails.colorfulhearts.api.event;

import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

/**
 * Used to register custom heart types. Currently, it can only be used to register overlay hearts.
 */
public class HeartRegistry {

    public <T extends OverlayHeart> T registerStatusEffectHeart(T heart) {
        Hearts.OVERLAY_HEARTS.put(heart.getId(), heart);
        return heart;
    }
}
