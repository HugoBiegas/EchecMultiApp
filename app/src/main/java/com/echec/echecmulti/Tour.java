package com.echec.echecmulti;

import android.icu.text.RelativeDateTimeFormatter;

import java.util.ArrayList;

public class Tour {
    public ArrayList<String> deplacementTourHost(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> déplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};

        int écar = coordoner+8;
        int non = 0;
        int bordBase=4;
        //tour pour manger ver le haut
        for(int i=0;i<8;i++){
            if (non == 0 && écar<64){
                if(Echiquier[écar].equals(""))
                    déplacement.add("D:"+écar);
                else if (color[écar].equals("N")){
                    déplacement.add("A:"+écar);
                    non =1;
                 }else{
                    non =1;
                }
            }else
                i=8;//sortire de la boucle
            écar+=8;
        }
        non=0;
        écar = coordoner-8;
        //tour pour manger ver le bat
        for (int i=0;i<8;i++){
            if (non ==0 && écar>=0){
                if(Echiquier[écar].equals(""))
                    déplacement.add("D:"+écar);
                else if (color[écar].equals("N")){
                   déplacement.add("A:"+écar);
                    non =1;
                }else
                    non=1;
            }else
                i=8;
            écar-=8;
        }
        for (int i=0;i<7;i++){
            if (coordoner == 0 || coordoner == 63)
                bordBase = 0;
            else if (coordoner == borderD[i])
                bordBase = 1;
            else if (coordoner == borderG[i])
                bordBase = 2;
        }
        non=0;
        écar = coordoner+1;
        //tour pour manger sur la droit
        for (int i=0;i<8;i++){
                    if (bordBase ==0)
                        non=1;
                    else if(écar == (coordoner+1) && bordBase == 1){
                        non=1;
                    }else if (bordBase == 2 && écar == (coordoner-1)) {
                        non = 1;
                    }else if (non == 0){
                        for (int j=0;j<7;j++){
                            if (borderD[j] == écar || borderG[j] ==écar){
                                non=1;
                                if (!Echiquier[écar].equals("") && color[écar].equals("N"))
                                    déplacement.add("A:"+écar);
                                else if (color[écar].equals("B"))
                                    déplacement.add("O:"+écar);
                                else
                                    déplacement.add("D:"+écar);
                            }
                        }
                        if(Echiquier[écar].equals(""))
                         déplacement.add("D:"+écar);
                        else if (color[écar].equals("N")){
                                déplacement.add("A:"+écar);
                                non=1;
                            }else if (color[écar].equals("B")){
                            déplacement.add("O:"+écar);
                            non=1;
                            }
                    }else
                        i=8;
           écar++;
        }
        non=0;
        écar = coordoner-1;
        //tour pour manger sur la gauche
        for (int i=0;i<8;i++){
            if (bordBase ==0)
                non=1;
            else if(écar == (coordoner+1) && bordBase == 1)
                 non=1;
            else if (bordBase == 2 && écar == (coordoner-1))
                    non = 1;
            else if (non == 0){
                for (int j=0;j<7;j++){
                    if (borderD[j] == écar || borderG[j] ==écar){
                        non=1;
                        if (!Echiquier[écar].equals("") && color[écar].equals("N"))
                            déplacement.add("A:"+écar);
                        else if (color[écar].equals("B"))
                            déplacement.add("O:"+écar);
                        else
                            déplacement.add("D:"+écar);
                    }
                }
                 if(Echiquier[écar].equals(""))
                     déplacement.add("D:"+écar);
                 else if (color[écar].equals("N")){
                      déplacement.add("A:"+écar);
                     non=1;
                 }else if (color[écar].equals("B")) {
                     déplacement.add("O:" + écar);
                     non = 1;
                 }
                }else
                    i=8;
            écar--;
        }
        return déplacement;
    }


    public ArrayList<String> deplacementTourGuest(String[] Echiquier, int coordoner, String[] color){
        ArrayList<String> déplacement = new ArrayList<>();
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};

        int écar = coordoner+8;
        int non = 0;
        int bordBase=4;
        //tour pour manger ver le haut
        for(int i=0;i<8;i++){
            if (non == 0 && écar<64){
                if(Echiquier[écar].equals(""))
                    déplacement.add("D:"+écar);
                else if (color[écar].equals("B")){
                    déplacement.add("A:"+écar);
                    non =1;
                }else{
                    non =1;
                }
            }else
                i=8;//sortire de la boucle
            écar+=8;
        }
        non=0;
        écar = coordoner-8;
        //tour pour manger ver le bat
        for (int i=0;i<8;i++){
            if (non ==0 && écar>=0){
                if(Echiquier[écar].equals(""))
                    déplacement.add("D:"+écar);
                else if (color[écar].equals("B")){
                    déplacement.add("A:"+écar);
                    non =1;
                }else
                    non=1;
            }else
                i=8;
            écar-=8;
        }
        for (int i=0;i<7;i++){
            if (coordoner == 0 || coordoner == 63)
                bordBase = 0;
            else if (coordoner == borderD[i])
                bordBase = 1;
            else if (coordoner == borderG[i])
                bordBase = 2;
        }
        non=0;
        écar = coordoner+1;
        //tour pour manger sur la droit
        for (int i=0;i<8;i++){
            if (bordBase ==0)
                non=1;
            else if(écar == (coordoner+1) && bordBase == 1){
                non=1;
            }else if (bordBase == 2 && écar == (coordoner-1)) {
                non = 1;
            }else if (non == 0){
                for (int j=0;j<7;j++){
                    if (borderD[j] == écar || borderG[j] ==écar){
                        non=1;
                        if (!Echiquier[écar].equals("") && color[écar].equals("B"))
                            déplacement.add("A:"+écar);
                        else if (color[écar].equals("N"))
                            déplacement.add("O:"+écar);
                        else
                            déplacement.add("D:"+écar);
                    }
                }
                if(Echiquier[écar].equals(""))
                    déplacement.add("D:"+écar);
                else if (color[écar].equals("B")){
                    déplacement.add("A:"+écar);
                    non=1;
                }else if (color[écar].equals("N")){
                    déplacement.add("O:"+écar);
                    non=1;
                }
            }else
                i=8;
            écar++;
        }
        non=0;
        écar = coordoner-1;
        //tour pour manger sur la gauche
        for (int i=0;i<8;i++){
            if (bordBase ==0)
                non=1;
            else if(écar == (coordoner+1) && bordBase == 1)
                non=1;
            else if (bordBase == 2 && écar == (coordoner-1))
                non = 1;
            else if (non == 0){
                for (int j=0;j<7;j++){
                    if (borderD[j] == écar || borderG[j] ==écar){
                        non=1;
                        if (!Echiquier[écar].equals("") && color[écar].equals("B"))
                            déplacement.add("A:"+écar);
                        else if (color[écar].equals("N"))
                            déplacement.add("O:"+écar);
                        else
                            déplacement.add("D:"+écar);
                    }
                }
                if(Echiquier[écar].equals(""))
                    déplacement.add("D:"+écar);
                else if (color[écar].equals("B")){
                    déplacement.add("A:"+écar);
                    non=1;
                }else if (color[écar].equals("N")) {
                    déplacement.add("O:" + écar);
                    non = 1;
                }
            }else
                i=8;
            écar--;
        }
        return déplacement;
    }
}
