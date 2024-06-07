/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import com.jme3.input.controls.ActionListener;
import mygame.Main;

/**
 *
 * @author jt
 */
public class GameState implements ActionListener{
    private Main game;

    public GameState(Main game) {
        this.game = game;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("onGameOver")){
            game.gameOver = true;
            game.soundManager.stopBGM();
            game.nifty.gotoScreen("gameOverScreen");
        }
    }
    
    
}
