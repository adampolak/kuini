package pl.edu.uj.tcs.kuini.model.live;

import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.LivePath;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Position;

/**
 * Interface describing actor whose state can be changed.
 * @author michal
 *
 */
public interface ILiveActor extends IActor {
	void performAction(float elapsedTime, ILiveState state);
	float getSpeed();
	void setPosition(Position position);
	void changeHP(float change);
	boolean isDead();
	void setPath(Path path);
	Path getPath();
	LivePath getLivePath();
	void setAngle(float angle);
}
