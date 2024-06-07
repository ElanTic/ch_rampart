/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Random;

/**
 *
 * @author jt
 */
public class ChooseWindow {
    Nifty nifty;
    String screen;
    String[] chillas;
    String id;
    private String[] options = new String[3];
    private Random random = new Random();

    public ChooseWindow(Nifty nifty, String screen, ScreenController controller, String[] chillas) {
        this.nifty = nifty;
        this.chillas = chillas;
        options[0] = chillas[0];
        options[1] = chillas[1];
        options[2] = chillas[2];
        this.id = screen;
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        nifty.addScreen(screen, new ScreenBuilder(screen) {{
            controller(controller);
            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                panel(new PanelBuilder("levelUpPanel") {{
                    childLayoutVertical();
                    align(Align.Center);
                    backgroundColor("#333a");
                    width("50%");
                    height("50%");
                    text(new TextBuilder() {{
                        text("Level Up! Choose an option:");
                        font("Interface/Fonts/Default.fnt");
                        height("10%");
                        width("100%");
                    }});
                    randomize();
                    panel(new PanelBuilder() {{
                        childLayoutHorizontal();
                        align(Align.Center);
                        control(new ButtonBuilder("option1", options[0] ) {{
                            width("30%");
                            align(Align.Center);
                            interactOnClick("selectOption("+options[0]+")");
                        }});
                        control(new ButtonBuilder("option2", options[1]) {{
                            width("30%");
                            align(Align.Center);
                            interactOnClick("selectOption("+options[1]+")");
                        }});
                        control(new ButtonBuilder("option3", options[2]) {{
                            width("30%");
                            align(Align.Center);
                            interactOnClick("selectOption("+options[2]+")");
                        }});
                    }});
                }});
            }});
        }}.build(nifty));
    }

    public void randomize() {
        for (int i = 0; i < options.length; i++) {
            options[i] = chillas[random.nextInt(chillas.length)];
        }
    }
    private String getOption(){
        return chillas[random.nextInt(chillas.length)];
    }
}