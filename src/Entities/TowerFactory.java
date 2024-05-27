/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Commands.Signal;
import Components.CoolDown;
import Components.Spawner;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.scene.Mesh;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TowerFactory {
    private BulletFactory generator;
    private AssetManager assetManager;
    private ArrayList<Tower> collection;
    private JsonNode sheets;  // Almacenar el nodo raíz para búsquedas posteriores

    public TowerFactory(BulletFactory generator, AssetManager assetManager, ArrayList<Tower> collection) {
        this.generator = generator;
        this.assetManager = assetManager;
        this.collection = collection;
    }

    public void loadJson(File jsonFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        sheets = rootNode.path("sheets");
    }

    public Tower createTower(String id, Node towerParentNode) {
        for (JsonNode sheet : sheets) {
            if (sheet.path("name").asText().equals("chinchillas")) {
                JsonNode lines = sheet.path("lines");
                for (JsonNode line : lines) {
                    if (line.path("chinchilla").asText().equals(id)) {
                        return createTowerFromJson(line, towerParentNode);
                    }
                }
            }
        }
        return null;  // O lanzar una excepción si el ID no se encuentra
    }

    private Tower createTowerFromJson(JsonNode jsonNode, Node towerParentNode) {
        String name = jsonNode.path("chinchilla").asText();
        float coolDown = jsonNode.path("cool_down").floatValue();
        int colorValue = jsonNode.path("color").intValue();
        String texture = jsonNode.path("texture").asText();
        String bulletType = jsonNode.path("bullet").asText();
        String meshPath = jsonNode.path("mesh").asText();

        ColorRGBA color = new ColorRGBA(
            ((colorValue >> 16) & 0xff) / 255.0f,
            ((colorValue >>  8) & 0xff) / 255.0f,
            ((colorValue      ) & 0xff) / 255.0f,
            //((colorValue >> 24) & 0xff) / 255.0f
            1
        );

        Geometry geom = createGeom(name, new Quad(2,2), texture, color);
        Spawner spawner = new Spawner(generator, bulletType);
        return createTower(name, new Vector3f(0, 1, 0), geom, coolDown, 0, spawner, towerParentNode);
    }

    private Tower createTower(String name, Vector3f loc, Geometry geom, float cooldown, float charge, Spawner spawner, Node towerParentNode) {
        Tower tower = new Tower(towerParentNode, name, loc, geom, new CoolDown(cooldown, charge, new Signal()), spawner);
        attachTower(tower, towerParentNode);
        return tower;
    }

    private void attachTower(Tower tower, Node towerParentNode) {
        collection.add(tower);
        towerParentNode.attachChild(tower.geom);
        tower.geom.setLocalTranslation(tower.loc);
    }
    
    private Geometry createGeom(String name, Mesh mesh, String texture,ColorRGBA color){
        Geometry geom = new Geometry(name, 
                //new Quad(2,2)
                mesh
        );
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        //mat.getAdditionalRenderState().setAlphaTest(true);
        //mat.getAdditionalRenderState().setAlphaFallOff(0.5f);
        mat.setTexture("ColorMap", assetManager.loadTexture(texture));
        geom.setMaterial(mat);
        geom.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/3, new Vector3f(1,0,0)));

        return geom;
    }
}