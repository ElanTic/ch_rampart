/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Towers;

import Components.Destroyer;
import Entities.Entity;
import Entities.Manager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

/**
 *
 * @author jt
 */
public class TowerManager extends Manager{
    public TowerManager(TowerFactory factory, BulletAppState bulletAppState, int group){
        this.factory = factory;
        this.collection = new ArrayList<Entity>();
        this.bulletAppState = bulletAppState;
        this.cGroup = group; 
    }
    
    public void attachTower(String id, Node nodo ){
        Tower tower = (Tower) factory.createEntity(id);
        attachEntity(tower, nodo);
        tower.hp.signal.connect(new Destroyer(tower, this));
    }
}
