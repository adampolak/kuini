package pl.edu.uj.tcs.kuini.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.uj.tcs.kuini.IController;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;
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
    
    public GamePlayView(Context context, IController controller, int myWidth, int myHeight) {
        super(context);
        
        this.controller = controller;
        changes = true;
        this.myWidth = myWidth;
        this.myHeight = myHeight;
        
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
    
    @Override
    public void onDraw(Canvas canvas) {
        updateState();
        /* will be changed to drawBitmap */ 
        Paint white = new Paint();
        white.setColor(new Random().nextInt());
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
        for(Position p : tmpPointList) {
            Paint paint = new Paint();
            paint.setColor(new Random().nextInt());
            canvas.drawCircle(p.getX(), p.getY(), 5, paint);    
        }
        
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        tmpPointList.add(new Position(event.getX(), event.getY()));
        return true;
    }


    
    @Override
    public void somethingChanged() {
        changes = true;
    }

}
