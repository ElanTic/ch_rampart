/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public abstract class Manager {
    protected ArrayList<Entity> collection;
    protected BulletAppState bulletAppState;
    //protected int cGroup;
    protected Factory factory;
    protected Node defaultNode;
    
    
    public void attachEntity(Entity entity, Node parent) {
        collection.add(entity);
        parent.attachChild(entity);
        //bulletAppState.getPhysicsSpace().add(entity.rigidBodyControl);
        //entity.rigidBodyControl.setCollisionGroup(cGroup);
    }
    
    public void attachEntity(String id, Vector3f poss) {
        Entity b = factory.createEntity(id);
        attachEntity(b, defaultNode);
        b.setLocalTranslation(poss);
    }
    
    public void attachEntity(String id, Node parent){
        Entity b = factory.createEntity(id);
        attachEntity(b, parent);
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
    
    public void dettachEntity(Entity entity) {
        entity.removeFromParent();
        collection.remove(entity);
    }

    public void update(float tpf) {
        for (Entity entity : collection) {
            entity.update(tpf);
        }
    }

    public Node getDefaultNode() {
        return defaultNode;
    }

    public void setDefaultNode(Node defaultNode) {
        this.defaultNode = defaultNode;
    }
}
