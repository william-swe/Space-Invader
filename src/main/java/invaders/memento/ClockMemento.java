package invaders.memento;

import invaders.engine.GameEngine;
import invaders.observer.Clock;

public class ClockMemento {
    private int minutes;
    private int seconds;
    private int frame;
    private boolean ticking;

    public ClockMemento(int minutes, int seconds, int frame, boolean ticking) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.frame = frame;
        this.ticking = ticking;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getFrame() {
        return frame;
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }
}
