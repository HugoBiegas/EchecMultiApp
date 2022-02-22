package com.echec.echecmulti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomActivity extends AppCompatActivity {
    ListView listView;//liste des View
    Button button;//bouton de créations de room
    List<String> roomList = new ArrayList<>();//liste des room
    String playerName="";//nom du joueur
    String roomName="";//nom de la room
    FirebaseDatabase database;//connections a la base de données
    DatabaseReference roomRef;//référence as la base de donnée pour une room
    DatabaseReference roomsRef;//référence as la base de donnée pour les room


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initialisation();
        CréationRoom();
        ItemCliquer();
        addRoomsEventListener();
    }

    private void ItemCliquer(){
        //si une personne clique sur un itéme de la liste des room
        listView.setOnItemClickListener(Itemcréjoueur2());
    }

    private AdapterView.OnItemClickListener Itemcréjoueur2(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //créations du joueur 2
                roomName = roomList.get(i);//on donne as roomName le nom de l'endroi ou a cliquer la personne
                roomRef = database.getReference("rooms/" + roomName + "/player2");//on crée le player2 dans la partie
                addRoomEventListener();//appelle de l'actions pour changer de page
                roomRef.setValue(playerName);//on mais la valeur de player2 aux nom du joueur
            }
        };
    }

    private void CréationRoom(){
        //si le bouton de créations de room est cliquer
        button.setOnClickListener(cliqueCrée());
    }

    private View.OnClickListener cliqueCrée(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //créations de la room et assigniations du joueur 1
                button.setText("créations de room");
                button.setEnabled(false);
                finish();
                startActivity(new Intent(getApplicationContext(),CreaRoom.class));//on lance l'activiter RooomActivity

            }
        };
    }

    private void initialisation(){
        database = FirebaseDatabase.getInstance();
        //récupérer le nom du joueur et donner le nom du joueur pour la room
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        listView = findViewById(R.id.listRoom);//affectations de la liste des room
        button = findViewById(R.id.buttonCreateRoom);//affectations du bonton
    }

    private void addRoomEventListener(){
        roomRef.addValueEventListener(addRoomEvent());
    }

    private ValueEventListener addRoomEvent(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //rejoidre une room
                button.setText("crations de room");//changement du bouton
                button.setEnabled(true);//changement du bouton pour le rendre non-cliquable
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);//créations de la page Game
                intent.putExtra("roomName",roomName);//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                intent.putExtra("playerhost","");//on donne en extrat la valeur de la roomName pour savoir si la personne et un gest ou l'host
                startActivity(intent);//on lance l'activiter

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ereur
                button.setText("creations de room");//changement du bouton
                button.setEnabled(true);//changement du bouton pour le rendre non-cliquable
                Toast.makeText(RoomActivity.this,"erreur!",Toast.LENGTH_SHORT).show();//message d'erreur

            }
        };
    }

    private void addRoomsEventListener(){
    roomsRef = database.getReference("rooms");//on récupére la référence de rooms
        roomsRef.addValueEventListener(addRoomsEvent());
    }
    private ValueEventListener addRoomsEvent(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //controle de la liste des room
                roomList.clear();//on enléve de la vue de l'utilisteur tout les rooms
                Iterable<DataSnapshot> rooms = snapshot.getChildren();// on prend l'état des rooms as un moment donner
                for (DataSnapshot snapshot1 : rooms){// on vas regarder tout les room existante
                    if(!snapshot1.getValue().toString().contains("player2")){//verifie si il y as deux joueur
                        roomList.add(snapshot1.getKey());//on vas rajouter les room une par une dans la liste
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_list_item_1,roomList);//créer une room dans roomActivity avec la liste roomList
                        listView.setAdapter(adapter);//ajoute un élément dans la liste
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //erreur rien
            }
        };
    }
}