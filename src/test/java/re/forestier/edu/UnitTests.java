package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

public class UnitTests {

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
    void testAddMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(120); 
        assertEquals(220, p.money);
        p.removeMoney(120);
        assertEquals(100, p.money);
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
    @DisplayName("impossible to insert an non - existent class")
    void testNonExistentClass() {
        player p = new player("Florian", "Grognak le barbare", "IdontExist", 100, new ArrayList<>());
        assertEquals(null, p.playerName);
    }

}
