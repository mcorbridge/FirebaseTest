package com.mcorbridge.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.mcorbridge.firebasetest.model.ApplicationModel;
import com.mcorbridge.firebasetest.vo.Player;

import java.util.HashMap;
import java.util.Map;


public class UpdateActivity extends ActionBarActivity {

    Firebase firebase;
    Player player;
    EditText editTextFname;
    EditText editTextLname;
    ApplicationModel applicationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        applicationModel = ApplicationModel.getInstance();
        String applicationTeam = applicationModel.getApplicationTeam();

        editTextFname = (EditText) findViewById(R.id.fname);
        editTextLname = (EditText) findViewById(R.id.lname);

        savedInstanceState = getIntent().getExtras();
        player = (Player)savedInstanceState.getSerializable("player");

        editTextFname.setText(player.getFname());
        editTextLname.setText(player.getLname());

        firebase = new Firebase("https://burning-fire-2704.firebaseio.com/teams/" + applicationTeam + "/players");


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

    public void updatePlayer(View v){

        editTextFname = (EditText) findViewById(R.id.fname);
        editTextLname = (EditText) findViewById(R.id.lname);

        String fname = editTextFname.getText().toString();
        String lname = editTextLname.getText().toString();

        Map<String, String> updates = new HashMap<String, String>();
        updates.put("fname", fname);
        updates.put("lname", lname);
        Map<String, Object> playerUpdate = new HashMap<String, Object>();
        playerUpdate.put(player.getUUID(),updates);
        firebase.updateChildren(playerUpdate);
    }
}
