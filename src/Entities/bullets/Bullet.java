/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.bullets;

import Entities.Entity;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author jt
 */
public class Bullet extends Entity{

    Vector3f acceleration;
    public float damage;
    public float mass;

    public Bullet(String name, Vector3f acceleration, float mass, float damage, Spatial shape) {
        this.name = name;
        this.acceleration = acceleration;
        this.damage = damage;
        this.body = shape;
        this.mass = mass;
        this.attachChild(shape);
        //Vector3f extent = bbox.getExtent(new Vector3f()).multLocal(1, 2, 1);
        //shape.setModelBound(worldBound);
        //BoxCollisionShape collisionShape = new BoxCollisionShape(extent);
    }

    public void update(float tpf) {
        // Apply the force
        super.updateLogicalState(tpf);
        Vector3f force = acceleration.mult(tpf);
        this.setLocalTranslation(this.getLocalTranslation().add(force));
    }

    
    public Bullet clone(Vector3f loc) {
        return null;
    }
}
