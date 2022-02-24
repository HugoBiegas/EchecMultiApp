package com.echec.echecmulti;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class PetitePion {

    public ArrayList<String> deplacementHostPion(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int début= coordoner,premier=0,bordBase=4;
        coordoner +=7;
            for (int i = 0; i < 3; i++) {
                if (i == 0 || i == 2 ) {
                    if (!Echiquier[coordoner].equals("") && color[coordoner].equals("N")) {
                        déplacement.add("A:" + coordoner);
                    } else {
                        déplacement.add("R:" + coordoner);
                    }
                } else {
                    if (Echiquier[coordoner].equals("")) {
                        déplacement.add("D:" + coordoner);
                        premier = 1;
                    } else {
                        déplacement.add("O:" + coordoner);
                        premier = 0;
                    }
                }
                coordoner++;
            }
        if (début<=15){
            coordoner +=6;
            if(Echiquier[coordoner].equals("") && premier == 1){
                déplacement.add("D:"+coordoner);
            }else{
                déplacement.add("O:"+coordoner);
            }
        }

        return déplacement;
    }

    public ArrayList<String> deplacementGuestPion(String[] Echiquier,int coordoner,String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int debut=coordoner,premier=0;

        //test tout les endroi ou peux marcher un pion et ou il peux manger
        coordoner -=7;
        for (int i=0;i<3;i++){
            if (i == 0 || i==2){
                if(!Echiquier[coordoner].equals("") && color[coordoner].equals("B"))
                    déplacement.add("A:"+coordoner);
                else
                    déplacement.add("R:"+coordoner);
            }else{
                if (Echiquier[coordoner].equals("")){
                    déplacement.add("D:"+coordoner);
                    premier =1;
                }else{
                    déplacement.add("O:"+coordoner);
                    premier =0;
                }
            }
            coordoner--;
        }
        if (debut>=48){
            coordoner -=6;
            if(Echiquier[coordoner].equals("") && premier == 1)
                déplacement.add("D:"+coordoner);
            else
                déplacement.add("O:"+coordoner);
        }
        return déplacement;
    }

    private void GrandRemplacement(){

    }
}
