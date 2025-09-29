package re.forestier.edu;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        player firstPlayer = new player("Florian", "Ruzberg de Rivehaute", "ADVENTURER", 200, new ArrayList<>());
        firstPlayer.addMoney(400);


        UpdatePlayer.addXp(firstPlayer, 35);
        System.out.println(Affichage.afficherJoueur(firstPlayer));
        System.out.println("------------------");
        UpdatePlayer.addXp(firstPlayer, -20);
        System.out.println(Affichage.afficherJoueur(firstPlayer));

        firstPlayer.level = 5;
        firstPlayer.healthpoints = 100;
        firstPlayer.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(firstPlayer);

        System.out.println(firstPlayer.currenthealthpoints);

        // player p = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        // p.healthpoints = 100;

        // // if level >= 3

        // UpdatePlayer.addXp(p, 40);
        // // if currentHP < HP/2
        // p.currenthealthpoints = 41;
        // p.inventory.add("Magic Bow");
        // UpdatePlayer.majFinDeTour(p);
        // System.out.println(p.currenthealthpoints);
    }
}