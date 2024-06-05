/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.bullets;

import Entities.Entity;
import Entities.Factory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;


/**
 *
 * @author jt
 */
public class BulletFactory implements Factory{
    private AssetManager assetManager;
    private JsonNode sheets;

    public BulletFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    public void loadJson(JsonNode jsonFile){
        sheets = jsonFile;
    }

    public Entity createEntity(String id){
        for (JsonNode line : sheets) {
            if (line.path("type").asText().equals(id)) {
                        return createEntityFromJson(line);
            }
        }
        return null;  // O lanzar una excepción si el ID no se encuentra
    }
    
    

    public Bullet createEntityFromJson(JsonNode jsonNode) {
        String type = jsonNode.path("type").asText();
        float scale = jsonNode.path("scale").floatValue();
        int colorValue = jsonNode.path("color").intValue();
        JsonNode accelerationNode = jsonNode.path("acceleration").get(0);
        Vector3f acceleration = new Vector3f(
            accelerationNode.path("x").floatValue(),
            accelerationNode.path("y").floatValue(),
            accelerationNode.path("z").floatValue()
        );
        float damage = jsonNode.path("damage").floatValue();
        float mass = jsonNode.path("mass").floatValue();
        
        String texture = jsonNode.path("texture").asText();
        String meshPath = jsonNode.path("mesh").asText();

        ColorRGBA color = new ColorRGBA(
            ((colorValue >> 16) & 0xff) / 255.0f,
            ((colorValue >>  8) & 0xff) / 255.0f,
            ((colorValue      ) & 0xff) / 255.0f,
            1
        );

        Spatial geom;
        geom= createGeom(type, scale, color);
            //geom = createGeom(type,meshPath,scale, texture, color);
        return new Bullet(type, acceleration, mass, damage, geom);
    }

    private Bullet createBullet(String name,  Vector3f acceleration, int damage, Geometry shape) {
        Bullet bullet = new Bullet(name, acceleration, 0.01f, damage, shape);
        return bullet;
    }

    private Geometry createGeom(String name, float scale, ColorRGBA color) {
        Geometry geom = new Geometry(name, new Box(Vector3f.ZERO, scale, scale, scale));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        return geom;
    }
    
    private Spatial createGeom(String name, String mesh, float scale, String texture, ColorRGBA color){
        Spatial geom =  assetManager.loadModel(mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        mat.setTexture("ColorMap", assetManager.loadTexture(texture));
        geom.setMaterial(mat);
        return geom;
    }
}