package com.echec.echecmulti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    String CompartPlayer="";
    String roomName = "";
    String role = "";
    String message = "";
    Button buttonqui;
    FirebaseDatabase database;//pour se connecter as la BDD
    DatabaseReference messageRef;//pour faire référence as la BDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //tout les méthode que nous allons utiliser
        initialistions();
        extragerer();
        POKEBTN();
        quiterBTN();
        message();

    }

    private void extragerer(){
        Bundle extra = getIntent().getExtras();//récuper l'extrat envoiller par roomActivity
        if(extra != null){
            roomName = extra.getString("roomName");;//récupére la valeur envoiller
            CompartPlayer = extra.getString("playerhost");
            if(CompartPlayer.equals(playerName))//teste pour savoir si ces le joueur1 ou 2
                role = "host";
            else
                role = "guest";
        }

    }
    private void POKEBTN(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //afficher un message
                button.setEnabled(false);//mais le bouton a false une foit que la personne as cliquer sur le bouton
                messageRef.setValue(role);//change l'informatiosn dans la BDD
            }
        });
    }

    private void quiterBTN(){

        buttonqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bouton pour quiter l'applications
            }
        });
    }

    private void initialistions(){
        button = findViewById(R.id.buttonPoke);//prend le bouton
        button.setEnabled(false);//mais le bouton a inclicable

        database = FirebaseDatabase.getInstance();//créer une instance
        buttonqui = findViewById(R.id.quiter);//récupére le bouton quiter

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
    }

    private void message(){
        messageRef = database.getReference("rooms/"+roomName+"/message");//crée le message de la BDD
        messageRef.setValue(role);//change l'informatiosn dans la BDD
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //message recu
                if(role.equals("host")){//teste si le joueur est l'host ou pas
                    if(snapshot.getValue().toString().contains("guest")){//regarde si l'endroit ou les donnée a changer contient guest
                        button.setEnabled(true);//si oui il fait que le bouton est cliquable
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("guest","a toi de jouer"), Toast.LENGTH_SHORT).show();

                    }
                }else{
                    if(snapshot.getValue().toString().contains("host")){//regarde si l'endroit ou les donnée a changer contient host:
                        button.setEnabled(true);//mais le bouton as clicable
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("host","a toi de jouer"), Toast.LENGTH_SHORT).show();

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