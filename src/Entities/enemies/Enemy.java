/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.enemies;

import Components.CoolDown;
import Components.Health;
import Entities.Entity;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class Enemy extends Entity {

    public Geometry body;
    public Geometry hitbox;
    public CoolDown cooldown;
    public Health hp;
    public float damage;
    public float speed;

    public Enemy(String name, Geometry geom, CoolDown cooldown, Health hp, float damage, float speed) {
        this.name = name;
        this.body = geom;
        this.cooldown = cooldown;
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;

        addCollisionBox();
        this.attachChild(body);
        geom.getLocalTranslation().addLocal(new Vector3f(-1, 0, 0));
    }
    
    @Override
    public void update(float tpf) {
        cooldown.update(tpf);
        Vector3f force = new Vector3f(0,speed *tpf,0);
        //rigidBodyControl.applyCentralForce(force);
    }

    private void addCollisionBox() {
        BoundingBox bbox = (BoundingBox) body.getWorldBound();
        Vector3f extent = bbox.getExtent(new Vector3f());
        BoxCollisionShape collisionShape = new BoxCollisionShape(extent);
        rigidBodyControl = new RigidBodyControl(collisionShape, 0f);
        //rigidBodyControl.setKinematic(true);
        //rigidBodyControl.setApplyPhysicsLocal(true);
        addControl(rigidBodyControl);
    }
}