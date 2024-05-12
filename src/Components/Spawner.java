/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Entities.Bullet;
import Entities.BulletFactory;
import com.jme3.math.Vector3f;

/**
 *
 * @author jt
 */
public class Spawner {
    BulletFactory bf;
    Bullet clon;

    public Spawner(BulletFactory bf, Bullet clon) {
        this.bf = bf;
        this.clon = clon;
    }

    public Bullet getClon() {
        return clon;
    }

    public void setClon(Bullet clon) {
        this.clon = clon;
    }
    
    public void spawn(Vector3f loc){
        bf.clone(clon, loc);
    }
}
