package pl.edu.uj.tcs.kuini.view;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.ActorType;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class KuiniView extends View implements OnTouchListener {
    
    private ICommandProxy proxy = null;
    private volatile IState currState = null;
    private int myWidth, myHeight;
    private List<Position> path;
    private Position startPosition;
    private boolean incremeanting_radius;
    private float pathRadius;
    private float max_radius_for_command = 40;
    private float radius_speed_growth = 3f;
    private int playerId;
    private boolean beenOut;
    
    private boolean showFps = true;
    private FpsCounter fpsCounter = new FpsCounter();
    private boolean showStats = true;
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public KuiniView(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);

        showFps = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("showFps", true);
        
        this.setOnTouchListener(this);
    }
    
    private static float[] ptsFromPositions(List<Position> path) {
        float[] pts = new float[path.size()*2];
        for(int i = 0; i < path.size(); i++) {
            pts[2*i] = path.get(i).getX();
            pts[2*i+1] = path.get(i).getY();
        }
        return pts;
    }
        
    private static int getColorFromId(IState state, int id) {
        PlayerColor playerColor = state.getPlayerStatesById().get(id).getColor();
        return Color.argb((int) 255*8/10, playerColor.getR(), playerColor.getG(), playerColor.getB());            
    }
    
    private static int getNumberOfAntsFromId(IState state, int id) {
        int result = 0;
        for(IActor actor : state.getActorStates())
            if(actor.getPlayerId() == id && actor.getActorType()==ActorType.ANT)
                result++;
        return result;
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        
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
        if(startPosition != null) {
            
            PlayerColor ourPlayerColor = state.getPlayerStatesById().get(playerId).getColor();
            int color = Color.argb((int) 255*3/10, ourPlayerColor.getR(), ourPlayerColor.getG(), ourPlayerColor.getB());            
            Paint paint = new Paint();
            paint.setColor(color);
            
            canvas.drawCircle(startPosition.getX(), startPosition.getY(), pathRadius, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(16);
            canvas.drawLines(ptsFromPositions(path), paint);
            List<Position> path2 = new ArrayList<Position>(path);
            path2.remove(0);
            canvas.drawLines(ptsFromPositions(path2), paint);
        
        } 
        if (showFps) fpsCounter.drawFps(canvas);
        if (showStats) {
            float x = 20.0f;
            float y = 50.0f;
            float size = 30.0f;
            if(showFps)
                y += size;
            float ourFood = state.getPlayerStatesById().get(playerId).getFood();
            /* TODO : how to get ant price? */
            float antPrice = 100.0f;
            Paint paint = new Paint();
            paint.setColor(getColorFromId(state, state.getFoodPlayerId()));
            paint.setTextSize(size);
            canvas.drawText("" + (int)ourFood + "/" + (int)antPrice + " ", 
                    x, y, paint);
            y += size;
            paint.setColor(getColorFromId(state, playerId));
            canvas.drawText("" + getNumberOfAntsFromId(state, playerId),
                    x, y, paint);
            y += size;
            for(int id : state.getPlayerStatesById().keySet()) {
                if(id == playerId)
                    continue;
                if(!state.getPlayerStatesById().get(id).isHuman())
                    continue;
                paint.setColor(getColorFromId(state, id));
                canvas.drawText("" + getNumberOfAntsFromId(state, id),
                        x, y, paint);
                y += size;
                
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        IState state = currState;
        
        if (state==null) return true;
        
        Position act = new Position(event.getX(), event.getY());
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            path = new ArrayList<Position>();
            path.add(act);
            incremeanting_radius = true;
            startPosition = act;
            pathRadius = max_radius_for_command;
            beenOut = false;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            ScalerInterface scaler = new SimpleScaler(state.getHeight(), state.getWidth(), myHeight, myWidth);
        	List<Position> modelPath = new ArrayList<Position>();
        	for(Position p : path){
        		modelPath.add(scaler.getModelPosition(p));
        	}
        	float modelRadius = scaler.getModelRadius(pathRadius);
            proxy.proxyCommand(new Command(modelRadius, new Path(modelPath), playerId));
            path = null;
            startPosition = null;
        }
        else {
            if(incremeanting_radius && startPosition.distanceTo(act) < max_radius_for_command)
                pathRadius += radius_speed_growth;
            else
                incremeanting_radius = false;
            path.add(act);
            if(beenOut == false && startPosition.distanceTo(act) > pathRadius) {
                path.clear();
                path.add(startPosition);
                path.add(act);
                beenOut = true;
            }
                
        }
        invalidate();
        return true;
    }
    
    public void stateChanged(IState state) {
        fpsCounter.nextFrame();
   
        currState = state;
        postInvalidate();
    }
    
    public void setCommandProxy(ICommandProxy proxy) {
        this.proxy = proxy;
    }
    
    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.myWidth = w;
        this.myHeight = h;
    }

}
