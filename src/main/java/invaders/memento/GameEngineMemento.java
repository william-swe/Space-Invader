package invaders.memento;

import invaders.engine.GameEngine;
import invaders.entities.Player;
import invaders.gameobject.GameObject;
import invaders.observer.Observer;
import invaders.rendering.Renderable;

import java.util.List;

public class GameEngineMemento implements Memento{
    private List<Renderable> renderables;
    private List<GameObject> gameObjects;
    private Player player;
    private int gameWidth;
    private int gameHeight;
    private List<Observer> observers;
    private int timer;

    /**
     * GameEngineMemento constructor.
     * @param renderables: List of renderables to store.
     * @param gameObjects: List of gameObjects to store.
     * @param player: A Player instance to store.
     * @param gameWidth: Game's width to store.
     * @param gameHeight: Game's height to store.
     * @param observers: List of observers to store.
     * @param timer: Timer to store.
     */
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

    /**
     * Gets a list of renderables.
     * @return a list of renderables.
     */
    public List<Renderable> getRenderables() {
        return renderables;
    }

    /**
     * Gets a list of gameObjects.
     * @return a list of gameObjects.
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Gets the stored instance of Player class.
     * @return the stored instance of Player class.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets game's width.
     * @return the game's width.
     */
    public int getGameWidth() {
        return gameWidth;
    }

    /**
     * Gets game's height.
     * @return the game's height.
     */
    public int getGameHeight() {
        return gameHeight;
    }

    /**
     * Gets a list of observers.
     * @return a list of observers.
     */
    public List<Observer> getObservers() {
        return observers;
    }

    /**
     * Gets timer.
     * @return timer.
     */
    public int getTimer() {
        return timer;
    }
}
