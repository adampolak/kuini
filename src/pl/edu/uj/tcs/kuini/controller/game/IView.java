package pl.edu.uj.tcs.kuini.controller.game;

import pl.edu.uj.tcs.kuini.model.IState;

public interface IView {
    
    void gameStarted(int playerId);
    void gameFinished();
    
    void stateChanged(IState state);
    
}
