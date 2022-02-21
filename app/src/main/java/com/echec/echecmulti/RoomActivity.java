package com.echec.echecmulti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    ListView listView;
    Button button;

    List<String> roomList = new ArrayList<>();
    String playerName="";
    String roomName="";
    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        database = FirebaseDatabase.getInstance();
        //récupérer le nom du joueur et donner le nom du joueur pour la room
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        listView = findViewById(R.id.listRoom);
        button = findViewById(R.id.buttonCreateRoom);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //créations de la room et assigniations du joueur 1
                button.setText("créations de room");
                button.setEnabled(false);
                roomName = playerName;
                roomRef = database.getReference("rooms/" + playerName + "/player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //créations du joueur 2
                roomName = roomList.get(i);
                roomRef = database.getReference("rooms/" + roomName + "/player2");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });
        //controle pour sa voir si la nouvelle room et valable
        addRoomsEventListener();
    }
    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //rejoidre une room
                button.setText("crations de room");
                button.setEnabled(true);
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("roomName",roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ereur
                button.setText("creations de room");
                button.setEnabled(true);
                Toast.makeText(RoomActivity.this,"erreur!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void addRoomsEventListener(){
    roomsRef = database.getReference("rooms");
    roomsRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            //controle de la liste des room
            roomList.clear();
            Iterable<DataSnapshot> rooms = snapshot.getChildren();
            for (DataSnapshot snapshot1 : rooms){
                roomList.add(snapshot1.getKey());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_list_item_1,roomList);
                listView.setAdapter(adapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            //erreur rien
        }
    });
    }
}