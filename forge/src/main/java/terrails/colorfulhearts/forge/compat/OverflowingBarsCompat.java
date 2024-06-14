package terrails.colorfulhearts.forge.compat;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import terrails.colorfulhearts.compat.OverflowingBarsCommonCompat;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRenderEvent;

public class OverflowingBarsCompat extends OverflowingBarsCommonCompat {

    public OverflowingBarsCompat(IEventBus bus) {
        super();
        if (drawBarRowCount != null) {
            MinecraftForge.EVENT_BUS.addListener(this::onPostRender);
        }
    }

    public void onPostRender(ForgeHeartRenderEvent.Post event) {
        this.render(event.getEvent());
    }
}
