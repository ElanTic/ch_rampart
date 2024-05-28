/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author jt
 */
public class BulletManager {
    private Node bulletParentNode;
    private ArrayList<Bullet> collection;
    private BulletFactory bf;

    public BulletManager(Node bulletParentNode, BulletFactory bf) {
        this.bulletParentNode = bulletParentNode;
        this.collection = new ArrayList<>();
        this.bf =bf;
    }
    
    public void attachBullet(String id, Vector3f poss){
        attachBullet(bf.createBulletById(id, poss));
    }

    public void attachBullet(Bullet bullet) {
        collection.add(bullet);
        bulletParentNode.attachChild(bullet.shape);
        bullet.shape.setLocalTranslation(bullet.poss);
    }

    public void update(float tpf) {
        for (Bullet bullet : collection) {
            bullet.update(tpf);
        }
    }

    public ArrayList<Bullet> getCollection() {
        return collection;
    }
}
