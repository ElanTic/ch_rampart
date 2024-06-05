/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

//import static Entities.Tower.mesh;
import Entities.Towers.Tower;
import Entities.Towers.TowerFactory;
import Entities.Towers.TowerManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jt
 */
public class ClickNode implements Command {
    private List<Node> nodes;
    private Ray ray;

    public ClickNode() {
        this.nodes = new ArrayList<>();
    }

    public ClickNode(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void removeNode(Node node) {
        this.nodes.remove(node);
    }

    public void execute(Ray ray) {
        CollisionResults results = new CollisionResults();
        for (Node node : nodes) {
            node.collideWith(ray, results);
            if (results.size() > 0) {
                Object nodo = results.getClosestCollision().getGeometry().getParent();
                if (ActionListener.class.isAssignableFrom(nodo.getClass())) {
                    ActionListener ac = (ActionListener) nodo;
                    ac.onAction("onClick", false, 0);
                    return;
                }
            }
        }
    }
}
