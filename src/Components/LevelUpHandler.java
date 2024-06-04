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
public class LevelUpHandler implements ActionListener {
    @Override
    public void onAction(String message, boolean isPressed, float tpf) {
        System.out.println("Level up! Message: " + message + ", Time per frame: " + tpf);
    }
}