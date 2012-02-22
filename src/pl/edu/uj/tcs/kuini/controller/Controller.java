package pl.edu.uj.tcs.kuini.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Model;

public class Controller {

    public interface StateChangeListener {
        void stateChanged(IState state);
    }
    
    private final StateChangeListener view;

    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    private final Model model;

    /*
    private final BlockingQueue<Serializable> sendingQueue = 
            new LinkedBlockingQueue<Serializable>();
            */

    private final Queue<Command> sendingQueue = 
            new LinkedList<Command>();
    
    /*
    private final Thread sender = new Thread() {
        @Override
        public void run() {
            while(!isInterrupted()) {
                try {
                    out.writeObject(sendingQueue.take());
                } catch (Exception e) { break; }
            }
        }
    };
    */

    public Controller(ObjectInputStream in, ObjectOutputStream out, Model model, StateChangeListener view) {
        this.in = in;
        this.out = out;
        this.model = model;
        this.view = view;
    }

    public void proxyCommand(Command command) throws IllegalStateException {
        // sendingQueue.add(command);
        synchronized(sendingQueue) { 
            sendingQueue.add(command);
        }
    }

    public void run() {
        //sender.start();
        view.stateChanged(model.getState());
        while(!Thread.interrupted()) {
            
            // sendingQueue.add(new ReadyForNextTurn());
            
            Command currCommand;
            synchronized(sendingQueue) {
                currCommand = sendingQueue.poll();
            }
            try {
                if (currCommand != null)
                    out.writeObject(currCommand);
                out.writeObject(new ReadyForNextTurn());
            } catch (IOException e) { break; }

            Turn turn;
            try {
                turn = (Turn)in.readObject();
            } catch (Exception e) { break; }
            
            for(Command command: turn) model.doCommand(command);
            model.nextTurn(turn.getElapsedTime());
            view.stateChanged(model.getState());
            if (model.getState().isGameEnded()) break;
            Thread.yield();
        }
        //sender.interrupt();
    }

}
