package terrails.colorfulhearts.api.heart.drawing;

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
}
