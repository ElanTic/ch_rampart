package Components;

import Entities.Tower;
import Entities.TowerManager;
import com.jme3.input.controls.ActionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jt
 */
public class Destroyer implements ActionListener {
    Tower tower;
    TowerManager manager;

    public Destroyer(Tower tower, TowerManager manager) {
        this.tower = tower;
        this.manager = manager;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("onZeroHealth")) {
            manager.detachTower(tower, tower.getParent());
        }
    }
}
