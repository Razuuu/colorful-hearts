package terrails.colorfulhearts.api.heart;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.SpriteHeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hearts {

    public static final HeartDrawing CONTAINER = SpriteHeartDrawing.build(ResourceLocation.fromNamespaceAndPath("colorfulhearts", "container")).finish(
            ResourceLocation.withDefaultNamespace("hud/heart/container"), ResourceLocation.withDefaultNamespace("hud/heart/container_blinking"),
            ResourceLocation.fromNamespaceAndPath("colorfulhearts", "heart/container_half"), ResourceLocation.fromNamespaceAndPath("colorfulhearts", "heart/container_half_blinking"),
            ResourceLocation.withDefaultNamespace("hud/heart/container_hardcore"), ResourceLocation.withDefaultNamespace("hud/heart/container_hardcore_blinking"),
            ResourceLocation.fromNamespaceAndPath("colorfulhearts", "heart/container_hardcore_half"), ResourceLocation.fromNamespaceAndPath("colorfulhearts", "heart/container_hardcore_half_blinking")
    );

    public static List<HeartDrawing> COLORED_HEALTH_HEARTS;
    public static List<HeartDrawing> COLORED_ABSORPTION_HEARTS;
    public static Map<ResourceLocation, OverlayHeart> OVERLAY_HEARTS = new HashMap<>();

    public static Optional<OverlayHeart> getOverlayHeartForPlayer(Player player) {
        return OVERLAY_HEARTS.values().stream().filter(heart -> heart.shouldDraw(player)).findFirst();
    }
}
