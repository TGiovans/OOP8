package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathnoteImpl;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.DirectColorModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
class TestDeathNote {

    DeathNote note;

    @BeforeEach
    void setUp(){
        note = new DeathnoteImpl();
        assertNotNull(note);
    }

    @Test
    void testNegRules(){
        try{
            note.getRule(0);
            fail();
        }catch(IndexOutOfBoundsException e){
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertNotEquals(e.getMessage(), "");
            try{
                note.getRule(-1);
                fail();
            }catch(IllegalArgumentException f){
                assertNotNull(f);
                assertNotNull(f.getMessage());
                assertNotEquals(f.getMessage(), "");
            }
        }
    }

    @Test
    void testRulesNotNull(){
        for(String rule : DeathNote.RULES){
            assertNotNull(rule);
            assertNotEquals(rule, "");
        }
    }

    @Test
    void testWritingUser(){
        assertFalse(note.isNameWritten("This Person"));
        note.writeName("This Person");
        assertTrue(note.isNameWritten("This Person"));
        assertFalse(note.isNameWritten("Another Person") || note.isNameWritten(""));
    }

    @Test
    void testCauseDeath(){
        try{
            note.writeDeathCause("Karting accident");
            fail();
        }catch(IllegalArgumentException e){
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertNotEquals(e.getMessage(), "");
        }
        note.writeName("Another Person");
        assertEquals("Heart attack", note.getDeathCause("Another Person"));
        note.writeName("Pilot Person");
        assertTrue(note.writeDeathCause("Pilot Person"));
        assertEquals("Karting accident", note.getDeathCause("Pilot Person"));
        Thread.sleep(100L);
        assertFalse(note.writeDeathCause("Overdose"));
        assertEquals(note.getDeathCause("Pilot Person"), "Karting accident");
    }

    @Test
    void testDeathDetails(){
        try{
            note.writeDetails("The kart exploded.");
            fail();
        }catch(IllegalArgumentException e){
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertNotEquals(e.getMessage(), "");
        }
        note.writeName("One More Person");
        assertNull(note.getDeathDetails("One More Person"));
        assertTrue(note.writeDetails("ran for too long"));
        assertEquals("ran for too long", note.getDeathDetails("One More Person"));
        note.writeName("New Person");
        Thread.sleep(6100L);
        assertFalse(note.writeDetails("Overdose"));
        assertEquals(note.getDeathDetails("New Person"), "Overdose");
    }
}