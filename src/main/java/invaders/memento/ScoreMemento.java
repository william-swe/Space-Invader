package invaders.memento;

import invaders.engine.GameEngine;
import invaders.observer.Score;

public class ScoreMemento implements Memento{
    private int score;

    /**
     * ScoreMemento constructor.
     * @param score: Score to store.
     */
    public ScoreMemento(int score) {
        this.score = score;
    }

    /**
     * Gets the stored score.
     * @return the stored score.
     */
    public int getScore() {
        return score;
    }
}
