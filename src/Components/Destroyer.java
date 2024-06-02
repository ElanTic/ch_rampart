package Components;

import Entities.Entity;
import Entities.Manager;
import Entities.Towers.Tower;
import Entities.Towers.TowerManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jt
 */
public class Destroyer implements ActionListener {
    Entity node;
    Manager manager;

    public Destroyer(Entity node, Manager manager) {
        this.node = node;
        this.manager = manager;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("onZeroHealth")) {
            manager.dettachEntity(node);
        }
    }
}
