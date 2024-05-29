/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class Bullet {

    String name;
    Vector3f acceleration;
    public Geometry shape;
    public RigidBodyControl rigidBodyControl;

    public Bullet(String name, Vector3f initialPosition, Vector3f acceleration, Geometry shape) {
        this.name = name;
        this.acceleration = acceleration;
        this.shape = shape;
        this.rigidBodyControl = new RigidBodyControl(0.01f);
        this.shape.addControl(rigidBodyControl);
        rigidBodyControl.setPhysicsLocation(initialPosition);
        rigidBodyControl.setLinearVelocity(acceleration.mult(10));
    }

    public void update(float tpf) {
        // Apply the force
        //Vector3f force = acceleration.mult(tpf);
        //rigidBodyControl.applyCentralForce(force);
    }

    
    public Bullet clone(Vector3f loc) {
        return new Bullet(name, loc, acceleration, shape.clone());
    }
}
