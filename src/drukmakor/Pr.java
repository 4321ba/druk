package drukmakor;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * PRinteli a kért dolgot
 * 
 */
public class Pr {
	private static boolean muted = true;
	public static void setMuted(boolean b) {
		muted = b;
	}
	public static void resetIdent() {
		ident = 0;
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
	private static void prident(char arrowMode) {
		for (int i = 0; i < ident; ++i)
			print("     |");
		if (arrowMode == '>')
			print("--->\\ ");
		else if (arrowMode == '<')
			print("<---/ ");
		else if (arrowMode == ' ')
			print(" ");
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
		prident('>');
		println(strip(ths) + "." + name + "()");
		ident++;
	}
	public static void fv(Object ths, String name, Object p) {
		prident('>');
		println(strip(ths) + "." + name + "(" + strip(p) + ")");
		ident++;
	}
	public static void fv(Object ths, String name, Object p1, Object p2) {
		prident('>');
		println(strip(ths) + "." + name + "(" + strip(p1) + ", " + strip(p2) + ")");
		ident++;
	}
	public static void ret() {
		ident--;
		prident('<');
		println("return");
	}
	public static boolean ret(boolean val) {
		ident--;
		prident('<');
		println("return " + strip(val));
		return val;
	}
	public static Pipe ret(Pipe val) {
		ident--;
		prident('<');
		println("return " + strip(val));
		return val;
	}
	public static Pump ret(Pump val) {
		ident--;
		prident('<');
		println("return " + strip(val));
		return val;
	}
	public static boolean inBool(String s) {
		while (true) {
			prident(' ');
			System.out.println("Bool value needed (" + s + "): false");
			try {
				return false;
			} catch (InputMismatchException e) {
				prident(' ');
                System.out.println("Invalid input!");
            }
		}
	}
	public static int inInt(String s) {
		while (true) {
			prident(' ');
			System.out.println("Int value needed (" + s + "): 0");
			try {
				return 0;
			} catch (InputMismatchException e) {
				prident(' ');
                System.out.println("Invalid input!");
            }
		}
	}
}
