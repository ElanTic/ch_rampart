/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public BulletManager(Node bulletParentNode, BulletFactory bf) {
        this.bulletParentNode = bulletParentNode;
        this.collection = new ArrayList<>();
        this.factory =bf;
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
    
    public void attachBullet(String id, Vector3f poss){
        attachBullet(factory.createBulletById(id, poss));
    }

    public void attachBullet(Bullet bullet) {
        collection.add(bullet);
        bulletParentNode.attachChild(bullet.shape);
        bullet.shape.setLocalTranslation(bullet.poss);
    }

    public void update(float tpf) {
        ArrayList<Bullet> delleted = new ArrayList<Bullet>();
        for (Bullet bullet : collection){
            if (bullet.poss.y > -20){
                bullet.update(tpf);
            }
            else {
                delleted.add(bullet);
            }
        }
        for(Bullet bullet : delleted){
            collection.remove(bullet);
            bulletParentNode.detachChild(bullet.shape);
        }
        delleted.clear();
    }

    public ArrayList<Bullet> getCollection() {
        return collection;
    }
}
