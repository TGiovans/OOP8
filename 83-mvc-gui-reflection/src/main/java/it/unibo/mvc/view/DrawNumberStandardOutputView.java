package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;
import static java.lang.System.out;

/**
 * Class to show output on stdout.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    @Override
    public void result(final DrawResult res) {
        out.println(res.getDescription());
    }

    @Override
    public void setController(final DrawNumberController observer) {
    }

    @Override
    public void start() {
    }
}
