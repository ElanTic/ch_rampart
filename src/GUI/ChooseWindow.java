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
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author jt
 */
public class ChooseWindow {
    Nifty nifty;
    String screen;
    public ChooseWindow( Nifty nifty, String screen, ScreenController controller ) {
        this.nifty = nifty;

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
                    panel(new PanelBuilder() {{
                        childLayoutHorizontal();
                        align(Align.Center);
                        control(new ButtonBuilder("option1", "quick") {{
                            width("30%");
                            align(Align.Center);
                            interactOnClick("selectOption(quick)");
                        }});
                        control(new ButtonBuilder("option2", "mid") {{
                            width("30%");
                            align(Align.Center);
                            interactOnClick("selectOption(mid)");
                        }});
                        control(new ButtonBuilder("option3", "big") {{
                            width("30%");
                            align(Align.Center);
                            interactOnClick("selectOption(big)");
                        }});
                    }});
                }});
            }});
        }}.build(nifty));
    }
    /*
    
    */
}
