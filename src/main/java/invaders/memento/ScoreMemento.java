package invaders.memento;

import invaders.engine.GameEngine;
import invaders.observer.Score;

public class ScoreMemento {
    private int score;

    public ScoreMemento(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
