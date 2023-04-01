package drukmakor;

import java.util.ArrayList;
import java.util.List;

public class WaterController {
	boolean pushed = false;
	private List<ActiveElement> vertices = new ArrayList<ActiveElement>();
	void tick() {
		for (ActiveElement ae : vertices)
			ae.tick(pushed);
		pushed = !pushed;
	}
	void add(ActiveElement ae) {
		vertices.add(ae);
	}
	
	private static WaterController theWC = new WaterController();
	public static WaterController get() {
		return theWC;
	}
}
