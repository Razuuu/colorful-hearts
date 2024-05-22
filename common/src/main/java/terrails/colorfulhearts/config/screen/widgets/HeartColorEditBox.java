package terrails.colorfulhearts.config.screen.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.config.screen.HeartType;
import terrails.colorfulhearts.render.RenderUtils;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class HeartColorEditBox extends EditBox {

    private static final Pattern HEX_FORMAT = Pattern.compile("^#[0-9a-fA-F]{0,6}$"), HEX_MATCH = Pattern.compile("^#[0-9a-fA-F]{6}$");

    private boolean invalidHex;
    private final HeartType type;

    public HeartColorEditBox(Font font, int x, int y, int width, int height, HeartType type) {
        this(font, x, y, width, height, null, type);
    }

    public HeartColorEditBox(Font font, int x, int y, int width, int height, @Nullable EditBox editBox, HeartType type) {
        super(font, x, y, width, height, editBox, Component.empty());
        this.type = type;
        this.setResponder((str) -> {});
        this.setFilter((str) -> HEX_FORMAT.matcher(str).matches());
        this.setMaxLength(7);
        if (editBox != null) {
            this.setValue(editBox.getValue());
        }
    }

    public boolean isInvalidHex() {
        return this.invalidHex;
    }

    public int getColor() {
        return Integer.decode(this.getValue()) & 0xFFFFFF;
    }

    @Override
    public void setResponder(@NotNull Consumer<String> responder) {
        super.setResponder((str) -> {
            invalidHex = !HEX_MATCH.matcher(str).matches();
            responder.accept(str);
        });
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);

        if (!this.isVisible()) {
            return;
        }

        if (this.isInvalidHex()) {
            boolean isBordered = this.getInnerWidth() < this.width; // blame source for making #isBordered private
            if (isBordered) {
                // draw over the border in red if the hex text is invalid
                int borderColor = this.isFocused() ? 0xFFD6231A : 0xFF590707;
                guiGraphics.fill(this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY(), borderColor);
                guiGraphics.fill(this.getX() - 1, this.getY() + this.height, this.getX() + this.width + 1,  this.getY() + this.height + 1, borderColor);
                guiGraphics.fill(this.getX() - 1, this.getY(), this.getX(), this.getY() + this.height, borderColor);
                guiGraphics.fill(this.getX() + this.width, this.getY(), this.getX() + this.width + 1, this.getY() + this.height, borderColor);
            }
        } else {
            int x = this.getX() + this.width - 11;
            int y = this.getY() + this.height / 2 - 4;

            RenderSystem.setShaderTexture(0, this.type.isHealthType() ? CColorfulHearts.HEALTH_ICONS_LOCATION : CColorfulHearts.ABSORPTION_ICONS_LOCATION);

            int texX = 0;
            switch (type) {
                case HEALTH_POISONED:
                case ABSORBING_POISONED:
                    texX += 18;
                    break;
                case HEALTH_WITHERED:
                case ABSORBING_WITHERED:
                    texX += 36;
                    break;
                case HEALTH_FROZEN:
                case ABSORBING_FROZEN:
                    texX += 54;
                    break;
            }

            int texY = 0;
            // draw colored heart
            RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, this.getColor(), 255);

            // draw white dot
            texY += 9;
            RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

            // add shading
            texY += 9;
            RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);
        }
    }
}
