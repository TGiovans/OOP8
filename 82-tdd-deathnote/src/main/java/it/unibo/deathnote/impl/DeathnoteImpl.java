package it.unibo.deathnote.impl;

import java.util.List;
import java.util.Map;
import it.unibo.deathnote.api.DeathNote;

public class DeathnoteImpl implements DeathNote {

    /*ATK PLAN DN1:
    create subclass human with name, death cause and details.
    write in theuir instances when updating.
    save time of death for reference.
    */
    @Override
    public String getRule(int ruleNumber) {
        throw new IndexOutOfBoundsException("ERROR: get rekt");
    }

    @Override
    public void writeName(String name) {
        throw new UnsupportedOperationException("ERROR: get rekt");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new UnsupportedOperationException("ERROR: get rekt");
    }

    @Override
    public boolean writeDetails(String details) {
        throw new UnsupportedOperationException("ERROR: get rekt");
    }

    @Override
    public String getDeathCause(String name) {
        throw new UnsupportedOperationException("ERROR: get rekt");
    }

    @Override
    public String getDeathDetails(String name) {
        throw new UnsupportedOperationException("ERROR: get rekt");
    }

    @Override
    public boolean isNameWritten(String name) {
        throw new UnsupportedOperationException("ERROR: get rekt");
    }
    
}
