package com.mcorbridge.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mcorbridge.firebasetest.vo.Player;


public class ModifyActivity extends ActionBarActivity {

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        savedInstanceState = getIntent().getExtras();
        player = (Player)savedInstanceState.getSerializable("player");
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

}
