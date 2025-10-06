package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.Affichage;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class UnitTests {

    // PLAYER

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.playerName, is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }


    @Test
    @DisplayName("AssertUpdateMoney")
    void testUpdateMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(120); 
        assertEquals(220, p.money);
        p.removeMoney(120);
        assertEquals(100, p.money);
        p.removeMoney(p.money);
        assertEquals(0, p.money);
        // p.addMoney(null);
        // assertEquals(220, p.money);
    }

    @Test
    @DisplayName("Adding 0 don't change the amount")
    void testAddingZeroMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(0);
        assertEquals(100, p.money);
    }

    @Test
    @DisplayName("Retrieve the good amount of Level")
    void testRetrievingGoodLevel() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertEquals(1, p.retrieveLevel());
        UpdatePlayer.addXp(p, 10); // xp : 10, pour niveau 2
        assertEquals(2, p.retrieveLevel());
        UpdatePlayer.addXp(p, 17); // xp : 27, pour niveau 3
        assertEquals(3, p.retrieveLevel());
        UpdatePlayer.addXp(p, 30); // xp : 57, pour niveau 4
        assertEquals(4, p.retrieveLevel());
        UpdatePlayer.addXp(p, 54); // xp : 111, pour niveau 5
        assertEquals(5, p.retrieveLevel());
    }

    @Test
    @DisplayName("getXP get the good amount")
    void testGetXP() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        UpdatePlayer.addXp(p, 222);
        assertEquals(222, p.getXp());
    }

    @Test
    @DisplayName("Verify class")
    void testClass() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertEquals("ADVENTURER", p.getAvatarClass());
        player p2 = new player("Florian2", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        assertEquals("DWARF", p2.getAvatarClass());
        player p3 = new player("Florian3", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        assertEquals("ARCHER", p3.getAvatarClass());
    }

    @Test
    @DisplayName("impossible to insert an non - existent class")
    void testNonExistentClass() {
        player p = new player("Florian", "Grognak le barbare", "IdontExist", 100, new ArrayList<>());
        assertEquals(null, p.playerName);
    }

    // // Affichage

    // @Test
    // @DisplayName("assertItemAffichage"
    // void testAffichageItems() {
    //     player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    // 


    // UpdatePlayer

    @Test
    @DisplayName("si xp n'augmente pas le niveau, il reste le même")
    void testAddxp() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        int levelBefore = p.retrieveLevel();
        boolean bool = UpdatePlayer.addXp(p, 5); // Adding 5 XP for not leveling UP
        int LevelAfter = p.retrieveLevel();
        assertEquals(levelBefore, LevelAfter);
        assertFalse(bool);
    }



    @Test
    @DisplayName("Gagner assez d'XP augmente bien le niveau")
    void testLevelingUp() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        int levelBefore = p.retrieveLevel();
        boolean bool = UpdatePlayer.addXp(p, 35); // Adding 5 XP for not leveling UP
        int LevelAfter = p.retrieveLevel();
        assertTrue(LevelAfter > levelBefore);
        assertTrue(bool);
    }


    @Test
    @DisplayName("Test si le joueur est vraiment KO lorsque les PV sont à 0 et inversement")
    void testPLayerKO() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.currenthealthpoints = 0;
        UpdatePlayer.majFinDeTour(p);

        assertTrue(outContent.toString().contains("Le joueur est KO !"));

        outContent.reset();

        p.currenthealthpoints = 17;
        UpdatePlayer.majFinDeTour(p);

        assertFalse(outContent.toString().contains("Le joueur est KO !"));
    }


    @Test
    @DisplayName("Impossible to have more CurrentHP than the maxHP")
    void testMaxHP() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 728;
        assertTrue(p.currenthealthpoints > p.healthpoints);
        UpdatePlayer.majFinDeTour(p);
        assertEquals(100, p.currenthealthpoints);
    }


    @Test
    @DisplayName("HP Do not change when CurrentHP > HP/2")
    void testHP() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 100;
        p.currenthealthpoints = 72;
        assertTrue(p.currenthealthpoints > p.healthpoints/2);
        UpdatePlayer.majFinDeTour(p);
        assertEquals(72, p.currenthealthpoints);
    }



    @Test
    @DisplayName("Testint HP Updating as an ADventurer")
    void testAdventurerHP() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 100;

        // if level >= 3

        UpdatePlayer.addXp(p, 30);
        // if currentHP < HP/2
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(42, p.currenthealthpoints);

        // if level <= 3

        UpdatePlayer.addXp(p, -20);
        // if currentHP < HP/2
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(41, p.currenthealthpoints);
    }






        @Test
    @DisplayName("Testint HP Updating as an Dwarf")
    void testDwarfHP() {
        player p = new player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 100;

        // Without Holy Elixir

        // if currentHP < HP/2
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(41, p.currenthealthpoints);

        // With Holy Elixir
        p.inventory.add("Holy Elixir");

        // if currentHP < HP/2
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(42, p.currenthealthpoints);


    }



            @Test
    @DisplayName("Testint HP Updating as an Archer")
    void testArcherHP() {
        player p = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 100;

        // Without Magic Bow

        // if currentHP < HP/2
        p.currenthealthpoints = 40;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(41, p.currenthealthpoints);

        // With Magic Bow
        p.inventory.add("Magic Bow");

        // if currentHP < HP/2
        p.currenthealthpoints = 41;
        UpdatePlayer.majFinDeTour(p);
        assertEquals(46, p.currenthealthpoints);


    }



    @Test
    @DisplayName("Les abilités sont mis à jour correctement lors de level up")
        void testAbilities() {
        player p = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());

        assertEquals(1, p.retrieveLevel());
        assertThat(p.abilities.get("INT"), is(1));
        assertThat(p.abilities.get("ATK"), is(3));
        assertThat(p.abilities.get("CHA"), is(1));
        assertThat(p.abilities.get("VIS"), is(3));
        
        UpdatePlayer.addXp(p, 10); // xp : 10, pour niveau 2
        assertEquals(2, p.retrieveLevel());
        assertThat(p.abilities.get("INT"), is(1));
        assertThat(p.abilities.get("ATK"), is(3));
        assertThat(p.abilities.get("CHA"), is(2));
        assertThat(p.abilities.get("VIS"), is(3)); 
        assertThat(p.abilities.get("DEF"), is(1));       

        UpdatePlayer.addXp(p, 17); // xp : 27, pour niveau 3
        assertEquals(3, p.retrieveLevel());
        assertThat(p.abilities.get("INT"), is(1));
        assertThat(p.abilities.get("ATK"), is(3));
        assertThat(p.abilities.get("CHA"), is(2));
        assertThat(p.abilities.get("VIS"), is(3)); 
        assertThat(p.abilities.get("DEF"), is(1)); 

        UpdatePlayer.addXp(p, 30); // xp : 57, pour niveau 4
        assertEquals(4, p.retrieveLevel());
        assertThat(p.abilities.get("INT"), is(1));
        assertThat(p.abilities.get("ATK"), is(3));
        assertThat(p.abilities.get("CHA"), is(2));
        assertThat(p.abilities.get("VIS"), is(3)); 
        assertThat(p.abilities.get("DEF"), is(2));     

        UpdatePlayer.addXp(p, 54); // xp : 111, pour niveau 5
        assertEquals(5, p.retrieveLevel());
        assertThat(p.abilities.get("INT"), is(1));
        assertThat(p.abilities.get("ATK"), is(4));
        assertThat(p.abilities.get("CHA"), is(2));
        assertThat(p.abilities.get("VIS"), is(3)); 
        assertThat(p.abilities.get("DEF"), is(2)); 
    }

}
