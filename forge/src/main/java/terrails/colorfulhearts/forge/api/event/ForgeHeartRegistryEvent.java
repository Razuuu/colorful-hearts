package terrails.colorfulhearts.forge.api.event;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

/**
 * Used to register custom heart types. Currently, it can only be used to register status effect type hearts.
 * IMPORTANT: Make sure to register an event listener on the mod-loader bus and not on the regular Neo bus
 *              as the event is run before the regular event bus is initialized
 */
public class ForgeHeartRegistryEvent extends Event implements IModBusEvent {

    private final HeartRegistry registry;

    public ForgeHeartRegistryEvent(HeartRegistry registry) {
        this.registry = registry;
    }

    public <T extends StatusEffectHeart> T registerStatusEffectHeart(T heart) {
        return this.registry.registerStatusEffectHeart(heart);
    }
}
