package terrails.colorfulhearts.fabric.compat;

import terrails.colorfulhearts.compat.OverflowingBarsCommonCompat;
import terrails.colorfulhearts.fabric.api.event.FabHeartEvents;

public class OverflowingBarsCompat extends OverflowingBarsCommonCompat {

    public OverflowingBarsCompat() {
        super();
        if (drawBarRowCount != null) {
            FabHeartEvents.POST_RENDER.register(this::render);
        }
    }
}
