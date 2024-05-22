package terrails.colorfulhearts.forge.render;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import terrails.colorfulhearts.render.HeartRenderer;

import java.util.Objects;

public class RenderEventHandler {

    public static final RenderEventHandler INSTANCE = new RenderEventHandler();

    private final Minecraft client = Minecraft.getInstance();
    private long lastHealthTime, healthBlinkTime;
    private int displayHealth, lastHealth;

    public void renderHearts(RenderGuiOverlayEvent.Pre event) {
        if (event.isCanceled() || client.options.hideGui || !event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_HEALTH.id()) || !Objects.requireNonNull(client.gameMode).canHurtPlayer() || !(client.getCameraEntity() instanceof Player player)) {
            return;
        }

        client.getProfiler().push("health");

        int absorption = Mth.ceil(player.getAbsorptionAmount());
        int health = Mth.ceil(player.getHealth());

        long tickCount = this.client.gui.getGuiTicks();
        boolean highlight = this.healthBlinkTime > tickCount && (this.healthBlinkTime - tickCount) / 3L % 2L == 1L;

        if (health < this.lastHealth && player.invulnerableTime > 0) {
            this.lastHealthTime = Util.getMillis();
            this.healthBlinkTime = (tickCount + 20);
        } else if (health > this.lastHealth && player.invulnerableTime > 0) {
            this.lastHealthTime = Util.getMillis();
            this.healthBlinkTime = (tickCount + 10);
        }

        if (Util.getMillis() - this.lastHealthTime > 1000L) {
            this.displayHealth = health;
            this.lastHealthTime = Util.getMillis();
        }

        this.lastHealth = health;
        int maxHealth = Mth.ceil(Math.max((float) player.getAttributeValue(Attributes.MAX_HEALTH), Math.max(this.displayHealth, health)));

        final ForgeGui gui = (ForgeGui) client.gui;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        int left = width / 2 - 91;
        int top = height - gui.leftHeight;

        // handle half heart requiring absorption to move one row up
        boolean hasAbsorptionRow = (absorption + Math.min(20, maxHealth == 19 ? 20 : maxHealth)) > 20;
        int offset = 10 + (hasAbsorptionRow ? 10 : 0);
        gui.leftHeight += offset;

        HeartRenderer.INSTANCE.renderPlayerHearts(guiGraphics, player, left, top, maxHealth, health, this.displayHealth, absorption, highlight);

        client.getProfiler().pop();

        event.setCanceled(true);
    }
}