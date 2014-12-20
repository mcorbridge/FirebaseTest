package com.mcorbridge.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;
import com.mcorbridge.firebasetest.vo.Player;

import java.util.HashMap;
import java.util.Map;


public class DeleteActivity extends ActionBarActivity {

    Firebase firebase;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        savedInstanceState = getIntent().getExtras();
        player = (Player)savedInstanceState.getSerializable("player");
        firebase = new Firebase("https://burning-fire-2704.firebaseio.com/").child("teams/bruins/players");
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
                return super.onOptionsItemSelected(item);
        }
    }

    public void deletePlayer(View v){
        Map<String, String> updates = new HashMap<String, String>();
        updates.put("lname", null);
        updates.put("fname", null);
        Map<String, Object> playerUpdate = new HashMap<String, Object>();
        playerUpdate.put(player.getUUID(),updates);
        firebase.updateChildren(playerUpdate);
    }
}
