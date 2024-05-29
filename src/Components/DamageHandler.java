/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import com.jme3.input.controls.ActionListener;

/**
 *
 * @author jt
 */
public class DamageHandler implements ActionListener {
    Health health;

    public DamageHandler(Health health) {
        this.health = health;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("onCollision")) {
            health.reduceHealth((int) tpf); // Reducir la salud en base al daño recibido
        }
    }
}
