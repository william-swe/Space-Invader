package invaders.physics;

/**
 * A utility class for storing position information
 */
public class Vector2D implements Cloneable{

	private double x;
	private double y;

	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D otherVector2D) {
		this.x = otherVector2D.getX();
		this.y = otherVector2D.getY();
	}

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public void setX(double x){
		this.x = x;
	}

	public void setY(double y){
		this.y = y;
	}

	@Override
	public Vector2D clone() {
		return new Vector2D(this);
	}
}
