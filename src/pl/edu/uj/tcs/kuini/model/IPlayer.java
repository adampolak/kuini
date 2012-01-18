package pl.edu.uj.tcs.kuini.model;

public interface IPlayer {
    int getId();
    String getName();
    PlayerColor getColor();
    long getScore();
}
