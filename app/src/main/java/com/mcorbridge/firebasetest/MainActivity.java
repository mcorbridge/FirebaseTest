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
import com.mcorbridge.firebasetest.vo.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    Intent intent;
    Firebase firebase;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



    }

    public void doFirebase(View view){
        // Firebase !!
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://burning-fire-2704.firebaseio.com/teams/bruins/players");

        intent = new Intent(this, ReadActivity.class);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Player> players = snapShotToArray(dataSnapshot);
                intent.putExtra("players",players);
                startActivity(intent);
                //removeEventListener();
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
                Collection c = m.values();
                Object[] names = c.toArray();
                //String name = names[1] + " " + names[0] + "/" + pairs.getKey();
                Player player = new Player();
                player.setFname(names[1].toString());
                player.setLname(names[0].toString());
                player.setUUID(pairs.getKey().toString());
                //bruins.add(name);
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
