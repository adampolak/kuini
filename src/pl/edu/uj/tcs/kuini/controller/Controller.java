package pl.edu.uj.tcs.kuini.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.Model;
import pl.edu.uj.tcs.kuini.model.IState;

public class Controller {

    public interface StateChangeListener {
        void stateChanged(IState state);
    }
    
    private final StateChangeListener view;

    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    private final Model model;


    private final BlockingQueue<Serializable> sendingQueue = 
            new LinkedBlockingQueue<Serializable>();

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

    public Controller(ObjectInputStream in, ObjectOutputStream out, Model model, StateChangeListener view) {
        this.in = in;
        this.out = out;
        this.model = model;
        this.view = view;
    }

    public void proxyCommand(Command command) throws IllegalStateException {
        sendingQueue.add(command);
    }

    public void run() {
        sender.start();
        view.stateChanged(model.getState());
        while(!Thread.interrupted()) {
            sendingQueue.add(new ReadyForNextTurn());

            Turn turn;
            try {
                turn = (Turn)in.readObject();
            } catch (Exception e) { break; }
            
            for(Command command: turn) model.doCommand(command);
            model.nextTurn(turn.getElapsedTime());
            view.stateChanged(model.getState());
            if (model.getState().isGameEnded()) break;
            // Thread.yield();
        }
        sender.interrupt();
    }

}
