package Commands;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Node;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jt
 */
public class GetCard implements Command{
    Node nodo;
    

    public GetCard(Node nodo) {
        this.nodo = nodo;
        
    }

    @Override
    public void execute(Ray direction) {
        CollisionResults results = new CollisionResults();
        nodo.collideWith(direction, results);
        if (results.size()>0){
            Node nodo = results.getClosestCollision().getGeometry().getParent();
                //ptower.setId(nodo.getName());
                nodo.setLocalTranslation(100,100,100);
        }
    }
    
}
