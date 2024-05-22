package terrails.colorfulhearts.api.heart;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hearts {

    public static HeartDrawing CONTAINER;

    public static List<HeartDrawing> COLORED_HEALTH_HEARTS;
    public static List<HeartDrawing> COLORED_ABSORPTION_HEARTS;
    public static Map<ResourceLocation, StatusEffectHeart> STATUS_EFFECT_HEARTS = new HashMap<>();

    public static Optional<StatusEffectHeart> getEffectHeartForPlayer(Player player) {
        return STATUS_EFFECT_HEARTS.values().stream().filter(heart -> heart.shouldDraw(player)).findFirst();
    }
}
