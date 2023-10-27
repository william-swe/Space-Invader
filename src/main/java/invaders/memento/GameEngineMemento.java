package invaders.memento;

import invaders.engine.GameEngine;
import invaders.entities.Player;
import invaders.gameobject.GameObject;
import invaders.observer.Observer;
import invaders.rendering.Renderable;

import java.util.List;

public class GameEngineMemento {
    private List<Renderable> renderables;
    private List<GameObject> gameObjects;
    private Player player;
    private int gameWidth;
    private int gameHeight;
    private List<Observer> observers;
    private int timer;

    public GameEngineMemento(List<Renderable> renderables, List<GameObject> gameObjects,
                             Player player, int gameWidth, int gameHeight,
                             List<Observer> observers, int timer) {
        this.renderables = renderables;
        this.gameObjects = gameObjects;
        this.player = player;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.observers = observers;
        this.timer = timer;
    }

    public List<Renderable> getRenderables() {
        return renderables;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public int getTimer() {
        return timer;
    }
}
