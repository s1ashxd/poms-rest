package ru.compot.pomsrest.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.compot.pomsrest.ashley.components.FoodComponent;
import ru.compot.pomsrest.ashley.components.PlayerComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;

public class FoodSystem extends IteratingSystem {

    private final Entity player;
    private final NPCSystem npcSystem;

    private final boolean[] places = new boolean[5];

    public FoodSystem(Entity player, NPCSystem npcSystem) {
        super(Family.all(TransformComponent.class, FoodComponent.class).get());
        this.player = player;
        this.npcSystem = npcSystem;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        FoodComponent fc = Mappers.FOOD_MAPPER.get(entity);
        if (!fc.dragging) return;
        PlayerComponent playerData = Mappers.PLAYER_MAPPER.get(player);
        TransformComponent foodTransform = Mappers.TRANSFORM_MAPPER.get(entity);
        if (playerData.dragging) {
            foodTransform.x = playerData.mousePoint.x;
            foodTransform.y = playerData.mousePoint.y;
        } else {
            npcSystem.setInteractData(playerData.mousePoint.x, playerData.mousePoint.y, fc);
            fc.dragging = false;
        }
    }

    public boolean[] getPlaces() {
        return places;
    }
}
