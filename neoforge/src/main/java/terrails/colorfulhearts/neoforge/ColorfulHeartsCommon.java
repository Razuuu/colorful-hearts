package terrails.colorfulhearts.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import terrails.colorfulhearts.CColorfulHearts;

@Mod(CColorfulHearts.MOD_ID)
public class ColorfulHeartsCommon {

    public ColorfulHeartsCommon(IEventBus bus, ModContainer container, Dist dist) {
        if (dist.isClient()) {
            new ColorfulHearts(bus, container);
        }
    }
}
