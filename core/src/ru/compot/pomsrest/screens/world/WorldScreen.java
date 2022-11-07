package ru.compot.pomsrest.screens.world;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.systems.InteractSystem;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.screens.GameScreen;

public class WorldScreen extends GameScreen {

    public static final float WORLD_WIDTH = 3332f;
    public static final float GROUND_HEIGHT = 62f;
    public static final float MIN_CAMERA_X = GameCore.CAMERA_WIDTH;
    public static final float MAX_CAMERA_X = WORLD_WIDTH - MIN_CAMERA_X;
    public static final float CAMERA_MOVE_OFFSET = 70f;

    public WorldScreen(float playerX) {
        EntityFactory.createWorldGround(engine);
        EntityFactory.createInteractArea(
                engine,
                200f, GROUND_HEIGHT,
                160f, 200f,
                InteractType.WORLD_ENTER_AREA
        );
        Entity player = EntityFactory.createPlayerEntity(engine, playerX, GROUND_HEIGHT, camera);
        engine.addSystem(new InteractSystem(player));
        stage.addListener(new WorldInputListener(player, camera));
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(player);
        float cameraX = playerX + transform.originX;
        if (cameraX > MIN_CAMERA_X) camera.position.x = cameraX;
    }
}
