package drukmakor;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class Pipe extends Element {

	ActiveElement end1;//nem lehet null!
	ActiveElement end2;//mindig ez a NULL ha van null!
	boolean isOccupied = false;
	
	boolean isPierced = false;
	@Override boolean fix() {
		boolean prevIsBroken = isPierced;
		isPierced = false;
		return prevIsBroken;
	}
	@Override boolean piercePipe() {
		boolean prevIsBroken = isPierced;
		isPierced = true;
		if (hasWater) {
			PointCounter.get().addSaboteurPoint();
			hasWater = false;
		}
		return !prevIsBroken;
	}
	
	
	public Pipe(ActiveElement ae1, ActiveElement ae2) throws IOException {    // mi történik ha teli ciszernához veszünk fel új csövet??? Exception amit a ciszternának el kell kapni
		boolean res1 = ae1.connectPipe(this);
		if (!res1) {
			throw new IOException("Failed to connect to Pump!");
		}
		if (ae2 != null) {
			boolean res2 = ae2.connectPipe(this);
			if (!res2) {
				boolean res3 = ae1.disconnectPipe(this);//konzisztens állapotba visszaállítani a varázslatot
				assert(res3);
				throw new IOException("Failed to connect to Pump!");
			}
		}
		b = new Button(getCoords(), this);
		Main.d.drl.add(this);
	}

	boolean hasConnectionTo(ActiveElement ae) {//null esetén azt adja vissza, hogy egyik vége legalább null-e
		return end1 == ae || end2 == ae;
	}
	ActiveElement getOtherEnd(ActiveElement ae) {//vissazadja az egyik végét, amelyik nem ae
		assert(end1 != end2);//ezeket ott kéne ellenőrizni ahol változnak
		if (end1 != ae)
			return end1;
		return end2;
	}
	boolean disconnectFrom(ActiveElement ae) {// ha rajta állnak akkor ne engedje a felvételt
		if (isOccupied)
			return false;
		assert(end1 != end2);
		if (ae != end1 && ae != end2)
			throw new RuntimeException("A cső egyik vége sem ae!");
		if (ae == end1)
			end1 = end2;
		end2 = null;
		return true;
	}
	void connectTo(ActiveElement ae) {//ctorból hívva még mindkettő null lesz!
		if (end2 != null)
			throw new RuntimeException("Mindkét végén foglalt csőhöz történő csatlakoztatás!");
		end2 = ae;
		if (end1 == null) {
			end1 = end2;
			end2 = null;
		}
		if (b != null)//ctorból
			b.c=getCoords();
	}
	
	@Override boolean acceptCharacter(Element from) {
		if (isOccupied || end2 == null)//ha fel van véve az egyik fele, akkor lehessen-e rálépni? perpillanat ha rajta áll valaki, akkor nem lehet felvenni, tehát szimmetriai okokból ne lehessen rálépni
			return false;
		if (from != end1 && from != end2 && from != null)
			return false;
		isOccupied = true;
		return true;
	}
	@Override void characterExited() {
		isOccupied = false;
	}
	boolean addWater() {
		if (hasWater)
			return false;
		if (isPierced) {
			PointCounter.get().addSaboteurPoint();
			return true;
		}
		hasWater = true;
		return true;
	}
	boolean drainWater() {
		if (!hasWater)
			return false;
		hasWater = false;
		return true;
	}

	@Override Pump placePump() {
		assert(end1!=null&&end2!=null);
		boolean previsocc = isOccupied;//azért kell, mert occupied csövet nem lehet felszedni
		isOccupied = false;
		Pump p = new Pump(getCoords());
		ActiveElement prevend1 = end1;
		boolean res1 = end1.disconnectPipe(this);
		assert(res1);
		Pipe pi = null;
		try {
			pi = new Pipe(prevend1, p);
		} catch (IOException e) {
			assert(false);
		}
		boolean res2 = p.connectPipe(this);
		assert(res2);
		b.c=getCoords();
		isOccupied = previsocc;
		return p;
	}

	
	
	
	
	
	public void drawfromplayer(Coords playerc, Graphics g) {
		assert(end2==null);
		g.setColor(new Color(isPierced? 200 : 50 , end2==null?200:0, isOccupied?255:0));
		Coords e1 = end1.getCoords();
		Coords e2 = playerc;
		Coords c = new Coords((e1.x+e2.x)/2, (e1.y+e2.y)/2);
		g.drawLine(e1.x, e1.y, e2.x, e2.y);
		b.c=c;
		b.draw(g);
		if (!hasWater)
			return;
		g.setColor(new Color(100, 100, 245));
		g.fillOval(c.x-2, c.y+6, 5, 12);
	}

	@Override public void draw(Graphics g) {
		if (end2 == null)
			return;
		g.setColor(new Color(isPierced? 200 : 50 , end2==null?200:0, isOccupied?255:0));
		Coords e1 = end1.getCoords();
		Coords e2 = end2.getCoords();
		g.drawLine(e1.x, e1.y, e2.x, e2.y);
		super.draw(g);
	}
	
	@Override
	Coords getCoords() {
		Coords e1 = end1.getCoords();
		Coords masik = e1.copy();
		masik.y-=20;
		Coords e2 = end2==null?masik:end2.getCoords();//TODO mi használná még ezt, pl vízcsepp kirajzolás? idk
		return new Coords((e1.x+e2.x)/2, (e1.y+e2.y)/2);
	}
}
