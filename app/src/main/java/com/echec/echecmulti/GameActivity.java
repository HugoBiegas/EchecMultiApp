package com.echec.echecmulti;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    GridView gridViewMortN;
    GridView gridViewMortB;
    ArrayList<String> MortB = new ArrayList<>();
    ArrayList<String> MortN = new ArrayList<>();
    int cpt=0;
    ArrayList<String> colorActionPion = new ArrayList<>();
    ArrayList<String> echecMath = new ArrayList<>();
    ArrayList<String> Posibiliter = new ArrayList<>();
    ArrayList<Integer> PositionValble =new ArrayList<>();
    PetitePion pion = new PetitePion();//créer les pion
    Tour tour = new Tour();//créer les tour
    Cavalier cavalier = new Cavalier();//créer les cavalier
    Dame dame = new Dame();//créer les dame
    Foue foue = new Foue();//créer les foue
    Roi roi = new Roi();//créer les roi
    int selectionner=-1;//emplacement celectionner par la personne, initialiser a -1 car nes pas sur le plataux
    int coup=0;//nombre de coup jouer par la personne
    int couleur=0;
    TextView textView;
    Integer positiondepart=0;
    Integer positionarriver=0;
    String[] BordPiece = new String[64];
    String[] BordPieceTest = new String[64];
    String[] colorP = new String[64];
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
        echecini();
        initialisationshost();
        extragerer();
        itemaction();
        quiterBTN();
    }
    private void initialisationshost(){
        messageRef = database.getReference("rooms/"+roomName);
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

    private void initialistions(){
        gridView = findViewById(R.id.grid_echec);
        buttonqui = findViewById(R.id.quiter);//récupére le bouton quiter
        gridView = findViewById(R.id.grid_echec);
        gridViewMortN = findViewById(R.id.grid_echec_mort_N);
        gridViewMortB = findViewById(R.id.grid_echec_mort_B);
        textView = findViewById(R.id.NomJoueur);

        gridView.setEnabled(false);
        database = FirebaseDatabase.getInstance();//créer une instance

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        playerName = preferences.getString("playerName","");
        textView.setText("joueur : " + playerName);

    }

    private void echecini(){
        initialisationsEchiquier();
        gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
    }

    private void deplacement(){
        String déplace="";
        if (colorP[positionarriver].equals("N")){
            MortB.add(BordPiece[positionarriver]);
            gridViewMortN.setAdapter(new adapterMortNoir(getApplicationContext(),MortB));
        }
        else if (colorP[positionarriver].equals("B")){
            MortN.add(BordPiece[positionarriver]);
            gridViewMortB.setAdapter(new adapterMortBlanc(getApplicationContext(),MortN));
        }

        if(positionarriver == 0 && (roi.GetbougRoi() == 0 || roi.GetbougRoi() == 2) && positiondepart == 4 && BordPiece[0].equals("T")){
            déplace=BordDepDepart(déplace);
            BordPiece[2]=déplace;
            déplace=ColorDepDepart(déplace);
            colorP[2]= déplace;

            déplace=BordDepArriver(déplace);
            BordPiece[3]=déplace;
            déplace=ColorDepArriver(déplace);
            colorP[3]= déplace;
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
        }else{
            déplace=BordDepDepart(déplace);
            BordPiece[positionarriver]=déplace;
            déplace=ColorDepDepart(déplace);
            colorP[positionarriver]= déplace;
        }
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
                            colorActionPion.clear();
                        }
                        else{
                            trouverPositionCorecte(i);
                            colorActionPion.clear();
                        }
                    else
                        trouverLesdeplacement(i);
                }else if(role.equals("guest") && colorP[i].equals("N") || coup==1){
                    if(coup==1)//si la piéce et vide ou que ces le deusiéme coup
                        if (selectionner == i){
                            caseNonValide();
                            colorActionPion.clear();
                        }
                        else{
                            trouverPositionCorecte(i);
                            colorActionPion.clear();
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
            Toast.makeText(GameActivity.this, "votre roi est toucher", Toast.LENGTH_SHORT).show();
            gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
            positiondepart = -1;
            coup--;
        }else if(Echec() == 2){
            Toast.makeText(GameActivity.this, "Echec et mat", Toast.LENGTH_SHORT).show();
            gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
            positiondepart = -1;
            coup--;
        }
        else{
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
            if (role.equals("host"))
                colorActionPion = roi.deplacementRoiHost(BordPiece,i,colorP);
            else
                colorActionPion = roi.deplacementRoiGuest(BordPiece,i,colorP);
        }
        gridView.setAdapter(new adapterGrild(getApplicationContext(),BordPiece,colorP,colorActionPion));
        coup++;
        selectionner = i;
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

    private void quiterBTN(){

        buttonqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messageRef =database.getReference("rooms/"+roomName+"/playerRoom");
                Intent ActivityB= new Intent(getApplicationContext(), RoomActivity.class);
                startActivity(ActivityB);
                finish();
                messageRef.setValue("deco");
                //bouton pour quiter l'applications
            }
        });
    }

    private void message(){
        messageRef = database.getReference("rooms/"+roomName+"/playerRoom");//crée le message de la BDD
        messageRef.setValue("co");
        messageRef = database.getReference("rooms/"+roomName+"/message");//crée le message de la BDD
        messageRef.setValue(role+":20:20");//change l'informatiosn dans la BDD
        addRoomEventListener();
    }

    private void addRoomEventListener(){
        messageRef = database.getReference("rooms/"+roomName+"/playerRoom");//crée le message de la BDD
        messageRef.addValueEventListener(addRoomEventClose());
        messageRef = database.getReference("rooms/"+roomName+"/message");//crée le message de la BDD
        messageRef.addValueEventListener(addRoomEvent());


    }
    private ValueEventListener addRoomEventClose(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.getValue().toString().contains("deco")){
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

    private ValueEventListener addRoomEvent(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //message recu
                if(role.equals("host")){//teste si le joueur est l'host ou pas
                    if(snapshot.getValue().toString().contains("guest")){//regarde si l'endroit ou les donnée a changer contient guest
                        action(snapshot);
                        //est affiche un message
                        Toast.makeText(GameActivity.this, "" + snapshot.getValue(String.class).replace("guest:"+positiondepart+":"+positionarriver,"a toi de jouer"), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(snapshot.getValue().toString().contains("host")){//regarde si l'endroit ou les donnée a changer contient host:
                        action(snapshot);
                        //est affiche un message
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
        Posibiliter.clear();
        ArrayList<Integer> toucher = new ArrayList<Integer>();
        toucher.clear();
        int compteur = 0;
        int  AttaqueR=0;
        for (int i=0;i<BordPiece.length;i++){
            BordPieceTest[i] = BordPiece[i];

        }
        String deplace =BordPieceTest[positiondepart];
        BordPieceTest[positiondepart] ="";
        BordPieceTest[positionarriver]=deplace;

        if (role.equals("host")){
            toucher.addAll(RechecheGuesttoucherHost());
            if (toucher.size() != 0){
                RechecheHostP();
                for (int i=0;i< toucher.size();i++){
                    trouverLesdeplacementAttaquer(toucher.get(i));
                    for (int j=0;j<echecMath.size();j++){
                        for (int k=0;k<Posibiliter.size();k++){
                            if (Posibiliter.get(k).equals(echecMath.get(j))){
                                AttaqueR=1;
                            }
                       }
                   }
                }
                //echec et mat
                if (AttaqueR ==0){
                    Toast.makeText(this, "echec et math", Toast.LENGTH_SHORT).show();
                    AttaqueR = 2;
                }else if (AttaqueR == 1){
                    Toast.makeText(this, "echec", Toast.LENGTH_SHORT).show();
                    AttaqueR = 1;
                }
            }
            //rien le roi nes pas toucher
        }else{
            toucher.addAll(RechechehosttoucherGuest());
            if (toucher.size() != 0){
                RechecheGuestP();
                for (int i=0;i< toucher.size();i++){
                    trouverLesdeplacementAttaquer(toucher.get(i));
                    for (int j=0;j<echecMath.size();j++){
                        for (int k=0;k<Posibiliter.size();k++){
                            if (Posibiliter.get(k).equals(echecMath.get(j))){
                                AttaqueR=1;
                            }
                        }
                    }
                }
                //echec et mat
                if (AttaqueR ==0){
                    Toast.makeText(this, "echec et math", Toast.LENGTH_SHORT).show();
                    AttaqueR = 2;
                }else if (AttaqueR == 1){
                    AttaqueR = 1;
                    Toast.makeText(this, "echec", Toast.LENGTH_SHORT).show();
                }
            }
            //rien le roi nes pas toucher
        }
        return AttaqueR;
    }

    private ArrayList<Integer> RechecheGuesttoucherHost(){
        ArrayList<Integer> toucher = new ArrayList<>();
        for (int i=0;i<63;i++){
            if (!BordPieceTest[i].equals("") && colorP[i].equals("N")){
                if (BordPieceTest[i].equals("P")){
                    echecMath = pion.deplacementGuestPion(BordPieceTest,i,colorP);
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("F")){
                    echecMath = foue.deplacementFoueGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("D")){
                    echecMath = dame.deplacementDameGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("R")){
                    echecMath = roi.deplacementRoiGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("T")){
                    echecMath = tour.deplacementTourGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("C")){
                    echecMath = cavalier.deplacementCavalierGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueGuest() == true)
                        toucher.add(i);
                }
                echecMath.clear();
            }
        }
        return toucher;
    }

    private ArrayList<Integer> RechechehosttoucherGuest(){
        ArrayList<Integer> toucher = new ArrayList<>();
        for (int i=0;i<63;i++){
            if (!BordPieceTest[i].equals("") && colorP[i].equals("B")){
                if (BordPieceTest[i].equals("P")){
                    echecMath = pion.deplacementGuestPion(BordPieceTest,i,colorP);
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("F")){
                    echecMath = foue.deplacementFoueGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("D")){
                    echecMath = dame.deplacementDameGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("R")){
                    echecMath = roi.deplacementRoiGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("T")){
                    echecMath = tour.deplacementTourGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }else if (BordPieceTest[i].equals("C")){
                    echecMath = cavalier.deplacementCavalierGuest(BordPieceTest,i,colorP);
                    if (RechercheAttauqueHost() == true)
                        toucher.add(i);
                }
                echecMath.clear();
            }
        }
        return toucher;
    }

    private void RechecheHostP(){
        for (int i=0;i<63;i++){
            if (!BordPiece[i].equals("") && colorP[i].equals("B")){
                if (BordPiece[i].equals("P"))
                    Posibiliter.addAll(pion.deplacementHostPion(BordPiece,i,colorP));
                if (BordPiece[i].equals("F"))
                    Posibiliter.addAll(foue.deplacementFoueHost(BordPiece,i,colorP));
                if (BordPiece[i].equals("D"))
                    Posibiliter.addAll(dame.deplacementDameHost(BordPiece,i,colorP));
                if (BordPiece[i].equals("R"))
                    Posibiliter.addAll(roi.deplacementRoiHost(BordPiece,i,colorP));
                if (BordPiece[i].equals("T"))
                    Posibiliter.addAll(tour.deplacementTourHost(BordPiece,i,colorP));
                if (BordPiece[i].equals("C"))
                    Posibiliter.addAll(cavalier.deplacementCavalierHost(BordPiece,i,colorP));
            }
        }
    }
    private void RechecheGuestP(){
        for (int i=0;i<63;i++){
            if (!BordPiece[i].equals("") && colorP[i].equals("N")){
                if (BordPiece[i].equals("P"))
                    Posibiliter.addAll(pion.deplacementGuestPion(BordPiece,i,colorP));
                if (BordPiece[i].equals("F"))
                    Posibiliter.addAll(foue.deplacementFoueGuest(BordPiece,i,colorP));
                if (BordPiece[i].equals("D"))
                    Posibiliter.addAll(dame.deplacementDameGuest(BordPiece,i,colorP));
                if (BordPiece[i].equals("R"))
                    Posibiliter.addAll(roi.deplacementRoiGuest(BordPiece,i,colorP));
                if (BordPiece[i].equals("T"))
                    Posibiliter.addAll(tour.deplacementTourGuest(BordPiece,i,colorP));
                if (BordPiece[i].equals("C"))
                    Posibiliter.addAll(cavalier.deplacementCavalierGuest(BordPiece,i,colorP));
            }
        }
    }


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
        echecMath.clear();
        if (BordPiece[i].equals("P")){
            if (role.equals("host"))
                echecMath.addAll(pion.deplacementHostPion(BordPiece,i,colorP));
            else
                echecMath.addAll(pion.deplacementGuestPion(BordPiece,i,colorP));
        }
        else if (BordPiece[i].equals("T")){
            if (role.equals("host"))
                echecMath.addAll(tour.deplacementTourHost(BordPiece,i,colorP));
            else
                echecMath.addAll(tour.deplacementTourGuest(BordPiece,i,colorP));
        }else if(BordPiece[i].equals("C")){
            if (role.equals("host"))
                echecMath.addAll(cavalier.deplacementCavalierHost(BordPiece,i,colorP));
            else
                echecMath.addAll(cavalier.deplacementCavalierGuest(BordPiece,i,colorP));
        }else if (BordPiece[i].equals("F")){
            if (role.equals("host"))
                echecMath.addAll(foue.deplacementFoueHost(BordPiece,i,colorP));
            else
                echecMath.addAll(foue.deplacementFoueGuest(BordPiece,i,colorP));
        }else if (BordPiece[i].equals("D")){
            if (role.equals("host"))
                echecMath.addAll(dame.deplacementDameHost(BordPiece,i,colorP));
            else
                echecMath.addAll(dame.deplacementDameGuest(BordPiece,i,colorP));
        }else if (BordPiece[i].equals("R")){
            if (role.equals("host"))
                echecMath.addAll(roi.deplacementRoiHost(BordPiece,i,colorP));
            else
                echecMath.addAll( roi.deplacementRoiGuest(BordPiece,i,colorP));
        }
    }

}