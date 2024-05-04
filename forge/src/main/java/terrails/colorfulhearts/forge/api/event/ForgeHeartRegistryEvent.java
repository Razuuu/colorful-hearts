package terrails.colorfulhearts.forge.api.event;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

public class ForgeHeartRegistryEvent extends Event implements IModBusEvent {

    private final HeartRegistry registry;

    public ForgeHeartRegistryEvent(HeartRegistry registry) {
        this.registry = registry;
    }

    public <T extends StatusEffectHeart> T registerStatusEffectHeart(T heart) {
        return this.registry.registerStatusEffectHeart(heart);
    }
}
