package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class HostGame extends AbstractGame {

    private static final int HOST_PLAYER_ID = 1;
    public static final int GUEST_PLAYER_ID = 2;
    
    private final BluetoothAdapter btAdapter;
    
    public HostGame(IView view, BluetoothAdapter btAdapter) {
        super(view);
        this.btAdapter = btAdapter;
    }
    
    @Override
    public void run() {
        
        BluetoothServerSocket serverSocket = null;
        BluetoothSocket socket = null;
        
        try {
        
            serverSocket = 
                    btAdapter.listenUsingInsecureRfcommWithServiceRecord("Kuini", KUINI_UUID);
            socket = serverSocket.accept();
            serverSocket.close();
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            // String guestPlayerName = (String)in.readObject();
            // String guestPlayerName = "Guest";
            
            model = new ModelFactory().getModel();
            /*
            model = new ModelFactory().getModel(
                    new IPlayerStub[]{
                            new PlayerStub(getPlayerName(), PlayerColor.RED),
                            new PlayerStub(guestPlayerName, PlayerColor.BLUE),
                    }, 800.0f/480.0f, "ANTS!"
            );
            */

            /*
            out.writeInt(GUEST_PLAYER_ID);
            out.writeObject(model);
            */
            
            ControllersServer server = new ControllersServer();
            createLocalController(server, HOST_PLAYER_ID);
            server.addPlayer(in, out, GUEST_PLAYER_ID);
            
            server.start();
            
            view.gameStarted(HOST_PLAYER_ID);
            
            controller.run();
            
            server.interrupt();
            
            view.gameFailed();

        } catch (Exception e) {
            
            Log.i("HostGame", Log.getStackTraceString(e));
            view.gameFailed();
        }
        
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {}

        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {}
        
        
    }

}
