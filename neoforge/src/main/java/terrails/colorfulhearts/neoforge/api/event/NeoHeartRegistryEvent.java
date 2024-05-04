package terrails.colorfulhearts.neoforge.api.event;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

public class NeoHeartRegistryEvent extends Event implements IModBusEvent {

    private final HeartRegistry registry;

    public NeoHeartRegistryEvent(HeartRegistry registry) {
        this.registry = registry;
    }

    public <T extends StatusEffectHeart> T registerStatusEffectHeart(T heart) {
        return this.registry.registerStatusEffectHeart(heart);
    }
}
