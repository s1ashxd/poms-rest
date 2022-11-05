package ru.compot.pomsrest.screens.world;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.ashley.systems.InteractSystem;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.screens.GameScreen;

public class WorldScreen extends GameScreen {

    public static final float WORLD_WIDTH = 3332f, GROUND_HEIGHT = 62f;

    public WorldScreen(float playerX) {
        EntityFactory.createWorldGround(engine);
        EntityFactory.createInteractArea(
                engine,
                200f, GROUND_HEIGHT,
                160f, 200f,
                InteractType.WORLD_ENTER_AREA
        );
        Entity player = EntityFactory.createPlayerEntity(engine, playerX, GROUND_HEIGHT);
        engine.addSystem(new InteractSystem(player));
        stage.addListener(new WorldInputListener(player));
    }
}
