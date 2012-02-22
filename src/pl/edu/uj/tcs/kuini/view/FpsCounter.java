package pl.edu.uj.tcs.kuini.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FpsCounter {

    private long lastFrameTimestamp = 0;
    private long averageFrameDuration = 0;
    private long lastTurnTimestamp = 0;
    private long averageTurnDuration = 0;
    
    private final Paint paint;
    
    public FpsCounter() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(25.0f);
    }
    
    public void nextTurn() {
        long timestamp = System.currentTimeMillis();
        if (lastTurnTimestamp > 0)
            averageTurnDuration = 
                (9 * averageTurnDuration + (timestamp - lastTurnTimestamp))/10;
        lastTurnTimestamp = timestamp;
    }
    
    public void drawFps(Canvas canvas) {
        long timestamp = System.currentTimeMillis();
        if (lastFrameTimestamp > 0)
            averageFrameDuration = 
                (9 * averageFrameDuration + (timestamp - lastFrameTimestamp))/10;
        lastFrameTimestamp = timestamp;        
        
        long fps = averageFrameDuration > 0 ? 1000 / averageFrameDuration : 0;
        long tps = averageTurnDuration > 0 ? 1000 / averageTurnDuration : 0;
        /*
        canvas.drawText(Long.toString(fps)+" FPS  "+Long.toString(tps)+" TPS", 
                20.0f, 45.0f, paint);
        */
        canvas.drawText(Long.toString(tps)+" FPS", 5.0f, 25.0f, paint);
    }
    
}
