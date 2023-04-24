package drukmakor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

public class Proto {
	public static void main(String[] args) {
		interpret(System.in);
	}
	
	private static boolean isExiting = false;
	
	// feldolgozza az is-ben jövő parancsokat
	private static void interpret(InputStream is) {
		Scanner sc = new Scanner(is);
		while (!isExiting && sc.hasNextLine()) {
			String[] args = sc.nextLine().split(" ");
			// üres sor esetén újat kérünk, de nem dobunk hibát
			if (args.length == 1 && args[0].equals(""))
				continue;
			// ha érvényes parancs
			if (commands.containsKey(args[0])) {
				try {
					commands.get(args[0]).accept(args);
				} catch (Exception e) {
					System.out.println("Command failed: " + e.getMessage());
				}
			// invalid parancs esetén hibát írunk
			} else {
				System.out.println("Invalid command: \"" + args[0] + "\"!");
			}
		}
		sc.close();
	}

	
	
	// muteolható kiírás
	private static boolean isMuted = false;
	private static void condPr(String s) {
		if (!isMuted)
			System.out.println(s);
	}
	
	
	
	// random
	private static double randVal = -1.0; // <0.0 esetén igazi random értéket adunk
	private static Random random = new Random();
	public static double randomNextDouble() {
		if (randVal < 0.0)
			return random.nextDouble();
		return randVal;
	}
	
	
	// az eltárolt objektumok listái
	// a nevekben az index 1-gyel el van tolva! Ami itt a 0-s index, az a me1 pl
	private static List<Mechanic> meList = new LinkedList<>();
	private static List<Saboteur> saList = new LinkedList<>();
	private static List<Pipe> piList = new LinkedList<>();
	private static List<Pump> puList = new LinkedList<>();
	private static List<Cistern> ciList = new LinkedList<>();
	private static List<Source> soList = new LinkedList<>();
	
	
	
	// új objektumot regisztrálva létrehozó függvények
	// ezeket a függvényeket a modell is használhatja, sőt, ezeket kell használnia,
	// hogy rendesen nevet is kapjanak a futás közben létrehozott objektumok
	public static Mechanic newMechanic(Element cp) {
		Mechanic me = new Mechanic(cp);
		meList.add(me);
		return me;
	}
	public static Saboteur newSaboteur(Element cp) {
		Saboteur sa = new Saboteur(cp);
		saList.add(sa);
		return sa;
	}
	public static Pipe newPipe(ActiveElement ae1, ActiveElement ae2) {
		Pipe pi = new Pipe(ae1, ae2);
		piList.add(pi);
		return pi;
	}
	public static Pump newPump() {
		Pump pu = new Pump();
		puList.add(pu);
		return pu;
	}
	public static Cistern newCistern() {
		Cistern ci = new Cistern();
		ciList.add(ci);
		return ci;
	}
	public static Source newSource() {
		Source so = new Source();
		soList.add(so);
		return so;
	}
	
	
	// objektumból csinálunk nevet
	private static String nameOf(Object obj) {
		if (meList.contains(obj))
			return "me" + (meList.indexOf(obj) + 1);
		if (saList.contains(obj))
			return "sa" + (saList.indexOf(obj) + 1);
		if (piList.contains(obj))
			return "pi" + (piList.indexOf(obj) + 1);
		if (puList.contains(obj))
			return "pu" + (puList.indexOf(obj) + 1);
		if (ciList.contains(obj))
			return "ci" + (ciList.indexOf(obj) + 1);
		if (soList.contains(obj))
			return "so" + (soList.indexOf(obj) + 1);
		throw new IllegalArgumentException("Object \"" + obj + "\" is not in the database!");
	}
	
	
	
