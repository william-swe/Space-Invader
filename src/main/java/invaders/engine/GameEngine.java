package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.ConfigReader;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.builder.EnemyBuilder;
import invaders.factory.EnemyProjectile;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.entities.Player;
import invaders.memento.*;
import invaders.observer.Clock;
import invaders.observer.Observer;
import invaders.observer.Score;
import invaders.observer.Subject;
import invaders.rendering.Renderable;
import invaders.strategy.FastProjectileStrategy;
import invaders.strategy.SlowProjectileStrategy;
import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements Subject, Cloneable {
	private List<GameObject> gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();
	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();
	private List<Renderable> renderables = new ArrayList<>();
	private Player player;
	private boolean left;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45;
	private List<Observer> observers = new ArrayList<>();
	private ClockCareTaker clockCareTaker = new ClockCareTaker();
	private ScoreCareTaker scoreCareTaker = new ScoreCareTaker();

	public GameEngine(String config){
		load(config);
	}

	/**
	 * Copy Constructor.
	 * @param otherEngine: Engine to be copied.
	 */
	public GameEngine(GameEngine otherEngine) {
		// Copy player
		this.player = otherEngine.getPlayer().clone();
		this.renderables.add(this.player);
		this.gameObjects.add(this.player);

		// Copy configurations
		this.left = otherEngine.isLeft();
		this.right = otherEngine.isRight();
		this.gameWidth = otherEngine.getGameWidth();
		this.gameHeight = otherEngine.getGameHeight();
		this.timer = otherEngine.getTimer();

		// Copy player's projectile
		for (Projectile p: this.player.getPlayerProjectile()) {
			this.renderables.add(p);
			this.gameObjects.add(p);
		}

		// Copy enemy, enemy's projectiles and bunkers
		for (Renderable ro: otherEngine.getRenderables()) {
			if (ro instanceof Enemy) {
				Enemy enemy = ((Enemy) ro).clone();
				this.renderables.add(enemy);
				this.gameObjects.add(enemy);
				for (Projectile ep: enemy.getEnemyProjectile()) {
					this.renderables.add(ep);
					this.gameObjects.add(ep);
				}
			} else if (ro instanceof Bunker) {
				Bunker bunker = ((Bunker) ro).clone();
				this.renderables.add(bunker);
				this.gameObjects.add(bunker);
			}
		}

		// Copy observers
		this.observers = otherEngine.getObservers();

		Clock clock = otherEngine.getClock();
		ClockMemento clockMemento = new ClockMemento(clock.getMinutes(), clock.getSeconds(),
		clock.getFrame(), clock.isTicking());
		this.clockCareTaker.setMemento(clockMemento);

		Score score = otherEngine.getScore();
		ScoreMemento scoreMemento = new ScoreMemento(score.getScore());
		this.scoreCareTaker.setMemento(scoreMemento);
	}

	/**
	 * Load objects for the game.
	 */
	public void load(String config) {
		// Read the config here
		ConfigReader.parse(config);

		// Get game width and height
		gameWidth = ((Long)((JSONObject)
				ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject)
				ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		//Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		gameObjects.add(player);
		renderables.add(player);

		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		//Get Bunkers info
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}

		EnemyBuilder enemyBuilder = new EnemyBuilder();
		//Get Enemy info
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = director.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			gameObjects.add(enemy);
			renderables.add(enemy);
		}

		if (this.observers == null) {
			this.observers = new ArrayList<>();
		}
	}

	/**
	 * Change difficulty level
	 */
	public void reload(String config) {
		// Remove old objects
		for (Renderable object: renderables) {
			while(object.isAlive()) {
				object.takeDamage(1);
			}
		}
		pendingToAddGameObject.clear();
		pendingToAddRenderable.clear();
		pendingToRemoveGameObject.clear();
		pendingToRemoveRenderable.clear();

		// Reset clock and score
		for (Observer observer: observers) {
			if (observer instanceof Clock) {
				((Clock) observer).reset();
			}
			if (observer instanceof Score) {
				((Score) observer).reset();
			}
		}

		// Load new objects
		load(config);
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		timer+=1;

		movePlayer();

		for(GameObject go: gameObjects){
			go.update(this);
		}

		notifyObservers();

		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						||(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))){
				}else{
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
					}
				}
			}
		}


		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}

	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}

	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}

	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();
			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Check if the game is over.
	 * Game is over if either the player is destroyed or all enemies are destroyed.
	 * @return true if game is over. Otherwise, false.
	 */
	public boolean isGameOver() {
		boolean isAllAliensAreDestroyed = true;
		for (Renderable ro: renderables) {
			if (ro.getRenderableObjectName().equals("Enemy") && ro.isAlive()) {
				isAllAliensAreDestroyed = false;
				break;
			}
		}
		return !player.isAlive() || isAllAliensAreDestroyed;
	}

	/**
	 * Register an observer.
	 * @param o: Observer to register.
	 */
	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	/**
	 * Notify all observers.
	 */
	@Override
	public void notifyObservers() {
		for (Observer o: observers) {
			o.update();
		}
	}

	/**
	 * Remove an observer.
	 * @param o: Observer to remove.
	 */
	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);
	}

	/**
	 * Save the current state of the game.
	 * @return a memento object that contains saved data.
	 */
	public GameEngineMemento saveState() {
		System.out.println("Save successfully!");
		return new GameEngineMemento(this.clone());
	}

	/**
	 * Undo the last shot.
	 * @param gameEngineMemento: A memento object that contains saved data of the last state.
	 */
	public void undo(GameEngineMemento gameEngineMemento) {
		if (gameEngineMemento != null) {
			// Remove old objects
			for (Renderable ro: renderables) {
				while (ro.isAlive()) {
					ro.takeDamage(1);
				}
			}

			GameEngine gameEngine = gameEngineMemento.getData();
			// Add old objects
			this.renderables.addAll(gameEngine.getRenderables());
			this.gameObjects.addAll(gameEngine.getGameObjects());

			// Set up player and configurations
			this.player = gameEngine.getPlayer();
			this.gameWidth = gameEngine.getGameWidth();
			this.gameHeight = gameEngine.getGameHeight();
			this.right = false;
			this.left = false;
			this.observers = gameEngine.getObservers();
			this.timer = gameEngine.getTimer();

			// Set up observers
			Clock clock = this.getClock();
			ClockMemento clockMemento = gameEngine.getClockCareTaker().getMemento();
			clock.setMinutes(clockMemento.getMinutes());
			clock.setSeconds(clockMemento.getSeconds());
			clock.setFrame(clockMemento.getFrame());
			clock.setTicking(clockMemento.isTicking());

			Score score = this.getScore();
			ScoreMemento scoreMemento = gameEngine.getScoreCareTaker().getMemento();
			score.setScore(scoreMemento.getScore());
			System.out.println("Undo successfully!");
		} else {
			System.out.println("There's nothing to undo!");
		}
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public int getTimer() {
		return timer;
	}

	public List<Observer> getObservers() {
		return observers;
	}

	/**
	 * Get clock observer.
	 * @return the clock observer.
	 */
	public Clock getClock() {
		for (Observer o: observers) {
			if (o instanceof Clock clock) {
				return clock;
			}
		}
		return null;
	}

	/**
	 * Get score observer.
	 * @return the score observer.
	 */
	public Score getScore() {
		for (Observer o: observers) {
			if (o instanceof Score score) {
				return score;
			}
		}
		return null;
	}

	/**
	 * Get a clone of GameEngine.
	 * @return a deep copy of GameEngine.
	 */
	@Override
	public GameEngine clone() {
		return new GameEngine(this);
	}

	public ClockCareTaker getClockCareTaker() {
		return clockCareTaker;
	}

	public ScoreCareTaker getScoreCareTaker() {
		return scoreCareTaker;
	}

	/**
	 * Remove all slow projectiles.
	 */
	public void removeAllSlowProjectiles() {
		Score score = this.getScore();
		for (Renderable ro: renderables) {
			if (ro instanceof EnemyProjectile ep &&
					ep.getStrategy() instanceof SlowProjectileStrategy &&
			ep.isAlive()) {
				while (ep.isAlive()) {
					ep.takeDamage(1);
				}
				score.setScore(score.getScore() + 1);
			}
		}
	}

	/**
	 * Remove all fast projectiles.
	 */
	public void removeAllFastProjectiles() {
		Score score = this.getScore();
		for (Renderable ro: renderables) {
			if (ro instanceof EnemyProjectile ep &&
					ep.getStrategy() instanceof FastProjectileStrategy &&
					ep.isAlive()) {
				while (ep.isAlive()) {
					ep.takeDamage(1);
				}
				score.setScore(score.getScore() + 2);
			}
		}
	}

	/**
	 * Remove all slow aliens.
	 */
	public void removeAllSlowAliens() {
		Score score = this.getScore();
		for (Renderable ro: renderables) {
			if (ro instanceof Enemy enemy &&
					enemy.getProjectileStrategy() instanceof SlowProjectileStrategy &&
					enemy.isAlive()) {
				while (enemy.isAlive()) {
					enemy.takeDamage(1);
				}
				score.setScore(score.getScore() + 3);
			}
		}
	}

	/**
	 * Remove all fast aliens.
	 */
	public void removeAllFastAliens() {
		Score score = this.getScore();
		for (Renderable ro: renderables) {
			if (ro instanceof Enemy enemy &&
					enemy.getProjectileStrategy() instanceof FastProjectileStrategy &&
					enemy.isAlive()) {
				while (enemy.isAlive()) {
					enemy.takeDamage(1);
				}
				score.setScore(score.getScore() + 4);
			}
		}
	}
}
