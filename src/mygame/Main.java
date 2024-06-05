package mygame;

import Commands.BarHandler;
import Commands.ChangeColor;
import Commands.ClickNode;
import Components.LevelUpHandler;
import Components.PointsCounter;
import Components.Spawner;
import Entities.Card;
import Entities.Tile;
import Entities.bullets.BulletFactory;
import Entities.bullets.BulletManager;
import Entities.Towers.TowerFactory;
import Entities.Towers.TowerManager;
import Entities.enemies.EnemyFactory;
import Entities.enemies.EnemyManager;
import GUI.PBar;
import Player.PlayerController;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import java.io.File;
import java.io.IOException;
import java.util.Random;
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
    EnemyManager eManager;
    PBar bar;
    private BulletAppState bulletAppState;
    float spawn;
    
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
            Node grid = new Node("grid");
            Node entities = new Node("entities");
            Node gui= new Node("gui");
            Node deck = new Node("deck");
            
            entities.attachChild(creepNode);
            entities.attachChild(playerNode);
            entities.attachChild(grid);
            
            gui.attachChild(deck);
                     
            BulletFactory bfactory = new BulletFactory(this.assetManager);
            bManager = new BulletManager(bfactory, bulletAppState);
            bManager.setDefaultNode(playerNode);
            bManager.addCollisionNode(creepNode);
            TowerFactory tfactory = new TowerFactory(bManager, this.assetManager);
            tManager = new TowerManager(tfactory, bulletAppState);
            EnemyFactory efactory = new EnemyFactory(this.assetManager);
            
            PointsCounter pcounter = new PointsCounter(50);
            bar = new PBar( NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort), this.guiViewPort);            
            BarHandler hb = new BarHandler(bar, pcounter);
            
            eManager = new EnemyManager(efactory, pcounter);
            eManager.setDefaultNode(creepNode);
            eManager.addCollisionNode(grid);
            File db = new File("assets/ch_rampart");
            tManager.loadJson(db, "chinchillas");
            bManager.loadJson(db, "bullets");
            eManager.loadJson(db, "enemies");
            ClickNode clicker = new ClickNode();
            clicker.addNode(guiNode);
            clicker.addNode(grid);
            ChangeColor cColor = new ChangeColor(grid, ColorRGBA.Green);
            controller = new PlayerController(this.getInputManager(), this.cam, clicker, cColor);
            
            
            
            entities.setLocalTranslation(-12,-10,-25);
            entities.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI/4, new Vector3f(1,0,0)));
            
            gui.setLocalTranslation(-100, -100, -1);
            createCard("mid", deck, new Vector3f(-1,0,0),ColorRGBA.Red);
            createCard("big", deck, new Vector3f(0,0,0), ColorRGBA.Green);
            createCard("quick", deck, new Vector3f(1,0,0),ColorRGBA.Blue);
            
            tManager.setPrototype("big");
            
            createGrid(12,2f,grid);
            
            rootNode.attachChild(entities);
            rootNode.attachChild(gui);
            //cam.setLocation(new Vector3f(0,-40, 40));
            //cam.setRotation(new Quaternion().fromAngleAxis(FastMath.PI/10, new Vector3f(1,0,0)));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        controller.update();
        bManager.update(tpf);
        tManager.update(tpf);
        eManager.update(tpf);
        bar.update();
        generateEnemy(tpf);
        
    }
    
    public void generateEnemy(float tpf){
        Spawner spawner = new Spawner(eManager, "hunter");
        spawn += tpf;
        if (spawn>= 3){
            spawn -=3;
            Random r = new Random();
            spawner.spawn(rootNode.getChild("cell_"+r.nextInt(11)+"_0").getLocalTranslation());
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
                Tile tile = new Tile("cell_" + i + "_" + j, tManager);
                tile.setLocalTranslation(i * CELL_SIZE, j * CELL_SIZE, 0);
                
                Geometry cell = new Geometry("Ground", quad);
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat.setColor("Color", ColorRGBA.Orange);
                mat.setTexture("ColorMap", assetManager.loadTexture("Textures/sand.jpg"));
                cell.setMaterial(mat);

                
                tile.attachChild(cell);
                grid.attachChild(tile);
            }
        }
    }
    
    public void createCard(String id, Node deck, Vector3f poss, ColorRGBA color){
        Card nodo = new Card(id, tManager);
        nodo.setLocalTranslation(poss);
        Geometry cell = myBox("card", color);
         nodo.attachChild(cell);
         deck.attachChild(nodo);
    }
    
    private Geometry myBox(String name,  ColorRGBA color){
        Geometry geom = new Geometry(name,
                new Quad(1,1)
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
