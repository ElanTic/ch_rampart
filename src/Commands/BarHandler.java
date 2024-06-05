/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import Components.PointsCounter;
import GUI.PBar;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author jt
 */
public class BarHandler implements ActionListener {
    private PBar bar;
    private PointsCounter pcounter;

    public BarHandler(PBar bar, PointsCounter pcounter) {
        this.bar = bar;
        this.pcounter = pcounter;
        pcounter.connectLevelUpHandler(this);
        
        bar.setPoints(pcounter.getPoints());
        bar.setNextLevel(pcounter.getNextLevel());
        bar.setLevelCount(pcounter.getLevelCount());
        
    }
    

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("LevelUp")){
            bar.setNextLevel(pcounter.getNextLevel());
            bar.setLevelCount(pcounter.getLevelCount());
        }
        bar.setPoints(pcounter.getPoints());
    }
    
    
    
}
