/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.enemies;

import Components.Destroyer;
import Entities.Entity;
import Entities.Manager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public class EnemyManager extends Manager{

    EnemyFactory factory;
    
    public EnemyManager(EnemyFactory factory, BulletAppState bulletAppState, int group) {
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

    public void attachEnemy(String id, Node node) {
        Enemy enemy = factory.createEnemy(id);
        attachEntity( enemy, node);
        enemy.hp.signal.connect(new Destroyer(enemy, this));
        //enemy.rigidBodyControl.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_02);
    }
}
