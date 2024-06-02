/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities;

import Entities.enemies.EnemyFactory;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public abstract class Manager {
    protected ArrayList<Entity> collection;
    protected BulletAppState bulletAppState;
    protected int cGroup;
    
    
    public void attachEntity(Entity entity, Node parent) {
        collection.add(entity);
        parent.attachChild(entity);
        bulletAppState.getPhysicsSpace().add(entity.rigidBodyControl);
        entity.rigidBodyControl.setPhysicsLocation(parent.getWorldTranslation().add(new Vector3f(1, 1, 0)));
        entity.rigidBodyControl.setPhysicsRotation(parent.getWorldRotation());
        entity.rigidBodyControl.setCollisionGroup(cGroup);
    }
    
    public void dettachEntity(Entity entity) {
        Node parent = entity.getParent();
        parent.detachChild(entity);
        bulletAppState.getPhysicsSpace().remove(entity.rigidBodyControl);
        collection.remove(entity);
    }

    public void update(float tpf) {
        for (Entity entity : collection) {
            entity.update(tpf);
        }
    }
}
