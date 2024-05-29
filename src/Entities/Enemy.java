/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Commands.Shoot;
import Components.CoolDown;
import Components.Spawner;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author jt
 */
public class Enemy {
    String name;
    public Vector3f poss;
    Vector3f acceleration;
    public Geometry shape;

    
    public Enemy(String name, Vector3f poss, Vector3f acceleration, Geometry shape) {
        this.name = name;
        this.poss = poss;
        this.acceleration = acceleration;
        this.shape = shape;
    }  
    
    public void update(float tpf){
        poss.addLocal(acceleration.mult(tpf));
        this.shape.setLocalTranslation(poss);
    }
   
}
