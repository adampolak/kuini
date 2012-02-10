package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.model.factories.IPlayerStub;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.model.factories.PlayerStub;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class HostGame extends AbstractGame {

    public static final int HOST_PLAYER_ID = 1;
    public static final int GUEST_PLAYER_ID = 2;
    
    private final BluetoothAdapter btAdapter;
    private final int N;
    
    public HostGame(IView view, BluetoothAdapter btAdapter, int N) {
        super(view);
        this.btAdapter = btAdapter;
        this.N = N;
    }
    
    @Override
    public void run() {
        
        BluetoothServerSocket serverSocket = null;
        BluetoothSocket socket = null;
        
        try {
        
            ControllersServer server = new ControllersServer();
            createLocalController(server, HOST_PLAYER_ID);
            
            serverSocket = 
                    btAdapter.listenUsingInsecureRfcommWithServiceRecord("Kuini", KUINI_UUID);
            socket = serverSocket.accept();
            serverSocket.close();
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            server.addPlayer(in, out, GUEST_PLAYER_ID);
            
            model = new ModelFactory().getModel(
                    new IPlayerStub[]{
                            new PlayerStub("Host", HOST_PLAYER_ID),
                            new PlayerStub("Guest", GUEST_PLAYER_ID),
                    }, 800.0f/480.0f, "ANTS!", 1.0f, true);
            
            server.start();
            
            view.gameStarted(HOST_PLAYER_ID);
            
            controller.run();
            
            server.interrupt();
            
            view.gameFinished();

        } catch (Exception e) {
            
            Log.i("HostGame", Log.getStackTraceString(e));
            view.gameFinished();
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
