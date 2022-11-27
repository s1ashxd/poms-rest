package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.NPCComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.scene2d.restaurant.RecipesRegistry;

public class NPCSystem extends IteratingSystem {

    private static final long SPAWN_RATE = 15000L;
    private static final long EAT_TIME = 1000L;

    private final boolean[] rightChairs = new boolean[6];
    private final boolean[] leftChairs = new boolean[6];

    private final FoodSystem foodSystem;

    private final Vector2 interactPoint = new Vector2();
    private FoodComponent interactFood;

    public NPCSystem(FoodSystem foodSystem) {
        super(Family.all(TransformComponent.class, TransformAnimationComponent.class, NPCComponent.class).get());
        this.foodSystem = foodSystem;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NPCComponent npc = Mappers.NPC_MAPPER.get(entity);
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        if (interactFood != null) {
            if (transform.x >= interactPoint.x && transform.y >= interactPoint.y && transform.x + transform.width <= interactPoint.x && transform.y + transform.height <= interactPoint.y) {
                if (npc.food == interactFood.food) {
                    npc.food = -1;
                    ImmutableArray<Entity> entities = getEngine().getEntitiesFor(Family.all(FoodComponent.class).get());
                    getEngine().removeEntity(entities.get(interactFood.index));
                    foodSystem.getPlaces()[interactFood.index] = false;
                }
            }
        }
        if (npc.lastTimeEat > -1 && System.currentTimeMillis() - npc.lastTimeEat > EAT_TIME) {
            texture.visible = false;
            npc.lastTimeEat = -1;
            npc.lastTimeSpawn = npc.lastTimeEat = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - npc.lastTimeSpawn < SPAWN_RATE || texture.visible) return;
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(entity);
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(entity);
        transformAnimation.animate(TransformAnimationType.POSITION, 100f, 0f, 20f, transform.x, transform.y, () -> {
            npc.food = (int) Math.round(RecipesRegistry.RECIPES.length * Math.random());
            textureAnimation.reset();
            transformAnimation.stopAnimations(TransformAnimationType.POSITION);
        });
    }

    public void setInteractData(float x, float y, FoodComponent food) {
        interactPoint.set(x, y);
        interactFood = food;
    }
}
