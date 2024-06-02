/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.bullets;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class Bullet extends Node{

    String name;
    Vector3f acceleration;
    public Geometry shape;
    public RigidBodyControl rigidBodyControl;
    public float damage;
    public float mass;

    public Bullet(String name, Vector3f initialPosition, Vector3f acceleration, float mass, float damage, Geometry shape) {
        this.name = name;
        this.acceleration = acceleration;
        this.damage = damage;
        this.shape = shape;
        this.mass = mass;
        this.rigidBodyControl = new RigidBodyControl(mass);
        this.shape.addControl(rigidBodyControl);
        rigidBodyControl.setPhysicsLocation(initialPosition);
        //rigidBodyControl.setLinearVelocity(acceleration);
        this.attachChild(shape);
    }

    public void update(float tpf) {
        // Apply the force
        Vector3f force = acceleration.mult(tpf);
        rigidBodyControl.applyCentralForce(force);
    }

    
    public Bullet clone(Vector3f loc) {
        //return new Bullet(name, loc, acceleration, shape.clone());
        return null;
    }
}
