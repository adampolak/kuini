package pl.edu.uj.tcs.kuini.model;

import pl.edu.uj.tcs.kuini.model.geometry.Position;

public interface IActor {

    Position getPosition();
    float getRadius();
    ActorType getActorType();
    long getId();
    float getAngle(); // radians
    int getPlayerId();
    float getHP();
}
