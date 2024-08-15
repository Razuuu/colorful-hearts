package terrails.colorfulhearts.forge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoader;
import terrails.colorfulhearts.PlatformProxy;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.event.HeartRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.forge.event.ForgeHeartUpdateEvent;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRegistryEvent;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRenderEvent;

public class PlatformProxyImpl implements PlatformProxy {

    @Override
    public String getLoader() {
        return "forge";
    }

    @Override
    public void applyConfig() {
        ColorfulHearts.CONFIG_SPEC.save();
    }

    @Override
    public boolean forcedHardcoreHearts() {
        return false;
    }

    @Override
    public HeartRenderEvent.Pre preRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        ForgeHeartRenderEvent.Pre event = new ForgeHeartRenderEvent.Pre(guiGraphics, x, y, blinking, hardcore, overlayHeart);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getEvent();
    }

    @Override
    public void postRenderEvent(GuiGraphics guiGraphics, int x, int y, boolean blinking, boolean hardcore, OverlayHeart overlayHeart) {
        MinecraftForge.EVENT_BUS.post(new ForgeHeartRenderEvent.Post(guiGraphics, x, y, blinking, hardcore, overlayHeart));
    }

    @Override
    public void heartRegistryEvent(HeartRegistry registry) {
        ModLoader.get().postEvent(new ForgeHeartRegistryEvent(registry));
    }

    @Override
    public void heartUpdateEvent() {
        MinecraftForge.EVENT_BUS.post(new ForgeHeartUpdateEvent());
    }
}
