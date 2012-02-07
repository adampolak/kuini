package pl.edu.uj.tcs.kuini.controller;

import pl.edu.uj.tcs.kuini.model.IState;

public interface IView {
    
    void gameStarted(int playerId);
    void gameBroken();
    /* void gameFinished(); */
    
    void stateChanged(IState state);
    
}
