/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Towers;

import Components.Destroyer;
import Entities.Entity;
import Entities.Manager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;


/**
 *
 * @author jt
 */
public class TowerManager extends Manager{
    public TowerManager(TowerFactory factory, BulletAppState bulletAppState){
        this.factory = factory;
        this.collection = new ArrayList<Entity>();
        this.bulletAppState = bulletAppState;
        //this.cGroup = group; 
    }
    
    @Override
    public void attachEntity(String id, Node nodo ){
        Tower tower = (Tower) factory.createEntity(id);
        attachEntity(tower, nodo);
        tower.hp.signal.connect(new Destroyer(tower, this));
        tower.getLocalTranslation().addLocal(new Vector3f(1, 0, 0));
    }
}
