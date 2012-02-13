package pl.edu.uj.tcs.kuini.model;

public interface IPlayer {
    int getId();
    String getName();
    PlayerColor getColor();
    float getScore();
    float getFood();
    boolean isHuman();
}
