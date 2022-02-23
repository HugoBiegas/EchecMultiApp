package com.echec.echecmulti;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class PetitePion {

    public String[] déplacementHostPion(String[] Echiquier,int coordoner,String[] color){
        String[] déplacement = new String[4];
        int cpt=0,premier=0;
        //test tout les endroi ou peux marcher un pion et ou il peux manger
        coordoner +=7;
            for (int i=0;i<3;i++){
                if (i == 0 || i==2){
                    if(!Echiquier[coordoner].equals("") && color[coordoner].equals("N")){
                        déplacement[cpt] = "A:"+coordoner;
                        cpt++;
                    }else{
                        déplacement[cpt] = "R:"+coordoner;
                        cpt++;
                    }
                }else{
                    if (Echiquier[coordoner].equals("")){
                        déplacement[cpt] = "D:"+coordoner;
                        cpt++;
                        premier =1;
                    }else{
                        déplacement[cpt] = "O:"+coordoner;
                        cpt++;
                        premier =0;
                    }
                }
                coordoner++;
            }
        coordoner +=6;
        if(Echiquier[coordoner].equals("") && premier == 1){
            déplacement[cpt] = "D:"+coordoner;
        }else{
            déplacement[cpt] = "O:"+coordoner;
        }
        cpt=0;
        return déplacement;
    }

    public String[] déplacementGuestPion(String[] Echiquier,int coordoner,String[] color){
        String[] déplacement = new String[4];
        int cpt=0,premier=0;
        //test tout les endroi ou peux marcher un pion et ou il peux manger
        coordoner -=7;
        for (int i=0;i<3;i++){
            if (i == 0 || i==2){
                if(!Echiquier[coordoner].equals("") && color[coordoner].equals("B")){
                    déplacement[cpt] = "A:"+coordoner;
                    cpt++;
                }else{
                    déplacement[cpt] = "R:"+coordoner;
                    cpt++;
                }
            }else{
                if (Echiquier[coordoner].equals("")){
                    déplacement[cpt] = "D:"+coordoner;
                    cpt++;
                    premier=1;
                }else{
                    déplacement[cpt] = "O:"+coordoner;
                    cpt++;
                    premier=0;
                }
            }
            coordoner--;
        }
        coordoner -=6;
        if(Echiquier[coordoner].equals("")&& premier == 1){
            déplacement[cpt] = "D:"+coordoner;
        }else{
            déplacement[cpt] = "O:"+coordoner;
        }

        cpt=0;
        return déplacement;
    }
}
