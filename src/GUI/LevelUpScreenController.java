/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Entities.Manager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;

/**
 *
 * @author jt
 */
public class LevelUpScreenController implements ScreenController {
        private Nifty nifty;
        private Screen screen;
        
        private Main game;
        Manager manager;
        public LevelUpScreenController(Main game, Manager manager){
            this.manager= manager;
            this.game = game;
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

        public void selectOption(String option) {
            manager.setPrototype(option);
            game.pause = false;
            nifty.gotoScreen("HUD");
        }
    }
