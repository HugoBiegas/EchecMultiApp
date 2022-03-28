package com.echec.echecmulti.Connection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echec.echecmulti.R;
import com.echec.echecmulti.Room.RoomActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class connexiontest extends AppCompatActivity {
    EditText editText; //nom de l'utilisateur
    Button button;  //bouton de connections
    String playerName="";   //le nom du joueur
    FirebaseDatabase database; // connections as la BDD
    DatabaseReference playerRef;// référence de la BD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexiontest);
        //regarder si le joueur existe
        editText = findViewById(R.id.editTextLogin);//récupérations du champs nom
        button = findViewById(R.id.buttonLogin);//récupérations du bouton(pour l'actions)

        database = FirebaseDatabase.getInstance();//créations de l'instance

        //action appret avoir cliquer sur connection
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerName = editText.getText().toString();//récupérations du nom de l'utilisateur
                editText.setText("");//remise as "" du texte
                if(!playerName.equals("")){//on vérifie bien que l'utilisateur as sési un truque
                    button.setText("connections en cours ...");//changement du bouton connections en connections en cours
                    button.setEnabled(false);//pour que le bouton ne soit pas clicable une dexiéme foi
                    playerRef = database.getReference("players/" + playerName);//créations de players avec une sous catégori avec le nom du joueur
                    addEventListener();//appelle de fonctions
                    playerRef.setValue("");//on mais la référence du joueur a ""(on pourat mettre d'autre info)

                }
            }
        });

    }
    //créations de l'endroit ou on vas gérer les évenement
    public void addEventListener(){
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //succer - continue sur le prochain écran et sauvegarde le nom du joueur
                SharedPreferences preferences = getSharedPreferences("PREFS",0);//permet de modifier un ensemble particulier
                SharedPreferences.Editor editor = preferences.edit();//permet de changer la valeur contenut dans preferences
                editor.putString("playerName", playerName);//remplace le nom du joueur par le vraix nom du joueur
                editor.apply();//applique le changement de nom de joueur
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));//on lance l'activiter RooomActivity
                finish();//on arréte la tache actuelle
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //erreur
                button.setText("connections");//change le texte du bouton
                button.setEnabled(true);//rend visible le bouton
                Toast.makeText(connexiontest.this,"erreur!",Toast.LENGTH_SHORT).show();//affiche un message d'erreur
            }
        });
    }
}