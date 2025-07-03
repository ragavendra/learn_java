public class Singleton {
	private Singleton instance = new Singleton();

	private Singleton(){}

	public Singleton getInstance(){
		return instance;
	}
}
