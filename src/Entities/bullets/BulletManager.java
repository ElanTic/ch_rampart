/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.bullets;

import Entities.Entity;
import Entities.Manager;
import Entities.enemies.Enemy;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.Application;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jt
 */
public class BulletManager extends Manager {
    //private Node bulletParentNode;
    private float viewportLeft;
    private float viewportRight;
    private float viewportTop;
    private float viewportBottom;

    public BulletManager( BulletFactory bf, BulletAppState bulletAppState) {
        this.collection = new ArrayList<>();
        this.factory = bf;
        this.bulletAppState = bulletAppState;
        //this.cGroup = group;

        // Initialize the viewport boundaries
        //this.viewportLeft = -app.getCamera().getWidth() / 2f;
        //this.viewportRight = app.getCamera().getWidth() / 2f;
        //this.viewportTop = app.getCamera().getHeight() / 2f;
        //this.viewportBottom = -app.getCamera().getHeight() / 2f;
    }

    public void loadJson(File jsonFile, String root) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        JsonNode sheets = rootNode.path("sheets");
        for (JsonNode sheet : sheets) {
            if (sheet.path("name").asText().equals(root)) {
                JsonNode lines = sheet.path("lines");
                factory.loadJson(lines);
                return;
            }
        }
    }

    public void attachBullet(String id, Vector3f poss) {
        Bullet b = (Bullet)factory.createEntity(id);
        attachEntity(b, defaultNode);
        b.setLocalTranslation(poss);
    }

    public void update(float tpf) {
        ArrayList<Entity> deleted = new ArrayList<>();
        for (Entity bullet : collection) {
            
            Vector3f bulletPos = bullet.getLocalTranslation();
            if (bulletPos.y < -20 ) {
                deleted.add(bullet);
            } else {
                /*CollisionResults results = new CollisionResults();
                bulletParentNode.getParent().getChild("grid").collideWith(((Bullet)bullet).shape.getWorldBound(), results);
                if (results.size()>0){
                    Enemy enemy = (Enemy)results.getClosestCollision().getGeometry().getParent();
                    enemy.hp.reduceHealth(((Bullet)bullet).damage);
                }*/
                
                    bullet.update(tpf);
            }
        }
        for (Entity bullet : deleted) {
            dettachEntity(bullet);
        }
        deleted.clear();
    }
    
    public void checkCollisions(Node nodo){
        for (Iterator<Entity> it = collection.iterator(); it.hasNext();) {
            Bullet bullet = (Bullet)it.next();
            Geometry bulletGeometry = bullet.shape; // Assuming Bullet has a method to get its Geometry
            BoundingVolume bulletBounding = bulletGeometry.getWorldBound();

            CollisionResults results = new CollisionResults();
            nodo.collideWith(bulletBounding, results); // Use BoundingVolume for collision check
            if (results.size()>0){
                bullet.setLocalTranslation(-100,-100,-100);
                Enemy enemy = (Enemy)results.getClosestCollision().getGeometry().getParent();
                enemy.hp.reduceHealth(((Bullet)bullet).damage);
            }
        }
    
    }
}
