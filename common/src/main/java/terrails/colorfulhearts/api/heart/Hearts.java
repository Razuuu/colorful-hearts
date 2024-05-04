package terrails.colorfulhearts.api.heart;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.SpriteHeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hearts {

    public static final HeartDrawing CONTAINER = SpriteHeartDrawing.build(CColorfulHearts.location("container")).finish(
            new ResourceLocation("hud/heart/container"), new ResourceLocation("hud/heart/container_blinking"),
            new ResourceLocation("colorfulhearts", "heart/container_half"), new ResourceLocation("colorfulhearts", "heart/container_half_blinking"),
            new ResourceLocation("hud/heart/container_hardcore"), new ResourceLocation("hud/heart/container_hardcore_blinking"),
            new ResourceLocation("colorfulhearts", "heart/container_hardcore_half"), new ResourceLocation("colorfulhearts", "heart/container_hardcore_half_blinking")
    );

    public static List<HeartDrawing> COLORED_HEALTH_HEARTS;
    public static List<HeartDrawing> COLORED_ABSORPTION_HEARTS;
    public static Map<ResourceLocation, StatusEffectHeart> STATUS_EFFECT_HEARTS = new HashMap<>();

    public static Optional<StatusEffectHeart> getEffectHeartForPlayer(Player player) {
        return STATUS_EFFECT_HEARTS.values().stream().filter(heart -> heart.shouldDraw(player)).findFirst();
    }

}
