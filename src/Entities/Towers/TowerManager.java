/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Towers;

import Components.Destroyer;
import Entities.Entity;
import Entities.Manager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;
import java.util.ArrayList;


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
        //tower.rigidBodyControl.setPhysicsLocation(nodo.getWorldTranslation().add(new Vector3f(1, 1, 0)));
        //tower.rigidBodyControl.setPhysicsRotation(nodo.getWorldRotation());
    }
}
