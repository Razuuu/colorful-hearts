package terrails.colorfulhearts.config;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.LoaderExpectPlatform;
import terrails.colorfulhearts.api.event.HeartRegistry;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.SpriteHeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ConfigUtils {

    public static void loadColoredHearts() {
        List<HeartDrawing> hearts = new ArrayList<>();

        if (Configuration.HEALTH.vanillaHearts.get()) {
            hearts.add(SpriteHeartDrawing.build(CColorfulHearts.location("health_vanilla")).finish(
                    ResourceLocation.withDefaultNamespace("hud/heart/full"), ResourceLocation.withDefaultNamespace("hud/heart/full_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/half"), ResourceLocation.withDefaultNamespace("hud/heart/half_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/hardcore_full"), ResourceLocation.withDefaultNamespace("hud/heart/hardcore_full_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/hardcore_half"), ResourceLocation.withDefaultNamespace("hud/heart/hardcore_half_blinking")
            ));
        }

        Iterator<Integer> colors = Configuration.HEALTH.colors.get().stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            hearts.add(SpriteHeartDrawing.build(CColorfulHearts.location("health_" + color)).finish(
                    CColorfulHearts.location("heart/health/full_" + color), CColorfulHearts.location("heart/health/full_blinking_" + color),
                    CColorfulHearts.location("heart/health/half_" + color), CColorfulHearts.location("heart/health/half_blinking_" + color),
                    CColorfulHearts.location("heart/health/hardcore_full_" + color), CColorfulHearts.location("heart/health/hardcore_full_blinking_" + color),
                    CColorfulHearts.location("heart/health/hardcore_half_" + color), CColorfulHearts.location("heart/health/hardcore_half_blinking_" + color)
            ));
        }

        Hearts.COLORED_HEALTH_HEARTS = List.copyOf(hearts);
        hearts = new ArrayList<>();

        if (Configuration.ABSORPTION.vanillaHearts.get()) {
            hearts.add(SpriteHeartDrawing.build(CColorfulHearts.location("absorbing_vanilla")).finish(
                    ResourceLocation.withDefaultNamespace("hud/heart/absorbing_full"), ResourceLocation.withDefaultNamespace("hud/heart/absorbing_full_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/absorbing_half"), ResourceLocation.withDefaultNamespace("hud/heart/absorbing_half_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/absorbing_hardcore_full"), ResourceLocation.withDefaultNamespace("hud/heart/absorbing_hardcore_full_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/absorbing_hardcore_half"), ResourceLocation.withDefaultNamespace("hud/heart/absorbing_hardcore_half_blinking")
            ));
        }

        colors = Configuration.ABSORPTION.colors.get().stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            hearts.add(SpriteHeartDrawing.build(CColorfulHearts.location("absorbing_" + color)).finish(
                    CColorfulHearts.location("heart/absorbing/full_" + color), CColorfulHearts.location("heart/absorbing/full_blinking_" + color),
                    CColorfulHearts.location("heart/absorbing/half_" + color), CColorfulHearts.location("heart/absorbing/half_blinking_" + color),
                    CColorfulHearts.location("heart/absorbing/hardcore_full_" + color), CColorfulHearts.location("heart/absorbing/hardcore_full_blinking_" + color),
                    CColorfulHearts.location("heart/absorbing/hardcore_half_" + color), CColorfulHearts.location("heart/absorbing/hardcore_half_blinking_" + color)
            ));
        }

        Hearts.COLORED_ABSORPTION_HEARTS = List.copyOf(hearts);
    }

    public static void loadStatusEffectHearts() {
        HeartRegistry registry = new HeartRegistry();
        registry.registerOverlayHeart(buildEffectHearts(Configuration.HEALTH.poisonedColors.get(), Configuration.ABSORPTION.poisonedColors.get(), "poisoned", MobEffects.POISON, ResourceLocation.withDefaultNamespace("poison")));
        registry.registerOverlayHeart(buildEffectHearts(Configuration.HEALTH.witheredColors.get(), Configuration.ABSORPTION.witheredColors.get(), "withered", MobEffects.WITHER, ResourceLocation.withDefaultNamespace("wither")));
        registry.registerOverlayHeart(buildEffectHearts(Configuration.HEALTH.frozenColors.get(), Configuration.ABSORPTION.frozenColors.get(), "frozen", Player::isFullyFrozen, ResourceLocation.withDefaultNamespace("frozen")));
        LoaderExpectPlatform.heartRegistryEvent(registry);
    }

    private static OverlayHeart buildEffectHearts(List<String> healthColors, List<String> absorptionColors, String effectName, Holder<MobEffect> effect, ResourceLocation id) {
        return buildEffectHearts(healthColors, absorptionColors, effectName, player -> player.hasEffect(effect), id);
    }

    private static OverlayHeart buildEffectHearts(List<String> healthColors, List<String> absorptionColors, String effectName, Predicate<Player> condition, ResourceLocation id) {
        List<HeartDrawing> drawings = new ArrayList<>();

        Iterator<Integer> colors = healthColors.stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            drawings.add(SpriteHeartDrawing.build(CColorfulHearts.location(effectName + "_" + color)).finish(
                    CColorfulHearts.location("heart/health/" + effectName + "/full_" + color), CColorfulHearts.location("heart/health/" + effectName + "/full_blinking_" + color),
                    CColorfulHearts.location("heart/health/" + effectName + "/half_" + color), CColorfulHearts.location("heart/health/" + effectName + "/half_blinking_" + color),
                    CColorfulHearts.location("heart/health/" + effectName + "/hardcore_full_" + color), CColorfulHearts.location("heart/health/" + effectName + "/hardcore_full_blinking_" + color),
                    CColorfulHearts.location("heart/health/" + effectName + "/hardcore_half_" + color), CColorfulHearts.location("heart/health/" + effectName + "/hardcore_half_blinking_" + color)
            ));
        }

        if (drawings.size() == 1) {
            drawings.add(0, SpriteHeartDrawing.build(CColorfulHearts.location(effectName + "_vanilla")).finish(
                    ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_full"), ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_full_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_half"), ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_half_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_hardcore_full"), ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_hardcore_full_blinking"),
                    ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_hardcore_half"), ResourceLocation.withDefaultNamespace("hud/heart/" + effectName + "_hardcore_half_blinking")
            ));
        }

        colors = absorptionColors.stream().map(s -> Integer.decode(s) & 0xFFFFFF).iterator();
        while (colors.hasNext()) {
            Integer color = colors.next();
            drawings.add(SpriteHeartDrawing.build(CColorfulHearts.location(effectName + "_absorbing_" + color)).finish(
                    CColorfulHearts.location("heart/absorbing/" + effectName + "/full_" + color), CColorfulHearts.location("heart/absorbing/" + effectName + "/full_blinking_" + color),
                    CColorfulHearts.location("heart/absorbing/" + effectName + "/half_" + color), CColorfulHearts.location("heart/absorbing/" + effectName + "/half_blinking_" + color),
                    CColorfulHearts.location("heart/absorbing/" + effectName + "/hardcore_full_" + color), CColorfulHearts.location("heart/absorbing/" + effectName + "/hardcore_full_blinking_" + color),
                    CColorfulHearts.location("heart/absorbing/" + effectName + "/hardcore_half_" + color), CColorfulHearts.location("heart/absorbing/" + effectName + "/hardcore_half_blinking_" + color)
            ));
        }

        return OverlayHeart.build(id, condition).addHealth(drawings.get(0), drawings.get(1)).addAbsorption(drawings.get(2), drawings.get(3)).finish();
    }
}
