package mygame;

import Commands.ChangeColor;
import Commands.PlaceTower;
import Entities.Bullet;
import Entities.BulletFactory;
import Entities.Tower;
import Entities.TowerFactory;
import Player.PlayerController;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import java.util.ArrayList;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    PlayerController controller;
    ArrayList<Tower> towerCollection;
    ArrayList<Bullet> bulletCollection;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Chinchilla\'s rampart");
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        
        Box b = new Box(33, 33, 0);
        Geometry geom = new Geometry("Box", b);
        

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        Node creepNode = new Node("creeps");
        Node playerNode = new Node("player");
        Node towerNode = new Node("tower"); 
        Node grid = new Node("grid");
        rootNode.setLocalTranslation(-12,-12,-25);
        
        createGrid(12,2f,grid);
        
        rootNode.attachChild(creepNode);
        rootNode.attachChild(playerNode);
        rootNode.attachChild(towerNode);
        rootNode.attachChild(grid);
        //grid.attachChild(geom);
        bulletCollection = new ArrayList<Bullet>();
        towerCollection = new ArrayList<Tower>();
        BulletFactory bfactory = new BulletFactory(playerNode, this.assetManager, bulletCollection);
        TowerFactory tfactory = new TowerFactory(bfactory,this.assetManager, towerCollection);
        PlaceTower ptower = new PlaceTower(grid, tfactory);
        ChangeColor cColor = new ChangeColor(grid, ColorRGBA.Green);
        controller = new PlayerController(this.getInputManager(), this.cam, ptower, cColor);
        
        rootNode.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI/4, new Vector3f(1,0,0)));
        
        //cam.setLocation(new Vector3f(0,-40, 40));
        //cam.setRotation(new Quaternion().fromAngleAxis(FastMath.PI/10, new Vector3f(1,0,0)));
    }

    @Override
    public void simpleUpdate(float tpf) {
        controller.update();
        ArrayList<Bullet> delleted = new ArrayList<Bullet>();
        for (Bullet bullet : bulletCollection){
            if (bullet.poss.y > -20){
                bullet.update(tpf);
            }
            else {
                delleted.add(bullet);
            }
        }
        for(Bullet bullet : delleted){
            System.out.println("Deleted bullet: " + bullet.parent.detachChild(bullet.shape));
            bulletCollection.remove(bullet);
        }
        delleted.clear();
        for (Tower tower : towerCollection){
            tower.update(tpf);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void createGrid(int GRID_SIZE, float CELL_SIZE, Node grid){
     for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Quad quad = new Quad(CELL_SIZE, CELL_SIZE);
                Node nodo = new Node("cell_" + i + "_" + j);
                Geometry cell = new Geometry("Ground", quad);
                nodo.setLocalTranslation(i * CELL_SIZE, j * CELL_SIZE, 0);

                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat.setColor("Color", ColorRGBA.Orange);
                mat.setTexture("ColorMap", assetManager.loadTexture("Textures/sand.jpg"));
                cell.setMaterial(mat);

                
                nodo.attachChild(cell);
                grid.attachChild(nodo);
            }
        }
    }
}
