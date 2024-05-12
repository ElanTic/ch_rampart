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
public class TowerFactory {
    private BulletFactory generator;
    private AssetManager assetManager;
    private ArrayList<Tower> collection;

    public TowerFactory(BulletFactory generator, AssetManager assetManager, ArrayList collection) {
        this.generator = generator;
        this.assetManager = assetManager;
        this.collection = collection;
    }
    
    
    

    public Tower createTower(String name, Vector3f loc, Geometry geom, float cooldown, float charge, Spawner spawner, Node towerParentNode) {
        Tower tower = new Tower(
                towerParentNode, 
                name, 
                loc, 
                geom,
                new CoolDown(
                    cooldown, 
                    charge,
                    new Signal()
                ), 
                spawner
        
        );
        attachTower(tower, towerParentNode); // Attach tower to the parent node
        return tower;
    }

    private void attachTower(Tower tower, Node towerParentNode) {
        collection.add(tower);
        towerParentNode.attachChild(tower.geom); 
        tower.geom.setLocalTranslation(tower.loc); 
    }
    
    
    
    public Tower redTower(String name, Node towerParentNode){
        return createTower(
                name, 
                //new Vector3f(1,1,1), 
                new Vector3f(0,1,0),
                myBox(name,  ColorRGBA.LightGray), 
                2.5f, 
                2.4f, 
                new Spawner(generator, generator.createBullet( new Vector3f(0,0,0))), 
                towerParentNode
        );

    }
        
    private Geometry myBox(String name,  ColorRGBA color){
        Geometry geom = new Geometry(name, 
                //new Box(Vector3f.ZERO, 1, 1, 1)
                //new Sphere(3,4,1)
                new Quad(2,2)
        );
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        //mat.getAdditionalRenderState().setAlphaTest(true);
        //mat.getAdditionalRenderState().setAlphaFallOff(0.5f);
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Chinchi.png"));
        geom.setMaterial(mat);
        geom.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/3, new Vector3f(1,0,0)));

        return geom;
    }
}