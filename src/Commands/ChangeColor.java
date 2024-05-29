/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author jt
 */
public class ChangeColor implements Command {

    private Node grid;
    private ColorRGBA markColor;
    private Geometry previous;
    private ColorRGBA previousColor;

    public ChangeColor(Node grid, ColorRGBA markColor) {
        this.grid = grid;
        this.markColor = markColor;
    }
    
    @Override
    public void execute(Ray ray) {
        AmbientLight ambient = new AmbientLight(); 
        ambient.setColor(new ColorRGBA(1,1,1,1)); 
        CollisionResults results = new CollisionResults();
        grid.collideWith(ray, results);
        if (results.size()>0){
            Geometry g = results.getClosestCollision().getGeometry();
            if(g == previous){
                return;
            }
            if(previous != null){
                previous.getMaterial().setColor("Color", previousColor);
            }
            previous = g;
            previousColor =(ColorRGBA) g.getMaterial().getParamValue("Color");
            g.getMaterial().setColor("Color", markColor);
        }  
    }
    
}
