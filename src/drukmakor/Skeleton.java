package drukmakor;

public class Skeleton {
	public static void main(String[] args) {
		seq_5_3_1_1();
		//ini_5_4_1();
		//ini_5_4_2();
		//ini_5_4_3();
		//ini_5_4_4();
	}
	private static Mechanic m;
	private static Saboteur s;
	private static Pump p1, p2;
	private static Source s1;
	private static Cistern c1;
	private static Pipe pi1, pi2, pi3;
	
	private static void ini_5_4_1() {
		Pr.fv("Skeleton", "ini_5_4_1");
		p1 = new Pump();
		s1 = new Source();
		c1 = new Cistern();
		pi1 = new Pipe(p1, s1);
		pi2 = new Pipe(p1, c1);
		pi3 = new Pipe(p1, s1);
		m = new Mechanic(c1);
		m.pickUpPump();
		m.moveTo(pi2);
		m.moveTo(p1);
		m.disconnectPipe(0);
		Pr.ret();
	}
	private static void ini_5_4_2() {
		Pr.fv("Skeleton", "ini_5_4_2");
		p1 = new Pump();
		c1 = new Cistern();
		pi1 = new Pipe(c1, null);
		pi2 = new Pipe(p1, c1);
		m = new Mechanic(c1);
		Pr.ret();
	}
	private static void ini_5_4_3() {
		Pr.fv("Skeleton", "ini_5_4_3");
		p1 = new Pump();
		c1 = new Cistern();
		pi1 = new Pipe(p1, c1);
		pi2 = new Pipe(p1, c1);
		m = new Mechanic(c1);
		m.disconnectPipe(1);
		m.moveTo(pi1);
		Pr.ret();
	}
	private static void ini_5_4_4() {
		Pr.fv("Skeleton", "ini_5_4_4");
		p1 = new Pump();
		p2 = new Pump();
		pi1 = new Pipe(p1, p2);
		m = new Mechanic(pi1);
		Pr.ret();
	}
	private static void ini_5_4_5() {
		Pr.fv("Skeleton", "ini_5_4_5");
		s1 = new Source();
		c1 = new Cistern();
		p1 = new Pump();
		pi1 = new Pipe(s1, p1);
		pi2 = new Pipe(s1, c1);
		s = new Saboteur(s1);
		Pr.ret();
	}
	private static void ini_5_4_6() {
		Pr.fv("Skeleton", "ini_5_4_6");
		c1 = new Cistern();
		p1 = new Pump();
		pi1 = new Pipe(c1, p1);
		s = new Saboteur(pi1);
		Pr.ret();
	}
	private static void seq_5_3_1_1() {
		Pr.fv("Skeleton", "seq_5_3_1_1");
		ini_5_4_1();
		m.fix();
		Pr.ret();
	}
	
	
}
