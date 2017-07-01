/**
 * 
 */
package math;

/**
 * @author Docter60
 *
 */
public class Vector2 {

	public static final Vector2 zero = new Vector2(0, 0);
	
	protected double x;
	protected double y;
	
	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2 v){
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public double length(){
		return Math.sqrt(x * x + y * y);
	}
	
	public void normalize(){
		double length = length();
		this.x /= length;
		this.y /= length;
	}
	
	public double dot(Vector2 v){
		return this.x * v.getX() + this.y * v.getY();
	}
	
	public void add(Vector2 v){
		this.x += v.getX();
		this.y += v.getY();
	}
	
	public void subtract(Vector2 v){
		this.x -= v.getX();
		this.y -= v.getY();
	}
	
	public void scale(double c){
		this.x *= c;
		this.y *= c;
	}

	public double getX() { return x; }

	public void setX(double x) { this.x = x; }

	public double getY() { return y; }

	public void setY(double y) { this.y = y; }
	
	public static Vector2 normalized(Vector2 v){
		double length = v.length();
		double x = 0;
		double y = 0;
		if(length > 0){
			x = v.x / length;
			y = v.y / length;
		}
		return new Vector2(x, y);
	}
	
	public static Vector2 lerp(Vector2 u, Vector2 v, double t){
		double x = Mathg.lerp(u.getX(), v.getX(), t);
		double y = Mathg.lerp(u.getY(), v.getY(), t);
		
		return new Vector2(x, y);
	}
	
	public static Vector2 floored(Vector2 v) {
		int x = (int) Math.floor(v.getX());
		int y = (int) Math.floor(v.getY());
		
		return new Vector2(x,y);
	}
	
	public static Vector2 scaled(Vector2 v, double scale) { 
		Vector2 copy = new Vector2(v.getX(), v.getY());
		copy.scale(scale);
		return copy;
	}
	
	@Override
	public String toString() {
		return "<" + this.getX() + ", " + this.getY() + ">";
	}
	
}
