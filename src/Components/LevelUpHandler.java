/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class LevelUpHandler implements ActionListener {
    private Node guiNode;
            
    
   
    public LevelUpHandler( Node guiNode) {
        this.guiNode = guiNode;
    }
    public void onAction(String message, boolean isPressed, float tpf) {
        guiNode.setLocalTranslation(-1,-4,-1);
        
    }
}