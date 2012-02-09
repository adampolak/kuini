package pl.edu.uj.tcs.kuini.model;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;

public class GridActorWatcher implements IActorWatcher {
	private class GridPosition{
		public final int x;
		public final int y;
		public GridPosition(int x, int y){
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			return 11+17*x+13*y;
		}
		@Override
		public boolean equals(Object o) {
			if(o instanceof GridPosition){
				GridPosition p = (GridPosition) o;
				return x == p.x && y == p.y;
			}
			return false;
		}
	}
	private final Map<Long, GridPosition> positionsById = new HashMap<Long, GridPosition>();
	private final Vector<ILiveActor>[][] actorsGrid;
	private final float boardWidth;
	private final float boardHeight;
	private final int gridWidth;
	private final int gridHeight;
	private final int[] dx = {-1,0,0,1};
	private final int[] dy = {0,-1,1,0};
	
	@SuppressWarnings("unchecked")
	public GridActorWatcher(int gridWidth, int gridHeight, float boardWidth,float boardHeight){
		actorsGrid = (Vector<ILiveActor>[][]) Array.newInstance(Vector.class, gridWidth, gridHeight);
		for(int x=0;x<gridWidth;x++)
			for(int y=0;y<gridHeight;y++)
				actorsGrid[x][y] = new Vector<ILiveActor>();
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
	}

	@Override
	public List<ILiveActor> getNeighbours(Position position, float radius) {
		List<ILiveActor> result = new LinkedList<ILiveActor>();
		int checked=0;
		for(GridPosition p : getNeighbourFields(positionToGridPosition(position), 
				radius)){
			for(ILiveActor actor : actorsGrid[p.x][p.y]){
				checked++;
				if(position.distanceTo(actor.getPosition()) <= radius + actor.getRadius())
					result.add(actor);
			}
		}
		//Log.d("COLLISION", "Checked: "+checked+" ok: "+result.size());
		return result;
	}

	@Override
	public void updatePosition(ILiveActor actor) {
		removeActor(actor);
		addActor(actor);
	}
	
	private float fieldWidth(){
		return boardWidth / gridWidth;
	}
	
	private float fieldHeight(){
		return boardHeight / gridHeight;
	}
	
	private Set<GridPosition> getNeighbourFields(GridPosition start, float radius){
		Queue<GridPosition> queue = new LinkedList<GridPosition>();
		queue.add(start);
		Set<GridPosition> result = new HashSet<GridPosition>();
		result.add(start);
		while(!queue.isEmpty()){
			GridPosition p = queue.poll();
			for(int i=0;i<4;i++){
				GridPosition q = new GridPosition(p.x+dx[i], p.y+dy[i]);
				if(validPosition(q) && !result.contains(q)){
					if(distance(start, q) <= radius)
						queue.add(q);
					result.add(q);
				}
			}
		}
		return result;
	}
	/*
	private List<GridPosition> getNeighbourFields(GridPosition start, float radius){
		List<GridPosition> result = new LinkedList<GridPosition>();
		int width = (int)(1+radius/fieldWidth());
		int height = (int)(1+radius/fieldHeight());
		GridPosition p;
		for(int x=Math.max(0, start.x-width);x<Math.min(gridWidth, start.x+width);x++)
			for(int y=Math.max(0, start.y-height);y<Math.min(gridHeight, start.y+height);y++){
				p = new GridPosition(x, y);
				if(distance(p, start) < radius)result.add(p);
			}
				
		return result;
	}*/
			
	
	private boolean validPosition(GridPosition p) {
		return p.x >= 0 
			&& p.x < gridWidth
			&& p.y >= 0
			&& p.y < gridHeight;
	}

	private float square(float a){
		return a*a;
	}
	
	private float distance(GridPosition a, GridPosition b){
		return (float) Math.sqrt(square(fieldWidth()*(a.x-b.x) 
				+ square(fieldHeight()*(a.y-b.y))));
		
	}
	
	private float fieldDiagonal(){
		return ((float) Math.round(1000000*Math.sqrt(square(fieldWidth())+square(fieldHeight()))))/1000000;
	}
	
	private GridPosition positionToGridPosition(Position p){
		return new GridPosition(
				Math.max(0, Math.min(gridWidth-1, (int) (p.getX()/fieldWidth()))),
				Math.max(0, Math.min(gridHeight-1, (int) (p.getY()/fieldHeight())))
				);
	}

	@Override
	public void addActor(ILiveActor actor) {
		GridPosition position = positionToGridPosition(actor.getPosition());
		positionsById.put(actor.getId(), position);
		actorsGrid[position.x][position.y].add(actor);
	}

	@Override
	public void removeActor(ILiveActor actor) {
		if(!positionsById.containsKey(actor.getId()))return;
		GridPosition position = positionsById.get(actor.getId());
		actorsGrid[position.x][position.y].remove(actor);
		positionsById.remove(actor.getId());
	}

}
