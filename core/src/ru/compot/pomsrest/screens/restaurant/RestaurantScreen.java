package ru.compot.pomsrest.screens.restaurant;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.ashley.systems.InteractSystem;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;
import ru.compot.pomsrest.ashley.utils.factory.EntityFactory;
import ru.compot.pomsrest.screens.GameScreen;

public class RestaurantScreen extends GameScreen {

    public static final float RESTAURANT_WIDTH = 600, RESTAURANT_HEIGHT = 700;

    public RestaurantScreen() {
        EntityFactory.createRestaurantBackground(engine);
        Entity player = EntityFactory.createPlayerEntity(engine, 10f, 62f);
        EntityFactory.createInteractArea(engine, 0f, 0f, 100f, 200f, InteractType.RESTAURANT_EXIT_AREA);
        engine.addSystem(new InteractSystem(player));
        stage.addListener(new RestaurantInputListener(player));
    }

}
