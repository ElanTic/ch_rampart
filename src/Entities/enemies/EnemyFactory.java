/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.enemies;

import Commands.Signal;
import Components.CoolDown;
import Components.Health;
import Entities.Entity;
import Entities.Factory;
import com.fasterxml.jackson.databind.JsonNode;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Quad;
import java.io.IOException;

/**
 *
 * @author jt
 */
public class EnemyFactory implements Factory{

    private AssetManager assetManager;
    private JsonNode sheets;

    public EnemyFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    @Override
    public void loadJson(JsonNode jsonFile){
        sheets = jsonFile;
    }
    @Override
    public Entity createEntity(String id) {
        for (JsonNode line : sheets) {
            if (line.path("type").asText().equals(id)) {
                return createEntityFromJson(line);
            }
        }
        return null;
    }
    @Override
    public Entity createEntityFromJson(JsonNode jsonNode) {
        String name = jsonNode.path("type").asText();
        float coolDown = jsonNode.path("cool_down").floatValue();
        float hp = jsonNode.path("healt").floatValue();
        int colorValue = jsonNode.path("color").intValue();
        String texture = jsonNode.path("texture").asText();
        String meshPath = jsonNode.path("mesh").asText();
        float damage = jsonNode.path("damage").floatValue();
        float speed = jsonNode.path("speed").floatValue();
        float points = jsonNode.path("points").floatValue();

        ColorRGBA color = new ColorRGBA(
            ((colorValue >> 16) & 0xff) / 255.0f,
            ((colorValue >>  8) & 0xff) / 255.0f,
            ((colorValue      ) & 0xff) / 255.0f,
            1
        );

        Geometry geom = createGeom(name, new Quad(2, 2), texture, color);
        return createEnemy(name, geom, coolDown, hp, damage, speed, points);
    }

    private Enemy createEnemy(String name, Geometry geom, float cooldown, float hp, float damage, float speed, float points) {
        return new Enemy(name, geom, new CoolDown(cooldown, 0, new Signal()), new Health(hp, new Signal()), damage, speed, points);
    }

    private Geometry createGeom(String name, Mesh mesh, String texture, ColorRGBA color) {
        Geometry geom = new Geometry(name, mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        mat.setTexture("ColorMap", assetManager.loadTexture(texture));
        geom.setMaterial(mat);
        geom.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI / 3, new Vector3f(1, 0, 0)));
        return geom;
    }
}
