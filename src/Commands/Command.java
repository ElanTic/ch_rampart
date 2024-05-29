/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import com.jme3.math.Ray;

/**
 *
 * @author jt
 */
public interface Command {
    public void execute(Ray direction);
}
