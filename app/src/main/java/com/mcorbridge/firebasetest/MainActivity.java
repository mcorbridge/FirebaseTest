package com.mcorbridge.firebasetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mcorbridge.firebasetest.model.ApplicationModel;
import com.mcorbridge.firebasetest.vo.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    Intent intent;
    Firebase firebase;
    ValueEventListener valueEventListener;
    ApplicationModel applicationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        applicationModel = ApplicationModel.getInstance();

        checkWiFiConnection();
    }

    public void doFirebaseBruins(View view){
        applicationModel.setApplicationTeam("bruins");
        doFirebase();
    }

    public void doFirebaseLeafs(View view){
        applicationModel.setApplicationTeam("leafs");
        doFirebase();
    }

    public void doFirebase(){
        // Firebase !!
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://burning-fire-2704.firebaseio.com/teams/" + applicationModel.getApplicationTeam() + "/players");

        intent = new Intent(this, ReadActivity.class);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Player> players = snapShotToArray(dataSnapshot);
                intent.putExtra("players",players);
                startActivity(intent);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("******************************** the read failed ********************************");
                System.out.println(firebaseError.getMessage());
            }
        };

        firebase.addListenerForSingleValueEvent(valueEventListener);
    }

    private void removeEventListener(){
        firebase.removeEventListener(valueEventListener);
    }


    private ArrayList<Player> snapShotToArray(DataSnapshot dataSnapshot){
        Map<String,Object> team =  (Map<String,Object>)dataSnapshot.getValue();
        ArrayList<Player> players = new ArrayList<Player>();
        Iterator it = team.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            //System.out.println(pairs.getKey() + " = " + pairs.getValue());
            Map<String, String> m = (Map<String, String>) pairs.getValue();
            if (m == null) {
                System.out.println("fucking null!");
            } else {
                Player player = new Player();
                player.setFname(m.get("fname"));
                player.setLname(m.get("lname"));
                player.setUUID(pairs.getKey().toString());
                players.add(player);
            }
        }
        return players;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(false);
            menu.getItem(2).setEnabled(false);
            menu.getItem(3).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create:
                intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                intent = new Intent(this, DeleteActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_update:
                intent = new Intent(this, UpdateActivity.class);
                startActivity(intent);
                return true;
            default:
                System.out.println("action bar selected");
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkWiFiConnection(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if ( mWifi.isConnected() && !applicationModel.getWifiConnected() ) {
            String[] connectedInfo =  onReceive();
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("You are connected to: " + connectedInfo[0] + " with " + connectedInfo[1] + " bars")
                    .setIcon(R.drawable.emo_im_tongue_sticking_out)
                    .setPositiveButton("nice!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // stub
                        }
                    })
                    .show();
            applicationModel.setWifiConnected(true);
        }else if( !mWifi.isConnected() ){
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("You are NOT connected to WiFi")
                    .setIcon(R.drawable.emo_im_sad)
                    .setPositiveButton("sux", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // stub
                        }
                    })
                    .show();
            applicationModel.setWifiConnected(false);
        }
    }

    public String[] onReceive() {
        int numberOfLevels=5;
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level= WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
        String[] a = new String[2];
        a[0] = wifiInfo.getSSID();
        a[1] = Integer.toString(level);
        return a;
    }
}
