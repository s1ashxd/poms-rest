package ru.compot.pomsrest.ashley.utils.factory;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityBuilder {

    private final Engine engine;
    private final List<Component> components = new ArrayList<>();

    public EntityBuilder(Engine engine) {
        this.engine = engine;
        this.components.add(new TransformComponent());
    }

    public static EntityBuilder create(Engine engine) {
        return new EntityBuilder(engine);
    }

    public EntityBuilder setPosition(float x, float y) {
        TransformComponent tc = createComponent(TransformComponent.class);
        tc.x = x;
        tc.y = y;
        return this;
    }

    public EntityBuilder setSize(float width, float height) {
        TransformComponent bc = createComponent(TransformComponent.class);
        bc.width = width;
        bc.height = height;
        return this;
    }

    public EntityBuilder setTextureRegion(TextureRegion region) {
        createComponent(TextureComponent.class).region = region;
        return this;
    }

    public EntityBuilder setTexture(Texture texture) {
        return setTextureRegion(new TextureRegion(texture));
    }

    public EntityBuilder setZIndex(int zIndex) {
        createComponent(TextureComponent.class).zIndex = zIndex;
        return this;
    }

    public EntityBuilder setTextureSize(float width, float height) {
        TextureComponent tc = createComponent(TextureComponent.class);
        tc.width = width;
        tc.height = height;
        return this;
    }

    public <T extends Component> EntityBuilder addComponent(Class<T> clazz) {
        createComponent(clazz);
        return this;
    }

    public <T extends Component> EntityBuilder addComponent(Class<T> clazz, Consumer<T> applier) {
        T component = createComponent(clazz);
        applier.accept(component);
        return this;
    }

    public Entity build() {
        Entity entity = engine.createEntity();
        components.forEach(entity::add);
        engine.addEntity(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T createComponent(Class<T> clazz) {
        for (Component c : components) {
            if (c.getClass() == clazz) return (T) c;
        }
        T result = engine.createComponent(clazz);
        components.add(result);
        return result;
    }
}