package com.echec.echecmulti.Pion;

import java.util.ArrayList;

public class Foue {

    public ArrayList<String> deplacementFoueHost(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> deplacement = new ArrayList<>();
        int[] border = new int[]{0, 8, 16, 24, 32, 40, 48, 56,7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int ecart = coordoner + 7,dup=0;
        int bordBase=4;
        for (int i=0;i<7;i++){
            if (coordoner == 0 || coordoner == 63)
                bordBase = 0;
            else if (coordoner == borderD[i])
                bordBase = 1;
            else if (coordoner == borderG[i])
                bordBase = 2;
        }

        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner-7) && bordBase == 1||bordBase == 2 && ecart == (coordoner+7))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("N")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                    if (Echiquier[ecart].equals(""))
                        deplacement.add("D:"+ecart);
                    else if (color[ecart].equals("N")){
                        deplacement.add("A:"+ecart);
                        i=8;
                    }else
                        i=8;
                }else
                    i=12;
            }
            ecart +=7;
        }

        dup=0;
        ecart = coordoner + 9;
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+9) && bordBase == 1||bordBase == 2 && ecart == (coordoner-9))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("N")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                if (Echiquier[ecart].equals(""))
                    deplacement.add("D:"+ecart);
                else if (color[ecart].equals("N")){
                    deplacement.add("A:"+ecart);
                    i=8;
                }else
                    i=8;
                }else
                    i=12;
            }

            ecart +=9;
        }

        dup=0;
        ecart = coordoner -7;
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner-7) && bordBase == 1||bordBase == 2 && ecart == (coordoner+7))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("N")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                if (Echiquier[ecart].equals(""))
                    deplacement.add("D:"+ecart);
                else if (color[ecart].equals("N")){
                    deplacement.add("A:"+ecart);
                    i=8;
                }else
                    i=8;
                }else
                    i=12;
            }
            ecart -=7;
        }


        dup=0;
        ecart = coordoner -9;
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+9) && bordBase == 1||bordBase == 2 && ecart == (coordoner-9))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("N")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                if (Echiquier[ecart].equals(""))
                    deplacement.add("D:"+ecart);
                else if (color[ecart].equals("N")){
                    deplacement.add("A:"+ecart);
                    i=8;
                }else
                    i=8;
                }else
                    i=12;
            }
            ecart -=9;
        }

        return deplacement;
    }

    public ArrayList<String> deplacementFoueGuest(String[] Echiquier, int coordoner, String[] color) {
        ArrayList<String> deplacement = new ArrayList<>();
        int[] border = new int[]{0, 8, 16, 24, 32, 40, 48, 56,7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderD = new int[]{ 7, 15, 23, 31, 39, 47, 55, 63};
        int[] borderG = new int[]{0, 8, 16, 24, 32, 40, 48, 56};
        int ecart = coordoner + 7,dup=0;
        int bordBase=4;
        for (int i=0;i<7;i++){
            if (coordoner == 0 || coordoner == 63)
                bordBase = 0;
            else if (coordoner == borderD[i])
                bordBase = 1;
            else if (coordoner == borderG[i])
                bordBase = 2;
        }

        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner-7) && bordBase == 1||bordBase == 2 && ecart == (coordoner+7))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("B")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                    if (Echiquier[ecart].equals(""))
                        deplacement.add("D:"+ecart);
                    else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                        i=8;
                    }else
                        i=8;
                }else
                    i=12;
            }
            ecart +=7;
        }

        dup=0;
        ecart = coordoner + 9;
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+9) && bordBase == 1||bordBase == 2 && ecart == (coordoner-9))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("B")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                    if (Echiquier[ecart].equals(""))
                        deplacement.add("D:"+ecart);
                    else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                        i=8;
                    }else
                        i=8;
                }else
                    i=12;
            }

            ecart +=9;
        }

        dup=0;
        ecart = coordoner -7;
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner-7) && bordBase == 1||bordBase == 2 && ecart == (coordoner+7))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("B")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                    if (Echiquier[ecart].equals(""))
                        deplacement.add("D:"+ecart);
                    else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                        i=8;
                    }else
                        i=8;
                }else
                    i=12;
            }
            ecart -=7;
        }


        dup=0;
        ecart = coordoner -9;
        for (int i=0;i<8;i++){
            if (bordBase ==0 ||ecart == (coordoner+9) && bordBase == 1||bordBase == 2 && ecart == (coordoner-9))
                dup=1;
            else
            if (ecart<64 && ecart>0){
                for (int j=0;j<15;j++){
                    if (border[j] == ecart){
                        if (Echiquier[ecart].equals(""))
                            deplacement.add("D:"+ecart);
                        else if (color[ecart].equals("B")){
                            deplacement.add("A:"+ecart);
                        }
                        dup=1;
                    }
                }
                if (dup==0){
                    if (Echiquier[ecart].equals(""))
                        deplacement.add("D:"+ecart);
                    else if (color[ecart].equals("B")){
                        deplacement.add("A:"+ecart);
                        i=8;
                    }else
                        i=8;
                }else
                    i=12;
            }
            ecart -=9;
        }

        return deplacement;
    }


}
