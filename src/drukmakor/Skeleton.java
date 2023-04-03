package drukmakor;

public class Skeleton {
	public static void main(String[] args) {
		System.out.println("Welcome to Drukmákor! Type in the number of your favourite sequence diagram!");
		while(true) {
			int i = Pr.inInt("5.3.? (0: exit)");
			try {
				if (i == 0) break;
				else if (i == 1) seqs_5_3_1();
				else if (i == 2) seqs_5_3_2();
				else if (i == 3) seqs_5_3_3();
				else if (i == 4) seqs_5_3_4();
			} catch (Exception e) {
				System.out.println("The sequence ceased to exist. They passed away. " + e);
				Pr.resetIdent();
			}
		}
	}
	
	private static Mechanic m;
	private static Saboteur s;
	private static Pump p1, p2;
	private static Source s1;
	private static Cistern c1;
	private static Pipe pi1, pi2, pi3;
	
	
	private static void seqs_5_3_1() {
		int i = Pr.inInt("5.3.1.?");
		if (i == 1) seq_5_3_1_1();
		else if (i == 2) seq_5_3_1_2();
		else if (i == 3) seq_5_3_1_3();
		else if (i == 4) seq_5_3_1_4();
		else if (i == 5) seq_5_3_1_5();
	}
	private static void seqs_5_3_2() {
		int i = Pr.inInt("5.3.2.?");
		if (i == 1) seq_5_3_2_1();
		else if (i == 2) seq_5_3_2_2();
		else if (i == 3) seq_5_3_2_3();
		else if (i == 4) seq_5_3_2_4();
	}
	private static void seqs_5_3_3() {
		int i = Pr.inInt("5.3.3.?");
		if (i == 1) seq_5_3_3_1();
		else if (i == 2) seq_5_3_3_2();
		else if (i == 3) seq_5_3_3_3();
		else if (i == 4) seq_5_3_3_4();
		else if (i == 5) seq_5_3_3_5();
		else if (i == 6) seq_5_3_3_6();
		else if (i == 7) seq_5_3_3_7();
		else if (i == 8) seq_5_3_3_8();
		else if (i == 9) seq_5_3_3_9();
		else if (i == 10) seq_5_3_3_10();
	}
	private static void seqs_5_3_4() {
		int i = Pr.inInt("5.3.4.?");
		if (i >= 1 && i <= 4) seq_5_3_4_1();
		else if (i >= 5 && i <= 7) seq_5_3_4_5();
		else if (i >= 8 && i <= 9) seq_5_3_4_8();
	}
	
	
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
		m.pickUpPump();
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
	private static void seq_5_3_1_2() {
		Pr.fv("Skeleton", "seq_5_3_1_2");
		ini_5_4_4();
		m.fix();
		Pr.ret();
	}
	private static void seq_5_3_1_3() {
		Pr.fv("Skeleton", "seq_5_3_1_3");
		ini_5_4_2();
		m.fix();
		Pr.ret();
	}
	private static void seq_5_3_1_4() {
		Pr.fv("Skeleton", "seq_5_3_1_4");
		ini_5_4_6();
		s.piercePipe();
		Pr.ret();
	}
	private static void seq_5_3_1_5() {
		Pr.fv("Skeleton", "seq_5_3_1_5");
		ini_5_4_5();
		s.piercePipe();
		Pr.ret();
	}
	
	
	private static void seq_5_3_2_1() {
		Pr.fv("Skeleton", "seq_5_3_2_1");
		ini_5_4_1();
		m.alterPump(Pr.inInt("inPipeIdx"), Pr.inInt("outPipeIdx"));
		Pr.ret();
	}
	private static void seq_5_3_2_2() {
		Pr.fv("Skeleton", "seq_5_3_2_2");
		ini_5_4_5();
		s.alterPump(Pr.inInt("inPipeIdx"), Pr.inInt("outPipeIdx"));
		Pr.ret();
	}
	private static void seq_5_3_2_3() {
		Pr.fv("Skeleton", "seq_5_3_2_3");
		ini_5_4_1();
		m.moveTo(pi3);
		Pr.ret();
	}
	private static void seq_5_3_2_4() {
		Pr.fv("Skeleton", "seq_5_3_2_4");
		ini_5_4_6();
		s.moveTo(pi1);
		Pr.ret();
	}
	
	
	private static void seq_5_3_3_1() {
		Pr.fv("Skeleton", "seq_5_3_3_1");
		ini_5_4_4();
		m.pickUpPump();
		Pr.ret();
	}
	private static void seq_5_3_3_2() {
		Pr.fv("Skeleton", "seq_5_3_3_2");
		ini_5_4_2();
		m.pickUpPump();
		Pr.ret();
	}
	private static void seq_5_3_3_3() {
		Pr.fv("Skeleton", "seq_5_3_3_3");
		ini_5_4_3();
		m.placePump();
		Pr.ret();
	}
	private static void seq_5_3_3_4() {
		Pr.fv("Skeleton", "seq_5_3_3_4");
		ini_5_4_1();
		m.placePump();
		Pr.ret();
	}
	private static void seq_5_3_3_5() {
		Pr.fv("Skeleton", "seq_5_3_3_5");
		ini_5_4_3();
		m.connectPipe(Pr.inInt("index"));
		Pr.ret();
	}
	private static void seq_5_3_3_6() {
		Pr.fv("Skeleton", "seq_5_3_3_6");
		ini_5_4_1();
		m.connectPipe(Pr.inInt("index"));
		Pr.ret();
	}
	private static void seq_5_3_3_7() {
		Pr.fv("Skeleton", "seq_5_3_3_7");
		ini_5_4_4();
		m.disconnectPipe(Pr.inInt("index"));
		Pr.ret();
	}
	private static void seq_5_3_3_8() {
		Pr.fv("Skeleton", "seq_5_3_3_8");
		ini_5_4_2();
		m.disconnectPipe(Pr.inInt("index"));
		Pr.ret();
	}
	private static void seq_5_3_3_9() {
		Pr.fv("Skeleton", "seq_5_3_3_9");
		ini_5_4_4();
		m.pickUpDanglingPipe(Pr.inInt("index"));
		Pr.ret();
	}
	private static void seq_5_3_3_10() {
		Pr.fv("Skeleton", "seq_5_3_3_10");
		ini_5_4_2();
		m.pickUpDanglingPipe(Pr.inInt("index"));
		Pr.ret();
	}
	
	
	private static void seq_5_3_4_1() {
		Pr.fv("Skeleton", "seq_5_3_4_1");
		ini_5_4_2();
		c1.tick();
		Pr.ret();
	}
	private static void seq_5_3_4_5() {
		Pr.fv("Skeleton", "seq_5_3_4_5");
		ini_5_4_1();
		p1.tick();
		Pr.ret();
	}
	private static void seq_5_3_4_8() {
		Pr.fv("Skeleton", "seq_5_3_4_8");
		ini_5_4_5();
		s1.tick();
		Pr.ret();
	}
	
}
