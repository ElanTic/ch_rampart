/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Entities.Manager;
import Entities.bullets.Bullet;
import Entities.bullets.BulletFactory;
import Entities.bullets.BulletManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author jt
 */
public class Spawner {
    Manager bm;
    String clon;

    public Spawner(Manager bm, String clon) {
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
        bm.attachEntity(clon, loc);
        //bf.clone(clon, loc);
        //bf.createBulletById(clon, loc);
    }
}
