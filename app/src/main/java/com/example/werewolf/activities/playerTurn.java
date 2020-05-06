package com.example.werewolf.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.werewolf.R;
import com.example.werewolf.adapter.playersAdapter;
import com.example.werewolf.model.Players;
import com.example.werewolf.model.PlayersArray;
import com.example.werewolf.model.startPlayers;
import com.example.werewolf.model.startPlayersArray;
import com.example.werewolf.utils.itemClickSupport;

import java.util.ArrayList;

public class playerTurn extends AppCompatActivity {
    int ct;
    TextView roleName, playerNameAction, playerFriends;
    String rolestring, actionedplayer;
    String friends = "Werewolf : ";
    startPlayers sp;
    Integer index;
    ArrayList<Players> thePlayers;
    ArrayList<startPlayers> dePlayers;
    Button actionbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_turn);
        playerNameAction = findViewById(R.id.playernameaction);
        actionbtn = findViewById(R.id.confirmactionbtn);
        playerFriends = findViewById(R.id.playerfriends);
        playerFriends.setVisibility(View.GONE);
        index = startPlayersArray.index;
        sp = startPlayersArray.theData.get(index);
        rolestring = sp.getSrole();
        actionbtn = findViewById(R.id.confirmactionbtn);
        roleName = findViewById(R.id.playerrolename);
        thePlayers = PlayersArray.theData;
        dePlayers = startPlayersArray.theData;

        if (ct == 0) {
            for (int i = 0; i < startPlayersArray.werewolves.size(); i++) {
                if (i == 0) {
                    friends += startPlayersArray.werewolves.get(i);
                } else {
                    friends += ", " + startPlayersArray.werewolves.get(i);
                }
            }
            ct++;
        }

        if (rolestring.equals("ww")) {
            playerFriends.setVisibility(View.VISIBLE);
            playerFriends.setText(friends);
            actionbtn.setText(R.string.actionkill);
            roleName.setText(R.string.roleww);
        } else if (rolestring.equals("sr")) {
            actionbtn.setText(R.string.actionselect);
            roleName.setText(R.string.rolesr);
        } else if (rolestring.equals("kn")) {
            actionbtn.setText(R.string.actionguard);
            roleName.setText(R.string.roleknight);
        } else if (rolestring.equals("ht")) {
            actionbtn.setText(R.string.actionkill);
            roleName.setText(R.string.roleht);
        } else if (rolestring.equals("vg")) {
            actionbtn.setText(R.string.actiondone);
            roleName.setText(R.string.rolevg);
        }


        RecyclerView myrv = findViewById(R.id.rv_turn);
        playersAdapter myAdapter = new playersAdapter(this, thePlayers);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);
        itemClickSupport.addTo(myrv).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                playerNameAction.setText(thePlayers.get(position).getPname());
                actionedplayer = thePlayers.get(position).getPname();
                startPlayersArray.seered = dePlayers.get(position).getSrole();
                startPlayersArray.seeredplayer = dePlayers.get(position).getSname();
            }
        });

        actionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rolestring.equals("sr")) {
                    Intent intent = new Intent(playerTurn.this, seerAction.class);
                    startActivity(intent);
                } else {
                    if (startPlayersArray.index+1 < startPlayersArray.theData.size()) {
                        startPlayersArray.index++;
                        Intent intent = new Intent(playerTurn.this, playerPrompt.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(playerTurn.this, finishTurn.class);
                        startActivity(intent);
                    }

                }
            }
        });

    }
}
