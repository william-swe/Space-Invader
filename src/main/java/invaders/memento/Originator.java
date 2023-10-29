package invaders.memento;

import invaders.memento.Memento;

public interface Originator {
    Memento saveState();
    void undo(Memento memento);
}
