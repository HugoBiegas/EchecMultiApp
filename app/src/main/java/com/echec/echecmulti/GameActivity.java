package com.echec.echecmulti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    Button button;
    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";
    FirebaseDatabase database;
    DatabaseReference messageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button = findViewById(R.id.buttonPoke);
        button.setEnabled(false);

        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            roomName = extra.getString("roomName");
            if(roomName.equals(playerName))
                role = "host";
            else
                role = "guest";
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //afficher un message
                button.setEnabled(false);
                message = role + ":Poked!:";
                messageRef.setValue(message);
            }
        });
        //
        messageRef = database.getReference("rooms/"+roomName+"/message");
        message = role + ":Poked!:";
        messageRef.setValue(message);
        addRoomEventListener();
    }
    private void addRoomEventListener(){
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //message recu
                if(role.equals("host")){
                    if(snapshot.getValue(String.class).contains("guest:")){//regarde si dans la BD il y as guest
                        button.setEnabled(true);
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("gest:",""), Toast.LENGTH_SHORT).show();

                    }
                }else{
                    if(snapshot.getValue(String.class).contains("host:")){//regarde si dans la BD il y as host
                        button.setEnabled(true);
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("host:",""), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //erreur retenter
                messageRef.setValue(message);
            }
        });

    }

}