package invaders.factory;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

public class EnemyProjectile extends Projectile{
    private ProjectileStrategy strategy;

    public EnemyProjectile(Vector2D position, ProjectileStrategy strategy, Image image) {
        super(position,image);
        this.strategy = strategy;
    }

    public EnemyProjectile(EnemyProjectile otherProjectile) {
        super(otherProjectile.getPosition().clone(),
                new Image(otherProjectile.getImage().getUrl(),
                        otherProjectile.getImage().getWidth(),
                        otherProjectile.getImage().getHeight(), true, true));
        this.strategy = otherProjectile.strategy;
    }

    @Override
    public void update(GameEngine model) {
        strategy.update(this);

        if(this.getPosition().getY()>= model.getGameHeight() - this.getImage().getHeight()){
            this.takeDamage(1);
        }

    }
    @Override
    public String getRenderableObjectName() {
        return "EnemyProjectile";
    }

    public ProjectileStrategy getStrategy() {
        return strategy;
    }

    @Override
    public EnemyProjectile clone() {
        return new EnemyProjectile(this);
    }
}
