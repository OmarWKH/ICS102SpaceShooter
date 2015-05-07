public interface Shooter extends GameObject {
	//but what if you get a powerup that decreases coolDown? On the other hand, all shooters should have a coolDown
	public void fire();
}