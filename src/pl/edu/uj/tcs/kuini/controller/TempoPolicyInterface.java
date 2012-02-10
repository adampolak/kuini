package pl.edu.uj.tcs.kuini.controller;

public interface TempoPolicyInterface {
    
    void ready(int playerId);
    void nextTurn() throws InterruptedException;
    
}
