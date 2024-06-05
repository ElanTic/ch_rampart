/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.enemies;

import Components.Destroyer;
import Components.LevelUpHandler;
import Components.PointsCounter;
import Entities.Entity;
import Entities.Manager;
import Entities.Towers.Tower;
import Entities.bullets.Bullet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jt
 */
public class EnemyManager extends Manager{
    private PointsCounter pc;
    public EnemyManager(EnemyFactory factory, LevelUpHandler lh ) {
        this.factory = factory;
        this.collection = new ArrayList<Entity>();
        pc = new PointsCounter(50);
        pc.connectLevelUpHandler(lh);
        //this.cGroup = group;
    }
    
    @Override 
    public void attachEntity(Entity entity, Node node){
        super.attachEntity(entity, node);
        ((Enemy)entity).hp.signal.connect(new Destroyer(entity, this)); 
        
    }
    @Override 
    public void dettachEntity(Entity entity){
        pc.increase(((Enemy) entity).points);
        super.dettachEntity(entity);
    }
    
    @Override
    public void update(float tpf){
        for (Iterator<Entity> it = collection.iterator(); it.hasNext();) {
            Enemy enemy = (Enemy)it.next();
            Node node = this.checkCollisions(enemy);
            if(node != null){
                ((Entity) node).onHit(enemy.damage);
                enemy.onCollision(3);
            }
            else{
                enemy.update(tpf);
            }
            
        }
    }
    
    @Override
    public Node checkCollisions(Entity entity){
        for (Node nodo : colidableNodes) {
            Spatial bulletGeometry = entity.body;
            BoundingVolume bulletBounding = bulletGeometry.getWorldBound();
            CollisionResults results = new CollisionResults();
            nodo.collideWith(bulletBounding, results); 
            for (CollisionResult r : results){
                Node n = r.getGeometry().getParent();
                if( n instanceof Entity){
                    return n;
                }
            }
        }
        return null;
    }
}
