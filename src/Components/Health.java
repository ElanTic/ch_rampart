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
public class Health {
    int health;
    public Signal signal;

    public Health(int initialHealth, Signal signal) {
        this.health = initialHealth;
        this.signal = signal;
    }

    public void reduceHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            signal.emit("onZeroHealth", 0); // Emitir señal cuando la salud baja a 0
        }
    }
}
