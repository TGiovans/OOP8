package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathnoteImpl;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDeathNote {

    private static final String HUMAN1 = "this person";
    private static final String HUMAN2 = "another person";
    private static final String CAUSE1 = "Karting accident";
    private static final String CAUSEDEF = "Heart attack";
    private static final String CAUSE2 = "Overdose";
    private static final long SLEEP_SHORT = 100L;
    private static final long SLEEP_LONG = 6000L + SLEEP_SHORT;
    private DeathNote note; 

    @BeforeEach
    void setUp() {
        note = new DeathnoteImpl();
        assertNotNull(note);
    }

    @Test
    void testNegRules() {
        try {
            note.getRule(0);
            fail();
        } catch (final IllegalArgumentException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertNotEquals(e.getMessage(), "");
            try {
                note.getRule(-1);
                fail();
            } catch (final IllegalArgumentException err) {
                assertNotNull(err);
                assertNotNull(err.getMessage());
                assertNotEquals(err.getMessage(), "");
            }
        }
    }

    @Test
    void testRulesNotNull() {
        int i = 0;
        while (true) {
            try {
                final String rule = note.getRule(i);
                assertNotNull(rule);
            } catch (final IllegalArgumentException e) {
                assertNotNull(e);
                assertNotNull(e.getMessage());
                assertNotEquals(e.getMessage(), "");
                break;
            } 
            i++;
        }
    }

    @Test
    void testWritingUser() {
        assertFalse(note.isNameWritten(HUMAN1));
        note.writeName(HUMAN1);
        assertTrue(note.isNameWritten(HUMAN1));
        assertFalse(note.isNameWritten(HUMAN2) || note.isNameWritten(""));
    }

    @Test
    void testCauseDeath() throws InterruptedException {
        try {
            note.writeDeathCause(CAUSE1);
            fail();
        } catch (final IllegalStateException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertNotEquals(e.getMessage(), "");
        }
        note.writeName(HUMAN2);
        assertEquals(note.getDeathCause(HUMAN2), CAUSEDEF.toLowerCase(Locale.getDefault()));
        note.writeName(HUMAN1);
        assertTrue(note.writeDeathCause(CAUSE1));
        assertEquals(CAUSE1.toLowerCase(Locale.getDefault()), note.getDeathCause(HUMAN1));
        Thread.sleep(SLEEP_SHORT);
        assertFalse(note.writeDeathCause(CAUSE2));
        assertEquals(note.getDeathCause(HUMAN1), CAUSE1.toLowerCase(Locale.getDefault()));
    }

    @Test
    void testDeathDetails() throws InterruptedException {
        try {
            note.writeDetails("The kart exploded.");
            fail();
        } catch (final IllegalStateException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertNotEquals(e.getMessage(), "");
        }
        note.writeName(HUMAN2);
        assertEquals(note.getDeathDetails(HUMAN2), "");
        assertTrue(note.writeDetails("ran for too long"));
        assertEquals("ran for too long", note.getDeathDetails(HUMAN2));
        note.writeName("New Person");
        Thread.sleep(SLEEP_LONG);
        assertFalse(note.writeDetails(CAUSE2));
        assertNotEquals(note.getDeathDetails(HUMAN2), CAUSE2);
    }
}
