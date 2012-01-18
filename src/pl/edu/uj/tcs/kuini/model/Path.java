package pl.edu.uj.tcs.kuini.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class Path implements Serializable{
	private static final long serialVersionUID = 5320233972083651111L;
	public final List<Position> positions;
	public static final Path EMPTY_PATH = new Path(new LinkedList<Position>());
	public Path(List<Position> positions){
		this.positions = new ArrayList<Position>(positions);
	}
	public List<Position> getPositions(){
		return Collections.unmodifiableList(positions);
	}
	public boolean isEmpty(){
		return positions.isEmpty();
	}
	public Position getStart(){
		return positions.get(0);
	}
	public Position getEnd(){
		return positions.get(positions.size()-1);
	}
}
