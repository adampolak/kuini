package pl.edu.uj.tcs.kuini.view;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class KuiniView extends View implements OnTouchListener {
    
    private ICommandProxy proxy = null;
    /*
    private boolean changes;
    private IState state;
    private IState newWaitingState;
    */
    private volatile IState currState = null;
    private final int myWidth;
    private final int myHeight;
    private List<Position> path;
    private float pathRadius;
    private float max_radius_for_command = 40;
    private float radius_speed_growth = 3f;
    private int playerId;
    
    private boolean showFps = true;
    private FpsCounter fpsCounter = new FpsCounter();
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public KuiniView(Context context, int myWidth, int myHeight) {
        super(context);
        
        /* this.controller = controller; */
        //changes = true;
        this.myWidth = myWidth;
        this.myHeight = myHeight;

        setFocusable(true);
        setFocusableInTouchMode(true);

        showFps = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("showFps", true);
        
        this.setOnTouchListener(this);
    }

    /*
    private void updateState() {
        if(!changes)
            return;
        synchronized (this) {
            changes = false;
            state = newWaitingState;
            newWaitingState = null;
        }
    }
    */
    
    private float[] ptsFromPositions(List<Position> path) {
        float[] pts = new float[path.size()*2];
        for(int i = 0; i < path.size(); i++) {
            pts[2*i] = path.get(i).getX();
            pts[2*i+1] = path.get(i).getY();
        }
        return pts;
    }
        
    @Override
    public void onDraw(Canvas canvas) {
        /* updateState(); */
        
        IState state = currState;
        
        /* will be changed to drawBitmap */ 
        Paint black = new Paint();
        black.setColor(Color.DKGRAY);
        canvas.drawPaint(black);
        
        if(state == null)
            return;
        
        ScalerInterface scaler = new SimpleScaler(state.getHeight(), state.getWidth(), myHeight, myWidth);

        Paint white = new Paint();
        white.setColor(Color.LTGRAY);
        Position upLeft = scaler.getCanvasPosition(new Position(0, 0));
        Position downRight = scaler.getCanvasPosition(new Position(state.getWidth(), state.getHeight()));
        canvas.drawRect(upLeft.getX(), upLeft.getY(), downRight.getX(), downRight.getY(), white);
        
        for(IActor actor : state.getActorStates()) {
            Position canvasPosition = scaler.getCanvasPosition(actor.getPosition());            
            float canvasRadius = scaler.getCanvasRadius(actor.getRadius());
            IPlayer player = state.getPlayerStatesById().get(actor.getPlayerId());
            PlayerColor playerColor = player.getColor();
            float alpha = ((float) actor.getHP() / actor.getMaxHP()) * ((1<<8)-1);
            int color = Color.argb((int)alpha, playerColor.getR(), playerColor.getG(), playerColor.getB());

            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawCircle(canvasPosition.getX(), canvasPosition.getY(), canvasRadius, paint);
        }
        if(path != null) {
            
            PlayerColor ourPlayerColor = state.getPlayerStatesById().get(playerId).getColor();
            int color = Color.argb((int) 255*3/10, ourPlayerColor.getR(), ourPlayerColor.getG(), ourPlayerColor.getB());            
            Paint paint = new Paint();
            paint.setColor(color);
            
            canvas.drawCircle(path.get(0).getX(), path.get(0).getY(), pathRadius, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawLines(ptsFromPositions(path), paint);
            List<Position> path2 = new ArrayList<Position>(path);
            path2.remove(0);
            canvas.drawLines(ptsFromPositions(path2), paint);
        } 
/*        for(Position p : tmpPointList) {
            Paint paint = new Paint();
            paint.setColor(new Random().nextInt());
            canvas.drawCircle(p.getX(), p.getY(), 5, paint);    
        }
  */
        if (showFps) fpsCounter.drawFps(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        IState state = currState;
        
        if (state==null) return true;
        
        Position act = new Position(event.getX(), event.getY());
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            path = new ArrayList<Position>();
            path.add(act);
            pathRadius = max_radius_for_command;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            // TODO rescale positions, radius, etc
        	Log.d("COMMAND", "Command sent!");
            ScalerInterface scaler = new SimpleScaler(state.getHeight(), state.getWidth(), myHeight, myWidth);
        	List<Position> modelPath = new ArrayList<Position>();
        	for(Position p : path){
        		modelPath.add(scaler.getModelPosition(p));
        	}
        	float modelRadius = scaler.getModelRadius(pathRadius);
            proxy.proxyCommand(new Command(modelRadius, new Path(modelPath), playerId));
            path = null;
        }
        else {
            if(path.size()==1 && path.get(0).distanceTo(act) < max_radius_for_command)
                pathRadius += radius_speed_growth;
            else
                path.add(act);
        }
        invalidate();
        return true;
    }
    
    public void stateChanged(IState state) {
        /*
        synchronized (this) {
            newWaitingState = new State(state);
            changes = true;
        }
        */
        
        fpsCounter.nextFrame();
   
        currState = state;
        postInvalidate();
//        invalidate();
    }
    
    public void setCommandProxy(ICommandProxy proxy) {
        this.proxy = proxy;
    }

}
