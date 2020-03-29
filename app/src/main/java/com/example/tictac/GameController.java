package com.example.tictac;

import com.example.tictac.EnumPack.EnumHardLevel;
import com.example.tictac.LevelSettings.LevelSettings;

public class GameController {
    private int[][] arrayFirst;
    private int arraySize = 3;


    public GameController() {
        createArray();
    }

    private void createArray(){
        switch (LevelSettings.getLevelSize()){
            case SECOND:
                arraySize = 5;
                break;
            case THIRD:
                arraySize = 7;
                break;
        }
        arrayFirst = new int[arraySize][arraySize];
    }

    public boolean setArray(int x, int y, boolean player){
        if(arrayFirst[x][y] == 0 && player){
            arrayFirst[x][y] = 1;
            return true;
        }
        else if(arrayFirst[x][y] == 0 && !player){
            arrayFirst[x][y] = 2;
            return true;
        }
        return false;
    }

    public boolean setArray(int x, int y, int index){
        switch (index){
            case 1:
                if(arrayFirst[x][y] == 0){
                    arrayFirst[x][y] = 1;
                    return true;
                }
                break;
            case 2:
                if(arrayFirst[x][y] == 0){
                    arrayFirst[x][y] = 2;
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean nooneWon(){
        for (int i = 0; i < arraySize; i++){
            for (int j = 0; j < arraySize; j++){
                if(arrayFirst[i][j] == 0)
                    return false;
            }
        }
        return true;
    }


    public boolean arrayCheck(){
        boolean isCheck;
        for(int i = 0; i < arraySize; i++){
            isCheck = true;
            for(int j = 0; j < arraySize - 1; j++){
                if(isCheck){
                    isCheck = arrayFirst[i][j] == arrayFirst[i][j + 1] && arrayFirst[i][j] != 0;
                }
            }
            if(isCheck) return true;
        }
        for(int j = 0; j < arraySize; j++){
            isCheck = true;
            for(int i = 0; i < arraySize - 1; i++){
                if(isCheck){
                    isCheck = arrayFirst[i][j] == arrayFirst[i + 1][j] && arrayFirst[i][j] != 0;
                }
            }
            if(isCheck) return true;
        }
        switch (arraySize){
            case 3:
                return (arrayFirst[0][0] == arrayFirst[1][1] && arrayFirst[2][2] == arrayFirst[1][1] && arrayFirst[0][0] != 0) ||
                       (arrayFirst[0][2] == arrayFirst[1][1] && arrayFirst[2][0] == arrayFirst[1][1] && arrayFirst[0][2] != 0);
            case 5:
                return (arrayFirst[0][0] == arrayFirst[1][1] && arrayFirst[1][1] == arrayFirst[2][2] && arrayFirst[2][2] == arrayFirst[3][3] && arrayFirst[3][3] == arrayFirst[4][4] && arrayFirst[0][0] != 0)||
                        (arrayFirst[0][4] == arrayFirst[1][3] && arrayFirst[1][3] == arrayFirst[2][2] && arrayFirst[2][2] == arrayFirst[3][1] && arrayFirst[3][1] == arrayFirst[4][0] && arrayFirst[0][4] != 0);
            case 7:
                return (arrayFirst[0][0] == arrayFirst[1][1] && arrayFirst[1][1] == arrayFirst[2][2] && arrayFirst[2][2] == arrayFirst[3][3] && arrayFirst[3][3] == arrayFirst[4][4] && arrayFirst[4][4] == arrayFirst[5][5] && arrayFirst[5][5] == arrayFirst[6][6] && arrayFirst[0][0] != 0)||
                        (arrayFirst[0][6] == arrayFirst[1][5] && arrayFirst[1][5] == arrayFirst[2][4] && arrayFirst[2][4] == arrayFirst[3][3] && arrayFirst[3][3] == arrayFirst[4][2] && arrayFirst[4][2] == arrayFirst[5][1] && arrayFirst[5][1] == arrayFirst[6][0] && arrayFirst[0][6] != 0);
        }
        return false;
    }

    private int[] array;
    public int[] botGoes(EnumHardLevel hardLevel){
         array = new int[2];

        switch (hardLevel){
            case EASY:
                randomTwoPoints();
                return array;
            case MEDIUM:
            case HARD:
                findVictory(hardLevel);
                return array;
        }
        return array;
    }

    private void randomTwoPoints(){
        System.out.println("RANDOM");
        int x = (int) (Math.random()) * arraySize;
        int y = (int) (Math.random()) * arraySize;
        while(!setArray(x, y, false)) {
            x = (int) (Math.random() * arraySize);
            y = (int) (Math.random() * arraySize);
            array[0] = x;
            array[1] = y;
        }
    }

    private int[] findVictory(EnumHardLevel hardLevel){ // pererabotat'
        int counter = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if(arrayFirst[i][j] == 1)
                    counter++;
                else if(arrayFirst[i][j] == 2)
                    counter = -10;
            }
            if(counter == 2){
                for (int k = 0; k < 3; k++){
                    if(arrayFirst[i][k] == 0) {
                        if(((int)(Math.random() * 2) == 0) && hardLevel == EnumHardLevel.MEDIUM) {
                            array[0] = i;
                            array[1] = k;
                            setArray(i, k, false);
                            System.out.println("NOT RANDOM " +"x " + i + " y " + k);
                            return array;
                        }
                        else if(((int)(Math.random() * 10) < 8) && hardLevel == EnumHardLevel.HARD){
                            array[0] = i;
                            array[1] = k;
                            setArray(i, k, false);
                            System.out.println("NOT RANDOM " +"x " + i + " y " + k);
                            return array;
                        }
                        else {
                            randomTwoPoints();
                            return array;
                        }
                    }
                }
            }
            counter = 0;
            for (int g = 0; g < 3; g++){
                if(arrayFirst[g][i] == 1)
                    counter++;
                else if(arrayFirst[g][i] == 2)
                    counter = -10;
            }
            if(counter == 2){
                for (int k = 0; k < 3; k++){
                    if(arrayFirst[k][i] == 0) {
                        if(((int)(Math.random() * 2) == 0) && hardLevel == EnumHardLevel.MEDIUM) {
                            array[0] = k;
                            array[1] = i;
                            setArray(k, i, false);
                            System.out.println("NOT RANDOM" + "x " + k + " y " + i);
                            return array;
                        }
                        else if(((int)(Math.random() * 10) < 8) && hardLevel == EnumHardLevel.HARD){
                            array[0] = k;
                            array[1] = i;
                            setArray(k, i, false);
                            System.out.println("NOT RANDOM" + "x " + k + " y " + i);
                            return array;
                        }
                        else {
                            randomTwoPoints();
                            return array;
                        }
                    }
                }
            }
            counter = 0;
        }

        counter = 0;
        for(int i = 0; i < 3; i++){
            if(arrayFirst[i][i] == 1)
                counter++;
            else if(arrayFirst[i][i] == 2)
                counter = -10;
        }
        if(counter == 2){
            for(int i = 0; i < 3; i++){
                if(arrayFirst[i][i] == 0) {
                    if(((int)(Math.random() * 2) == 0) && hardLevel == EnumHardLevel.MEDIUM) {
                        array[0] = i;
                        array[1] = i;
                        setArray(i, i, false);
                        return array;
                    }else if(((int)(Math.random() * 10) < 8) && hardLevel == EnumHardLevel.HARD){
                        array[0] = i;
                        array[1] = i;
                        setArray(i, i, false);
                        return array;
                    }
                    else {
                        randomTwoPoints();
                        return array;
                    }
                }
            }
        }
        counter = 0;
        for(int i = 0, j = 2; i < 3; i++, j--){
            if(arrayFirst[i][j] == 1)
                counter++;
            else if(arrayFirst[i][j] == 2)
                counter = -10;
        }
        if(counter == 2){
            for(int i = 0, j = 2; i < 3; i++, j--){
                if(arrayFirst[i][j] == 0) {
                    if(((int)(Math.random() * 2) == 0) && hardLevel == EnumHardLevel.MEDIUM) {
                        array[0] = i;
                        array[1] = j;
                        setArray(i, j, false);
                        return array;
                    }
                    else if(((int)(Math.random() * 10) < 8) && hardLevel == EnumHardLevel.HARD){
                        array[0] = i;
                        array[1] = j;
                        setArray(i, j, false);
                        return array;
                    }
                    else {
                        randomTwoPoints();
                        return array;
                    }
                }
            }
        }

        randomTwoPoints();
        return array;
    }

}
