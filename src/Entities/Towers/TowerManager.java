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
    TowerFactory factory;

    public TowerManager(TowerFactory factory, BulletAppState bulletAppState, int group){
        this.factory = factory;
        this.collection = new ArrayList<Entity>();
        this.bulletAppState = bulletAppState;
        this.cGroup = group; 
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
    
    public void attachTower(String id, Node nodo ){
        Tower tower = factory.createTower(id);
        attachEntity(tower, nodo);
        tower.hp.signal.connect(new Destroyer(tower, this));
    }
}
