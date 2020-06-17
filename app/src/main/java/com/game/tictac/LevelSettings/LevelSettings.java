package com.game.tictac.LevelSettings;

import com.game.tictac.EnumPack.EnumHardLevel;
import com.game.tictac.EnumPack.EnumLevel;
import com.game.tictac.EnumPack.EnumType;

public class LevelSettings {
    private static EnumLevel levelSize = EnumLevel.FIRST;
    private static EnumType levelWhoPlayer = EnumType.OFFLINE;
    private static EnumHardLevel levelHard = EnumHardLevel.EASY;

    public static EnumLevel getLevelSize() {
        return levelSize;
    }

    public static void setLevelSize(EnumLevel levelSize) {
        LevelSettings.levelSize = levelSize;
    }

    public static EnumType getLevelWhoPlayer() {
        return levelWhoPlayer;
    }

    public static void setLevelWhoPlayer(EnumType levelWhoPlayer) {
        LevelSettings.levelWhoPlayer = levelWhoPlayer;
    }

    public static EnumHardLevel getLevelHard() {
        return levelHard;
    }

    public static void setLevelHard(EnumHardLevel levelHard) {
        LevelSettings.levelHard = levelHard;
    }
}
