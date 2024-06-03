/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Towers;

import Commands.Shoot;
import Components.CoolDown;
import Components.Health;
import Components.Spawner;
import Entities.Entity;
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
public class Tower  extends Entity{
    //String name;
    public Geometry body;
    public Geometry hitbox;
    CoolDown cooldown;
    public Spawner spawner;
    Shoot shoot;
    //public RigidBodyControl rigidBodyControl;
    public Health hp;
    
    public Tower(String name, Geometry geom, CoolDown cooldown, Health hp, Spawner spawner) {
        this.name = name;
        this.body = geom;
        this.cooldown = cooldown;
        this.hp = hp;
        this.spawner = spawner;
        shoot = new Shoot(this);
        cooldown.signal.connect(shoot);
        addCollisionBox();
        this.attachChild(body);
        geom.getLocalTranslation().addLocal(new Vector3f(-1,0,0));
    }
    
    public void update(float tpf){
        cooldown.update(tpf);
        
    }  
     private void addCollisionBox() {
        BoundingBox bbox = (BoundingBox) body.getWorldBound();
        Vector3f extent = bbox.getExtent(new Vector3f());        
        BoxCollisionShape collisionShape = new BoxCollisionShape(extent);
        //rigidBodyControl = new RigidBodyControl(collisionShape, 0);
        //addControl(rigidBodyControl);
        
    }
}
