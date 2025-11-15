package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private static final int VIEWS_PER_TYPE = 3;
    
    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist 
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final Class<?> cl1 = Class.forName("it.unibo.mvc.view.DrawNumberSwingView");
        final Class<?> cl2 = Class.forName("it.unibo.mvc.view.DrawNumberStandardOutputView");
        for(int i = 0; i<VIEWS_PER_TYPE; i++) {
            app.addView((DrawNumberView) cl1.getConstructor().newInstance());
            app.addView((DrawNumberView) cl2.getConstructor().newInstance());
        }
    }
}
