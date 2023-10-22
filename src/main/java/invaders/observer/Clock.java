package invaders.observer;

import invaders.engine.GameEngine;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Clock extends Label implements Observer {
    private Subject subject;
    private int minutes;
    private int seconds;
    private int frame;
    private boolean ticking;

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

    public void reset() {
        minutes = 0;
        seconds = 0;
        frame = 0;
        ticking = true;
        setText("0:00");
    }

    @Override
    public void update() {
        if (subject instanceof GameEngine && ((GameEngine) subject).isGameOver()) {
            ticking = false;
        } else {
            tick();
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

}
