package se.biplob.game.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    private final Resident resident= new Resident("Resident", 10, 3);
    private final Burglar burglar= new Burglar(10, 4);

    @Test
    void testTakeHit_Decreases_Health_For_resident() {
        assertEquals(10, resident.getHealth());
        burglar.punch(resident);
        assertEquals(6, resident.getHealth());
    }
    @Test
    void testTakeHit_Decreases_Health_For_Burglar() {
        assertEquals(10, burglar.getHealth());
        resident.punch(burglar);
        assertEquals(7, burglar.getHealth());
    }
    @Test
    void testIsConscious_TrueWhenHealthPositive() {
        assertTrue(resident.isConscious());
        assertTrue(burglar.isConscious());

        resident.punch(burglar);
        assertTrue(burglar.isConscious());
        resident.punch(burglar);
        resident.punch(burglar);
        assertTrue(burglar.isConscious());
        resident.punch(burglar);

        assertFalse(burglar.isConscious());
    }
    @Test
    void testResidentDamageIncreasesWhenFryingPanFound() {

        assertEquals(3, resident.getDamage());
        resident.setDamage();
        assertEquals(5, resident.getDamage());
    }
}