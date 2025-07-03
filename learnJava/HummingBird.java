
public final class HummingBird extends Bird
{
	private int _legsHeight = 3;

	private int _tailLength = 6;

	private int _beakLength = 1;

	public int getLegsHeight(){ return _legsHeight; }

	public int getTailLength(){ return _tailLength; }

	public int getBeakLength(){ return _beakLength; }

    public int MovingSpeed() {
		return _movingSpeed;
    }
}

///<summary>Base abstract class to hold the defaults for the bird.
/// At this point assuming a bird has these deafult properties.</summary>
abstract class Bird implements IBird
{
	// can fly?
	protected boolean _fly = true;

	// can walk?
	private boolean _walk = true;

	private int _eyesCount = 2;

	private int _earsCount = 2;

	private int _noseCount = 1;

	private int _mouthCount = 1;

	private int _legsCount = 2;

	private boolean _walking;

	private boolean _flying;

	protected int _movingSpeed;

	private boolean _tail = true;

	public boolean getFly() {
		return _fly;
	} 

	public abstract int getBeakLength();

	public abstract int getTailLength();

	public abstract int getLegsHeight();
}

interface IBird 
// : IAnimal
{
	public boolean getFly(); 

	public int getBeakLength();

	// in km / hour
	// public int MovingSpeed();
}
