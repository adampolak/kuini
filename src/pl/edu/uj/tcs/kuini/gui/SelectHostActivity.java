package pl.edu.uj.tcs.kuini.gui;

import android.R;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectHostActivity extends ListActivity {

    private BluetoothAdapter btAdapter;
    private ArrayAdapter<String> hosts;
    
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                hosts.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        hosts = new ArrayAdapter<String>(this, R.layout.simple_list_item_1);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        
        setListAdapter(hosts);
        
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        
        if (btAdapter.isDiscovering())
            btAdapter.cancelDiscovery();
        
        btAdapter.startDiscovery();
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btAdapter.isDiscovering())
            btAdapter.cancelDiscovery();
        unregisterReceiver(receiver);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String info = ((TextView) v).getText().toString();
        String address = info.substring(info.length() - 17);
        Intent intent = new Intent(this, KuiniActivity.class);
        intent.putExtra("game", KuiniActivity.JOIN_GAME);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        intent.putExtra("device", device);
        startActivity(intent);
        finish();
    }
    
}
