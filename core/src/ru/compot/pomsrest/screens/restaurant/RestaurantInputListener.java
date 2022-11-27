package ru.compot.pomsrest.screens.restaurant;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ai.GraphImpl;
import ru.compot.pomsrest.ai.GraphNode;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.scene2d.restaurant.recipebook.RecipeBookActor;
import ru.compot.pomsrest.utils.Animated2DCamera;
import ru.compot.pomsrest.utils.PathfinderUtils;

import java.util.Iterator;
import java.util.Map;

public class RestaurantInputListener extends InputListener {

    private final Entity player;
    private final GraphImpl graph;
    private final Entity[] food;

    public RestaurantInputListener(Entity player, GraphImpl graph, Entity[] food) {
        this.player = player;
        this.graph = graph;
        this.food = food;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        if (playerData.moveBlocked) return false;
        RestaurantScreen screen = (RestaurantScreen) playerData.screen;
        Animated2DCamera camera = playerData.camera;
        float halfViewportWidth = camera.viewportWidth / 2f;
        float halfViewportHeight = camera.viewportHeight / 2f;
        RecipeBookActor recipeBook = screen.getRecipeBookActor();
        if (recipeBook.isOpened()) {
            Rectangle rect = new Rectangle(recipeBook.getX() + camera.position.x - halfViewportWidth, recipeBook.getY() + camera.position.y - halfViewportHeight, recipeBook.getWidth(), recipeBook.getHeight());
            if (!rect.contains(x, y)) {
                if (screen.getMinigameActor() != null)
                    screen.openRecipeBook();
                else recipeBook.close();
            }
            return false;
        }

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
        if (nodes == null || !nodes.hasNext()) return false;
        PathfinderUtils.animatePath(nodes, transform, transformAnimation, null);
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
        for (Entity f : food) {
            FoodComponent fc = Mappers.FOOD_MAPPER.get(f);
            if (fc.dragging) return;
            TransformComponent ftc = Mappers.TRANSFORM_MAPPER.get(f);
            if (ftc.x >= x && ftc.y >= y && ftc.x + ftc.width <= x && ftc.y + ftc.height <= y)
                foodToDrag = fc;
        }
        if (foodToDrag == null) return;
        foodToDrag.dragging = true;
    }

    public GraphImpl getGraph() {
        return graph;
    }
}
