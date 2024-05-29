/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bullet.BulletAppState;
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
public class TowerManager{
    ArrayList<Tower> collection;
    TowerFactory factory;
    private BulletAppState bulletAppState;


    public TowerManager(TowerFactory factory, BulletAppState bulletAppState){
        this.factory = factory;
        this.collection = new ArrayList<Tower>();
        this.bulletAppState = bulletAppState;
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
        attachTower(factory.createTower(id), nodo);
        
    }
    
    private void attachTower(Tower tower, Node towerParentNode) {
        collection.add(tower);
        towerParentNode.attachChild(tower.geom);
        //bulletAppState.getPhysicsSpace().add(tower.rigidBodyControl);
        //tower.rigidBodyControl.setPhysicsLocation(towerParentNode.getWorldTranslation());//.add(new Vector3f(0,1,0)));
    }
    
    public void update(float tpf){
        for (Tower tower : collection){
            tower.update(tpf);
        }
    }
}
