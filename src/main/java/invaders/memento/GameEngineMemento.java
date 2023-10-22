package invaders.memento;

import invaders.engine.GameEngine;

public class GameEngineMemento {
    private GameEngine gameEngine;

    public GameEngineMemento(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public GameEngine getData() {
        return gameEngine;
    }

    public void setData(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
