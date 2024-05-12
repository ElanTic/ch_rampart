/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class Bullet {
    
    String name;
    public Node parent;
    public Vector3f poss;
    Vector3f acceleration;
    public Geometry shape;

    public Bullet(String name, Node parent, Vector3f poss, Vector3f acceleration, Geometry shape) {
        this.name = name;
        this.parent = parent;
        this.poss = poss;
        this.acceleration = acceleration;
        this.shape = shape;
    }
    
    
    public void update(float tpf){
        poss.addLocal(acceleration.mult(tpf));
        this.shape.setLocalTranslation(poss);
    }
    
    public Bullet clone(Vector3f loc){
        return new Bullet(name, parent, loc, acceleration, shape);
    }
}
