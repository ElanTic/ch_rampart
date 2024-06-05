/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Components.LevelUpHandler;
import Components.PointsCounter;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.tools.SizeValue;


/**
 *
 * @author jt
 */
public class PBar {
    private Nifty nifty;
    int levelCount;
    double points;
    double nextLevel;
    public PBar( NiftyJmeDisplay niftyDisplay, ViewPort guiViewPort) {

        nifty = niftyDisplay.getNifty();

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        nifty.addScreen("HUD", new ScreenBuilder("HUD") {{
            controller(new DefaultScreenController() {
                @Override
                public void onStartScreen() {
                }

                @Override
                public void onEndScreen() {
                }
            });

             layer(new LayerBuilder("background") {{
                childLayoutVertical();
                panel(new PanelBuilder("levelInfoPanel") {{
                    childLayoutVertical();
                    align(Align.Left);
                    text(new TextBuilder("levelLabel") {{
                        text("Level: " + levelCount);
                        font("Interface/Fonts/Default.fnt");
                        height("30px");
                        width("100%");
                    }});
                    text(new TextBuilder("pointsLabel") {{
                        text("Points: " + points + "/" + nextLevel);
                        font("Interface/Fonts/Default.fnt");
                        height("30px");
                        width("100%");
                    }});
                }});
                panel(new PanelBuilder("progressPanel") {{
                    this.childLayoutAbsoluteInside();
                    align(Align.Center);
                    width("90%");
                    height("10px");
                    panel(new PanelBuilder("progressBarBackground") {{
                        childLayoutOverlay();
                        width("100%");
                        height("100%");
                        backgroundColor("#3338");
                    }});
                    panel(new PanelBuilder("progressBarForeground") {{
                        childLayoutAbsolute();
                        align(Align.Left);
                        width("0%");
                        height("100%");
                        backgroundColor("#00F8");
                    }});
                }});
            }});
        }}.build(nifty));

        guiViewPort.addProcessor(niftyDisplay);
        nifty.gotoScreen("HUD");
    }
    
    public void updateHUD() {
        nifty.getScreen("HUD").findElementByName("levelLabel").getRenderer(TextRenderer.class)
                .setText("Level: " + levelCount);
        nifty.getScreen("HUD").findElementByName("pointsLabel").getRenderer(TextRenderer.class)
                .setText("Points: " + (int) points + "/" + (int) nextLevel);
        nifty.getScreen("HUD").findElementByName("progressBarForeground")
                .setConstraintWidth(new SizeValue((getProgress() * 100) + "%"));
        nifty.getScreen("HUD").layoutLayers();
    }
    public void update(){
        updateHUD();
    }
    
    public float getProgress() {
        return (float) (points / nextLevel);
    }

    public int getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(int levelCount) {
        this.levelCount = levelCount;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(double nextLevel) {
        this.nextLevel = nextLevel;
    }  
}