	// névből csinálunk objektumot
	// tryParse null-t ad vissza, ha sikertelen a parszolás, de más típussá még lehet esetleg parszolni
	private static <T> T tryParse(String s, String twoLetterTypeName, List<T> listOfObjects) {
		if (s.length() < 3)
			throw new IllegalArgumentException("\"" + s + "\" is too short to be a name of an object!");
		if (!s.substring(0, 2).equals(twoLetterTypeName))
			return null;
		// innentől biztos, hogy a típus jó, szóval innentől exceptiont dobunk, mivel
		// hiba esetén biztosan rossz a formátum
		try {
			int idx = Integer.parseInt(s.substring(2)) - 1;
			return listOfObjects.get(idx);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number \"" + s.substring(2) + "\"!");
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Number \"" + s.substring(2) + "\" out of range! Valid range is from 1 to " + listOfObjects.size() + ".");
		}
	}
	// ezeket is lehetne generikusan, de valószínűleg classcastexceptionöket kéne elkapni, ami nem olyan szép
	private static Tickable parseTickable(String s) {
		Tickable t = tryParse(s, "me", meList);
		if (t == null) t = tryParse(s, "sa", saList);
		if (t == null) t = tryParse(s, "pi", piList);
		if (t == null) t = tryParse(s, "pu", puList);
		if (t == null) t = tryParse(s, "ci", ciList);
		if (t == null) t = tryParse(s, "so", soList);
		if (t == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of something that can tick!");
		return t;
	}
	private static Element parseElement(String s) {
		Element e = tryParse(s, "pi", piList);
		if (e == null) e = tryParse(s, "pu", puList);
		if (e == null) e = tryParse(s, "ci", ciList);
		if (e == null) e = tryParse(s, "so", soList);
		if (e == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of an Element!");
		return e;
	}
	private static ActiveElement parseActiveElement(String s) {
		ActiveElement ae = tryParse(s, "pu", puList);
		if (ae == null) ae = tryParse(s, "ci", ciList);
		if (ae == null) ae = tryParse(s, "so", soList);
		if (ae == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of an ActiveElement!");
		return ae;
	}
	private static Character parseCharacter(String s) {
		Character c = tryParse(s, "me", meList);
		if (c == null) c = tryParse(s, "sa", saList);
		if (c == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Character!");
		return c;
	}
	private static Mechanic parseMechanic(String s) {
		Mechanic me = tryParse(s, "me", meList);
		if (me == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Mechanic!");
		return me;
	}
	private static Saboteur parseSaboteur(String s) {
		Saboteur sa = tryParse(s, "sa", saList);
		if (sa == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Saboteur!");
		return sa;
	}
	private static Pipe parsePipe(String s) {
		Pipe pi = tryParse(s, "pi", piList);
		if (pi == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Pipe!");
		return pi;
	}
	private static Pump parsePump(String s) {
		Pump pu = tryParse(s, "pu", puList);
		if (pu == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Pump!");
		return pu;
	}
	private static Cistern parseCistern(String s) {
		Cistern ci = tryParse(s, "ci", ciList);
		if (ci == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Cistern!");
		return ci;
	}
	private static Source parseSource(String s) {
		Source so = tryParse(s, "so", soList);
		if (so == null) throw new IllegalArgumentException("Could not parse \"" + s + "\" as a name of a Source!");
		return so;
	}
	
	
	private static boolean parseBool(String s) {
		s = s.toLowerCase();
		if (s.equals("y") || s.equals("t") || s.equals("yes") || s.equals("true"))
			return true;
		if (s.equals("n") || s.equals("f") || s.equals("no") || s.equals("false"))
			return false;
		throw new IllegalArgumentException("Could not parse \"" + s + "\" as a boolean value!");
	}
	
	
	
	// parancsok
	// Egy parancs egy String[]-et kapó függvény, paraméterként kapja az argumentumokat,
	// úgy, hogy a parancsot magát is megkapja, a 0. indexben.
	// kiírni a condPr függvénnyel ír, hogyha silent load történik, akkor el lehessen némítani
	// a hibákat kivétel dobásával jelzi, melyet a hívó elkap, és kiírja a felhasználónak a hibát.
	private static Map<String, Consumer<String[]>> commands;
	static {
		commands = new HashMap<>();
		commands.put("#", Proto::hashtag);
		commands.put("exit", Proto::exit);
		commands.put("add", Proto::add);
		commands.put("clear", Proto::clear);
		commands.put("load", Proto::load);
		commands.put("get", Proto::get);
	}

	private static void hashtag(String[] args) {
	}
	
	private static void exit(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException("No argument should be given!");
		isExiting = true;
	}
	
	private static void add(String[] args) {
		if (args.length < 2)
			throw new IllegalArgumentException("At least one argument is required!");
		String type = args[1];
		if (type.equals("me")) {
			if (args.length != 3)
				throw new IllegalArgumentException(args.length > 3 ? "Too many arguments!" : "Too few arguments!");
			condPr(nameOf(newMechanic(parseElement(args[2]))));
		} else if (type.equals("sa")) {
			if (args.length != 3)
				throw new IllegalArgumentException(args.length > 3 ? "Too many arguments!" : "Too few arguments!");
			condPr(nameOf(newSaboteur(parseElement(args[2]))));
		} else if (type.equals("pi")) {
			if (args.length != 3 && args.length != 4)
				throw new IllegalArgumentException(args.length > 3 ? "Too many arguments!" : "Too few arguments!");
			ActiveElement end2 = null;
			if (args.length == 4)
				end2 = parseActiveElement(args[3]);
			condPr(nameOf(newPipe(parseActiveElement(args[2]), end2)));
		} else if (type.equals("pu")) {
			if (args.length > 2)
				throw new IllegalArgumentException("Too many arguments!");
			condPr(nameOf(newPump()));
		} else if (type.equals("ci")) {
			if (args.length > 2)
				throw new IllegalArgumentException("Too many arguments!");
			condPr(nameOf(newCistern()));
		} else if (type.equals("so")) {
			if (args.length > 2)
				throw new IllegalArgumentException("Too many arguments!");
			condPr(nameOf(newSource()));
		} else {
			throw new IllegalArgumentException("Non-existent type \"" + type + "\"! Available ones are me, sa, pi, pu, ci and so.");
		}
	}
	
	private static void clear(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException("No argument should be given!");
		meList.clear();
		saList.clear();
		piList.clear();
		puList.clear();
		ciList.clear();
		soList.clear();
		randVal = -1.0;
	}
	
	private static void load(String[] args) {
		if (args.length < 2)
			throw new IllegalArgumentException("Too few arguments!");
		if (args.length > 3)
			throw new IllegalArgumentException("Too many arguments!");
		boolean silent = false;
		if (args.length == 3)
			silent = parseBool(args[2]);
		// körbekerülni, hogyha silenten töltünk be egy olyan fájlt, amiben van egy nem silent betöltés
		boolean prevMuted = isMuted;
		if (silent)
			isMuted = true;
		try {
			interpret(new FileInputStream(args[1]));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File could not be read, " + e.getMessage() + ".");
		}
		isMuted = prevMuted;
	}
	
	private static void get(String[] args) {
		if (args.length != 2)
			throw new IllegalArgumentException(args.length > 2 ? "Too many arguments!" : "Too few arguments!");
		
	}
}
