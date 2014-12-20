package com.mcorbridge.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.mcorbridge.firebasetest.adapters.CustomAdapter;
import com.mcorbridge.firebasetest.vo.Player;

import java.util.ArrayList;


public class ReadActivity extends ActionBarActivity {

    Firebase firebase;
    Intent intent;
    ArrayList<Player> players;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);



        savedInstanceState = getIntent().getExtras();
        players = (ArrayList<Player>)savedInstanceState.getSerializable("players");
        //ArrayList<String> leafs = (ArrayList<String>)savedInstanceState.getSerializable("leafs");

        ArrayAdapter<Player> itemsAdapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, leafs);

        ListView listView = (ListView) findViewById(R.id.bruinList);
        listView.setAdapter(itemsAdapter);

        final CustomAdapter adapter = new CustomAdapter(this, players);
        //final CustomAdapter adapter = new CustomAdapter(this, leafs);

        listView.setAdapter(adapter);
        intent = new Intent(this, ModifyActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

                CustomAdapter customAdapter = (CustomAdapter)parent.getAdapter();
                Player player = customAdapter.getItem(position);


                //Player player = players.get(position);
                intent.putExtra("player",player);
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
//                CustomAdapter customAdapter = (CustomAdapter)parent.getAdapter();
//                System.out.println(customAdapter.getItem(position).toString());
//                //intent.putExtra("player", player);
//                //startActivity(intent);
//            }
//        });

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
}
