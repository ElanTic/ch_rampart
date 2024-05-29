/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import Entities.Tower;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;

/**
 *
 * @author jt
 */
public class Shoot implements ActionListener{
    Tower tower;

    public Shoot(Tower tower) {
        this.tower = tower;
    }
    

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("onCharged")) {
                shoot();
            }
    }
    
    public void shoot(){
        Vector3f vector = tower.geom.getParent().getWorldTranslation();
        tower.spawner.spawn(new Vector3f(vector.x+1 ,vector.y, vector.z).addLocal(tower.geom.getLocalTranslation()));
    }
    
    
}
