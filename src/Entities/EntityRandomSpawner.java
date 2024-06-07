/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Commands.Signal;
import Components.CoolDown;
import Components.Spawner;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jt
 */
public class EntityRandomSpawner implements ActionListener{
    private ArrayList<CoolDown> tiers;
    private Spawner spawner;
    private Random r;
    private Vector3f poss;

    public EntityRandomSpawner(Spawner spawner) {
        this.spawner = spawner;
        tiers = new ArrayList<CoolDown>();
        r = new Random();
        poss = new Vector3f(0,0,0);
    }

    public Vector3f getLocalTranslation() {
        return poss;
    }

    public void setLocalTranslation(Vector3f localTranslation) {
        this.poss = localTranslation;
    }
    
    
    
    public void update(float tpf){
        for(CoolDown c : tiers){
            c.update(tpf);
        }
    }
    
    public void spawn(String id){
        spawner.setClon(id);
        spawner.spawn(poss.add(new Vector3f(r.nextInt(12)*2,0,0)));
    }
    
    public void addTier(float cooldown, String[] entities){
        EntityTier tier = new EntityTier(entities, this);
        Signal s = new Signal();
        s.connect(tier);
        CoolDown cd = new CoolDown(cooldown, 0,s);
        tiers.add(cd);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("LevelUp")) {
            for(CoolDown c : tiers){
                if(c.getCooldown() < 1) continue;
                double decrease =  (4/c.getCooldown()) * Math.log(tpf) ;
                if(c.getCooldown() - decrease < 1){
                    c.setCooldown(1);
                    continue;
                }
                c.lowCooldown((float)decrease);
            }
        }
    }
    
}
