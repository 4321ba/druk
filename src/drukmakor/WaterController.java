package drukmakor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaterController {
	boolean pushed = false;
	List<ActiveElement> vertices = new ArrayList<ActiveElement>();
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
	    if (rand.nextBoolean()&&rand.nextBoolean()) {
	    	ActiveElement randomElement = vertices.get(rand.nextInt(vertices.size()));
	    	randomElement.randomEvent();
	    }
	}
}
