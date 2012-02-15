package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.model.PlayerStub;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class HostGame extends AbstractGame {

    public static final int HOST_PLAYER_ID = 1;
    public static final int GUEST_PLAYER_ID = 2;
    
    private final BluetoothAdapter btAdapter;
    
    /* Configuration: */
    private final int playersN;
    private final float gameSpeed;
    private final boolean healAnts;
    
    public HostGame(IView view, BluetoothAdapter btAdapter, int playersN, float gameSpeed, boolean healAnts) {
        super(view);
        this.btAdapter = btAdapter;
        this.playersN = 2; //playersN;
        this.gameSpeed = 1.0f; //gameSpeed;
        this.healAnts = true; //healAnts;
    }
    
    @Override
    public void run() {
        
        BluetoothServerSocket serverSocket = null;
        BluetoothSocket socket = null;
        
        try {
        
            PlayerStub players[] = new PlayerStub[playersN];
            
            ControllersServer server = new ControllersServer();
            createLocalController(server, HOST_PLAYER_ID);
            
            players[0] = new PlayerStub("Host", 1);
            
            for(int i=0; i<playersN-1; i++) {
            
                serverSocket = 
                        btAdapter.listenUsingInsecureRfcommWithServiceRecord("Kuini", KUINI_UUID);
                socket = serverSocket.accept();
                serverSocket.close();
            
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
                server.addPlayer(in, out, 2 + i);
                
                players[1+i] = new PlayerStub("Guest", 2 + i);
            
            }
            
            model = new ModelFactory().getModel(players, 800.0f/480.0f, "ANTS!", gameSpeed, healAnts);
            
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
