package pl.edu.uj.tcs.kuini.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FpsCounter {

    private long lastFrameTimestamp = 0;
    private long averageFrameDuration = 0;
    
    private final Paint paint;
    
    public FpsCounter() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(30.0f);
    }
    
    public void nextFrame() {
        long timestamp = System.currentTimeMillis();
        if (lastFrameTimestamp > 0)
            averageFrameDuration = 
                (9 * averageFrameDuration + (timestamp - lastFrameTimestamp))/10;
        lastFrameTimestamp = timestamp;
    }
    
    public void drawFps(Canvas canvas) {
        long fps = averageFrameDuration > 0 ? 1000 / averageFrameDuration : 0;
        canvas.drawText(Long.toString(fps)+" FPS", 
                20.0f, 50.0f, paint);
    }
    
}
