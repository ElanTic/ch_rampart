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
    public PointsCounter(float nextLevel) {
        this.points = 0;
        this.nextLevel = nextLevel;
        this.levelCount = 1; // Level count starts at 1
        this.levelUpSignal = new Signal();
    }

    // Method to increase points
    public void increase(float tpf) {
        points += tpf;
        if (points >= nextLevel) {
            points -= nextLevel; 
            levelCount++;
            increaseLevel();
            levelUpSignal.emit("LevelUp", (float)nextLevel); 
        }
        else{
            levelUpSignal.emit("Increase", (float)points);
        }
    }

    private void increaseLevel() {
        nextLevel = nextLevel + ((nextLevel / 3) * Math.log(levelCount));
    }

    public double getPoints() {
        return points;
    }

    public double getNextLevel() {
        return nextLevel;
    }

    public int getLevelCount() {
        return levelCount;
    }

    public void connectLevelUpHandler(ActionListener handler) {
        this.levelUpSignal.connect(handler);
    }

    public void disconnectLevelUpHandler(ActionListener handler) {
        this.levelUpSignal.disconnect(handler);
    }
}
