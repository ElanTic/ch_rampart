/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;


/**
 *
 * @author jt
 */
public class BulletFactory {
    private AssetManager assetManager;
    private JsonNode sheets;

    public BulletFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    public void loadJson(JsonNode jsonFile){
        sheets = jsonFile;
    }

    public Bullet createBulletById(String id, Vector3f coordinates){
        for (JsonNode line : sheets) {
            if (line.path("type").asText().equals(id)) {
                        return createBulletFromJson(line, coordinates);
            }
        }
        return null;  // O lanzar una excepción si el ID no se encuentra
    }
    
    

    private Bullet createBulletFromJson(JsonNode jsonNode, Vector3f coordinates) {
        String type = jsonNode.path("type").asText();
        float scale = jsonNode.path("scale").floatValue();
        int colorValue = jsonNode.path("color").intValue();
        JsonNode accelerationNode = jsonNode.path("acceleration").get(0);
        Vector3f acceleration = new Vector3f(
            accelerationNode.path("x").floatValue(),
            accelerationNode.path("y").floatValue(),
            accelerationNode.path("z").floatValue()
        );

        ColorRGBA color = new ColorRGBA(
            ((colorValue >> 16) & 0xff) / 255.0f,
            ((colorValue >>  8) & 0xff) / 255.0f,
            ((colorValue      ) & 0xff) / 255.0f,
            1
        );

        Geometry geom = createGeom(type, scale, color);
        return createBullet(type, coordinates, acceleration, geom);
    }

    private Bullet createBullet(String name, Vector3f pos, Vector3f acceleration, Geometry shape) {
        Bullet bullet = new Bullet(name, pos, acceleration, shape);
        return bullet;
    }

    private Geometry createGeom(String name, float scale, ColorRGBA color) {
        Geometry geom = new Geometry(name, new Box(Vector3f.ZERO, scale, scale, scale));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        return geom;
    }
}