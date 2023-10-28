package invaders.observer;

import invaders.engine.GameEngine;
import invaders.factory.EnemyProjectile;
import invaders.factory.PlayerProjectile;
import invaders.factory.Projectile;
import invaders.gameobject.Enemy;
import invaders.memento.ClockMemento;
import invaders.memento.Memento;
import invaders.memento.ScoreMemento;
import invaders.rendering.Renderable;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.NormalProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Score extends Label implements Observer, Originator {
    private Subject subject;
    private int score;

    /**
     * Score constructor.
     * @param subject: The subject that the score registers to.
     * @param x: The x position of the score.
     * @param y: The y position of the score.
     */
    public Score(Subject subject, int x, int y) {
        this.subject = subject;
        this.score = 0;
        subject.registerObserver(this);
        setLayoutX(x);
        setLayoutY(y);
        setText(String.valueOf(score));
        setFont(new Font("Arial", 16));
        setTextFill(Paint.valueOf("WHITE"));
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        score = 0;
        setText(String.valueOf(score));
    }

    /**
     * Updates the current score, based on the types of objects that the player hits.
     */
    @Override
    public void update() {
        // Update score based on each hit
        // slow projectile: 1, fast projectile: 2, slow alien: 3, fast alien: 4
        if (this.subject instanceof GameEngine) {
            // Loop through each player's projectile
            for (Projectile pp:
                    ((GameEngine) this.subject).getPlayer().getPlayerProjectile()) {
                // Find an enemy's projectile or an enemy that got hit by the player
                for (Renderable ro: ((GameEngine) this.subject).getRenderables()) {
                    if (ro instanceof EnemyProjectile ep &&
                            pp.isColliding(ro)) {
                        if (ep.getStrategy() instanceof SlowProjectileStrategy) {
                            // slow enemy projectile
                            score += 1;
                        } else if (ep.getStrategy() instanceof FastProjectileStrategy) {
                            // fast enemy projectile
                            score += 2;
                        }
                        setText(String.valueOf(score));
                    } else if (ro instanceof Enemy e &&
                    pp.isColliding(e)) {
                        if (e.getProjectileStrategy() instanceof SlowProjectileStrategy) {
                            // slow enemy
                            score += 3;
                        } else if (e.getProjectileStrategy() instanceof FastProjectileStrategy) {
                            // fast enemy
                            score += 4;
                        }
                        setText(String.valueOf(score));
                    }
                }
            }
        }
    }

    /**
     * Returns the current score.
     * @return the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the current score.
     * @param score: New score.
     */
    public void setScore(int score) {
        this.score = score;
        setText(String.valueOf(this.score));
    }

    /**
     * Saves the current state.
     * @return a memento that stores the current state.
     */
    @Override
    public Memento saveState() {
        return new ScoreMemento(getScore());
    }

    /**
     * Restores the last saved state.
     * @param memento: The memento that saves the last state.
     */
    @Override
    public void undo(Memento memento) {
        ScoreMemento scoreMemento = (ScoreMemento) memento;
        setScore(scoreMemento.getScore());
    }
}
