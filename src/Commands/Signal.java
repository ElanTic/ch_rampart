/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;
import com.jme3.input.controls.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jt
 */
public class Signal {
    
    private List<ActionListener> handlers;

    public Signal() {
        this.handlers = new ArrayList<>();
    }

    public void connect(ActionListener handler) {
        this.handlers.add(handler);
    }

    public void disconnect(ActionListener handler) {
        this.handlers.remove(handler);
    }
    
    public void emit(String message, float tpf){
        emit(message,false,tpf);
    
    }
    
    public void emit(String message, boolean isPressed, float tpf) {
        for (ActionListener handler : handlers) {
            handler.onAction(message, isPressed, tpf);
        }
    } 
}
