package terrails.colorfulhearts.api.heart.drawing;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.opengl.GL11;
import terrails.colorfulhearts.api.heart.Hearts;

import java.util.List;
import java.util.function.Predicate;

public class StatusEffectHeart {

    private final ResourceLocation id;
    private final Predicate<Player> condition;
    private final List<HeartDrawing> healthDrawings, absorptionDrawings;

    public StatusEffectHeart(ResourceLocation id, Predicate<Player> condition, HeartDrawing h1, HeartDrawing h2, HeartDrawing a1, HeartDrawing a2) {
        this.id = id;
        this.condition = condition;
        this.healthDrawings = List.of(h1, h2);
        this.absorptionDrawings = List.of(a1, a2);
    }

    public boolean shouldDraw(Player player) {
        return this.condition.test(player);
    }

    public List<HeartDrawing> getHealthDrawings() {
        return this.healthDrawings;
    }

    public List<HeartDrawing> getAbsorptionDrawings() {
        return this.absorptionDrawings;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "StatusEffectHeart{" +
                "id=" + id +
                ", healthDrawings=" + healthDrawings +
                ", absorptionDrawings=" + absorptionDrawings +
                '}';
    }

    public static Builder build(ResourceLocation id, Predicate<Player> condition) {
        return new Builder(id, condition);
    }

    public static class Builder {

        final ResourceLocation id;
        final Predicate<Player> condition;

        HeartDrawing healthFirst, healthSecond;

        HeartDrawing absorptionFirst, absorptionSecond;

        Builder(ResourceLocation id, Predicate<Player> condition) {
            this.id = id;
            this.condition = condition;
        }

        /**
         * Uses the provided {@link HeartDrawing} object for the first heart color and colors the same texture using the provided rgb values for the second heart color
         * Makes it easy for mods that do not want to provide their own alternate texture
         */
        public Builder addHealth(HeartDrawing drawing, float r, float g, float b) {
            return this.addHealth(drawing, r, g, b, 1.0f, GL11.GL_ONE, GL11.GL_SRC_COLOR);
        }

        public Builder addHealth(HeartDrawing drawing, float r, float g, float b, float alpha, int sourceFactor, int destinationFactor) {
            ResourceLocation secondId = drawing.getId().withSuffix("_2");
            HeartDrawing second = new HeartDrawing() {
                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(r, g, b, alpha);
                    RenderSystem.blendFunc(sourceFactor, destinationFactor);
                    drawing.draw(guiGraphics, x, y, half, hardcore, highlight);
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderSystem.disableBlend();
                }

                @Override
                public ResourceLocation getId() {
                    return secondId;
                }
            };
            this.healthFirst = drawing;
            this.healthSecond = second;
            return this;
        }

        public Builder addHealth(HeartDrawing first, HeartDrawing second) {
            this.healthFirst = first;
            this.healthSecond = second;
            return this;
        }

        public Builder addAbsorption(HeartDrawing drawing, float r, float g, float b) {
            return this.addAbsorption(drawing, r, g, b, 1.0f, GL11.GL_ONE, GL11.GL_SRC_COLOR);
        }

        public Builder addAbsorption(HeartDrawing drawing, float r, float g, float b, float alpha, int sourceFactor, int destinationFactor) {
            ResourceLocation secondId = drawing.getId().withSuffix("_2");
            HeartDrawing second = new HeartDrawing() {
                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(r, g, b, alpha);
                    RenderSystem.blendFunc(sourceFactor, destinationFactor);
                    drawing.draw(guiGraphics, x, y, half, hardcore, highlight);
                    RenderSystem.disableBlend();
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                }

                @Override
                public ResourceLocation getId() {
                    return secondId;
                }
            };
            this.absorptionFirst = drawing;
            this.absorptionSecond = second;
            return this;
        }

        public Builder addAbsorption(HeartDrawing first, HeartDrawing second) {
            this.absorptionFirst = first;
            this.absorptionSecond = second;
            return this;
        }

        public Builder blankAbsorption() {
            this.absorptionFirst = Hearts.CONTAINER;
            this.absorptionSecond = Hearts.CONTAINER;
            return this;
        }

        public StatusEffectHeart finish() {
            if (this.healthFirst == null || this.healthSecond == null) {
                throw new IllegalArgumentException("Health hearts were not defined");
            }

            if (this.absorptionFirst == null || this.absorptionSecond == null) {
                throw new IllegalArgumentException("Absorption hearts were not defined");
            }

            return new StatusEffectHeart(this.id, this.condition, this.healthFirst, this.healthSecond, this.absorptionFirst, this.absorptionSecond);
        }
    }
}
