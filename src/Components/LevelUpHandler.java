/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author jt
 */
public class LevelUpHandler implements ActionListener {
    private Nifty nifty;
    private String screen;

    public LevelUpHandler(Nifty nifty, String screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
    
    
    public void onAction(String message, boolean isPressed, float tpf) {
        if (message.equals("LevelUp")) {
                nifty.gotoScreen(screen);
        }
    }
}