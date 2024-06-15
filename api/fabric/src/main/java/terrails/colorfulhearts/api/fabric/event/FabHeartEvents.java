package terrails.colorfulhearts.api.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.event.HeartSingleRenderEvent;

import java.util.function.Consumer;

public class FabHeartEvents {

    public static final Event<Consumer<HeartRegistry>> HEART_REGISTRY = createEvent();

    public static final Event<Consumer<HeartRenderEvent.Pre>> PRE_RENDER = createEvent();
    public static final Event<Consumer<HeartRenderEvent.Post>> POST_RENDER = createEvent();

    public static final Event<Consumer<HeartSingleRenderEvent>> SINGLE_RENDER = createEvent();

    /**
     * Just an empty event used to notify about in-game changes from the Config Screen
     */
    public static final Event<Runnable> UPDATE = EventFactory.createArrayBacked(Runnable.class, listeners -> () -> {
        for (Runnable listener : listeners) {
            listener.run();
        }
    });

    private static <T> Event<Consumer<T>> createEvent() {
        return EventFactory.createArrayBacked(Consumer.class, listeners -> event -> {
            for (Consumer<T> listener : listeners) {
                listener.accept(event);
            }
        });
    }
}
