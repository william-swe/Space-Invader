package invaders.memento;

public class GameEngineCareTaker {
    private GameEngineMemento gameEngineMemento;

    public GameEngineMemento getMemento() {
        return gameEngineMemento;
    }

    public void setMemento(GameEngineMemento gameEngineMemento) {
        this.gameEngineMemento = gameEngineMemento;
    }
}
