package pl.edu.uj.tcs.kuini.view;

import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class SimpleScaler implements ScalerInterface{
    final float modelW, modelH;
    final float canvasW, canvasH;
    final float m2cModifier;
    final float dW, dH;
    public SimpleScaler(float modelH, float modelW, float canvasH, float canvasW) {
        super();
        this.modelH = modelH;
        this.modelW = modelW;
        this.canvasH = canvasH;
        this.canvasW = canvasW;
        m2cModifier = Math.min(canvasW / modelW, canvasH / modelH);
        dW = (canvasW - modelW * m2cModifier) / 2;
        dH = (canvasH - modelH * m2cModifier) / 2;
    }
    @Override
    public Position getCanvasPosition(Position modelPosition) {
        return new Position(dW + modelPosition.getX()*m2cModifier, dH + modelPosition.getY()*m2cModifier);
    }
    @Override
    public Position getModelPosition(Position canvasPosition) {
        return new Position((canvasPosition.getX() - dW) / m2cModifier, (canvasPosition.getY() - dH) / m2cModifier);
    }
    @Override
    public float getCanvasRadius(float modelRadius) {
        return modelRadius * m2cModifier;
    }
    @Override
    public float getModelRadius(float canvasRadius) {
        return canvasRadius / m2cModifier;
    }

}
