package com.echec.echecmulti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.echec.echecmulti.Connection.Profile;
import com.echec.echecmulti.Room.RoomActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Resultat extends AppCompatActivity {
    TextView textView;
    Button profil;
    Button Rooms;
    FirebaseDatabase database;//pour se connecter as la BDD
    DatabaseReference messageRef;//pour faire référence as la BDD
    String playerName="";
    String roomName="";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    DatabaseReference roomsRef;//référence as la base de donnée pour les room
    boolean cpt=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        textView = findViewById(R.id.voud);
        profil =findViewById(R.id.Profilvoud);
        Rooms = findViewById(R.id.Roomsvoud);
        database = FirebaseDatabase.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        addplayersEventLisener();

        String douv ="";

        Bundle extra = getIntent().getExtras();//récuper l'extrat envoiller par roomActivity
        if(extra != null) {
            douv = extra.getString("douv");
            playerName = extra.getString("playerName");
            roomName = extra.getString("roomName");
        }

        textView.setText(douv);


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                finish();
            }
        });

        Rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
                finish();
            }
        });
    }

    private void addplayersEventLisener(){
        roomsRef = database.getReference("players");//on récupére la référence de rooms
        roomsRef.addValueEventListener(addPlayersEvent());
    }
    private ValueEventListener addPlayersEvent(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //controle de la liste des room
                ArrayList<String> nomPlayer =  new ArrayList<>();
                String chaine = snapshot.getValue().toString().substring(snapshot.getValue().toString().indexOf(","),snapshot.getValue().toString().length());
                nomPlayer.add(snapshot.getValue().toString().substring(1,snapshot.getValue().toString().indexOf(",")));
                boolean cpt=true;
                while (cpt==true){
                    chaine = chaine.substring(2,chaine.length());
                    if (!chaine.contains(",")){
                        nomPlayer.add(chaine.substring(0,chaine.indexOf("}")));
                        cpt=false;
                    }else{
                        nomPlayer.add(chaine.substring(0,chaine.indexOf(",")));
                        chaine = chaine.substring(chaine.indexOf(","),chaine.length());
                    }
                }
                boolean ff=true;
                for (int i = 0; i < nomPlayer.size(); i++) {
                    if(nomPlayer.get(i).contains(playerName+"=Defaite")){//verifie si il y as deux joueur
                        //incrémenter défaite
                        addDefeat();
                        DatabaseReference PlayerRef = database.getReference("players/"+nomPlayer.get(i).substring(0,nomPlayer.get(i).indexOf("=")));
                        PlayerRef.setValue("");
                    }else if (nomPlayer.get(i).contains(playerName+"=Victoir")){
                        //incrémenter victoir
                        addVictory();
                        DatabaseReference PlayerRef = database.getReference("players/" + nomPlayer.get(i).substring(0,nomPlayer.get(i).indexOf("=")));
                        PlayerRef.setValue("");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //erreur rien
            }
        };
    }

            private void addDefeat(){
        //Recherche dans la collection users de la BD à l'aide de de la variable userId
                DocumentReference documentReference = fStore.collection("users").document(userId);
                documentReference.addSnapshotListener((documentSnapshot, e) -> {
                if(documentSnapshot.exists() && cpt==false)
                {
                    Integer loses = documentSnapshot.getLong("loses").intValue();
                    documentReference.update("loses",loses+1);
                    cpt=true;
                    Intent intent = new Intent(getApplicationContext(), Resultat.class);//créations de la page Game
                    intent.putExtra("douv", "Defaite");//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                    startActivity(intent);
                    finish();
                }
            });


    }

    private void addVictory()
    {
        //Recherche dans la collection users de la BD à l'aide de de la variable userId
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener((documentSnapshot, e) -> {
            if(documentSnapshot.exists() && cpt==false)
            {
                Integer victories = documentSnapshot.getLong("victories").intValue();
                documentReference.update("victories",victories+1);
                cpt=true;
                Intent intent = new Intent(getApplicationContext(), Resultat.class);//créations de la page Game
                intent.putExtra("douv", "Victoir");//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                startActivity(intent);
                finish();
            }
        });

    }
}