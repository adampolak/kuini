package pl.edu.uj.tcs.kuini.model;

import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class LivePath {
	private final Path path;
	private int idx = 0;
	
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
