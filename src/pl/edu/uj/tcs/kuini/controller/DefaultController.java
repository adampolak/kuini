package pl.edu.uj.tcs.kuini.controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.view.IGamePlayView;

public class DefaultController extends Thread implements IController {

    private final IGamePlayView view;

    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    private final IModel model;
    private volatile IState latestState;


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

    public DefaultController(ObjectInputStream in, ObjectOutputStream out, IModel model, IGamePlayView view) {
        this.in = in;
        this.out = out;
        this.model = model;
        this.view = view;
    }

    @Override
    public IState getCurrentState() {
        return latestState;
    }

    @Override
    public void proxyCommand(Command command) throws IllegalStateException {
        sendingQueue.add(command);
    }

    @Override
    public void run() {
        sender.start();
        latestState = model.getState();
        if (view != null) view.stateChanged(latestState);
        while(!isInterrupted()) {
            Turn turn;
            try {
                turn = (Turn)in.readObject();
            } catch (Exception e) { break; }
            for(Command command: turn) model.doCommand(command);
            model.nextTurn(turn.getElapsedTime());
            latestState = model.getState();
            if (view != null) view.stateChanged(latestState);
            sendingQueue.add(new IAmReady()); /* Not sure where to put it */
        }
        sender.interrupt();
    }

}
