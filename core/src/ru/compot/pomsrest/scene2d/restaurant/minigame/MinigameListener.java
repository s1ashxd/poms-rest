package ru.compot.pomsrest.scene2d.restaurant.minigame;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MinigameListener extends ClickListener {

    private final String componentID;
    private final MinigameActor actor;

    public MinigameListener(String componentID, MinigameActor actor) {
        this.componentID = componentID;
        this.actor = actor;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        actor.getIngredients().add(componentID);
        actor.onAddIngredient();
        return true;
    }
}
