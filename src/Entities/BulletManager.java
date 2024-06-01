/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.Application;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public class BulletManager {
    private Node bulletParentNode;
    private ArrayList<Bullet> collection;
    private BulletFactory factory;
    private BulletAppState bulletAppState;
    private int cGroup;

    private float viewportLeft;
    private float viewportRight;
    private float viewportTop;
    private float viewportBottom;

    public BulletManager(Node bulletParentNode, BulletFactory bf, BulletAppState bulletAppState, int group, Application app) {
        this.bulletParentNode = bulletParentNode;
        this.collection = new ArrayList<>();
        this.factory = bf;
        this.bulletAppState = bulletAppState;
        this.cGroup = group;

        // Initialize the viewport boundaries
        this.viewportLeft = -app.getCamera().getWidth() / 2f;
        this.viewportRight = app.getCamera().getWidth() / 2f;
        this.viewportTop = app.getCamera().getHeight() / 2f;
        this.viewportBottom = -app.getCamera().getHeight() / 2f;
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
        attachBullet(factory.createBulletById(id, poss));
    }

    public void attachBullet(Bullet bullet) {
        collection.add(bullet);
        bulletParentNode.attachChild(bullet);
        bulletAppState.getPhysicsSpace().add(bullet.rigidBodyControl);
        bullet.rigidBodyControl.setCollisionGroup(cGroup);
    }

    public void update(float tpf) {
        ArrayList<Bullet> deleted = new ArrayList<>();
        for (Bullet bullet : collection) {
            Vector3f bulletPos = bullet.rigidBodyControl.getPhysicsLocation();
            if (bulletPos.x < viewportLeft || bulletPos.x > viewportRight ||
                bulletPos.y < viewportBottom || bulletPos.y > viewportTop) {
                deleted.add(bullet);
            } else {
                bullet.update(tpf);
            }
        }
        for (Bullet bullet : deleted) {
            detachBullet(bullet);
        }
        deleted.clear();
    }
    
    public void detachBullet(Bullet bullet){
        collection.remove(bullet);
        bulletParentNode.detachChild(bullet);
        bulletAppState.getPhysicsSpace().remove(bullet.rigidBodyControl);
    
    }

    public ArrayList<Bullet> getCollection() {
        return collection;
    }
}
