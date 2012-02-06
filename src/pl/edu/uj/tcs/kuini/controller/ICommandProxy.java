package pl.edu.uj.tcs.kuini.controller;

import pl.edu.uj.tcs.kuini.model.Command;

public interface ICommandProxy {
    void proxyCommand(Command command);
}
