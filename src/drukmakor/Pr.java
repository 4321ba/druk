package drukmakor;
/**
 * PRinteli a kért dolgot
 * 
 */
public class Pr {
	private static boolean muted = false;
	public static void setMuted(boolean b) {
		muted = b;
	}
	private static void println(String s) {
		if (!muted)
			System.out.println(s);
	}
	private static void print(String s) {
		if (!muted)
			System.out.print(s);
	}
	private static int ident = 0;
	private static void prident(boolean arrowRight) {
		for (int i = 0; i < ident; ++i)
			print("     |");
		if (arrowRight)
			print("--->\\ ");
		else
			print("<---/ ");
	}
	private static String strip(Object o) {
		if (o == null)
			return "null";
		String s = o.toString();
		String projectName = new Pr().toString().split("\\.")[0];
		if (s.indexOf('.') != -1 && s.split("\\.")[0].equals(projectName)) {
			s = s.split("\\.")[1];
			return s.substring(0, s.length() - 5);
		}
		return s;
	}
	public static void fv(Object ths, String name) {
		prident(true);
		println(strip(ths) + "." + name + "()");
		ident++;
	}
	public static void fv(Object ths, String name, Object p) {
		prident(true);
		println(strip(ths) + "." + name + "(" + strip(p) + ")");
		ident++;
	}
	public static void fv(Object ths, String name, Object p1, Object p2) {
		prident(true);
		println(strip(ths) + "." + name + "(" + strip(p1) + ", " + strip(p2) + ")");
		ident++;
	}
	public static void ret() {
		ident--;
		prident(false);
		println("return");
	}
	public static boolean ret(boolean val) {
		ident--;
		prident(false);
		println("return " + strip(val));
		return val;
	}
	public static Pipe ret(Pipe val) {
		ident--;
		prident(false);
		println("return " + strip(val));
		return val;
	}
	public static Pump ret(Pump val) {
		ident--;
		prident(false);
		println("return " + strip(val));
		return val;
	}
}
