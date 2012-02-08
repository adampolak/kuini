package pl.edu.uj.tcs.kuini.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pl.edu.uj.tcs.kuini.model.Command;

public class ControllersServer extends Thread {

    private int debug = 0;
    private static final long MIN_TURN_DURATION = 30; // milliseconds
    
    private TempoPolicyInterface tempoPolicy;

	private class ClientInputHandler extends Thread {
        
        private final ObjectInputStream in;
        private final int playerId;
        
        public ClientInputHandler(ObjectInputStream in, int playerId) {
            this.in = in;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            while(!isInterrupted()) {
                try {
                    Object o = in.readObject();
                    if (o instanceof Command) {
                        Command c = (Command)o;
                        if (c.getPlayerId() != playerId) continue;
                        synchronized(currentCommands) {
                            currentCommands.add(c);
                        }
                    } else if (o instanceof ReadyForNextTurn)
                        tempoPolicy.ready(playerId);
                } catch (IOException e) { 
                    break; 
                } catch (ClassNotFoundException e) {
                    continue;
                }
            }
            try { in.close(); } catch (IOException e) {}
            ControllersServer.this.interrupt();
        }
    }

    private class ClientOutputHandler extends Thread {

        private final ObjectOutputStream out;
        private final BlockingQueue<Serializable> queue 
            = new LinkedBlockingQueue<Serializable>();

        public ClientOutputHandler(ObjectOutputStream out) {
            this.out = out;
        }

        public void nextTurn(Turn turn) {
            queue.add(turn); 
            /* It may throw IllegalStateException if capacity of
             * a queue is exceeded. It should not happen, thus
             * I guess it is an appropriate behavior.                  */
        }

        @Override
        public void run() {
            while(!isInterrupted()) {
                try {
                    out.writeObject(queue.take());
                } catch (IOException e) { 
                	break; 
                } catch (InterruptedException e) {
                	break;
                }
            }
            try { out.close(); } catch(IOException e) {}
            ControllersServer.this.interrupt();
        }

    }

    private final List<Command> currentCommands = 
            new ArrayList<Command>();

    private final List<ClientInputHandler> clientInputHandlers = 
            new ArrayList<ClientInputHandler>();
    private final List<ClientOutputHandler> clientOutputHandlers = 
            new ArrayList<ClientOutputHandler>();

    public void addPlayer(ObjectInputStream in, ObjectOutputStream out, int playerId) {
        if (getState() != Thread.State.NEW) throw new IllegalStateException();
        clientInputHandlers.add(new ClientInputHandler(in, playerId));
        clientOutputHandlers.add(new ClientOutputHandler(out));
    }

    @Override
    public void run() {
        
        // tempoPolicy = new SimplePolicy();
        tempoPolicy = new WaitForAllPolicy(clientInputHandlers.size());
        
        for(ClientInputHandler h: clientInputHandlers)
            h.start();
        for(ClientOutputHandler h: clientOutputHandlers) 
            h.start();

        long lastTurnTime = 0;
        
        while(!isInterrupted()) {
            long t = lastTurnTime + MIN_TURN_DURATION
                    - System.currentTimeMillis();
            if (t < 0) t = 0;
            try {
                sleep(t);
            } catch (InterruptedException e) { break; }
            lastTurnTime = System.currentTimeMillis();
            
            Turn turn;
            synchronized(currentCommands) {
                turn = new Turn(currentCommands, 0.5f, debug++);
                currentCommands.clear();
            }
            for(ClientOutputHandler h: clientOutputHandlers)
                h.nextTurn(turn);
        }

        for(ClientInputHandler h: clientInputHandlers) 
            h.interrupt();
        for(ClientOutputHandler h: clientOutputHandlers) 
            h.interrupt();
    }
}
