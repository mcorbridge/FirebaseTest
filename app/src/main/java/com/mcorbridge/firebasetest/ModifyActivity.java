package com.mcorbridge.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mcorbridge.firebasetest.vo.Player;

import java.util.Collection;
import java.util.Map;


public class ModifyActivity extends ActionBarActivity {

    private Player player;
    private String playerUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        savedInstanceState = getIntent().getExtras();
        player = (Player)savedInstanceState.getSerializable("player");

        Firebase.setAndroidContext(this);
        Firebase firebase = new Firebase("https://burning-fire-2704.firebaseio.com/");

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                DataSnapshot players = snapshot.child("teams/bruins/players");
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("******************************** the read failed ********************************");
                System.out.println(firebaseError.getMessage());
            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updatePlayer(View v){
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("player",player);
        startActivity(intent);
    }

    public void deletePlayer(View v){
        Intent intent = new Intent(this, DeleteActivity.class);
        intent.putExtra("player",player);
        startActivity(intent);
    }

    private String getPlayerUUID(String selectedText, DataSnapshot players){
        String playerUUID = null;
        for (DataSnapshot child : players.getChildren()) {
            Map<String, String> m = (Map<String, String>) child.getValue();
            if (m == null) {
                System.out.println("fucking null!");
            } else {
                Collection c = m.values();
                Object[] names = c.toArray();
                String name = names[1] + " " +names[0];
                if(selectedText.equals(name)){
                    playerUUID = child.getKey();
                    break;
                }
            }
        }
        return playerUUID;
    }
}
