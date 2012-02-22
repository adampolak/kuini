package pl.edu.uj.tcs.kuini.view;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.R;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.actions.SpawnAntAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
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
    
    private final Paint defaultPaint = new Paint();
    
    private boolean showFps = true;
    private FpsCounter fpsCounter = new FpsCounter();
    private boolean showStats = true;

    private boolean drawBg = true;
    private final Drawable background;
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    
    public KuiniView(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);

        showFps = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("showFps", true);
        
        drawBg  =PreferenceManager.getDefaultSharedPreferences(context).getBoolean("drawBg", true);
        background = context.getResources().getDrawable(R.drawable.sand);
        
        this.setOnTouchListener(this);
    }
    
    private static float[] ptsFromPositions(List<Position> path) {
        float[] pts = new float[(path.size()-1)*4];
        for(int i = 0; i < path.size() - 1; i++) {
            pts[4*i] = path.get(i).getX();
            pts[4*i+1] = path.get(i).getY();
            pts[4*i+2] = path.get(i+1).getX();
            pts[4*i+3] = path.get(i+1).getY();
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
    
    private static String addLeading0(int x) {
        if (x < 10) return "0"+x; else return ""+x;
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        
        canvas.drawColor(Color.DKGRAY);

        IState state = currState;
        if(state == null) return;
        
        ScalerInterface scaler = new SimpleScaler(state.getHeight(), state.getWidth(), myHeight, myWidth);

        Position upLeft = scaler.getCanvasPosition(new Position(0, 0));
        Position downRight = scaler.getCanvasPosition(new Position(state.getWidth(), state.getHeight()));
        if (drawBg) {
            background.setBounds((int)upLeft.getX(), (int)upLeft.getY(), (int)downRight.getX(), (int)downRight.getY());
            background.draw(canvas);
        } else {
            defaultPaint.setColor(Color.LTGRAY);
            defaultPaint.setColor(Color.rgb(0xb8, 0xa8, 0x90));
            canvas.drawRect(upLeft.getX(), upLeft.getY(), downRight.getX(), downRight.getY(), defaultPaint);
        }
        
        for(IActor actor : state.getActorStates()) {
            Position canvasPosition = scaler.getCanvasPosition(actor.getPosition());            
            float canvasRadius = scaler.getCanvasRadius(actor.getRadius());
            IPlayer player = state.getPlayerStatesById().get(actor.getPlayerId());
            PlayerColor playerColor = player.getColor();
            float alpha = 64 + ((float) actor.getHP() / actor.getMaxHP()) * 191;
            int color = Color.argb((int)alpha, playerColor.getR(), playerColor.getG(), playerColor.getB());

            defaultPaint.setColor(color);
            canvas.drawCircle(canvasPosition.getX(), canvasPosition.getY(), canvasRadius, defaultPaint);
        }
        if(startPosition != null) {
            
            PlayerColor ourPlayerColor = state.getPlayerStatesById().get(playerId).getColor();
            int color = Color.argb((int) 255*3/10, ourPlayerColor.getR(), ourPlayerColor.getG(), ourPlayerColor.getB());            
            Paint paint = new Paint();
            paint.setColor(color);
            
            canvas.drawCircle(startPosition.getX(), startPosition.getY(), pathRadius, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12);
            canvas.drawLines(ptsFromPositions(path), paint);
          
        } 
        if (showFps) fpsCounter.drawFps(canvas);
        if (showStats) {
            
            float x = myWidth / 3;
            float y = 25.0f;
            
            Paint paint = new Paint();
            paint.setTextSize(25.0f);
            paint.setTextAlign(Align.LEFT);

            paint.setColor(getColorFromId(state, playerId));
            canvas.drawText(addLeading0(getNumberOfAntsFromId(state, playerId)),
                    x, y, paint);
            x += 40.0f;
            for(int id : state.getPlayerStatesById().keySet()) {
                if(id == playerId)
                    continue;
                if(!state.getPlayerStatesById().get(id).isHuman())
                    continue;
                paint.setColor(getColorFromId(state, id));
                canvas.drawText(addLeading0(getNumberOfAntsFromId(state, id)),
                        x, y, paint);
                x += 40.0f;
                
            }
            
            
            y += 25.0f;
            x = myWidth/3;
            
            paint.setColor(getColorFromId(state, state.getFoodPlayerId()));
            float ourFood = state.getPlayerStatesById().get(playerId).getFood();
            canvas.drawText("Food: " + (int)ourFood + "/" + (int)SpawnAntAction.ANT_PRICE + " ", 
                    x, y, paint);
            
            
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
        fpsCounter.nextTurn();
   
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
