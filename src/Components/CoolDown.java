/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Commands.Signal;

/**
 *
 * @author jt
 */
public class CoolDown {
    float cooldown;
    float charge;
    public Signal signal;

    public CoolDown(float cooldown, float charge, Signal signal) {
        this.cooldown = cooldown;
        this.charge = charge;
        this.signal = signal;
    }
    
    public void update(float tpf){
        if((charge += tpf) >= cooldown){
            charge -= cooldown;
            signal.emit("onCharged", tpf);
        }
    }
    
    public void lowCooldown(float s){
        this.cooldown -= s;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
    
}
