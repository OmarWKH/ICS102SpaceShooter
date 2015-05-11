package gameobjects;

/**
 * This interface defines the shooting functions. The shooter will be a GameObject. The functions are shoot() and timeToShoot(), so it implies some kind of cool down.
 *
 * @author Omar Khashoggi
 */
public interface Shooter extends GameObject {
	/**
	 * This method is responisble for firing bullets. When it's called, it check if it's time for the shooter to shoot. If so, it fires.
	 *
	 */
	public void shoot();

	/**
	 * This method checks if it's time for the shooter to shoot or if it's still cooling down.
	 *
	 * @return returns true if it's time to shoot. False otherwise
	 */
	public boolean timeToShoot();
}