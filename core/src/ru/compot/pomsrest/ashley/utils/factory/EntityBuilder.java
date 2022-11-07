package ru.compot.pomsrest.ashley.utils.factory;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;

import java.util.ArrayList;

public class EntityBuilder {

    private final Engine engine;
    private final ArrayList<Component> components = new ArrayList<>();

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
        bc.originX = width / 2f;
        bc.originY = height / 2f;
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

    public <T extends Component> EntityBuilder addComponent(Class<T> clazz, ComponentConfig<T> applier) {
        T component = createComponent(clazz);
        applier.apply(component);
        return this;
    }

    public Entity build() {
        Entity entity = engine.createEntity();
        for (Component c : components) {
            entity.add(c);
        }
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

    @FunctionalInterface
    public interface ComponentConfig<T extends Component> {
        void apply(T component);
    }
}
