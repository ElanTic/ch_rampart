/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author jt
 */
public class GameOverScreenController implements ScreenController {
    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;

    public GameOverScreenController(SimpleApplication app) {
        this.app = app;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    public void onRetryButtonClicked() {
        app.restart();
    }

    public void onExitButtonClicked() {
        app.stop();
    }
}
