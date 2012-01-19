package pl.edu.uj.tcs.kuini.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.uj.tcs.kuini.controller.IController;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.Player;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GamePlayView extends View implements OnTouchListener, IGamePlayView{
    
    private IController controller;
    private boolean changes;
    private IState state;
    private final int myWidth;
    private final int myHeight;
    private List<Position> path;
    private float radius;
    private float max_radius_for_command = 20;
    private final int myID;
    
    public GamePlayView(Context context, IController controller, int myWidth, int myHeight, int id) {
        super(context);
        
        this.controller = controller;
        changes = true;
        this.myWidth = myWidth;
        this.myHeight = myHeight;
        this.myID = id;

        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);
    }

    private void updateState() {
        if(!changes)
            return;
        changes = false;
        state = controller.getCurrentState();
    }
    
    private List<Position> tmpPointList = new ArrayList<Position>(); 
    
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
        Log.d("ON DRAW", "called");
        updateState();
        /* will be changed to drawBitmap */ 
        Paint white = new Paint();
        white.setColor(Color.LTGRAY);
        canvas.drawPaint(white);
        
        float xModifier = (float) myWidth / state.getWidth();
        float yModifier = (float) myHeight / state.getHeight();
        float modifier = Math.min(xModifier, yModifier);
                
        for(IActor actor : state.getActorStates()) {
        
            Log.d("MODIFIER", "" + modifier + " " + myWidth + " " + state.getWidth());
            
            actor.getPosition(); 
            float newx = actor.getPosition().getX() * modifier;
            float newy = actor.getPosition().getY() * modifier;
            float radius = actor.getRadius() * modifier;
            IPlayer player = state.getPlayerStatesById().get(actor.getPlayerId());
            PlayerColor playerColor = player.getColor();
            float alpha = ((float) actor.getHP() / actor.getMaxHP()) * ((1<<8)-1);
            int color = Color.argb((int)alpha, playerColor.getR(), playerColor.getG(), playerColor.getB());

            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawCircle(newx, newy, radius, paint);
        }
        if(path != null) {
            Paint paint = new Paint();
            paint.setColor(Color.argb((int) 255*3/10, 0xFF, 0x00, 0x00));
            canvas.drawCircle(path.get(0).getX(), path.get(0).getY(), radius, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawLines(ptsFromPositions(path), paint);
        } 
/*        for(Position p : tmpPointList) {
            Paint paint = new Paint();
            paint.setColor(new Random().nextInt());
            canvas.drawCircle(p.getX(), p.getY(), 5, paint);    
        }
  */      
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Position act = new Position(event.getX(), event.getY());
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            path = new ArrayList<Position>();
            path.add(act);
            radius = max_radius_for_command;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            // TODO rescale positions, radius, etc
            controller.proxyCommand(new Command(radius, new Path(path), myID));
            path = null;
        }
        else {
            if(path.get(0).distanceTo(act) < max_radius_for_command)
                radius += 0.5f;
            else
                path.add(act);
        }
    //    tmpPointList.add(new Position(event.getX(), event.getY()));
        invalidate();
        return true;
    }


    
    @Override
    public void stateChanged(IState state) {
        changes = true;
        invalidate();
    }

}
