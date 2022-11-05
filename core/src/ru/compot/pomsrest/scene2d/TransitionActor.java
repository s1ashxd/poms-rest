package ru.compot.pomsrest.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.compot.pomsrest.Application;
import ru.compot.pomsrest.GameCore;
import ru.compot.pomsrest.screens.StageScreen;

public class TransitionActor extends Actor {

    private static final float TRANSITION_DURATION = .8f;

    private final ShapeRenderer rend = new ShapeRenderer();

    private TransitionActor() {
    }

    public static TransitionActor addTransitionToScreen(StageScreen screen, float r, float g, float b) {
        TransitionActor actor = new TransitionActor();
        actor.setColor(r, g, b, 0f);
        screen.getStage().addActor(actor);
        return actor;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        rend.setProjectionMatrix(batch.getProjectionMatrix());
        rend.begin(ShapeRenderer.ShapeType.Filled);
        rend.setColor(getColor());
        rend.rect(0f, 0f, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        rend.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void startTransition(Screen nextScreen) {
        getColor().a = 0f;
        addAction(Actions.sequence(Actions.fadeIn(TRANSITION_DURATION), Actions.run(() -> {
            if (nextScreen instanceof StageScreen)
                ((StageScreen) nextScreen).getTransition().startReversedTransition();
            Application.INSTANCE.setScreen(nextScreen);
        })));
    }

    public void startReversedTransition() {
        getColor().a = 1f;
        addAction(Actions.fadeOut(TRANSITION_DURATION));
    }
}
