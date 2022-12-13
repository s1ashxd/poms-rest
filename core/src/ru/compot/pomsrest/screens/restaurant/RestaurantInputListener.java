package ru.compot.pomsrest.screens.restaurant;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.scene2d.restaurant.recipebook.RecipeBookActor;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.PathfinderUtils;
import ru.compot.pomsrest.utils.constants.AnimationIDs;

import java.util.Iterator;

// слушатель нажатий в ресторане
public class RestaurantInputListener extends InputListener {

    private final Entity player;
    private final GraphImpl graph;
    private final Engine engine;

    public RestaurantInputListener(Entity player, GraphImpl graph, Engine engine) {
        this.player = player;
        this.graph = graph;
        this.engine = engine;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        if (playerData.moveBlocked) return false;
        RestaurantScreen screen = (RestaurantScreen) playerData.screen;
        Animated2DCamera camera = playerData.camera;
        RecipeBookActor recipeBook = screen.getRecipeBookActor();
        if (recipeBook.isOpened()) return false;

        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(player);
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(player);
        playerData.interact(x, y);

        float onCameraX = x - (camera.position.x - GameCore.CAMERA_WIDTH);
        if (onCameraX <= 20f || onCameraX >= GameCore.SCREEN_WIDTH - 20f) {
            float cameraX = x;
            float minCameraX = camera.viewportWidth / 2f;
            float maxCameraX = screen.getRestaurantWidth() - camera.viewportWidth / 2f;
            if (x < minCameraX) cameraX = minCameraX;
            else if (x > maxCameraX) cameraX = maxCameraX;
            camera.animate(cameraX, camera.position.y);
        }
        Iterator<GraphNode> nodes = PathfinderUtils.findPath(transform.x, transform.y, x, y, graph);
        if (nodes == null || !nodes.hasNext()) return true;
        TransformComponent playerTransform = Mappers.TRANSFORM_MAPPER.get(player);
        TextureAnimationComponent playerTextureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(player);
        PathfinderUtils.animatePath(
                nodes,
                transform,
                transformAnimation,
                node -> {
                    if (playerTransform.x == node.getX() && playerTransform.y == node.getY()) return;
                    float xDiff = playerTransform.x - node.getX();
                    float yDiff = playerTransform.y - node.getY();
                    double angle = Math.toDegrees(Math.atan(yDiff / xDiff));
                    System.out.println(angle);
                    if (angle > 45 || angle < -45) {
                        playerTextureAnimation.animate(playerTransform.y < node.getY() ? AnimationIDs.PLAYER_FORWARD_RUNNING : AnimationIDs.PLAYER_BACK_RUNNING, true);
                    } else playerTextureAnimation.animate(playerTransform.x < node.getX() ? AnimationIDs.PLAYER_RIGHT_RUNNING : AnimationIDs.PLAYER_LEFT_RUNNING, true);
                },
                playerTextureAnimation::reset);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        playerData.dragging = false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        playerData.mousePoint.set(x, y);
        playerData.dragging = true;
        FoodComponent foodToDrag = null;
        ImmutableArray<Entity> food = engine.getEntitiesFor(Family.all(FoodComponent.class).get());
        for (int i = 0; i < food.size(); i++) {
            Entity f = food.get(i);
            FoodComponent fc = Mappers.FOOD_MAPPER.get(f);
            if (fc.dragging) return;
            TransformComponent ftc = Mappers.TRANSFORM_MAPPER.get(f);
            if (ftc.x <= x && ftc.y <= y && ftc.x + ftc.width >= x && ftc.y + ftc.height >= y)
                foodToDrag = fc;
        }
        if (foodToDrag == null) return;
        foodToDrag.dragging = true;
    }
}
