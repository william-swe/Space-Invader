package invaders.memento;

public class GameEngineCareTaker {
    private GameEngineMemento gameEngineMemento;

    /**
     * Gets the gameEngineMemento.
     * @return the gameEngineMemento.
     */
    public GameEngineMemento getMemento() {
        return gameEngineMemento;
    }

    /**
     * Sets the gameEngineMemento.
     * @param gameEngineMemento: New gameEngineMemento.
     */
    public void setMemento(GameEngineMemento gameEngineMemento) {
        this.gameEngineMemento = gameEngineMemento;
    }
}
