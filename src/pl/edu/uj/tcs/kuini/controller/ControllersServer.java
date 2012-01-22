package pl.edu.uj.tcs.kuini.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pl.edu.uj.tcs.kuini.model.Command;

public class ControllersServer extends Thread {
    
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
                    Command c = (Command)in.readObject();
                    if (c.getPlayerId() != playerId) break;
                    synchronized(currentCommands) {
                        currentCommands.add(c);
                    }
                } catch (Exception e) { break; }
            }
            try { in.close(); } catch (IOException e) {}
        }
    }

    private class ClientOutputHandler extends Thread {

        private final ObjectOutputStream out;
        private final BlockingQueue<List<Command>> queue 
            = new LinkedBlockingQueue<List<Command>>();

        public ClientOutputHandler(ObjectOutputStream out) {
            this.out = out;
        }

        public void nextTurn(List<Command> commands) {
            queue.add(commands); 
            /* It may throw IllegalStateException if capacity of
             * a queue is exceeded. It should not happen, thus
             * I guess it is appropriate behavior.                  */
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
        }

    }

    private final long waitingTime;
    private final List<Command> currentCommands = 
            new ArrayList<Command>();

    private final List<ClientInputHandler> clientInputHandlers = 
            new ArrayList<ClientInputHandler>();
    private final List<ClientOutputHandler> clientOutputHandlers = 
            new ArrayList<ClientOutputHandler>();


    public ControllersServer(long waitingTime) {
        this.waitingTime = waitingTime;
    }
 
    void addPlayer(ObjectInputStream in, ObjectOutputStream out, int playerId) {
        if (getState() != Thread.State.NEW) throw new IllegalStateException();
        clientInputHandlers.add(new ClientInputHandler(in, playerId));
        clientOutputHandlers.add(new ClientOutputHandler(out));
    }

    @Override
    public void run() {
        for(ClientInputHandler h: clientInputHandlers)
            h.start();
        for(ClientOutputHandler h: clientOutputHandlers) 
            h.start();

        while(!isInterrupted()) {
            synchronized(currentCommands) {
                List<Command> moves = new ArrayList<Command>(currentCommands);
                for(ClientOutputHandler h: clientOutputHandlers)
                    h.nextTurn(moves);
                currentCommands.clear();
            }
            try {
                sleep(waitingTime);
            } catch (InterruptedException e) { break; }
        }

        for(ClientInputHandler h: clientInputHandlers) 
            h.interrupt();
        for(ClientOutputHandler h: clientOutputHandlers) 
            h.interrupt();
    }
}
