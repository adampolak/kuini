package pl.edu.uj.tcs.kuini.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FpsCounter {

    private volatile long lastFrameTimestamp = 0;
    private volatile long averageFrameDuration = 0;
    
    private final Paint paint;
    
    public FpsCounter() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(40.0f);
    }
    
    public void nextFrame() {
        long timestamp = System.currentTimeMillis();
        if (lastFrameTimestamp > 0)
            averageFrameDuration = 
                (9 * averageFrameDuration + (timestamp - lastFrameTimestamp))/10;
        lastFrameTimestamp = timestamp;
    }
    
    public void drawFps(Canvas canvas) {
        canvas.drawText(Long.toString(averageFrameDuration)+"ms", 
                20.0f, 60.0f, paint);
    }
    
}
