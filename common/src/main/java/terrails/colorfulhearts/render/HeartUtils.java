package terrails.colorfulhearts.render;

import net.minecraft.util.Mth;
import terrails.colorfulhearts.api.heart.drawing.Heart;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;

import java.util.List;

public class HeartUtils {

    public static Heart[] calculateHearts(List<HeartDrawing> healthDrawings, List<HeartDrawing> absorptionDrawings, int health, int maxHealth, int absorbing) {
        final int topHealth = health > 20 ? health % 20 : 0;
        final int bottomHealthRow = Math.max(0, Mth.floor(health / 20.0f) - 1);
        final int healthColorIndex = bottomHealthRow % healthDrawings.size();
        healthDrawings = List.of(
                healthDrawings.get((healthColorIndex + 1) % healthDrawings.size()),
                healthDrawings.get(healthColorIndex)
        );

        // usually there are only 10 absorption hearts, but there is a special case when there are more (when there are less than 10 health hearts)
        final int maxAbsorbing = maxHealth >= 19 ? 20 : 40 - maxHealth - (maxHealth % 2);

        final int topAbsorbing = absorbing > maxAbsorbing ? absorbing % maxAbsorbing : 0;
        final int bottomAbsorptionRow = Math.max(0, Mth.floor(absorbing / (float) maxAbsorbing) - 1);
        final int absorptionColorIndex = bottomAbsorptionRow % absorptionDrawings.size();
        absorptionDrawings = List.of(
                absorptionDrawings.get((absorptionColorIndex + 1) % absorptionDrawings.size()),
                absorptionDrawings.get(absorptionColorIndex)
        );

        maxHealth = Math.min(20, maxHealth);
        absorbing = Math.min(maxAbsorbing, absorbing);

        // offset added to index in for loop below to render absorbing hearts at correct positions
        // needed in case where absorbing hearts are rendered in same row as health (when there are less than 10 health hearts)
        final int absorbingOffset = Math.min(10, Mth.ceil(maxHealth / 2.0));

        final int maxHealthHearts = Mth.ceil(maxHealth / 2.0);
        final int maxAbsorbingHearts = Mth.ceil(maxAbsorbing / 2.0);

        final Heart[] hearts = new Heart[20];
        for (int i = 0; i < Math.max(maxHealthHearts, maxAbsorbingHearts); i++) {
            int value = i * 2;

            if (value < topHealth) {
                boolean half = value + 1 == topHealth;

                if (half) {
                    hearts[i] = Heart.full(healthDrawings.get(0), Heart.full(healthDrawings.get(1)));
                } else {
                    hearts[i] = Heart.full(healthDrawings.get(0));
                }
            } else if (value < health) {
                boolean halfBackground = value + 1 == maxHealth;
                boolean half = value + 1 == health;

                if (halfBackground) {
                    hearts[i] = Heart.half(healthDrawings.get(1));
                } else if (half) {
                    hearts[i] = Heart.full(healthDrawings.get(1), Heart.CONTAINER_FULL);
                } else {
                    hearts[i] = Heart.full(healthDrawings.get(1));
                }
            } else if (value < maxHealth) {
                boolean halfBackground = value + 1 == maxHealth;
                hearts[i] = halfBackground ? Heart.CONTAINER_HALF : Heart.CONTAINER_FULL;
            }

            if (value < topAbsorbing) {
                boolean half = value + 1 == topAbsorbing;

                if (half) {
                    hearts[i + absorbingOffset] = Heart.full(absorptionDrawings.get(0), Heart.full(absorptionDrawings.get(1)));
                } else {
                    hearts[i + absorbingOffset] = Heart.full(absorptionDrawings.get(0));
                }
            } else if (value < absorbing) {
                boolean half = value + 1 == absorbing;

                if (half) {
                    hearts[i + absorbingOffset] = Heart.half(absorptionDrawings.get(1));
                } else {
                    hearts[i + absorbingOffset] = Heart.full(absorptionDrawings.get(1));
                }
            }
        }
        return hearts;
    }
}
