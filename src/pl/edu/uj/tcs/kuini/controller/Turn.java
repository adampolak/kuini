package pl.edu.uj.tcs.kuini.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.Command;

public class Turn implements Iterable<Command>, Serializable {
    
    private static final long serialVersionUID = -4224392177340695376L;
    private final Command[] commands;
    private final float elapsedTime;
    
    public Turn(List<Command> commands, float elapsedTime) {
        this.commands = commands.toArray(new Command[0]);
        this.elapsedTime = elapsedTime;
    }
    
    public Iterator<Command> iterator() {
        return Arrays.asList(commands).iterator();
    }
    
    public float getElapsedTime() {
        return elapsedTime;
    }
}
