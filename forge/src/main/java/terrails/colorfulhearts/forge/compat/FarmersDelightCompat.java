package terrails.colorfulhearts.forge.compat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.common.MinecraftForge;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRenderEvent;
import vectorwing.farmersdelight.client.gui.ComfortHealthOverlay;

public class FarmersDelightCompat {

    public FarmersDelightCompat() {
        MinecraftForge.EVENT_BUS.addListener(this::onPostRender);
    }

    private void onPostRender(ForgeHeartRenderEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ForgeGui gui = (ForgeGui)mc.gui;
        if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
            ComfortHealthOverlay.renderComfortOverlay(gui, event.getGuiGraphics());
        }
    }
}
