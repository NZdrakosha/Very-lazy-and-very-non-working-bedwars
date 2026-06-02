package me.drakosha.bedwars.game;

import java.util.ArrayList;
import java.util.Arrays;

public class TeamColor {
    public static ArrayList<String> listAllAvailableColor(){
        return new ArrayList<>(Arrays.asList(
                "red", "blue", "lime", "yellow", "pink", "white", "cyan", "green"
        ));
    }
}
