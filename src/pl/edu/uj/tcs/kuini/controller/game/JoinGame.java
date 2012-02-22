package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.edu.uj.tcs.kuini.controller.Controller;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class JoinGame extends AbstractGame {

    private final BluetoothDevice device;
    
    public JoinGame(IView view, BluetoothDevice device) {
        super(view);
        this.device = device;
    }
    
    @Override
    public void run() {
        BluetoothSocket socket = null;
                
        try {
        
            socket = device.createRfcommSocketToServiceRecord(KUINI_UUID);
            socket.connect();
        
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            out.writeObject(playerName);
            
            int playerId = (Integer)in.readObject();
            model = ModelFactory.getModel((ModelFactory.Arguments)in.readObject());
            
            controller = new Controller(in, out, model, stateChangeListener);
            view.gameStarted(playerId);
            controller.run();

            view.gameFinished();
            
        } catch (Exception e) { /* IOException, ClassNotFoundException, ClassCastException */
            view.gameFinished();
        }
        
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {}
    }
    
}
