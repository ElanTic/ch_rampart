/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.controls.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author jt
 */
public class SoundManager implements ActionListener {
    private AssetManager assetManager;
    private AudioRenderer audioRenderer;
    private Map<String, Queue<AudioNode>> soundPools;
    private static final int POOL_SIZE = 5;
    private AudioNode bgmNode;

    public SoundManager(AssetManager assetManager, AudioRenderer audioRenderer) {
        this.assetManager = assetManager;
        this.audioRenderer = audioRenderer;
        this.soundPools = new HashMap<>();
    }
    
    public void loadJson(File jsonFile, String root) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        JsonNode sheets = rootNode.path("sheets");
        for (JsonNode sheet : sheets) {
            if (sheet.path("name").asText().equals(root)) {
                JsonNode lines = sheet.path("lines");
                loadSounds(lines);
            }
        }
    }
    

    public void loadSounds(JsonNode soundsJson) {
        for (JsonNode sound : soundsJson) {
            String eventId = sound.path("id").asText();
            String soundPath = sound.path("effect").asText();
             Queue<AudioNode> pool = new LinkedList<>();
            try {
                for (int i = 0; i < POOL_SIZE; i++) {
                    AudioNode audioNode = new AudioNode(assetManager, soundPath, DataType.Buffer);
                    audioNode.setPositional(false);
                    audioNode.setLooping(false);
                    audioNode.setVolume(1);
                    pool.add(audioNode);
                }
                soundPools.put(eventId, pool);
            } catch (AssetNotFoundException e) {
                System.err.println("Sound file not found: " + soundPath);
            } catch (Exception e) {
                System.err.println("Error loading sound file: " + soundPath);
                e.printStackTrace();
            }
        }
    }

    public void playSound(String eventId) {
         Queue<AudioNode> pool = soundPools.get(eventId);
        if (pool != null && !pool.isEmpty()) {
            AudioNode audioNode = pool.poll();
            if (audioNode != null) {
                audioNode.playInstance();
                pool.add(audioNode);  // Re-add the AudioNode back to the pool after playing
            }
        } else {
            System.out.println("Sound pool is empty or sound not found for event: " + eventId);
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        playSound(name);
    }
    
    public void loadBGM(String bgmPath) {
        try {
            bgmNode = new AudioNode(assetManager, bgmPath, DataType.Stream);
            bgmNode.setPositional(false);
            bgmNode.setLooping(true); 
            bgmNode.setVolume(0.5f); 
        } catch (AssetNotFoundException e) {
            System.err.println("BGM file not found: " + bgmPath);
        } catch (Exception e) {
            System.err.println("Error loading BGM file: " + bgmPath);
            e.printStackTrace();
        }
    }

    public void playBGM() {
        if (bgmNode != null) {
            bgmNode.play();
        } else {
            System.out.println("BGM not loaded.");
        }
    }

    public void stopBGM() {
        if (bgmNode != null) {
            bgmNode.stop();
        }
    }
}
