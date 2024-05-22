package terrails.colorfulhearts.api.event;

import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

/**
 * Used to register custom heart types. Currently, it can only be used to register status effect type hearts.
 */
public class HeartRegistry {

    public <T extends StatusEffectHeart> T registerStatusEffectHeart(T heart) {
        Hearts.STATUS_EFFECT_HEARTS.put(heart.getId(), heart);
        return heart;
    }
}