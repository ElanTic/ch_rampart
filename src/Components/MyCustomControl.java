/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Entities.Bullet;
import Entities.Tower;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author jt
 */
public class MyCustomControl extends RigidBodyControl
    implements PhysicsCollisionListener {
    public MyCustomControl(PhysicsSpace pSpace) {
        pSpace.addCollisionListener(this);       
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        if ( event.getNodeA().getParent() instanceof Bullet ) {
            performDamage((Bullet) event.getNodeA().getParent(), event.getNodeB());
        } else if ( event.getNodeB().getParent() instanceof Bullet ) {
            performDamage((Bullet) event.getNodeB().getParent(), event.getNodeA());
        }
    }
    
    public void performDamage(Bullet b, Spatial n){
        //System.out.println("1 completed");
        if(n instanceof Tower){
            ((Tower)n).hp.reduceHealth(b.damage);
            //b.rigidBodyControl.;
        }
    
    }
    
}
    
