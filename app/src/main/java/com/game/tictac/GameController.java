package com.game.tictac;

import com.game.tictac.EnumPack.EnumHardLevel;
import com.game.tictac.LevelSettings.LevelSettings;

public class GameController {
    private int[][] arrayFirst;
    private int[][] arrayFigures = {{0,1,1,1}, {1,0,1,1}, {1,1,0,1}, {1,1,1,0}, {0,1,1}, {1,0,1}, {1,1,0}};
    private int arraySize = 3;
    private int countToWin;


    public GameController() {
        createArray();

        switch (arraySize){
            case 3:
            case 5:
                countToWin = 3;
                break;
            case 7:
                countToWin = 4;
        }
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
        if(arrayFirst[x][y] == 0){
            if(player)
                arrayFirst[x][y] = 1;
            else
                arrayFirst[x][y] = 2;
            return true;
        }
        return false;
    }

    public boolean setArray(int x, int y, int index){
        if(arrayFirst[x][y] == 0) {
            switch (index) {
                case 1:
                    arrayFirst[x][y] = 1;
                    break;
                case 2:
                    arrayFirst[x][y] = 2;
                    break;
            }
            return true;
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

    public boolean arrayCheck(boolean index){ // проверяет выиграл ли после своего хода
        int find;
        if(index)
            find = 1; //player's figura
        else
            find = 2; //bot's figura

        int count, i = 0, j = arraySize - countToWin; //TODO: j budet vichislyatsa j = arraySize - countFigureToWin( Eto chislo oznachaet skolko figur nuzhno sobrat' chtoby viigrat' )

        for(int k = 0; k < (arraySize * 2) - 5; k++){ // проверяем диагонали, которые идут сверху вниз
            count = 0;
            for(int i2 = i, j2 = j; j2 < arraySize && i2 < arraySize; i2++, j2++){ // (i2 != (arraySize - 1)) || (j2 != (arraySize - 1))
                if(arrayFirst[i2][j2] == find) {
                    count++;
                    if (count == countToWin)
                        return true;
                }
                else
                    count = 0;
            }
            if(j > 0)
                j--;
            else if(j == 0)
                i++;
        }


        j = arraySize - 1;
        i = arraySize - countToWin;


        for(int k = 0; k < (arraySize * 2) - 5; k++){ // проверяем диагонали, которые идут снизу вверх
            count = 0;
            for(int i2 = i, j2 = j; i2 < arraySize && j2 >= 0; i2++, j2--){ // (i2 != (arraySize - 1)) || (j2 != (arraySize - 1))
                if(arrayFirst[i2][j2] == find) {
                    count++;
                    if (count == countToWin)
                        return true;
                }
                else
                    count = 0;
            }
            if(i > 0)
                i--;
            else if(i == 0)
                j--;
        }

        for(int k = 0; k < arraySize; k++){ // проверяем горизонтали и вертикали
            count = 0;
            for(int j2 = 0; j2 < arraySize; j2++){ // проверяем горизонтали
                if(arrayFirst[k][j2] == find) {
                    count++;
                    if (count == countToWin)
                        return true;
                }
                else
                    count = 0;
            }
            count = 0;
            for(int i2 = 0; i2 < arraySize; i2++){ // проверяем вертикали
                if(arrayFirst[i2][k] == find) {
                    count++;
                    if (count == countToWin)
                        return true;
                }
                else
                    count = 0;
            }
        }

        return false;
    }

    public boolean arrayCheck(){ // проверяет выиграл ли после своего хода
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
        int x, y;

        do{
            x = (int) (Math.random() * arraySize);
            y = (int) (Math.random() * arraySize);
            array[0] = x;
            array[1] = y;
        } while(!setArray(x, y, false));
        System.out.println("RANDOM");
    }

    private void pointNear(){
        int x = 0, y = 0;

        for (int i = 0; i < arraySize; i++){
            for (int j = 0; j < arraySize; j++){
                if(arrayFirst[i][j] == 1){
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        int direction;
        boolean isTrue = true;

        do{
            direction = (int) (Math.random() * 8);
            switch (direction){
                case 0:
                    if(x - 1 >= 0 && y - 1 >= 0 && arrayFirst[x - 1][y - 1] == 0){
                        x--;
                        y--;
                        isTrue = false;
                    }
                    break;
                case 1:
                    if(x - 1 >= 0 && arrayFirst[x - 1][y] == 0) {
                        x--;
                        isTrue = false;
                    }
                    break;
                case 2:
                    if(x - 1 >= 0 && y + 1 < arraySize && arrayFirst[x - 1][y + 1] == 0){
                        x--;
                        y++;
                        isTrue = false;
                    }
                    break;
                case 3:
                    if(y - 1 >= 0 && arrayFirst[x][y - 1] == 0) {
                        y--;
                        isTrue = false;
                    }
                    break;
                case 4:
                    if(y + 1 < arraySize && arrayFirst[x][y + 1] == 0) {
                        y++;
                        isTrue = false;
                    }
                    break;
                case 5:
                    if(x + 1 < arraySize && y - 1 >= 0 && arrayFirst[x + 1][y - 1] == 0){
                        x++;
                        y--;
                        isTrue = false;
                    }
                    break;
                case 6:
                    if(x + 1 < arraySize && arrayFirst[x + 1][y] == 0) {
                        x++;
                        isTrue = false;
                    }
                    break;
                case 7:
                    if(x + 1 < arraySize && y + 1 < arraySize && arrayFirst[x + 1][y + 1] == 0){
                        x++;
                        y++;
                        isTrue = false;
                    }
                    break;
            }
        } while(isTrue);

        array[0] = x;
        array[1] = y;
        setArray(x, y, false);
    }

    private int[] findVictory(EnumHardLevel hardLevel) {
        int figure = countToWin; //razmer nachalnoi figuri

        for (int k = 0; k < countToWin - 2; k++, figure--) { //cikl dlya proverki raznih figur
            int[] arrayBuffer = new int[figure];

            //region HorizontalAndVertical
            for (int i = 0; i < arraySize; i++) {

                for(int g = 0; g <= arraySize - figure; g++){ // skolko shagov na odnoi stroke

                    for (int j = g, scht = 0; j < figure + g; j++, scht++){ //schetchik
                        arrayBuffer[scht] = arrayFirst[i][j];

                        if(j == figure + g - 1 && checkFigure(arrayBuffer, figure, 1)){

                            if(hardLevel == EnumHardLevel.HARD || (hardLevel == EnumHardLevel.MEDIUM && (int) (Math.random() * 10) > 5)) {
                                for (int m = 0; m < figure; m++) {
                                    if (arrayBuffer[m] == 0 && arrayFirst[i][g + m] == 0) {
                                        setArrayFindVictory(i, g + m, "SmartHorizontal");
                                        return array;
                                    }
                                }
                            }
                            else
                                randomTwoPoints();
                        }

                    }
                }

                for(int g = 0; g <= arraySize - figure; g++){ // skolko shagov na odnoi stroke

                    for (int j = g, scht = 0; j < figure + g; j++, scht++){ //schitaet figuru
                        arrayBuffer[scht] = arrayFirst[j][i];

                        if(j == figure + g - 1 && checkFigure(arrayBuffer, figure, 1)){

                            if(hardLevel == EnumHardLevel.HARD || (hardLevel == EnumHardLevel.MEDIUM && (int) (Math.random() * 10) > 5)) {
                                for (int m = 0; m < figure; m++) {
                                    if (arrayBuffer[m] == 0 && arrayFirst[g + m][i] == 0) {
                                        setArrayFindVictory(g + m, i, "SmartVertical");
                                        return array;
                                    }
                                }
                            }
                            else
                                randomTwoPoints();
                        }
                    }
                }
            }
            //endregion

            //region Diagonali

            int  i = 0, j = arraySize - countToWin, temp = arraySize - j;

            int count = 0;
            switch (arraySize){
                case 3:
                    count = 1;
                    break;
                case 5:
                    count = 5;
                    break;
                case 7:
                    count = 7;
                    break;
            }

            for(int g = 0; g < count; g++){

                for(int t = 0; t <= temp - figure; t++) { // skolko raz budet schitat' nizhnii cikl

                    for (int i2 = i + t, j2 = j + t, scht = 0; i2 < figure + i + t; i2++, j2++, scht++) { //ischet figuri, schitaet
                        arrayBuffer[scht] = arrayFirst[i2][j2];

                        if(i2 == figure + i + t - 1 && checkFigure(arrayBuffer, figure, 1)){

                            if(hardLevel == EnumHardLevel.HARD || (hardLevel == EnumHardLevel.MEDIUM && (int) (Math.random() * 10) > 5)) {
                                for (int m = 0; m < figure; m++) {
                                    if (arrayBuffer[m] == 0 && arrayFirst[i + t + m][j + t + m] == 0) {
                                        setArrayFindVictory(i + t + m, j + t + m, "SmartSpad");
                                        return array;
                                    }
                                }
                            }
                            else
                                randomTwoPoints();
                        }
                    }
                }
                if(j > 0) {
                    j--;
                    temp = arraySize - j;
                }
                else if(j == 0) {
                    i++;
                    temp = arraySize - i;
                }
            }


            i = arraySize - countToWin;
            j = arraySize - 1;

            for(int g = 0; g < count; g++){

                for(int t = 0; t <= j - i - figure + 1; t++) { // skolko raz budet schitat' nizhnii cikl

                    for (int i2 = i + t, j2 = j - t, scht = 0; i2 < figure + i + t; i2++, j2--, scht++) { //ischet figuri, schitaet
                        arrayBuffer[scht] = arrayFirst[i2][j2];

                        if(i2 == figure + i + t - 1 && checkFigure(arrayBuffer, figure, 1)){

                            if(hardLevel == EnumHardLevel.HARD || (hardLevel == EnumHardLevel.MEDIUM && (int) (Math.random() * 10) > 5)) {
                                for (int m = 0; m < figure; m++) {
                                    if (arrayBuffer[m] == 0 && arrayFirst[i + t + m][j - t - m] == 0) {
                                        setArrayFindVictory(i + t + m, j - t - m, "SmartRost");
                                        return array;
                                    }
                                }
                            }
                            else
                                randomTwoPoints();
                        }
                    }
                }
                if(i > 0)
                    i--;
                else if(i == 0)
                    j--;
            }
            //endregion
        }


        if(hardLevel == EnumHardLevel.HARD)
            pointNear();
        else
            randomTwoPoints();

        return array;
    }

    private void setArrayFindVictory(int x, int y, String text){
        System.out.println(text + " " + x + " " + y);
        array[0] = x;
        array[1] = y;
        setArray(x, y, false);
    }

    private boolean checkFigure(int[] array, int figure, int index){
        int temp = 0;

        if(figure == 3)
            temp = 4;

        for (int i = temp, count = 0; i < figure + temp; i++, count = 0){
            for (int j = 0; j < figure; j++){

                switch (index){
                    case 1:
                        if(arrayFigures[i][j] != array[j])
                            break;
                        else
                            count++;

                        if(count == figure) {
                            return true;
                        }
                        break;
                    case 2:
                        if(arrayFirst[i][j] == 0){
                            if (array[j] == 0)
                                count++;
                            else
                                break;
                        }
                        else if(arrayFirst[i][j] != array[j])
                            count++;
                        break;
                }
            }
        }
        return false;
    }
}
