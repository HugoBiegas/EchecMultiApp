package com.echec.echecmulti.Pion;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class PetitePion {

    public ArrayList<String> deplacementHostPion(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int[] renplacement = new int[]{ 56,57,58,59,60,61,62,63};
        int début= coordoner,premier=0,bordBase=0;
        for (int i = 0; i < 8; i++) {
            if (borderD[i]==coordoner)
                bordBase=1;
            if (borderG[i]==coordoner)
                bordBase=2;
            if (renplacement[i]==coordoner)
                bordBase=3;
        }
        if (bordBase==3){
                déplacement.add("M:"+coordoner);
        }else {
            coordoner += 7;
            for (int i = 0; i < 3; i++) {
                if (coordoner < 63 && coordoner > 0) {
                    if ((i == 0 && bordBase != 1) || (i == 2 && bordBase != 2)) {
                        if (!Echiquier[coordoner].equals("") && color[coordoner].equals("N")) {
                            déplacement.add("A:" + coordoner);
                        } else {
                            déplacement.add("R:" + coordoner);
                        }
                    } else if (i==1){
                        if (Echiquier[coordoner].equals("")) {
                            déplacement.add("D:" + coordoner);
                            premier = 1;
                        } else {
                            déplacement.add("O:" + coordoner);
                            premier = 0;
                        }
                    }
                }

                coordoner++;
            }
            if (début <= 15) {
                coordoner += 6;
                if (Echiquier[coordoner].equals("") && premier == 1) {
                    déplacement.add("D:" + coordoner);
                } else {
                    déplacement.add("O:" + coordoner);
                }
            }
        }
        return déplacement;
    }

    public ArrayList<String> deplacementGuestPion(String[] Echiquier,int coordoner,String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int[] renplacement = new int[]{ 0,1,2,3,4,5,6,7};
        int debut=coordoner,premier=0,bordBase=0;
        for (int i = 0; i < 8; i++) {
            if (borderD[i]==coordoner)
                bordBase=1;
            if (borderG[i]==coordoner)
                bordBase=2;
            if (renplacement[i]==coordoner)
                bordBase=3;
        }
        if (bordBase==3){
            déplacement.add("M:"+coordoner);
        }else {
            //test tout les endroi ou peux marcher un pion et ou il peux manger
            coordoner -= 7;
            for (int i = 0; i < 3; i++) {
                if (coordoner < 63 && coordoner > 0) {
                    if ((i == 0 && bordBase != 1) || (i == 2 && bordBase != 2)) {
                        if (!Echiquier[coordoner].equals("") && color[coordoner].equals("B"))
                            déplacement.add("A:" + coordoner);
                        else
                            déplacement.add("R:" + coordoner);
                    } else if (i==1){
                        if (Echiquier[coordoner].equals("")) {
                            déplacement.add("D:" + coordoner);
                            premier = 1;
                        } else {
                            déplacement.add("O:" + coordoner);
                            premier = 0;
                        }
                    }
                }
                coordoner--;
            }
            if (debut >= 48) {
                coordoner -= 6;
                if (Echiquier[coordoner].equals("") && premier == 1)
                    déplacement.add("D:" + coordoner);
                else
                    déplacement.add("O:" + coordoner);
            }
        }
        return déplacement;
    }

    public ArrayList<String> AttaqueGuestPion(String[] Echiquier,int coordoner,String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int debut=coordoner,premier=0;

        //test tout les endroi ou peux marcher un pion et ou il peux manger
        coordoner -=7;
        for (int i=0;i<3;i++){
            if (coordoner<63 && coordoner>0){
                if (i == 0 || i==2){
                    if(!Echiquier[coordoner].equals("") && color[coordoner].equals("B"))
                        déplacement.add("A:"+coordoner);
                    else
                        déplacement.add("R:"+coordoner);
                }
            }
            coordoner--;
        }
        return déplacement;
    }
    public ArrayList<String> AttaqueHostPion(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int début= coordoner,premier=0,bordBase=4;
        coordoner +=7;
        for (int i = 0; i < 3; i++) {
            if (coordoner<63 && coordoner>0){
                if (i == 0 || i == 2 ) {
                    if (!Echiquier[coordoner].equals("") && color[coordoner].equals("N")) {
                        déplacement.add("A:" + coordoner);
                    } else {
                        déplacement.add("R:" + coordoner);
                    }
                }
            }
            coordoner++;
        }
        return déplacement;
    }
}
