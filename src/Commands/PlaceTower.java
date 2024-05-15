/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

//import static Entities.Tower.mesh;
import Entities.TowerFactory;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author jt
 */
public class PlaceTower implements Command{
    private Node grid;
    private Ray ray;
    TowerFactory generator;

    public PlaceTower(Node grid,  TowerFactory generator) {
        this.grid = grid;
        this.generator = generator;
    }
    
    

    public Node getGrid() {
        return grid;
    }

    public void setGrid(Node grid) {
        this.grid = grid;
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }
    
    public void execute(Ray ray){
        CollisionResults results = new CollisionResults();
        grid.collideWith(ray, results);
        if (results.size()>0){
            Node nodo = results.getClosestCollision().getGeometry().getParent();
            if (nodo.getChild("tower") != null ) {
                return;
            } else {
                generator.redTower("tower", nodo);
            } 
        }   
    }
    
}
