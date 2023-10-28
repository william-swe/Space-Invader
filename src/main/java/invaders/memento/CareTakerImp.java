package invaders.memento;

public class CareTakerImp implements CareTaker{
    private Memento memento;

    /**
     * Gets the memento.
     * @return the memento.
     */
    @Override
    public Memento getMemento() {
        return memento;
    }

    /**
     * Sets the memento.
     * @param memento: New memento.
     */
    @Override
    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
