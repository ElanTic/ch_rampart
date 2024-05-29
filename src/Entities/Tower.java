/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Commands.Shoot;
import Components.CoolDown;
import Components.Spawner;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
//import com.jme3.asset.AssetManager;

/**
 *
 * @author jt
 */
public class Tower {
    String name;
    public Geometry geom;
    CoolDown cooldown;
    public Spawner spawner;
    Shoot shoot;
    public RigidBodyControl rigidBodyControl;
    
    public Tower(String name, Geometry geom, CoolDown cooldown, Spawner spawner) {
        this.name = name;
        this.geom = geom;
        this.cooldown = cooldown;
        this.spawner = spawner;
        shoot = new Shoot(this);
        cooldown.signal.connect(shoot);
        //addCollisionBox();
    }
    
    public void update(float tpf){
        cooldown.update(tpf);
        
    }  
     private void addCollisionBox() {
        BoundingBox bbox = (BoundingBox) geom.getWorldBound();
        Vector3f extent = bbox.getExtent(new Vector3f());        
        BoxCollisionShape collisionShape = new BoxCollisionShape(extent);
        rigidBodyControl = new RigidBodyControl(collisionShape, 0);
        geom.addControl(rigidBodyControl);
    }
}
