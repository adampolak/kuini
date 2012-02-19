package pl.edu.uj.tcs.kuini.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.Command;

public class SerialControllersServer extends Thread {

    private static final long IDLE_TIME = 20; // milliseconds
    private static final int MAX_PLAYERS = 4;

    private final ObjectInputStream[] in = new ObjectInputStream[MAX_PLAYERS];
    private final ObjectOutputStream[] out = new ObjectOutputStream[MAX_PLAYERS];
    private final int[] playerId = new int[MAX_PLAYERS];
    private int N = 0;

    public void addPlayer(ObjectInputStream in, ObjectOutputStream out, int playerId) {
        if (getState() != Thread.State.NEW || N >= MAX_PLAYERS) 
            throw new IllegalStateException();
        this.in[N] = in;
        this.out[N] = out;
        this.playerId[N] = playerId;
        N++;
    }

    @Override
    public void run() {
        
        List<Command> commands = new ArrayList<Command>();
        long lastTurnTime = 0;
        
        while(!isInterrupted()) {
            try {
                for(int i=0; i<N; i++)
                    while(true) {
                        Object o = in[i].readObject();
                        if (o instanceof Command) {
                            Command c = (Command) o;
                            if (c.getPlayerId() == playerId[i])
                                commands.add(c);
                        }
                        if (o instanceof ReadyForNextTurn)
                            break;
                    }
            
                float turnDuration = 0.01f * Math.min(System.currentTimeMillis()-lastTurnTime, 1000);
                lastTurnTime = System.currentTimeMillis();
            
                Turn turn = new Turn(commands, turnDuration);
                commands.clear();
            
                for(int i=0; i<N; i++)
                    out[i].writeObject(turn);
                
                sleep(IDLE_TIME);
                Thread.yield();
            } catch (Exception e) { break; }
        }

        for(int i=0; i<N; i++) {
            try {
                in[i].close();
            } catch (IOException e) {}
            try {
                out[i].close();
            } catch (IOException e) {}
        }
    }
}