/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Commands.Signal;
import Entities.Entity;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author jt
 */
public class PointsCounter {
    private double points;
    private double nextLevel;
    private int levelCount;
    private Signal levelUpSignal;

    // Constructor
    public PointsCounter() {
        this.points = 0;
        this.nextLevel = 100; // Initial next level threshold
        this.levelCount = 1; // Level count starts at 1
        this.levelUpSignal = new Signal();
    }

    // Method to increase points
    public void increase(float tpf) {
        points += tpf;
        if (points >= nextLevel) {
            points -= nextLevel; 
            levelCount++;
            IncreaseLevel();
            levelUpSignal.emit("LevelUp", (float)nextLevel); 
        }
    }
    private void IncreaseLevel(){
        nextLevel = nextLevel + ((nextLevel/5) * Math.log(levelCount));
    }

    // Method to connect a handler to the levelUpSignal
    public void connectLevelUpHandler(ActionListener handler) {
        this.levelUpSignal.connect(handler);
    }

    // Method to disconnect a handler from the levelUpSignal
    public void disconnectLevelUpHandler(ActionListener handler) {
        this.levelUpSignal.disconnect(handler);
    }
}
