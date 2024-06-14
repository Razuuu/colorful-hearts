package terrails.colorfulhearts.api.heart;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hearts {

    public static HeartDrawing CONTAINER;

    public static List<HeartDrawing> COLORED_HEALTH_HEARTS;
    public static List<HeartDrawing> COLORED_ABSORPTION_HEARTS;
    public static Map<ResourceLocation, OverlayHeart> OVERLAY_HEARTS = new HashMap<>();

    public static Optional<OverlayHeart> getOverlayHeartForPlayer(Player player) {
        return OVERLAY_HEARTS.values().stream().filter(heart -> heart.shouldDraw(player)).findFirst();
    }
}
