package invaders.memento;

import invaders.engine.GameEngine;
import invaders.observer.Clock;

public class ClockMemento implements Memento{
    private int minutes;
    private int seconds;
    private int frame;
    private boolean ticking;

    /**
     * ClockMemento constructor.
     * @param minutes: Minutes to store.
     * @param seconds: Seconds to store.
     * @param frame: Frames to store.
     * @param ticking: Clock status to store.
     */
    public ClockMemento(int minutes, int seconds, int frame, boolean ticking) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.frame = frame;
        this.ticking = ticking;
    }

    /**
     * Gets the minutes.
     * @return the minutes.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Gets the seconds
     * @return the seconds.
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Gets the frames.
     * @return the frames.
     */
    public int getFrame() {
        return frame;
    }

    /**
     * Gets the clock's status.
     * @return the clock's status.
     */
    public boolean isTicking() {
        return ticking;
    }
}
