package invaders.observer;

import invaders.engine.GameEngine;
import invaders.memento.ClockMemento;
import invaders.memento.Memento;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Clock extends Label implements Observer, Originator {
    private Subject subject;
    private int minutes;
    private int seconds;
    private int frame;
    private boolean ticking;

    /**
     * Clock constructor.
     * @param subject: The subject that the clock registers to.
     * @param x: The x position of the clock.
     * @param y: The y position of the clock.
     */
    public Clock(Subject subject, int x, int y) {
        this.subject = subject;
        subject.registerObserver(this);
        this.minutes = 0;
        this.seconds = 0;
        this.frame = 0;
        this.ticking = true;
        setLayoutX(x);
        setLayoutY(y);
        setText("0:00");
        setFont(new Font("Arial", 16));
        setTextFill(Paint.valueOf("WHITE"));
    }

    /**
     * Updates the clock's time.
     */
    public void tick() {
        if (ticking) {
            frame++;
            if (frame == 120) {
                frame = 0;
                seconds++;
                if (seconds < 10) {
                    setText(minutes + ":" + "0" + seconds);
                } else if (seconds <= 59) {
                    setText(minutes + ":" + seconds);
                } else {
                    seconds = 0;
                    setText(++minutes + ":" + "00");
                }
            }
        }
    }

    /**
     * Resets the clock's time.
     */
    public void reset() {
        minutes = 0;
        seconds = 0;
        frame = 0;
        ticking = true;
        setText("0:00");
    }

    /**
     * Updates the clock if the game is not over. Otherwise, stops the clock.
     */
    @Override
    public void update() {
        if (subject instanceof GameEngine && ((GameEngine) subject).isGameOver()) {
            ticking = false;
        } else {
            tick();
        }
    }

    /**
     * Returns the minutes of the clock.
     * @return the minutes.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Set the minutes for the clock.
     * @param minutes: New minutes.
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Returns the seconds of the clock.
     * @return the seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Set the seconds for the clock.
     * @param seconds: New seconds.
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Returns the frames of the clock.
     * @return the frames.
     */
    public int getFrame() {
        return frame;
    }

    /**
     * Sets the frames for the clock.
     * @param frame: New frames
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * Returns true if the clock is ticking. Otherwise, false.
     * @return the status of the clock.
     */
    public boolean isTicking() {
        return ticking;
    }

    /**
     * Sets the status of the clock.
     * @param ticking: New status.
     */
    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    /**
     * Saves the current state.
     * @return a memento that stores the current state.
     */
    @Override
    public Memento saveState() {
        return new ClockMemento(getMinutes(), getSeconds(),
                getFrame(), isTicking());
    }

    /**
     * Restores the last saved state.
     * @param memento: The memento that saves the last state.
     */
    @Override
    public void undo(Memento memento) {
        ClockMemento clockMemento = (ClockMemento) memento;
        this.minutes = clockMemento.getMinutes();
        this.seconds = clockMemento.getSeconds();
        this.frame = clockMemento.getFrame();
        this.ticking = clockMemento.isTicking();
    }
}
