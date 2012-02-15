package pl.edu.uj.tcs.kuini.model.live;

import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class LivePath {
	private final Path path;
	private int idx = 1; // ignore first point (center of selection)
	
	public LivePath(Path path){
		this.path = path;
	}
	public boolean isEmpty(){
		return path.isEmpty();
	}
	public Position currentPosition(){
		return path.positions.get(idx);
	}
	public boolean hasNextPosition(){
		return idx < path.positions.size();
	}
	public void nextPosition(){
		idx++;
	}

}
