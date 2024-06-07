/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 *
 * @author jt
 */
public class GameOverScreen {
    public GameOverScreen(Nifty nifty, String screen, GameOverScreenController controller) {
        nifty.addScreen(screen, new ScreenBuilder(screen) {{
            controller(controller);
            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                backgroundColor("#333a");
            }});
            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                panel(new PanelBuilder("panel_top") {{
                    height("80%");
                    width("100%");
                    alignCenter();
                    valignCenter();
                    childLayoutCenter();
                    text(new TextBuilder() {{
                        text("Game Over");
                        font("Interface/Fonts/Default.fnt");
                        color("#FFFFFF"); // Ensure the text color is visible
                        width("100%");
                        height("20%");
                    }});
                }});
                panel(new PanelBuilder("panel_bottom") {{
                    height("20%");
                    width("100%");
                    alignCenter();
                    valignCenter();
                    childLayoutHorizontal();
                    panel(new PanelBuilder("panel_left") {{
                        height("100%");
                        width("50%");
                        alignCenter();
                        valignCenter();
                        childLayoutCenter();
                        control(new ButtonBuilder("RetryButton", "Retry") {{
                            alignCenter();
                            valignCenter();
                            width("80px"); // Set width and height to ensure the button is visible
                            height("40px");
                            interactOnClick("onRetryButtonClicked()");
                        }});
                    }});
                    panel(new PanelBuilder("panel_right") {{
                        height("100%");
                        width("50%");
                        alignCenter();
                        valignCenter();
                        childLayoutCenter();
                        control(new ButtonBuilder("ExitButton", "Exit") {{
                            alignCenter();
                            valignCenter();
                            width("80px"); // Set width and height to ensure the button is visible
                            height("40px");
                            interactOnClick("onExitButtonClicked()");
                        }});
                    }});
                }});
            }});
        }}.build(nifty));
    }
}
