/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public abstract class Manager {
    protected final ArrayList<Node> colidableNodes = new ArrayList<Node>();
    protected ArrayList<Entity> collection;
    protected BulletAppState bulletAppState;
    //protected int cGroup;
    protected Factory factory;
    protected Node defaultNode;
    protected String prototype;
    
    
    
    public void attachEntity(Entity entity, Node parent) {
        collection.add(entity);
        parent.attachChild(entity);
    }
    
    public void attachEntity(String id, Vector3f poss) {
        Entity b = factory.createEntity(id);
        attachEntity(b, defaultNode);
        b.setLocalTranslation(poss);
    }
    
    public void attachPrototype(Node parent){
        if(prototype == null) return;
        attachEntity(prototype, parent);
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
    
    public boolean addCollisionNode(Node nodo){
        return this.colidableNodes.add(nodo);
    }
    
    public boolean removeCollisionNode(Node nodo){
        return this.colidableNodes.remove(nodo);
    }
    
    public Node checkCollisions(Entity entity){
        for (Node nodo : colidableNodes) {
            Geometry bulletGeometry = entity.body;
            BoundingVolume bulletBounding = bulletGeometry.getWorldBound();
            CollisionResults results = new CollisionResults();
            nodo.collideWith(bulletBounding, results); 
            if (results.size()>0){
                return results.getClosestCollision().getGeometry().getParent();
            }
        }
        return null;
    }

    public String getPrototype() {
        return prototype;
    }

    public void setPrototype(String prototype) {
        this.prototype = prototype;
    }
    
}
