/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.enemies;

import Components.CoolDown;
import Components.Health;
import Entities.Entity;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author jt
 */
public class Enemy extends Entity {

    public Geometry hitbox;
    public CoolDown cooldown;
    public Health hp;
    public float damage;
    public float speed;
    public float points;
    
    public Enemy(String name, Geometry geom, Geometry hb, CoolDown cooldown, Health hp, float damage, float speed, float points) {
        this.setName(name);
        this.body = geom;
        this.cooldown = cooldown;
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.points = points;
        this.attachChild(body);
        this.hitbox = hb;
        this.attachChild(hb);
        
    }
    
    
    @Override
    public void update(float tpf){
        cooldown.update(tpf);
        moveEnemy(tpf);
    }

    private void moveEnemy(float tpf) {
        Vector3f force = new Vector3f(0, speed * tpf, 0);
        this.setLocalTranslation(this.getLocalTranslation().add(force));
    }
    
    @Override
    public void onHit(float tpf){
        this.hp.reduceHealth(tpf);
    }
    
    @Override
    public void onCollision(float tpf){
        moveEnemy(-tpf);
    
    }
}