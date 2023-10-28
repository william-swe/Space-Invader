package invaders.engine;

import invaders.memento.CareTakerImp;
import invaders.memento.GameEngineCareTaker;
import invaders.memento.GameEngineMemento;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KeyboardInputHandler {
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Map<String, MediaPlayer> sounds = new HashMap<>();
    CareTakerImp gameEngineCareTaker;

    KeyboardInputHandler(GameEngine model) {
        this.model = model;

        // TODO (longGoneUser): Is there a better place for this code?
        URL mediaUrl = getClass().getResource("/shoot.wav");
        String jumpURL = mediaUrl.toExternalForm();

        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("shoot", mediaPlayer);

        gameEngineCareTaker = new CareTakerImp();
    }

    void handlePressed(KeyEvent keyEvent) {
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            // Save and shoot a projectile
            gameEngineCareTaker.setMemento(model.saveState());
            if (model.shootPressed()) {
                MediaPlayer shoot = sounds.get("shoot");
                shoot.stop();
                shoot.play();
            }
        }

        if (keyEvent.getCode().equals(KeyCode.R)) {
            // Undo
            model.undo((GameEngineMemento) gameEngineCareTaker.getMemento());
            gameEngineCareTaker.setMemento(null);
        }

        if (keyEvent.getCode().equals(KeyCode.A)) {
            // Remove all aliens slow projectiles
            model.removeAllSlowProjectiles();
        }

        if (keyEvent.getCode().equals(KeyCode.S)) {
            // Remove all aliens fast projectiles
            model.removeAllFastProjectiles();
        }

        if (keyEvent.getCode().equals(KeyCode.D)) {
            // Remove all slow aliens
            model.removeAllSlowAliens();
        }

        if (keyEvent.getCode().equals(KeyCode.F)) {
            // Remove all fast aliens
            model.removeAllFastAliens();
        }

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = true;
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = true;
        }

        if (keyEvent.getCode().equals(KeyCode.DIGIT1)) {
            System.out.println("Switch to LEVEL 1!");
            model.reload("src/main/resources/config_easy.json");
        }
        if (keyEvent.getCode().equals(KeyCode.DIGIT2)) {
            System.out.println("Switch to LEVEL 2!");
            model.reload("src/main/resources/config_medium.json");
        }
        if (keyEvent.getCode().equals(KeyCode.DIGIT3)) {
            System.out.println("Switch to LEVEL 3!");
            model.reload("src/main/resources/config_hard.json");
        }

        if (left) {
            model.leftPressed();
        }

        if(right){
            model.rightPressed();
        }
    }

    void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = false;
            model.leftReleased();
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.rightReleased();
            right = false;
        }
    }
}
