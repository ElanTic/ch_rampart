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
public class Card extends Node implements ActionListener {
    
    Manager manager;

    public Card(String name, Manager manager) {
        super(name);
        this.manager = manager;
    }
    

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        manager.setPrototype(this.name);
        this.getParent().setLocalTranslation(-100, -100, -1);
    }
    
}
