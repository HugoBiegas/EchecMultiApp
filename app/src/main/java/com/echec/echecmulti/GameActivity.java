package com.echec.echecmulti;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.echec.echecmulti.Pion.Cavalier;
import com.echec.echecmulti.Pion.Dame;
import com.echec.echecmulti.Pion.Foue;
import com.echec.echecmulti.Pion.PetitePion;
import com.echec.echecmulti.Pion.Roi;
import com.echec.echecmulti.Pion.Tour;
import com.echec.echecmulti.Room.RoomActivity;
import com.echec.echecmulti.adapter.adapterGrild;
import com.echec.echecmulti.adapter.adapterMortBlanc;
import com.echec.echecmulti.adapter.adapterMortNoir;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    GridView gridViewMortN;
    GridView gridViewMortB;
    ArrayList<String> MortB = new ArrayList<>();
    ArrayList<String> MortN = new ArrayList<>();
    ArrayList<String> colorActionPion = new ArrayList<>();
    ArrayList<String> echecMath = new ArrayList<>();
    ArrayList<String> PosibiliterH = new ArrayList<>();
    ArrayList<String> PosibiliterG = new ArrayList<>();
    ArrayList<Integer> PositionValble =new ArrayList<>();
    PetitePion pion = new PetitePion();//créer les pion
    Tour tour = new Tour();//créer les tour
    Cavalier cavalier = new Cavalier();//créer les cavalier
    Dame dame = new Dame();//créer les dame
    Foue foue = new Foue();//créer les foue
    Roi roi = new Roi();//créer les roi
    int selectionner=-1;//emplacement celectionner par la personne, initialiser a -1 car nes pas sur le plataux
    int coup=0;//nombre de coup jouer par la personne
    TextView textView;
    Integer positiondepart=0;
    Integer positionarriver=0;
    String[] BordPiece = new String[64];
    String[] colorP = new String[64];
    String playerName = "";
    String CompartPlayer="";
    String roomName = "";
    String role = "";
    String message = "";
    Button buttonqui;
    FirebaseDatabase database;//pour se connecter as la BDD
    DatabaseReference messageRef;//pour faire référence as la BDD
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //tout les méthode que nous allons utiliser
        initialistions();
        echecini();
        initialisationshost();
        extragerer();
        itemaction();
        quiterBTN();
    }

    private boolean echecEtMath(){
        ArrayList<String> teste;
        boolean fin=false;
        for (int i=0;i<64;i++){
            if (BordPiece[i].equals("R") && colorP[i].equals("B"))
                fin =EchecRoitHost(i);
            else if (BordPiece[i].equals("R") && colorP[i].equals("N"))
                fin =EchecRoitGuest(i);
        }
        return fin;
    }
    private boolean EchecRoitHost(int i){
        boolean fin=false;
        ArrayList<String> teste;
        teste =roi.deplacementRoiHost(BordPiece,i,colorP);
        PosibiliterG.clear();
        RechecheGuestP(1);
        //récupérer les déplacement des noir
        //créations de la récupérations des coordoner et lettre
        Integer[] coordonner = new Integer[teste.size()];
        String[] Lettre = new String[teste.size()];
        ArrayList<Integer> coordonnerDep = new  ArrayList<>();
        ArrayList<String> LettreDep = new  ArrayList<>();
        int attaque=-1;
        //boucler pour voir si le roi et toucher
        for (int j = 0; j < teste.size(); j++) {
            coordonner[j] = Integer.parseInt(teste.get(j).substring(teste.get(j).indexOf(":")+1,teste.get(j).length()));
            Lettre[j] = teste.get(j).substring(0,teste.get(j).indexOf(":"));
            for (int k = 0; k < PosibiliterG.size(); k++) {
                coordonnerDep.add(Integer.parseInt(PosibiliterG.get(k).substring(PosibiliterG.get(k).indexOf(":")+1,PosibiliterG.get(k).length())));
                LettreDep.add(PosibiliterG.get(k).substring(0,PosibiliterG.get(k).indexOf(":")));
                if (BordPiece[coordonnerDep.get(k)].equals("R") && LettreDep.get(k).equals("A")){
                    if (k !=0)
                        attaque =coordonnerDep.get(k-1);
                    else
                        attaque = i;
                }
            }
        }
        //pathe
        PosibiliterG.clear();
        if (teste.isEmpty()){
            RechecheHostP(0);
            if (PosibiliterH.isEmpty()){
                fin=true;
                messageRef = database.getReference("players/"+playerName);
                messageRef.setValue("Defaite");
                Toast.makeText(this, "Egaliter", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(),RoomActivity.class));
                messageRef =database.getReference("rooms/"+roomName+"/playerRoom");
                messageRef.setValue("deco:"+playerName+":DP");
            }else
                PosibiliterH.clear();
        }
        //si il y as échec alors on regarde si le roi peux bouger
        if (attaque !=-1) {
            for (int j = 0; j < coordonner.length; j++) {
                if (attaque-14 == coordonner[j])
                    teste.remove("D:" + (attaque-14));
                else if (attaque-16 == coordonner[j])
                    teste.remove("D:" + (attaque-16));
                else if (attaque-18 == coordonner[j])
                    teste.remove("D:" + (attaque-18));
                else if(attaque+18 == coordonner[j])
                    teste.remove("D:" + (attaque+18));
                else if (attaque+16 == coordonner[j])
                    teste.remove("D:" + (attaque+16));
                else if (attaque+14 == coordonner[j])
                    teste.remove("D:" + (attaque+14));
                else if (attaque-2 == coordonner[j] && attaque-1 == i)
                    teste.remove("D:" + (attaque-2));
                else if (attaque+2 == coordonner[j] && attaque+1 == i)
                    teste.remove("D:" + (attaque+2));
            }
            for (int j = 0; j < Lettre.length; j++) {
                //on regarde si ces déplacement sont bon
                if (Lettre[j].equals("A") && colorP[coordonner[j]].equals("B")) {
                    teste.remove(Lettre[j] + ":" + coordonner[j]);
                }
                for (int k = 0; k < coordonnerDep.size(); k++) {
                    //on regarde si les coordonner d'attaque son égale aux rois
                    if (coordonner[j] == coordonnerDep.get(k)) {
                        teste.remove(Lettre[j] + ":" + coordonner[j]);
                    }
                }
            }
            //on regarde si il reste des emplacement
            if(teste.isEmpty()){
                //teste si les piéce de l'host peuve blocker l'attaque
                echecMath.clear();
                PosibiliterH.clear();
                //récupérer les endroit attaquer
                ArrayList<Integer> toucher = new ArrayList<>();
                toucher.clear();
                toucher.addAll(RechecheGuesttoucherHost(BordPiece,colorP));
                //attaque et toucher et i roi
                ArrayList<String> depAttaquent = new ArrayList<>();
                int cpt=0,dep=attaque;
                if ((i+1) == attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                         dep++;
                    }
                }
                else if ((i-1)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep--;
                    }
                }
                else if ((i+7)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep+=7;
                    }
                }
                else if ((i+8)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep+=8;
                    }
                }
                else if ((i+9)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep+=9;
                    }
                }
                else if ((i-7)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep-=7;
                    }
                }
                else if ((i-8)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep-=8;
                    }
                }
                else if ((i-9)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep-=9;
                    }
                }
                Toast.makeText(this, depAttaquent.toString(), Toast.LENGTH_SHORT).show();
                RechecheHostP(3);//3 pour ne pas prendre les déplacement du roi
                Integer[] coordonnerDepAttaque = new Integer[depAttaquent.size()];
                Integer[] coordonnerDepHost = new Integer[PosibiliterH.size()];
                String[] LettreHost = new String[PosibiliterH.size()];
                boolean finParti=true;
                for (int j = 0; j < depAttaquent.size()-1; j++) {
                    coordonnerDepAttaque[j] = Integer.parseInt(depAttaquent.get(j).substring(depAttaquent.get(j).indexOf(":")+1,depAttaquent.get(j).length()));
                    for (int k = 0; k < PosibiliterH.size(); k++) {
                        LettreHost[k] = PosibiliterH.get(k).substring(0,PosibiliterH.get(k).indexOf(":"));
                        coordonnerDepHost[k] = Integer.parseInt(PosibiliterH.get(k).substring(PosibiliterH.get(k).indexOf(":")+1,PosibiliterH.get(k).length()));
                        if((coordonnerDepAttaque[j] == coordonnerDepHost[k] && LettreHost[k].equals("D") )  || (coordonnerDepHost[k] == toucher.get(0) && LettreHost[k].equals("A"))){
                            finParti=false;
                        }

                    }
                }
                //si la fin partite est true ces que personne ne peux arréter l'attaquent dont il a perdu
                if (finParti == true){
                    fin=true;
                    messageRef = database.getReference("players/"+playerName);
                    messageRef.setValue("Defaite");
                    Toast.makeText(this, "Defaite", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),RoomActivity.class));
                    messageRef =database.getReference("rooms/"+roomName+"/playerRoom");
                    messageRef.setValue("deco:"+playerName+":D");
                }else
                    Toast.makeText(this, "echec", Toast.LENGTH_SHORT).show();
                    //si non ces que ces piéce peuve se défendre donc juste echec
            }
        }
        return fin;
    }
    private boolean EchecRoitGuest(int i){
        boolean fin=false;
        ArrayList<String> teste;
        teste =roi.deplacementRoiGuest(BordPiece,i,colorP);
        PosibiliterH.clear();
        RechecheHostP(1);
        //récupérer les déplacement des noir
        //créations de la récupérations des coordoner et lettre
        Integer[] coordonner = new Integer[teste.size()];
        String[] Lettre = new String[teste.size()];
        ArrayList<Integer> coordonnerDep = new  ArrayList<>();
        ArrayList<String> LettreDep = new  ArrayList<>();
        int attaque=-1;
        //boucler pour voir si le roi et toucher
        for (int j = 0; j < teste.size(); j++) {
            coordonner[j] = Integer.parseInt(teste.get(j).substring(teste.get(j).indexOf(":")+1,teste.get(j).length()));
            Lettre[j] = teste.get(j).substring(0,teste.get(j).indexOf(":"));
            for (int k = 0; k < PosibiliterH.size(); k++) {
                coordonnerDep.add(Integer.parseInt(PosibiliterH.get(k).substring(PosibiliterH.get(k).indexOf(":")+1,PosibiliterH.get(k).length())));
                LettreDep.add(PosibiliterH.get(k).substring(0,PosibiliterH.get(k).indexOf(":")));
                if (BordPiece[coordonnerDep.get(k)].equals("R") && LettreDep.get(k).equals("A")){
                    if (k !=0)
                        attaque =coordonnerDep.get(k-1);
                    else
                        attaque = i;
                }
            }
        }
        PosibiliterH.clear();
        //pathe
        if (teste.isEmpty()){
            RechecheGuestP(0);
            if (PosibiliterG.isEmpty()){
                fin=true;
                messageRef = database.getReference("players/"+playerName);
                messageRef.setValue("Defaite");
                Toast.makeText(this, "Egaliter", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(),RoomActivity.class));
                messageRef =database.getReference("rooms/"+roomName+"/playerRoom");
                messageRef.setValue("deco:"+playerName+":DP");
            }else
                PosibiliterG.clear();
        }
        //si il y as échec alors on regarde si le roi peux rien faire
        if (attaque !=-1) {
            for (int j = 0; j < coordonner.length; j++) {
                if (attaque-14 == coordonner[j])
                    teste.remove("D:" + (attaque-14));
                else if (attaque-16 == coordonner[j])
                    teste.remove("D:" + (attaque-16));
                else if (attaque-18 == coordonner[j])
                    teste.remove("D:" + (attaque-18));
                else if(attaque+18 == coordonner[j])
                    teste.remove("D:" + (attaque+18));
                else if (attaque+16 == coordonner[j])
                    teste.remove("D:" + (attaque+16));
                else if (attaque+14 == coordonner[j])
                    teste.remove("D:" + (attaque+14));
                else if (attaque-2 == coordonner[j] && attaque-1 == i)
                    teste.remove("D:" + (attaque-2));
                else if (attaque+2 == coordonner[j] && attaque+1 == i)
                    teste.remove("D:" + (attaque+2));
            }
            for (int j = 0; j < Lettre.length; j++) {
                //on regarde si ces déplacement sont bon
                if (Lettre[j].equals("A") && colorP[coordonner[j]].equals("B")) {
                    teste.remove(Lettre[j] + ":" + coordonner[j]);
                }
                for (int k = 0; k < coordonnerDep.size(); k++) {
                    //on regarde si les coordonner d'attaque son égale aux rois
                    if (coordonner[j] == coordonnerDep.get(k)) {
                        teste.remove(Lettre[j] + ":" + coordonner[j]);
                    }
                }
            }
            //on regarde si il reste des emplacement
            if(teste.isEmpty()){
                //teste si les piéce de l'host peuve blocker l'attaque
                echecMath.clear();
                PosibiliterG.clear();
                //récupérer les endroit attaquer
                ArrayList<Integer> toucher = new ArrayList<>();
                toucher.clear();
                toucher.addAll(RechechehosttoucherGuest(BordPiece,colorP));
                //permet de récupérer les emplacement
                ArrayList<String> depAttaquent = new ArrayList<>();
                int cpt=0,dep=attaque;
                if ((i+1) == attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep++;
                    }
                }
                else if ((i-1)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep--;
                    }
                }
                else if ((i+7)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep+=7;
                    }
                }
                else if ((i+8)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep+=8;
                    }
                }
                else if ((i+9)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep+=9;
                    }
                }
                else if ((i-7)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep-=7;
                    }
                }
                else if ((i-8)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep-=8;
                    }
                }
                else if ((i-9)== attaque){
                    while (cpt == 0){
                        if (dep == toucher.get(0))
                            cpt++;
                        else
                            depAttaquent.add("D:"+dep);
                        dep-=9;
                    }
                }
                //rajouter l'androit qui attaque (pour pouvoir la manger)
                depAttaquent.add("D:"+toucher.get(0));
                RechecheGuestP(3);//3 pour ne pas prendre les déplacement du roi
                Integer[] coordonnerDepAttaque = new Integer[depAttaquent.size()];
                Integer[] coordonnerDepGuest = new Integer[PosibiliterH.size()];
                String[] LettreGuest = new String[PosibiliterH.size()];
                boolean finParti=true;
                for (int j = 0; j < depAttaquent.size()-1; j++) {
                    coordonnerDepAttaque[j] = Integer.parseInt(depAttaquent.get(j).substring(depAttaquent.get(j).indexOf(":")+1,depAttaquent.get(j).length()));
                    for (int k = 0; k < PosibiliterH.size(); k++) {
                        LettreGuest[k] = PosibiliterH.get(k).substring(0,PosibiliterH.get(k).indexOf(":"));
                        coordonnerDepGuest[k] = Integer.parseInt(PosibiliterH.get(k).substring(PosibiliterH.get(k).indexOf(":")+1,PosibiliterH.get(k).length()));
                        if((coordonnerDepAttaque[j] == coordonnerDepGuest[k] && LettreGuest[k].equals("D") )  || (coordonnerDepGuest[k] == toucher.get(0) && LettreGuest[k].equals("A"))){
                            Toast.makeText(this, coordonnerDepAttaque[j].toString(), Toast.LENGTH_SHORT).show();
                            finParti=false;
                        }

                    }
                }
                //si la fin partite est true ces que personne ne peux arréter l'attaquent dont il a perdu
                if (finParti == true){
                    fin=true;
                    messageRef = database.getReference("players/"+playerName);
                    messageRef.setValue("Defaite");
                    Toast.makeText(this, "Defaite", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),RoomActivity.class));
                    messageRef =database.getReference("rooms/"+roomName+"/playerRoom");
                    messageRef.setValue("deco:"+playerName+":D");
                }else
                    Toast.makeText(this, "echec", Toast.LENGTH_SHORT).show();
            }
        }
        return fin;
    }

    private void initialistions(){
        gridView = findViewById(R.id.grid_echec);
        buttonqui = findViewById(R.id.quiter);//récupére le bouton quiter
        gridView = findViewById(R.id.grid_echec);
        gridViewMortN = findViewById(R.id.grid_echec_mort_N);
        gridViewMortB = findViewById(R.id.grid_echec_mort_B);
        textView = findViewById(R.id.NomJoueur);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        gridView.setEnabled(false);
        database = FirebaseDatabase.getInstance();//créer une instance

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        textView.setText("joueur : " + playerName); //Récupérer en Extra depuis CreaRoom ou RoomActivity si guest

    }

    //Récupére les données de rooms/roomName roomName qui a été envoyé en putExtra de CreRoom
    private void initialisationshost(){
        messageRef = database.getReference("rooms/"+roomName);
        //addListenerForSingleValueEvent a une changement de valeur apres roomName on execute unjoueuroudeux
        messageRef.addListenerForSingleValueEvent(unjoueuroudeux());
    }


    private ValueEventListener unjoueuroudeux(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().contains("player1")){
                    message();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    private void message(){
        messageRef = database.getReference("rooms/"+roomName+"/playerRoom");//Créer une ref playerRoom dans la RealtimeBD dans rooms
        messageRef.setValue("co");//Avec la ref récupérer d'en haut on met "co" comme valeur de situation
        messageRef = database.getReference("rooms/"+roomName+"/message");//Créer une ref message dans la RealtimeBD dans rooms
        messageRef.setValue(role+":20:20");//Dans la room on sait qui vient de jouer avec le role et la position départ et arrivé on met 20:20 de base
        //On va ensuite tester si un joueur a quitter dans addRoomEventLister qui ferme ou ouvre une room
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageRef = database.getReference("rooms/"+roomName+"/playerRoom");//crée le message de la BDD
        messageRef.addValueEventListener(addRoomEventClose());//depuis la ref d'en haut on execute addRoomClose();
        //Si addRoomEventClose ne donne rien on passe a addRoomEvent
        messageRef = database.getReference("rooms/"+roomName+"/message");//crée le message de la BDD
        messageRef.addValueEventListener(addRoomEvent());

    }

    //Cette méthode permet de savoir si la situation playerRoom a été mis a "déco" si oui on quitte
    //déco est entré si un joueur a cliquer sur le bouton quitter
    private ValueEventListener addRoomEventClose(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().contains("deco") && !snapshot.getValue().toString().contains(playerName)) {
                    messageRef = database.getReference("players/"+playerName);
                    if (snapshot.getValue().toString().contains("DP"))
                        messageRef.setValue("Defaite");
                    else
                        messageRef.setValue("Victoir");
                    messageRef = database.getReference("rooms/"+roomName);

                    messageRef.removeValue();
                    Intent ActivityB= new Intent(getApplicationContext(), RoomActivity.class);
                    startActivity(ActivityB);
                    finish();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    //Si depuis addRoomEventListener messageRef est host alors il peut jouer et l'echecetmath est de son coté
    private ValueEventListener addRoomEvent(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //message recu
                if(role.equals("host")){//teste si le joueur est l'host ou pas
                    if(snapshot.getValue().toString().contains("guest")){//regarde si l'endroit ou les donnée a changer contient guest
                        action(snapshot);
                        //affiche le message que si il est pas échec est math
                        if(echecEtMath()==false)
                            Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("guest:"+positiondepart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(snapshot.getValue().toString().contains("host")){//regarde si l'endroit ou les donnée a changer contient host:
                        action(snapshot);
                        //affiche le message que si il est pas échec est math
                        if(echecEtMath()==false)
                            Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("host:"+positiondepart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //erreur retenter
                messageRef.setValue(message);
            }
        };
    }

    private void quiterBTN(){
                buttonqui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        messageRef =database.getReference("players/"+playerName);
                        messageRef.setValue("D");
                                buttonqui.setEnabled(false);
                                    messageRef =database.getReference("rooms/"+roomName+"/playerRoom");
                                messageRef.setValue("deco:"+playerName+":D");
                                Intent ActivityB= new Intent(getApplicationContext(), RoomActivity.class);
                                startActivity(ActivityB);
                                finish();
                                //bouton pour quiter l'applications
                            }
                        });
    }

    private void echecini(){
        initialisationsEchiquier();
        gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
    }

    private void deplacement(){
        String déplace="";
        //rock en hat as gauche
        if(positionarriver == 0 && (roi.GetbougRoi() == 0 || roi.GetbougRoi() == 2) && positiondepart == 4 && BordPiece[0].equals("T")){
            déplace=BordDepDepart(déplace);
            BordPiece[2]=déplace;
            déplace=ColorDepDepart(déplace);
            colorP[2]= déplace;

            déplace=BordDepArriver(déplace);
            BordPiece[3]=déplace;
            déplace=ColorDepArriver(déplace);
            colorP[3]= déplace;
            //rock en aux a droite
        }else if (positionarriver==7 && BordPiece[7].equals("T")&&(roi.GetbougRoi() == 0 || roi.GetbougRoi() == 2 ) && positiondepart == 4){
            déplace=BordDepDepart(déplace);
            BordPiece[6]=déplace;
            déplace=ColorDepDepart(déplace);
            colorP[6]= déplace;

            déplace=BordDepArriver(déplace);
            BordPiece[5]=déplace;
            déplace=ColorDepArriver(déplace);
            colorP[5]= déplace;
        }
        //rocke en bat as droit
        else if (positionarriver==56 && BordPiece[56].equals("T") &&(roi.GetbougRoi() == 0 || roi.GetbougRoi() == 1) && positiondepart == 60){
            déplace=BordDepDepart(déplace);
            BordPiece[58]=déplace;
            déplace=ColorDepDepart(déplace);
            colorP[58]= déplace;

            déplace=BordDepArriver(déplace);
            BordPiece[59]=déplace;
            déplace=ColorDepArriver(déplace);
            colorP[59]= déplace;
        }
        //rocke en bat as gauche
        else if (positionarriver==63 && BordPiece[63].equals("T") &&(roi.GetbougRoi() == 0 || roi.GetbougRoi() == 1) && positiondepart == 60)
        {
            déplace=BordDepDepart(déplace);
            BordPiece[62]=déplace;
            déplace=ColorDepDepart(déplace);
            colorP[62]= déplace;

            déplace=BordDepArriver(déplace);
            BordPiece[61]=déplace;
            déplace=ColorDepArriver(déplace);
            colorP[61]= déplace;
            //mise en place des déplacement clasique
        }else{
            if (colorP[positionarriver].equals("N")){
                MortB.add(BordPiece[positionarriver]);
                gridViewMortN.setAdapter(new adapterMortNoir(getApplicationContext(),MortB));
            }
            else if (colorP[positionarriver].equals("B")){
                MortN.add(BordPiece[positionarriver]);
                gridViewMortB.setAdapter(new adapterMortBlanc(getApplicationContext(),MortN));
            }

            if(Echec()==1){
                gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
                positiondepart = -1;
                coup--;
            }else{
                déplace=BordDepDepart(déplace);
                BordPiece[positionarriver]=déplace;
                déplace=ColorDepDepart(déplace);
                colorP[positionarriver]= déplace;
            }

        }
        //actualisations du terrin
        gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
    }
    private String BordDepDepart(String déplace){
        déplace = BordPiece[positiondepart];
        BordPiece[positiondepart]="";
        return déplace;
    }
    private String BordDepArriver(String déplace){
        déplace = BordPiece[positionarriver];
        BordPiece[positionarriver]="";
        return déplace;
    }
    private String ColorDepDepart(String déplace){
        déplace = colorP[positiondepart];
        colorP[positiondepart] ="";
        return déplace;
    }
    private String ColorDepArriver(String déplace){
        déplace = colorP[positionarriver];
        colorP[positionarriver] ="";
        return déplace;
    }

    //tableau d'echec allant de 0 a 63, on place une piece avec des condition si ()
    private void initialisationsEchiquier(){
        for(int i = 0 ; i<BordPiece.length;i++){//pour intégrer tout les case
            if(i==0||i==7){
                BordPiece[i]="T"; //tour
                colorP[i] = "B";
            }else if(i==56||i==63){
                BordPiece[i]="T"; //tour
                colorP[i] = "N";
            }
            else if (i==1||i==6){
                BordPiece[i]="C";//cavalier
                colorP[i] = "B";
            }else if (i==57||i==62){
                BordPiece[i]="C";//cavalier
                colorP[i] = "N";
            }
            else if (i==2||i==5){
                BordPiece[i]="F";//foue
                colorP[i] = "B";
            }else if (i==58||i==61){
                BordPiece[i]="F";//foue
                colorP[i] = "N";
            }
            else if(i==3){
                BordPiece[i]="D"; //dame
                colorP[i] = "B";
            }else if (i==59){
                BordPiece[i]="D"; //dame
                colorP[i] = "N";
            }
            else if(i==4){
                BordPiece[i]="R";//roie
                colorP[i] = "B";
            }else if (i==60){
                BordPiece[i]="R";//roie
                colorP[i] = "N";
            }
            else if(8<=i && i<=15){

                BordPiece[i]="P";//petite pion
                colorP[i] = "B";
            }else if (48<=i && i<=55){
                BordPiece[i]="P";//petite pion
                colorP[i] = "N";
            }
            else{
                BordPiece[i]="";
                colorP[i] = "";
            }

        }
    }

    private void itemaction(){
        gridView.setOnItemClickListener(grilleaction());
    }
    private AdapterView.OnItemClickListener grilleaction(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //on regarde si ces l'host et que la couleur est Blanc alors on mais le coup a 1 et selecltionner a i pui si coup == 1 alors on fait des test pour bouger
                int posible=0;
                if(role.equals("host") && colorP[i].equals("B") || coup==1){
                    if(coup==1)//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            caseNonValide();
                            colorActionPion.clear();//on suprimse les couleur
                        }
                        else{
                            trouverPositionCorecte(i);
                            colorActionPion.clear();//on suprimse les couleur
                        }
                    else
                        trouverLesdeplacement(i);
                }else if(role.equals("guest") && colorP[i].equals("N") || coup==1){
                    if(coup==1)//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            caseNonValide();
                            colorActionPion.clear();//on suprimse les couleur
                        }
                        else{
                            trouverPositionCorecte(i);
                            colorActionPion.clear();//on suprimse les couleur
                        }
                    else
                        trouverLesdeplacement(i);
                }
            }
        };
    }
    private void trouverPositionCorecte(int position){
        int fin=0;
        String[] Lettre = new String[colorActionPion.size()];
        Integer[] coordonner = new Integer[colorActionPion.size()];
        for (int i=0;i<colorActionPion.size();i++){
            coordonner[i] = Integer.parseInt(colorActionPion.get(i).substring(colorActionPion.get(i).indexOf(":")+1,colorActionPion.get(i).length()));
            Lettre[i] = colorActionPion.get(i).substring(0,colorActionPion.get(i).indexOf(":"));

            if(position == coordonner[i]){
                if (Lettre[i].equals("O"))
                    fin=1;
                else if (Lettre[i].equals("A"))
                    PositionValble.add(coordonner[i]);
                else if (Lettre[i].equals("D") && fin == 0)
                    PositionValble.add(coordonner[i]);
                else if (Lettre[i].equals("R1"))
                    PositionValble.add(coordonner[i]);
                else if (Lettre[i].equals("R2"))
                    PositionValble.add(coordonner[i]);
            }
        }
        int posible=0;
        for (int j = 0; j < PositionValble.size(); j++) {
            if (PositionValble.get(j) == position) {
                posible = 1;
            }
        }
        PositionValble.clear();//nétoiller le tableaux
        if (BordPiece[selectionner].equals("P"))
            posibiliter(posible,position);
        else if (BordPiece[selectionner].equals("T"))
            posibiliter(posible,position);
        else if(BordPiece[selectionner].equals("C"))
            posibiliter(posible,position);
        else if (BordPiece[selectionner].equals("F"))
            posibiliter(posible,position);
        else if (BordPiece[selectionner].equals("D"))
            posibiliter(posible,position);
        else if (BordPiece[selectionner].equals("R"))
            posibiliter(posible,position);
    }

    private void posibiliter(int posible,int i){
        positionarriver = i;
        if (posible == 1)
            caseValide(i);
        else
            caseNonValide();
    }

    private void caseNonValide(){
        positiondepart = -1;
        coup--;
        gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
        Toast.makeText(GameActivity.this, "imposible", Toast.LENGTH_SHORT).show();
    }
    private void caseValide(int i){
        positiondepart = selectionner;
        positionarriver = i;
        if(Echec() == 1){
            gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
            positiondepart = -1;
            coup--;
        }else{
            deplacement();
            gridView.setEnabled(false);
            messageRef.setValue(role + ":" + selectionner + ":" + i);//change l'informatiosn dans la BDD
            coup = 0;
            selectionner = -1;
            Toast.makeText(GameActivity.this, "a votre adversaire de jouer", Toast.LENGTH_SHORT).show();
        }

    }

    private void trouverLesdeplacement(int i){
        PositionValble.clear();
        if (BordPiece[i].equals("P")){
            if (role.equals("host"))
                colorActionPion = pion.deplacementHostPion(BordPiece,i,colorP);
            else
                colorActionPion = pion.deplacementGuestPion(BordPiece,i,colorP);
        }
        else if (BordPiece[i].equals("T")){
            if (role.equals("host"))
                colorActionPion = tour.deplacementTourHost(BordPiece,i,colorP);
            else
                colorActionPion = tour.deplacementTourGuest(BordPiece,i,colorP);
        }else if(BordPiece[i].equals("C")){
            if (role.equals("host"))
                colorActionPion = cavalier.deplacementCavalierHost(BordPiece,i,colorP);
            else
                colorActionPion = cavalier.deplacementCavalierGuest(BordPiece,i,colorP);
        }else if (BordPiece[i].equals("F")){
            if (role.equals("host"))
                colorActionPion = foue.deplacementFoueHost(BordPiece,i,colorP);
            else
                colorActionPion = foue.deplacementFoueGuest(BordPiece,i,colorP);
        }else if (BordPiece[i].equals("D")){
            if (role.equals("host"))
                colorActionPion = dame.deplacementDameHost(BordPiece,i,colorP);
            else
                colorActionPion = dame.deplacementDameGuest(BordPiece,i,colorP);
        }else if (BordPiece[i].equals("R")){
            if (role.equals("host")){
                BlancRoi(i);
            }else{
                NoirRoi(i);
            }
        }

        gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
        coup++;
        selectionner = i;
    }
    private void BlancRoi(int i){
        colorActionPion =roi.deplacementRoiHost(BordPiece,i,colorP);
        PosibiliterG.clear();
        RechecheGuestP(1);
        //récupérer les déplacement des noir
        //créations de la récupérations des coordoner et lettre
        Integer[] coordonner = new Integer[colorActionPion.size()];
        String[] Lettre = new String[colorActionPion.size()];
        ArrayList<Integer> coordonnerDep = new  ArrayList<>();
        ArrayList<String> LettreDep = new  ArrayList<>();
        int attaque=-1;
        //boucler pour voir si le roi et toucher
        for (int j = 0; j < colorActionPion.size(); j++) {
            coordonner[j] = Integer.parseInt(colorActionPion.get(j).substring(colorActionPion.get(j).indexOf(":")+1,colorActionPion.get(j).length()));
            Lettre[j] = colorActionPion.get(j).substring(0,colorActionPion.get(j).indexOf(":"));
            for (int k = 0; k < PosibiliterG.size(); k++) {
                coordonnerDep.add(Integer.parseInt(PosibiliterG.get(k).substring(PosibiliterG.get(k).indexOf(":")+1,PosibiliterG.get(k).length())));
                LettreDep.add(PosibiliterG.get(k).substring(0,PosibiliterG.get(k).indexOf(":")));
                if (BordPiece[coordonnerDep.get(k)].equals("R") && LettreDep.get(k).equals("A")){
                    if (k !=0)
                        attaque =coordonnerDep.get(k-1);
                    else
                        attaque = i;
                }
            }
        }
        PosibiliterG.clear();
        //si il y as échec alors on regarde si le roi peux rien faire
        if (attaque !=-1) {
            for (int j = 0; j < coordonner.length; j++) {
                if (attaque-14 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque-14));
                else if (attaque-16 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque-16));
                else if (attaque-18 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque-18));
                else if(attaque+18 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque+18));
                else if (attaque+16 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque+16));
                else if (attaque+14 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque+14));
                else if (attaque-2 == coordonner[j] && attaque-1 == i)
                    colorActionPion.remove("D:" + (attaque-2));
                else if (attaque+2 == coordonner[j] && attaque+1 == i)
                    colorActionPion.remove("D:" + (attaque+2));
            }

        }
        for (int j = 0; j < Lettre.length; j++) {
            //on regarde si ces déplacement sont bon
            if (Lettre[j].equals("A") && colorP[coordonner[j]].equals("B")) {
                colorActionPion.remove(Lettre[j] + ":" + coordonner[j]);
            }
            for (int k = 0; k < coordonnerDep.size(); k++) {
                //on regarde si les coordonner d'attaque son égale aux rois
                if (coordonner[j] == coordonnerDep.get(k)) {
                    colorActionPion.remove(Lettre[j] + ":" + coordonner[j]);
                }
            }
        }
    }
    private void NoirRoi(int i){
        colorActionPion = roi.deplacementRoiGuest(BordPiece,i,colorP);
        //récupérer les déplacement des noir
        PosibiliterH.clear();
        RechecheHostP(1);
        //créations de la récupérations des coordoner et lettre
        Integer[] coordonner = new Integer[colorActionPion.size()];
        String[] Lettre = new String[colorActionPion.size()];
        ArrayList<Integer> coordonnerDep = new  ArrayList<>();
        ArrayList<String> LettreDep = new  ArrayList<>();
        int attaque=-1;
        //boucler pour voir si le roi et toucher
        for (int j = 0; j < colorActionPion.size(); j++) {
            coordonner[j] = Integer.parseInt(colorActionPion.get(j).substring(colorActionPion.get(j).indexOf(":")+1,colorActionPion.get(j).length()));
            Lettre[j] = colorActionPion.get(j).substring(0,colorActionPion.get(j).indexOf(":"));
            for (int l = 0; l < PosibiliterH.size(); l++) {
                coordonnerDep.add(Integer.parseInt(PosibiliterH.get(l).substring(PosibiliterH.get(l).indexOf(":")+1,PosibiliterH.get(l).length())));
                LettreDep.add(PosibiliterH.get(l).substring(0,PosibiliterH.get(l).indexOf(":")));
                if (BordPiece[coordonnerDep.get(l)].equals("R") && LettreDep.get(l).equals("A")){
                    if (l !=0)
                        attaque =coordonnerDep.get(l-1);
                    else
                        attaque = i;
                }
            }
        }

        PosibiliterH.clear();
        //si il y as échec alors on regarde si le roi peux rien faire
        if (attaque !=-1) {
            for (int j = 0; j < coordonner.length; j++) {
                if (attaque-14 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque-14));
                else if (attaque-16 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque-16));
                else if (attaque-18 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque-18));
                else if(attaque+18 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque+18));
                else if (attaque+16 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque+16));
                else if (attaque+14 == coordonner[j])
                    colorActionPion.remove("D:" + (attaque+14));
                else if (attaque-2 == coordonner[j] && attaque-1 == i)
                    colorActionPion.remove("D:" + (attaque-2));
                else if (attaque+2 == coordonner[j] && attaque+1 == i)
                    colorActionPion.remove("D:" + (attaque+2));
            }

        }
        for (int j = 0; j < Lettre.length; j++) {
            //on regarde si ces déplacement sont bon
            if (Lettre[j].equals("A") && colorP[coordonner[j]].equals("N")) {
                colorActionPion.remove(Lettre[j] + ":" + coordonner[j]);
            }
            for (int k = 0; k < coordonnerDep.size(); k++) {
                //on regarde si les coordonner d'attaque son égale aux rois
                if (coordonner[j] == coordonnerDep.get(k)) {
                    colorActionPion.remove(Lettre[j] + ":" + coordonner[j]);
                }
            }
        }
    }

    private void extragerer(){
        Bundle extra = getIntent().getExtras();//récuper l'extrat envoiller par roomActivity
        if(extra != null){
            roomName = extra.getString("roomName");;//récupére la valeur envoiller
            CompartPlayer = extra.getString("playerhost");
            if(CompartPlayer.equals(playerName))//teste pour savoir si ces le joueur1 ou 2(playerName)
                role = "host";
            else
                role = "guest";
        }

    }

    private void action(DataSnapshot snapshot){
        gridView.setEnabled(true);
        séparateur(snapshot.getValue().toString());
    }

    private void séparateur(String snapshot){
        String séparaeur = snapshot.substring(snapshot.indexOf(":")+1,snapshot.length());
        positiondepart = Integer.parseInt(séparaeur.substring(0,séparaeur.indexOf(":")));
        positionarriver = Integer.parseInt(séparaeur.substring(séparaeur.indexOf(":")+1,séparaeur.length()));
        deplacement();
    }

    private int Echec(){
        echecMath.clear();
        ArrayList<Integer> toucher = new ArrayList<>();
        String[] BordPieceTest = new String[64];
        String[] colorPTest = new String[64];
        int AttaqueR=0;
        for (int i=0;i<BordPiece.length;i++){
            BordPieceTest[i] = BordPiece[i];
            colorPTest[i] = colorP[i];
        }
        String deplace =BordPieceTest[positiondepart];
        BordPieceTest[positiondepart] ="";
        BordPieceTest[positionarriver]=deplace;
        deplace =colorPTest[positiondepart];
        colorPTest[positiondepart] ="";
        colorPTest[positionarriver]=deplace;

        if (role.equals("host")){
            toucher.clear();
            toucher.addAll(RechecheGuesttoucherHost(BordPieceTest,colorPTest));
            if (toucher.size() != 0)
                AttaqueR=1;
            //rien le roi nes pas toucher
        }else{
            toucher.clear();
            toucher.addAll(RechechehosttoucherGuest(BordPieceTest,colorPTest));
            if (toucher.size() !=0)
                AttaqueR=1;
        }
        return AttaqueR;
    }

    private ArrayList<Integer> RechecheGuesttoucherHost(String[] bord, String[] color){
        ArrayList<Integer> toucher = new ArrayList<>();
        for (int i=0;i<63;i++){
            if (!bord[i].equals("") && color[i].equals("N")){
                if (bord[i].equals("P")){
                    echecMath.addAll(pion.deplacementGuestPion(bord,i,color));
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (bord[i].equals("F")){
                    echecMath.addAll(foue.deplacementFoueGuest(bord,i,color)) ;
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (bord[i].equals("D")){
                    echecMath.addAll(dame.deplacementDameGuest(bord,i,color)) ;
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (bord[i].equals("R")){
                    echecMath.addAll(roi.deplacementRoiGuest(bord,i,color)) ;
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (bord[i].equals("T")){
                    echecMath.addAll(tour.deplacementTourGuest(bord,i,color));
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (bord[i].equals("C")){
                    echecMath.addAll(cavalier.deplacementCavalierGuest(bord,i,color));
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }
                echecMath.clear();
            }
        }
        return toucher;
    }

    private ArrayList<Integer> RechechehosttoucherGuest(String[] bord, String[] color){
        ArrayList<Integer> toucher = new ArrayList<>();
        for (int i=0;i<63;i++){
            echecMath.clear();
            if (!bord[i].equals("") && color[i].equals("B")){
                if (bord[i].equals("P")){
                    echecMath.addAll( pion.deplacementGuestPion(bord,i,color));
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (bord[i].equals("F")){
                    echecMath.addAll(foue.deplacementFoueGuest(bord,i,color));
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (bord[i].equals("D")){
                    echecMath.addAll( dame.deplacementDameGuest(bord,i,color));
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (bord[i].equals("R")){
                    echecMath.addAll( roi.deplacementRoiGuest(bord,i,color));
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (bord[i].equals("T")){
                    echecMath.addAll(tour.deplacementTourGuest(bord,i,color));
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (bord[i].equals("C")){
                    echecMath.addAll(cavalier.deplacementCavalierGuest(bord,i,color));
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }
            }
        }
        return toucher;
    }

    private void RechecheHostP(int enlever){
        for (int i=0;i<63;i++){
            if (!BordPiece[i].equals("") && colorP[i].equals("B")){
                if (BordPiece[i].equals("P")){
                    if (enlever == 1)
                        PosibiliterH.addAll(pion.AttaqueHostPion(BordPiece,i,colorP));
                    else
                        PosibiliterH.addAll(pion.deplacementHostPion(BordPiece,i,colorP));
                }
                else if (BordPiece[i].equals("F"))
                    PosibiliterH.addAll(foue.deplacementFoueHost(BordPiece,i,colorP));
                else if (BordPiece[i].equals("D"))
                    PosibiliterH.addAll(dame.deplacementDameHost(BordPiece,i,colorP));
                else if (BordPiece[i].equals("T"))
                    PosibiliterH.addAll(tour.deplacementTourHost(BordPiece,i,colorP));
                else if (BordPiece[i].equals("R") && enlever !=3)
                    PosibiliterH.addAll(roi.deplacementRoiHost(BordPiece,i,colorP));
                else if (BordPiece[i].equals("C"))
                    PosibiliterH.addAll(cavalier.deplacementCavalierHost(BordPiece,i,colorP));
            }
        }
    }
    private void RechecheGuestP(int enlever){
        for (int i=0;i<63;i++){
            if (!BordPiece[i].equals("") && colorP[i].equals("N")){
                if (BordPiece[i].equals("P")) {
                    if (enlever == 1)
                        PosibiliterG.addAll(pion.AttaqueGuestPion(BordPiece, i, colorP));
                    else
                        PosibiliterG.addAll(pion.deplacementGuestPion(BordPiece, i, colorP));
                }
                else if (BordPiece[i].equals("F"))
                    PosibiliterG.addAll(foue.deplacementFoueGuest(BordPiece,i,colorP));
                else if (BordPiece[i].equals("D"))
                    PosibiliterG.addAll(dame.deplacementDameGuest(BordPiece,i,colorP));
                else if (BordPiece[i].equals("R")&& enlever !=3)
                    PosibiliterG.addAll(roi.deplacementRoiGuest(BordPiece,i,colorP));
                else if (BordPiece[i].equals("T"))
                    PosibiliterG.addAll(tour.deplacementTourGuest(BordPiece,i,colorP));
                else if (BordPiece[i].equals("C"))
                    PosibiliterG.addAll(cavalier.deplacementCavalierGuest(BordPiece,i,colorP));
            }
        }
    }


    //retourne vrais si le roi et toucher par la piéce donnée du guest
    private Boolean RechercheAttauqueGuest(){
        Integer[] coordonner = new Integer[echecMath.size()];
        String[] Lettre = new String[echecMath.size()];
        for (int i=0;i<echecMath.size();i++){
            coordonner[i] = Integer.parseInt(echecMath.get(i).substring(echecMath.get(i).indexOf(":")+1,echecMath.get(i).length()));
            Lettre[i] = echecMath.get(i).substring(0,echecMath.get(i).indexOf(":"));
            if (Lettre[i].equals("A") && BordPiece[coordonner[i]].equals("R") && colorP[coordonner[i]].equals("B")){
                return true;
            }
        }
        return false;
    }

    //retourne vrais si le roi et toucher par la piéce donnée du host
    private Boolean RechercheAttauqueHost(){
        Integer[] coordonner = new Integer[echecMath.size()];
        String[] Lettre = new String[echecMath.size()];
        for (int i=0;i<echecMath.size();i++){
            coordonner[i] = Integer.parseInt(echecMath.get(i).substring(echecMath.get(i).indexOf(":")+1,echecMath.get(i).length()));
            Lettre[i] = echecMath.get(i).substring(0,echecMath.get(i).indexOf(":"));
            if (Lettre[i].equals("A") && BordPiece[coordonner[i]].equals("R") && colorP[coordonner[i]].equals("N")){
                return true;
            }
        }
        return false;
    }

    private void trouverLesdeplacementAttaquer(int i){
        if (BordPiece[i].equals("P")){
            if (colorP[i].equals("B"))
                echecMath.addAll(pion.deplacementHostPion(BordPiece,i,colorP));
            else
                echecMath.addAll(pion.deplacementGuestPion(BordPiece,i,colorP));
        }
        else if (BordPiece[i].equals("T")){
            if (colorP[i].equals("B"))
                echecMath.addAll(tour.deplacementTourHost(BordPiece,i,colorP));
            else
                echecMath.addAll(tour.deplacementTourGuest(BordPiece,i,colorP));
        }else if(BordPiece[i].equals("C")){
            if (colorP[i].equals("B"))
                echecMath.addAll(cavalier.deplacementCavalierHost(BordPiece,i,colorP));
            else
                echecMath.addAll(cavalier.deplacementCavalierGuest(BordPiece,i,colorP));
        }else if (BordPiece[i].equals("F")){
            if (colorP[i].equals("B"))
                echecMath.addAll(foue.deplacementFoueHost(BordPiece,i,colorP));
            else
                echecMath.addAll(foue.deplacementFoueGuest(BordPiece,i,colorP));
        }else if (BordPiece[i].equals("D")){
            if (colorP[i].equals("B"))
                echecMath.addAll(dame.deplacementDameHost(BordPiece,i,colorP));
            else
                echecMath.addAll(dame.deplacementDameGuest(BordPiece,i,colorP));
        }else if (BordPiece[i].equals("R")){
            if (colorP[i].equals("B"))
                BlancRoi(i);
            else
                NoirRoi(i);
        }
    }

}