/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Player;

import Commands.Command;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class PlayerController implements ActionListener{
    
    private InputManager inputManager;
    private Command ptower;
    private Command cColor;
    private Camera cam;
    private Ray direction;

    public PlayerController(InputManager inputManager, Camera cam, Command ptower, Command cColor) {
        this.inputManager = inputManager;
        this.cam = cam;
        init_keys();
        this.ptower = ptower;
        this.cColor = cColor;
    }
    
    private void init_keys(){
        this.inputManager.addMapping("PlaceTower", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        this.inputManager.addMapping("unknown", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        this.inputManager.addListener(this, "PlaceTower");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("PlaceTower") && !isPressed) {
                ptower.execute(direction);
            }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody    
    }
    
    
    
    public void update(){
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(
            new Vector2f(click2d.getX(), click2d.getY()), 0f);
        Vector3f dir = cam.getWorldCoordinates(
        new Vector2f(click2d.getX(), click2d.getY()), 1f).
        subtractLocal(click3d);
        direction = new Ray(click3d, dir);
        cColor.execute(direction);
    }
}
