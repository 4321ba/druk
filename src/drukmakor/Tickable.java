package drukmakor;

public interface Tickable {
	/**
	 * Tick fv, amit minden felülír
	 */
	abstract public void tick();
	
	// státuszlekérdező függvény
	public Object[] get();
	
}
