package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.edu.uj.tcs.kuini.controller.Controller;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

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
        
            socket = device.createInsecureRfcommSocketToServiceRecord(KUINI_UUID);
            socket.connect();
        
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            /*
            out.writeObject((String)getPlayerName());
            int playerId = in.readInt();
            model = (IModel) in.readObject();
            */
            
            int playerId = HostGame.GUEST_PLAYER_ID;
            model = new ModelFactory().getModel();
            /*
            model = new ModelFactory().getModel(
                    new IPlayerStub[]{
                            new PlayerStub("Host", PlayerColor.RED),
                            new PlayerStub(getPlayerName(), PlayerColor.BLUE),
                    }, 800.0f/480.0f, "ANTS!"
            );
            */

            controller = new Controller(in, out, model, stateChangeListener);
            view.gameStarted(playerId);
            controller.run();

            view.gameFailed();
            
        } catch (Exception e) { /* IOException, ClassNotFoundException, ClassCastException */
            Log.i("JoinGame", Log.getStackTraceString(e));
            view.gameFailed();
        }
        
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {}
    }
    
}
