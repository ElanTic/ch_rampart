package mygame;

import Commands.ChangeColor;
import Commands.PlaceTower;
import Entities.BulletFactory;
import Entities.BulletManager;
import Entities.TowerFactory;
import Entities.TowerManager;
import Player.PlayerController;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    PlayerController controller;
    TowerManager tManager;
    BulletManager bManager;
    private BulletAppState bulletAppState;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Chinchilla\'s rampart");
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        try {
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
            Node deck = new Node("deck");
            rootNode.setLocalTranslation(-12,-10,-25);
            
            createCard("mid", deck, new Vector3f(0,-4,0),ColorRGBA.Red);
            createCard("big", deck, new Vector3f(4,-4,0), ColorRGBA.Green);
            createCard("quick", deck, new Vector3f(8,-4,0),ColorRGBA.Blue);
            
            
            createGrid(12,2f,grid);
            
            rootNode.attachChild(creepNode);
            rootNode.attachChild(playerNode);
            rootNode.attachChild(towerNode);
            rootNode.attachChild(grid);
            rootNode.attachChild(deck);
            //grid.attachChild(geom);           
            BulletFactory bfactory = new BulletFactory(this.assetManager);
            bManager = new BulletManager(playerNode, bfactory, bulletAppState);
            TowerFactory tfactory = new TowerFactory(bManager, this.assetManager);
            tManager = new TowerManager(tfactory, bulletAppState);
            File db = new File("assets/ch_rampart");
            tManager.loadJson(db, "chinchillas");
            bManager.loadJson(db, "bullets");
            PlaceTower ptower = new PlaceTower(grid, tManager, "big");
            ChangeColor cColor = new ChangeColor(grid, ColorRGBA.Green);
            controller = new PlayerController(this.getInputManager(), this.cam, ptower, cColor);
            
            rootNode.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI/4, new Vector3f(1,0,0)));
            
            //cam.setLocation(new Vector3f(0,-40, 40));
            //cam.setRotation(new Quaternion().fromAngleAxis(FastMath.PI/10, new Vector3f(1,0,0)));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        controller.update();
        bManager.update(tpf);
        tManager.update(tpf);
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
    
    public void createCard(String id, Node deck, Vector3f poss, ColorRGBA color){
        Node nodo = new Node(id);
        nodo.setLocalTranslation(poss);
        Geometry cell = myBox("card", color);
         nodo.attachChild(cell);
         deck.attachChild(nodo);
    
    }
    
    private Geometry myBox(String name,  ColorRGBA color){
        Geometry geom = new Geometry(name, 
                //new Box(Vector3f.ZERO, 1, 1, 1)
                //new Sphere(3,4,1)
                new Quad(4,4)
        );
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        //mat.getAdditionalRenderState().setAlphaTest(true);
        //mat.getAdditionalRenderState().setAlphaFallOff(0.5f);
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Chinchi.png"));
        geom.setMaterial(mat);
        //geom.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/3, new Vector3f(1,0,0)));

        return geom;
    }
}
