/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.enemies;

import Components.Destroyer;
import Entities.Entity;
import Entities.Manager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public class EnemyManager extends Manager{
    
    public EnemyManager(EnemyFactory factory, BulletAppState bulletAppState) {
        this.factory = factory;
        this.collection = new ArrayList<Entity>();
        this.bulletAppState = bulletAppState;
        //this.cGroup = group;
    }
    
    @Override 
    public void attachEntity(Entity entity, Node node){
        super.attachEntity(entity, node);
        ((Enemy)entity).hp.signal.connect(new Destroyer(entity, this));
        
    }
}
