package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.NPCComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.scene2d.restaurant.RecipesRegistry;
import ru.compot.pomsrest.utils.PathfinderUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;

import java.util.Iterator;

// система нпс
public class NPCSystem extends IteratingSystem {

    public static final long SPAWN_RATE = 20000L;
    public static final long EAT_TIME = 3000L;

    private final GraphImpl graph;
    private final boolean[] tableCellsAccess;

    private TransformComponent interactTransform;
    private FoodComponent interactFood;

    public NPCSystem(GraphImpl graph, boolean[] tableCellsAccess) {
        super(Family.all(TransformComponent.class, TransformAnimationComponent.class, NPCComponent.class).get());
        this.graph = graph;
        this.tableCellsAccess = tableCellsAccess;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NPCComponent npc = Mappers.NPC_MAPPER.get(entity);
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        if (npc.startTimeEat > -1L && System.currentTimeMillis() - npc.startTimeEat >= EAT_TIME) {
            texture.visible = false;
            npc.spawned = false;
            npc.lastTimeSpawn = System.currentTimeMillis();
            npc.startTimeEat = -1L;
            return;
        }
        if (interactFood != null) {
            if (interactTransform.x >= transform.x && interactTransform.y >= transform.y && interactTransform.x <= transform.x + transform.width && interactTransform.y <= transform.y + transform.height) {
                if (npc.food == interactFood.recipe) {
                    tableCellsAccess[interactFood.position] = false;
                    ImmutableArray<Entity> foods = getEngine().getEntitiesFor(Family.all(FoodComponent.class).get());
                    for (int i = 0; i < foods.size(); i++) {
                        Entity f = foods.get(i);
                        if (Mappers.FOOD_MAPPER.get(foods.get(i)).position == interactFood.position)
                            getEngine().removeEntity(f);
                    }
                    npc.startTimeEat = System.currentTimeMillis();
                    getEngine().removeEntity(Mappers.CLOUD_MAPPER.get(npc.cloud).foodEntity);
                    getEngine().removeEntity(npc.cloud);
                }
                interactTransform.x = interactFood.positionX;
                interactTransform.y = interactFood.positionY;
                interactTransform = null;
                interactFood = null;
            }
        }
        if (System.currentTimeMillis() - npc.lastTimeSpawn <= SPAWN_RATE) {
            texture.visible = false;
            return;
        }
        if (npc.spawned) return;
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(entity);
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(entity);
        texture.visible = npc.spawned = true;
        npc.food = RecipesRegistry.RECIPES[(int) Math.round((RecipesRegistry.RECIPES.length - 1) * Math.random())];
        EntityFactory.createCloudEntity(getEngine(), entity);
        Iterator<GraphNode> nodes = PathfinderUtils.findPath(0f, 40f, npc.targetX, npc.targetY, graph);
        if (nodes != null && nodes.hasNext()) {
            textureAnimation.animate(AnimationIDs.NPC_RUNNING, true);
            transform.x = 0f;
            transform.y = 40f;
        }
        PathfinderUtils.animatePath(nodes, transform, transformAnimation, null, () -> {
            Mappers.TEXTURE_MAPPER.get(npc.cloud).visible = true;
            Mappers.TEXTURE_MAPPER.get(Mappers.CLOUD_MAPPER.get(npc.cloud).foodEntity).visible = true;
            transform.x += 22f + npc.xSitOffset;
            transform.y += 30f + npc.ySitOffset;
            textureAnimation.reset();
            transformAnimation.stopAnimations(TransformAnimationType.POSITION);
        });
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (interactFood != null) {
            interactTransform.x = interactFood.positionX;
            interactTransform.y = interactFood.positionY;
            interactTransform = null;
            interactFood = null;
        }
    }

    public void setInteractFoodEntity(TransformComponent interactTransform, FoodComponent interactFood) {
        this.interactTransform = interactTransform;
        this.interactFood = interactFood;
    }
}
