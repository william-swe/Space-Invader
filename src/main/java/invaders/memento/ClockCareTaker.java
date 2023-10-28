package invaders.memento;

public class ClockCareTaker {
    private ClockMemento memento;

    /**
     * Gets the clockMemento.
     * @return the clockMemento.
     */
    public ClockMemento getMemento() {
        return memento;
    }

    /**
     * Sets the clockMemento.
     * @param memento: New clockMemento.
     */
    public void setMemento(ClockMemento memento) {
        this.memento = memento;
    }
}
