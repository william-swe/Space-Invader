package invaders.memento;

public class ScoreCareTaker {
    private ScoreMemento memento;

    /**
     * Gets scoreMemento.
     * @return scoreMemento.
     */
    public ScoreMemento getMemento() {
        return memento;
    }

    /**
     * Sets scoreMemento.
     * @param memento: New scoreMemento.
     */
    public void setMemento(ScoreMemento memento) {
        this.memento = memento;
    }
}
