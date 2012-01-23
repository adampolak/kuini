package pl.edu.uj.tcs.kuini.view;

import pl.edu.uj.tcs.kuini.controller.IController;
import pl.edu.uj.tcs.kuini.model.IState;

public interface IGamePlayView {
    
    void stateChanged(IState state);
    void setController(IController controller);

}
