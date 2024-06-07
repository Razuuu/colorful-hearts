package terrails.colorfulhearts.fabric.api;

import terrails.colorfulhearts.api.event.HeartRegistry;

/**
 * Custom "colorfulhearts" entryPoint type for fabric.mod.json
 * An alternative to events
 */
public interface ColorfulHeartsApi {

    /**
     *  Used to register custom heart types. Currently, it can only be used to register overlay hearts.
     */
    void registerHearts(HeartRegistry registry);
}
