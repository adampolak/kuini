package pl.edu.uj.tcs.kuini.view;

import pl.edu.uj.tcs.kuini.model.geometry.Position;

public interface ScalerInterface {
    Position getCanvasPosition(Position modelPosition);
    Position getModelPosition(Position canvasPosition);
    float getCanvasRadius(float modelRadius);
    float getModelRadius(float canvasRadius);
}
