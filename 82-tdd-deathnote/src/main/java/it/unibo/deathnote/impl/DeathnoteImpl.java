package it.unibo.deathnote.impl;

import java.util.TreeMap;
import java.util.Locale;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

/**
 * Implementation of "DeathNote" interface.
 */
public final class DeathnoteImpl implements DeathNote {

    private final Map<String, DeadHuman> noteLog;
    private DeadHuman latest;

    /**
     * Creates a new instance of the class.
     */
    public DeathnoteImpl() {
        this.noteLog = new TreeMap<>();
        latest = null;
    }

    @Override
    public String getRule(final int ruleNumber) {
        try {
            return RULES.get(ruleNumber - 1);
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No such rule exists.", e);
        }
    }

    @Override
    public void writeName(final String name) {
            latest = new DeadHuman(System.currentTimeMillis());
            noteLog.put(name, latest);
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause != null && !noteLog.isEmpty()) {
            return latest.updateCause(cause);
        } else {
            throw new IllegalStateException("Unable to write cause. Make sure you wrote a name or provided a cause.");
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if (details != null && !noteLog.isEmpty()) {
            return latest.updateDet(details);
        } else {
            throw new IllegalStateException("Unable to write details. Make sure you wrote a name or provided details.");
        }
    }

    @Override
    public String getDeathCause(final String name) {
        final DeadHuman victim = noteLog.get(name);
        if (isNameWritten(name)) {
            return victim.getDeath();
        } else {
            throw new IllegalArgumentException("Name not written.");
        }
    }

    @Override
    public String getDeathDetails(final String name) {
        final DeadHuman victim = noteLog.get(name);
        if (isNameWritten(name)) {
            return victim.getDet();
        } else {
            throw new IllegalArgumentException("Name not written.");
        }
    }

    @Override
    public boolean isNameWritten(final String name) {
        return noteLog.get(name) != null;
    }

    private final class DeadHuman {

        private static final int CAUSE_EDIT_TIME = 40;
        private static final int DETAILS_EDIT_TIME = 6040;
        private final long timeOfDeath;
        private String deathCause;
        private String deathDetails;

        protected DeadHuman(final long toD, final String death, final String more) {
            this.timeOfDeath = toD;
            this.deathCause = death.toLowerCase(Locale.getDefault());
            this.deathDetails = more;
        }

        protected DeadHuman(final long toD) {
            this(toD, "Heart attack", "");
        }

        private String getDeath() {
            return this.deathCause;
        }

        private boolean updateCause(final String cause) {
            if (this.canEditDeath()) {
                this.deathCause = cause.toLowerCase(Locale.getDefault());
                return true;
            } else {
                return false;
            }
        }

        private String getDet() {
            return this.deathDetails;
        }

        private boolean updateDet(final String details) {
            if (this.canEditDetails()) {
                this.deathDetails = details;
                return true;
            } else {
                return false;
            }
        }

        public boolean canEditDeath() {
            return (System.currentTimeMillis() - timeOfDeath) < CAUSE_EDIT_TIME;
        }

        public boolean canEditDetails() {
            return (System.currentTimeMillis() - timeOfDeath) < DETAILS_EDIT_TIME;
        }
    }
}
