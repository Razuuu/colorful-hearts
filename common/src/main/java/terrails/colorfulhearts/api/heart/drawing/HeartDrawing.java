package terrails.colorfulhearts.api.heart.drawing;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public abstract class HeartDrawing {

    /**
     * should handle every single heart variant for the specific type/colour
     * @param half render half a heart
     * @param hardcore render hardcore variant of a heart
     * @param highlight render highlighted/blinking variant of a heart
     */
    public abstract void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight);

    /**
     * Used for easier differentiation while debugging
     * @return drawing's id
     */
    public abstract ResourceLocation getId();

    @Override
    public String toString() {
        return getId().toString();
    }

    public static HeartDrawing colorBlend(HeartDrawing drawing, ResourceLocation id, float r, float g, float b, float a, int sourceFactor, int destinationFactor) {
        return new HeartDrawing() {
            @Override
            public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                drawing.draw(guiGraphics, x, y, half, hardcore, highlight);
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(r, g, b, a);
                RenderSystem.blendFunc(sourceFactor, destinationFactor);
                drawing.draw(guiGraphics, x, y, half, hardcore, highlight);
                RenderSystem.disableBlend();
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            }

            @Override
            public ResourceLocation getId() {
                return id;
            }
        };
    }
}
