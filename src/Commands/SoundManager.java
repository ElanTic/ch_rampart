/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.controls.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jt
 */
public class SoundManager implements ActionListener {
    private AssetManager assetManager;
    private AudioRenderer audioRenderer;
    private Map<String, AudioNode> soundEffects;

    public SoundManager(AssetManager assetManager, AudioRenderer audioRenderer) {
        this.assetManager = assetManager;
        this.audioRenderer = audioRenderer;
        this.soundEffects = new HashMap<>();
    }

    public void loadSounds(JsonNode soundsJson) {
        for (JsonNode sound : soundsJson.path("lines")) {
            String eventId = sound.path("id").asText();
            String soundPath = sound.path("effect").asText();
            AudioNode audioNode = new AudioNode(assetManager, soundPath, DataType.Stream);
            soundEffects.put(eventId, audioNode);
        }
    }

    public void playSound(String eventId) {
        AudioNode audioNode = soundEffects.get(eventId);
        if (audioNode != null) {
            audioNode.play();
        } else {
            System.out.println("Sound not found for event: " + eventId);
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        playSound(name);
    }
}
