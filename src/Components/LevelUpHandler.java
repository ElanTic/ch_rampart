/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import GUI.ChooseWindow;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import mygame.Main;

/**
 *
 * @author jt
 */
public class LevelUpHandler implements ActionListener {
    private Main game;
    private ChooseWindow screen;

    public LevelUpHandler(Main game, ChooseWindow screen) {
        this.game = game;
        this.screen = screen;
    }
    
    
    public void onAction(String message, boolean isPressed, float tpf) {
        if (message.equals("LevelUp")) {
                game.pause = true;
                screen.show();
        }
    }
}