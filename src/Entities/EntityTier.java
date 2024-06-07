/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.jme3.input.controls.ActionListener;
import java.util.Random;

/**
 *
 * @author jt
 */
public class EntityTier implements ActionListener {
    String[] entities;
    Random r;
    EntityRandomSpawner spawner;

    public EntityTier(String[] enemies, EntityRandomSpawner spawner) {
        this.entities = enemies;
        this.spawner = spawner;
        r = new Random();
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        spawner.spawn(entities[r.nextInt(entities.length)]);
    }
    
    
    
}
