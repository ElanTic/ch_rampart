/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class Tile extends Node implements ActionListener {
    Manager manager;

    public Tile(String name, Manager manager) {
        super(name);
        this.manager = manager;
    }
    

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(this.getQuantity() > 1) return;
        manager.attachPrototype(this);
    }
    
}
