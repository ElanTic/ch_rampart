/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Entities.Bullet;
import Entities.BulletFactory;
import Entities.BulletManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author jt
 */
public class Spawner {
    BulletManager bm;
    String clon;

    public Spawner(BulletManager bm, String clon) {
        this.bm = bm;
        this.clon = clon;
    }

    public String getClon() {
        return clon;
    }

    public void setClon(String clon) {
        this.clon = clon;
    }
    
    public void spawn(Vector3f loc){
        bm.attachBullet(clon, loc);
        //bf.clone(clon, loc);
        //bf.createBulletById(clon, loc);
    }
}
