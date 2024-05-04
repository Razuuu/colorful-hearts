package terrails.colorfulhearts.fabric.api;

import terrails.colorfulhearts.api.event.HeartRegistry;

/**
 * Custom "colorfulhearts" entryPoint type for fabric.mod.json
 * An alternative to events
 */
public interface ColorfulHeartsApi {

    void registerHearts(HeartRegistry registry);
}
