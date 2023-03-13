package drukmakor;

public class Coords {
	public int x;
	public int y;
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Coords copy() {
		return new Coords(x,y);
	}
}
