/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public class BulletFactory {
    private Node bulletParentNode;
    private AssetManager assetManager;
    private ArrayList<Bullet> collection;

    public BulletFactory(Node bulletParentNode, AssetManager assetManager, ArrayList collection) {
        this.bulletParentNode = bulletParentNode;
        this.assetManager = assetManager;
        this.collection = collection;
    }

    

    public Bullet createBullet(String name, Vector3f pos, Vector3f acceleration, Geometry shape) {
        Bullet bullet = new Bullet(name, bulletParentNode, pos, acceleration, shape);
        attachBullet(bullet); 
        return bullet;
    }

    private void attachBullet(Bullet bullet) {
        collection.add(bullet);
        bulletParentNode.attachChild(bullet.shape); 
        bullet.shape.setLocalTranslation(bullet.poss); 
    }
    
    public Bullet createBullet(Vector3f cordinates){
        return createBullet("", cordinates, new Vector3f(0,-5f,0), myBox(collection.size() + "",  ColorRGBA.White));
    
    }
    private Geometry myBox(String name,  ColorRGBA color){
        Geometry geom = new Geometry(name, new Box(Vector3f.ZERO, .3f,.3f,.3f));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        return geom;
    }
    
    private Geometry myBox2(String name,  ColorRGBA color){
        Geometry geom = new Geometry(name, new Box(Vector3f.ZERO, 1f,1f,1f));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        return geom;
    }
    
      private Geometry myBox3(String name,  ColorRGBA color){
        Geometry geom = new Geometry(name, new Box(Vector3f.ZERO, .1f,.1f,.1f));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        return geom;
    }
    
    public void clone(Bullet bullet, Vector3f loc){
        attachBullet(createBullet(loc));
    }
    
    public Bullet createBullet(String id, Vector3f cordinates){
        switch(id){
            case"big":
                return createBullet(id, cordinates, new Vector3f(0,-4f,0), myBox2(collection.size() + "",  ColorRGBA.White));
            case"small":
                return createBullet(id, cordinates, new Vector3f(0,-12f,0), myBox3(collection.size() + "",  ColorRGBA.White));
            default:
                return createBullet(id, cordinates, new Vector3f(0,-6f,0), myBox(collection.size() + "",  ColorRGBA.White));
        }
    }
}