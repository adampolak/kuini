package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.controller.SerialControllersServer;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.model.factories.PlayerStub;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class HostGame extends AbstractGame {

    private static final int HOST_PLAYER_ID = 1;
    private static final int GUEST_PLAYER_ID = 2;
    
    private final BluetoothAdapter btAdapter;
    
    /* ModelFactory arguments: */
    private final int playersN;
    private final float gameSpeed;
    private final boolean healAnts;
    
    public HostGame(IView view, BluetoothAdapter btAdapter, int playersN, float gameSpeed, boolean healAnts) {
        super(view);
        this.btAdapter = btAdapter;
        this.playersN = playersN;
        this.gameSpeed = gameSpeed;
        this.healAnts = healAnts;
    }
    
    @Override
    public void run() {
        
        BluetoothServerSocket serverSocket = null;
        BluetoothSocket socket = null;
        
        try {
            
            // ControllersServer server = new ControllersServer();
            SerialControllersServer server = new SerialControllersServer();

            PlayerStub players[] = new PlayerStub[playersN];
            players[0] = new PlayerStub(playerName, HOST_PLAYER_ID);
            
            List<ObjectOutputStream> outs = new ArrayList<ObjectOutputStream>();
            
            for(int i=0; i<playersN-1; i++) {
            
                serverSocket = 
                        btAdapter.listenUsingRfcommWithServiceRecord("Kuini", KUINI_UUID);
                socket = serverSocket.accept();
                serverSocket.close();
            
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
                server.addPlayer(in, out, GUEST_PLAYER_ID + i);
                
                String guestPlayerName = (String)in.readObject(); // "Guest";
                out.writeObject(new Integer(GUEST_PLAYER_ID + i));
                
                players[1+i] = new PlayerStub(guestPlayerName, GUEST_PLAYER_ID + i);
                
                outs.add(out);
            
            }
            
            long seed = System.currentTimeMillis();
            ModelFactory.Arguments args = new ModelFactory.Arguments(players, RATIO, seed, gameSpeed, healAnts); 
            model = ModelFactory.getModel(args);

            createLocalController(server, HOST_PLAYER_ID);
            
            for(ObjectOutputStream out: outs)
                out.writeObject(args);
            
            server.start();
            
            view.gameStarted(HOST_PLAYER_ID);
            
            controller.run();
            
            server.interrupt();
            
            view.gameFinished();

        } catch (Exception e) {
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
