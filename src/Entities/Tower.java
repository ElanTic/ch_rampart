/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Commands.Shoot;
import Components.CoolDown;
import Components.Spawner;
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
    Node parent;
    String name;
    Vector3f loc;
    public Geometry geom;
    CoolDown cooldown;
    public Spawner spawner;
    Shoot shoot;
    
    public Tower(Node parent, String name, Vector3f loc, Geometry geom, CoolDown cooldown, Spawner spawner) {
        this.parent = parent;
        this.name = name;
        this.loc = loc;
        this.geom = geom;
        this.cooldown = cooldown;
        this.spawner = spawner;
        shoot = new Shoot(this);
        cooldown.signal.connect(shoot);
    }
    
    public void update(float tpf){
        cooldown.update(tpf);
        
    }  
}
