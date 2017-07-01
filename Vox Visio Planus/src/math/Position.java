package math;

public class Position extends Vector2{

	public Position() {
		super(0, 0);
	}
	
	public Position(double x, double y) {
		super(x, y);
	}
	
	public Position(Position p){
		super(p);
	}
	
	@Override
	public String toString() {
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
}
