package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathnoteImpl implements DeathNote {

    private final class DeadHuman{

        private final long timeOfDeath;
        private String deathCause;
        private String deathDetails;
        private final static int CAUSE_EDIT_TIME = 40;
        private final static int DETAILS_EDIT_TIME = 6040;

        protected DeadHuman(long toD, String death, String more){
            this.timeOfDeath=toD;
            this.deathCause=death.toLowerCase(Locale.getDefault());
            this.deathDetails=more;
        }

        protected DeadHuman(long toD){
            this(toD, "Heart attack", null);
        }

        private final String getDeath(){
            return this.deathCause;
        }

        private final boolean updateCause(String cause){
            if(this.canEditDeath()){
                this.deathCause=cause.toLowerCase(Locale.getDefault());
                return true;
            }else{
                return false;
            }
        }

        private final String getDet(){
            return this.deathDetails;
        }

        private final boolean updateDet(String details){
            if(this.canEditDetails()){
                this.deathDetails=details;
                return true;
            }else{
                return false;
            }
        }

        public final boolean canEditDeath(){
            return (System.currentTimeMillis()-timeOfDeath) < CAUSE_EDIT_TIME;
        }

        public final boolean canEditDetails(){
            return (System.currentTimeMillis()-timeOfDeath) < DETAILS_EDIT_TIME;
        }
    }
    /*ATK PLAN DN1:
    create subclass human with name, death cause and details.
    write in their instances when updating.
    save time of death for reference.
    */
    private Map<String,DeadHuman> noteLog;
    private DeadHuman latest;

    public DeathnoteImpl(){
        this.noteLog = new HashMap<>();
        latest = null;
    }

    @Override
    public String getRule(int ruleNumber) {
        try{
            return RULES.get(ruleNumber-1);
        }catch(IndexOutOfBoundsException e){
            throw new IllegalArgumentException("No such rule exists.");
        }
    }

    @Override
    public void writeName(String name) {
        if(name==null){
            throw new NullPointerException("No name written.");
        }else{
            latest = new DeadHuman(System.currentTimeMillis());
            noteLog.put(name, latest);
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(cause==null || noteLog.isEmpty()){
            return latest.updateCause(cause);
        }else{
            throw new IllegalStateException("Unable to write cause. Make sure you wrote a name or provided a cause.");
        }
    }

    @Override
    public boolean writeDetails(String details) {
        if(details==null || noteLog.isEmpty()){
            return latest.updateDet(details);
        }else{
            throw new IllegalStateException("Unable to write details. Make sure you wrote a name or provided details.");
        }
    }

    @Override
    public String getDeathCause(String name) {
        DeadHuman victim = noteLog.get(name);
        if(isNameWritten(name)){
            return victim.getDeath();
        }else{
            throw new IllegalArgumentException("Name not written.");
        }
    }

    @Override
    public String getDeathDetails(String name) {
        DeadHuman victim = noteLog.get(name);
        if(isNameWritten(name)){
            return victim.getDet();
        }else{
            throw new IllegalArgumentException("Name not written.");
        }
    }

    @Override
    public boolean isNameWritten(String name) {
        return (noteLog.get(name)!=null);
    }
    
}
