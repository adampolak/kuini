package pl.edu.uj.tcs.kuini.controller;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.view.IGamePlayView;

public interface IController extends Runnable {
    IState getCurrentState();
    void proxyCommand(Command command);
    void setView(IGamePlayView view);
}
