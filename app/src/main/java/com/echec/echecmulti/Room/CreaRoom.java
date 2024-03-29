package com.echec.echecmulti.Room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echec.echecmulti.GameActivity;
import com.echec.echecmulti.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreaRoom extends AppCompatActivity {

    String roomName="";//nom de la room
    String playerName="";
    EditText nomDonner;
    FirebaseDatabase database;//connections a la base de données
    DatabaseReference roomRef;//référence as la base de donnée pour une room
    Button button;//bouton de créations de room
    Button buttonQuite;
    boolean ff=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_room);
        initialisations();
        créationderoom();
        quiter();
    }

    private void quiter(){
        buttonQuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonQuite.setEnabled(false);
                finish();
                startActivity(new Intent(getApplicationContext(),RoomActivity.class));
            }
        });
    }
    private void créationderoom(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomName = nomDonner.getText().toString();
                nomDonner.setText("");
                if (!roomName.equals("")){
                    button.setText("en cours de créations...");
                    button.setEnabled(false);
                    roomRef = database.getReference("rooms/"+roomName);//ping un endroit prési de la BDD
                    roomRef.addListenerForSingleValueEvent(contentgetexiste());
                }
            }
        });
    }
    private ValueEventListener contentgetexiste(){
        return new ValueEventListener() {// regarde si sa existe
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){//verifications si la room existe ou pas
                    roomRef = database.getReference("rooms/" + roomName + "/player1");//créations de la room avec le jouer1
                    addRoomEventListener();
                    roomRef.setValue(playerName);
                }else{
                    Toast.makeText(CreaRoom.this, "Room dejat existante", Toast.LENGTH_SHORT).show();
                    button.setText("Créations room");
                    button.setEnabled(true);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
    }

    private void initialisations(){
        button = findViewById(R.id.CreaRoom);
        nomDonner = findViewById(R.id.nomRoom);
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        database = FirebaseDatabase.getInstance();
        buttonQuite = findViewById(R.id.quiter);
    }
    private void addRoomEventListener(){
        roomRef.addValueEventListener(addRoom());
    }

    private ValueEventListener addRoom(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //rejoidre une room
                if(ff==false){
                    button.setText("crations de room");//changement du bouton
                    button.setEnabled(false);//changement du bouton pour le rendre non-cliquable
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);//créations de la page Game
                    intent.putExtra("roomName",roomName);
                    intent.putExtra("playerhost", playerName);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                    startActivity(intent);//on lance l'activiter
                    finish();
                    ff=true;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ereur
                button.setText("creations de room");//changement du bouton
                button.setEnabled(true);//changement du bouton pour le rendre non-cliquable
                Toast.makeText(CreaRoom.this,"erreur!",Toast.LENGTH_SHORT).show();//message d'erreur

            }
        };
    }
}