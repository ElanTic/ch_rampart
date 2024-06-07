package mygame;

import Commands.BarHandler;
import Commands.ChangeColor;
import Commands.ClickNode;
import Commands.SoundManager;
import Components.GameState;
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
import Entities.EntityRandomSpawner;
import GUI.ChooseWindow;
import GUI.GameOverScreen;
import GUI.GameOverScreenController;
import GUI.LevelUpScreenController;
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
import com.jme3.texture.Texture;
import de.lessvoid.nifty.Nifty;
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
    
    public boolean gameOver = false;
    public boolean pause = false;
    public SoundManager soundManager;
    public GameState gameState;
    private BulletAppState bulletAppState;
    public PBar bar;
    public Nifty nifty;
    PlayerController controller;
    TowerManager tManager;
    BulletManager bManager;
    EnemyManager eManager;
    EntityRandomSpawner spawner;
    
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Chinchilla\'s rampart");
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //bulletAppState = new BulletAppState();
        try {
            gameState = new GameState(this);
            flyCam.setDragToRotate(true);
            inputManager.setCursorVisible(true);            
            Node creepNode = new Node("creeps");
            Node playerNode = new Node("player");
            Node grid = new Node("grid");
            Node entities = new Node("entities");
            
            entities.attachChild(creepNode);
            entities.attachChild(playerNode);
            entities.attachChild(grid);
            //entities.attachChild(quadGeometry);
            
            soundManager = new SoundManager(assetManager, audioRenderer);
            
            BulletFactory bfactory = new BulletFactory(this.assetManager);
            bManager = new BulletManager(bfactory, bulletAppState);
            bManager.setDefaultNode(playerNode);
            bManager.addCollisionNode(creepNode);
            TowerFactory tfactory = new TowerFactory(bManager, this.assetManager);
            tManager = new TowerManager(tfactory, soundManager);
            EnemyFactory efactory = new EnemyFactory(this.assetManager);
            
            PointsCounter pcounter = new PointsCounter(50);
            
            NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
            nifty = niftyDisplay.getNifty();
            GameOverScreenController gameOverScreenController = new GameOverScreenController(this);
            GameOverScreen gos = new GameOverScreen(nifty, "gameOverScreen", gameOverScreenController);
            //nifty.fromXml("GUI/GameOverScreen.xml", "gameOverScreen", gameOverScreenController);

            bar = new PBar(nifty);            
            BarHandler hb = new BarHandler(bar, pcounter);
            String screen = "levelUpScreen";
            LevelUpHandler luh = new LevelUpHandler(this, screen);
            LevelUpScreenController lusc = new LevelUpScreenController(this ,tManager);
            
            ChooseWindow window = new ChooseWindow(nifty, screen, lusc);
            pcounter.connectLevelUpHandler(luh);
            
            guiViewPort.addProcessor(niftyDisplay);
            
            eManager = new EnemyManager(efactory, soundManager, pcounter);
            eManager.setDefaultNode(creepNode);
            eManager.addCollisionNode(grid);
            eManager.connectListener(soundManager);
            eManager.connectListener(gameState);
            
            spawner = new EntityRandomSpawner(new Spawner(eManager,""));
            spawner.addTier(7, new String[]{"fox"});
            spawner.addTier(15, new String[]{"hunter"});
            spawner.addTier(40, new String[]{"sus"});
            File db = new File("assets/ch_rampart");
            tManager.loadJson(db, "chinchillas");
            bManager.loadJson(db, "bullets");
            eManager.loadJson(db, "enemies");
            soundManager.loadJson(db, "sounds");
            soundManager.loadBGM("Sounds/bgm.ogg");
            
            ClickNode clicker = new ClickNode();
            clicker.addNode(grid);
            ChangeColor cColor = new ChangeColor(grid, ColorRGBA.Green);
            controller = new PlayerController(this.getInputManager(), this.cam, clicker, cColor);
            
            entities.setLocalTranslation(-12,-10,-25);
            entities.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI/4, new Vector3f(1,0,0)));
            
            createWorld();
            createGrid(12,2f,grid);
            
            
            rootNode.attachChild(entities);
            spawner.setLocalTranslation(rootNode.getChild("cell_" + 0 + "_" + 0).getLocalTranslation());
            soundManager.playBGM();
            defend(0,4);
            defend(8,12);
            tManager.setPrototype("big");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void defend(int a, int b){
        for(int i = a ; i <b; i++){
            Node n = (Node)rootNode.getChild("cell_" + i + "_" + 1);
            tManager.attachEntity("shield", n);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(!gameOver && !pause){
            controller.update();
            bManager.update(tpf);
            tManager.update(tpf);
            eManager.update(tpf);
            spawner.update(tpf);
            bar.update();
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
    
    public void createWorld(){
        Quad quad = new Quad(settings.getWidth(), settings.getHeight());
            Geometry quadGeometry = new Geometry("BackgroundQuad", quad);

            // Cargar la textura de la imagen para el fondo
            Texture backgroundTexture = assetManager.loadTexture("Textures/suelo.jpg");

            // Crear un material y asignar la textura
            Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            material.setTexture("ColorMap", backgroundTexture);

            // Asignar el material a la geometría
            quadGeometry.setMaterial(material);

            // Colocar la geometría en la posición correcta
            quadGeometry.setLocalTranslation(-700, -500, -880); // Asegurar que el fondo esté detrás de otros elementos

            // Adjuntar la geometría al rootNode para que se renderice en pantalla
            rootNode.attachChild(quadGeometry);
    }
    
    @Override
    public void restart() {
        gameOver = false;
        simpleInitApp();
    }
    
}
