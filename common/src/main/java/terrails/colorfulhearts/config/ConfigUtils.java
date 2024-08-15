package terrails.colorfulhearts.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.render.RenderUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ConfigUtils {

    public static void loadColoredHearts() {
        List<HeartDrawing> hearts = new ArrayList<>();

        if (Configuration.HEALTH.vanillaHearts.get()) {
            hearts.add(new HeartDrawing() {

                private static final ResourceLocation ID = new ResourceLocation("vanilla");

                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                    int texX = Gui.HeartType.NORMAL.getX(half, highlight);
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, hardcore ? 45 : 0);
                }

                @Override
                public ResourceLocation getId() {
                    return ID;
                }
            });
        }

        Iterator<Integer> colors = Configuration.HEALTH.colors.get().stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            ResourceLocation id = new ResourceLocation(CColorfulHearts.MOD_ID, "health_" + color);
            hearts.add(new HeartDrawing() {

                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.HEALTH_ICONS_LOCATION);
                    RenderSystem.enableBlend();

                    int texX = 0;
                    if (half) {
                        texX += 9;
                    }

                    int texY = hardcore ? 36 : 0;

                    // draw colored heart
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, color, 255);

                    // draw white dot or hardcore overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add shading or withered overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add highlight
                    if (highlight) {
                        texY -= 18;
                        RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 127);
                    }

                    RenderSystem.disableBlend();
                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                }

                @Override
                public ResourceLocation getId() {
                    return id;
                }
            });
        }

        Hearts.COLORED_HEALTH_HEARTS = List.copyOf(hearts);
        hearts = new ArrayList<>();

        if (Configuration.ABSORPTION.vanillaHearts.get()) {
            hearts.add(new HeartDrawing() {

                private static final ResourceLocation ID = new ResourceLocation("absorbing");

                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                    int texX = Gui.HeartType.ABSORBING.getX(half, highlight);
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, hardcore ? 45 : 0);
                }

                @Override
                public ResourceLocation getId() {
                    return ID;
                }
            });
        }

        colors = Configuration.ABSORPTION.colors.get().stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            final ResourceLocation id = new ResourceLocation(CColorfulHearts.MOD_ID, "absorbing_" + color);
            hearts.add(new HeartDrawing() {

                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.ABSORPTION_ICONS_LOCATION);
                    RenderSystem.enableBlend();

                    int texX = 0;
                    if (half) {
                        texX += 9;
                    }

                    int texY = hardcore ? 36 : 0;

                    // draw colored heart
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, color, 255);

                    // draw white dot or hardcore overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add shading or withered overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add highlight
                    if (highlight) {
                        texY -= 18;
                        RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 127);
                    }

                    RenderSystem.disableBlend();
                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                }

                @Override
                public ResourceLocation getId() {
                    return id;
                }
            });
        }

        Hearts.COLORED_ABSORPTION_HEARTS = List.copyOf(hearts);
    }

    public static void loadStatusEffectHearts() {
        HeartRegistry registry = new HeartRegistry();
        registry.registerOverlayHeart(buildEffectHeart(Configuration.HEALTH.poisonedColors.get(), Configuration.ABSORPTION.poisonedColors.get(), Gui.HeartType.POISIONED));
        registry.registerOverlayHeart(buildEffectHeart(Configuration.HEALTH.witheredColors.get(), Configuration.ABSORPTION.witheredColors.get(), Gui.HeartType.WITHERED));
        registry.registerOverlayHeart(buildEffectHeart(Configuration.HEALTH.frozenColors.get(), Configuration.ABSORPTION.frozenColors.get(), Gui.HeartType.FROZEN));
        CColorfulHearts.PROXY.heartRegistryEvent(registry);
    }

    private static OverlayHeart buildEffectHeart(List<String> healthColors, List<String> absorptionColors, Gui.HeartType type) {
        List<HeartDrawing> drawings = new ArrayList<>();

        String effectName;
        Predicate<Player> predicate;
        int textureX;
        switch (type) {
            case POISIONED -> {
                effectName = "poison";
                predicate = player -> player.hasEffect(MobEffects.POISON);
                textureX = 18;
            }
            case WITHERED -> {
                effectName = "wither";
                predicate = player -> player.hasEffect(MobEffects.WITHER);
                textureX = 36;
            }
            case FROZEN -> {
                effectName = "frozen";
                predicate = Player::isFullyFrozen;
                textureX = 54;
            }
            default -> throw new IllegalArgumentException("Tried to build a StatusEffectHeart for invalid type: " + type);
        }

        Iterator<Integer> colors = healthColors.stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            final ResourceLocation id = new ResourceLocation(CColorfulHearts.MOD_ID, effectName + "_" + color);
            drawings.add(new HeartDrawing() {
                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.HEALTH_ICONS_LOCATION);

                    int texX = textureX;
                    if (half) {
                        texX += 9;
                    }

                    int texY = hardcore ? 36 : 0;

                    // draw colored heart
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, color, 255);

                    // draw white dot or hardcore overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add shading or withered overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add highlight
                    if (highlight) {
                        texY -= 18;
                        RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 127);
                    }

                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                }

                @Override
                public ResourceLocation getId() {
                    return id;
                }
            });
        }

        if (drawings.size() == 1) {
            final ResourceLocation id = new ResourceLocation(CColorfulHearts.MOD_ID, effectName + "_vanilla");
            drawings.add(0, new HeartDrawing() {
                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                    int texX = type.getX(half, highlight);
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, hardcore ? 45 : 0);
                }

                @Override
                public ResourceLocation getId() {
                    return id;
                }
            });
        }

        colors = absorptionColors.stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            final ResourceLocation id = new ResourceLocation(CColorfulHearts.MOD_ID, effectName + "_absorbing_" + color);
            drawings.add(new HeartDrawing() {
                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.setShaderTexture(0, CColorfulHearts.ABSORPTION_ICONS_LOCATION);

                    int texX = textureX;
                    if (half) {
                        texX += 9;
                    }

                    int texY = hardcore ? 36 : 0;

                    // draw colored heart
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, color, 255);

                    // draw white dot or hardcore overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add shading or withered overlay
                    texY += 9;
                    RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 255);

                    // add highlight
                    if (highlight) {
                        texY -= 18;
                        RenderUtils.drawTexture(guiGraphics.pose(), x, y, texX, texY, 127);
                    }

                    RenderSystem.setShaderTexture(0, CColorfulHearts.GUI_ICONS_LOCATION);
                }

                @Override
                public ResourceLocation getId() {
                    return id;
                }
            });
        }
        return OverlayHeart.build(new ResourceLocation(effectName), predicate).addHealth(drawings.get(0), drawings.get(1)).addAbsorption(drawings.get(2), drawings.get(3)).finish();
    }
}
