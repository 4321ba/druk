package drukmakor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaterController {
	boolean pushed = false;
	private List<ActiveElement> vertices = new ArrayList<ActiveElement>();
	void tick() {
		if (!pushed)
			for (ActiveElement ae : vertices)
				ae.pushWater();
		else
			for (ActiveElement ae : vertices)
				ae.pullWater();
		pushed = !pushed;
		
		//https://www.baeldung.com/java-random-list-element
	    Random rand = new Random();
	    if (rand.nextBoolean()&&rand.nextBoolean()&&rand.nextBoolean()&&rand.nextBoolean()) {
	    	ActiveElement randomElement = vertices.get(rand.nextInt(vertices.size()));
	    	randomElement.randomEvent();
	    }
	}
	void add(ActiveElement ae) {
		vertices.add(ae);
	}
	
	private static WaterController theWC = new WaterController();
	public static WaterController get() {
		return theWC;
	}
}
