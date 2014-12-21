package com.mcorbridge.firebasetest;

import android.content.Intent;
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
}
