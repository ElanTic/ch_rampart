/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author jt
 */
public interface Factory {
    public void loadJson(JsonNode jsonFile);
    public Entity createEntity(String id);
    public Entity createEntityFromJson(JsonNode jsonNode);
    
}
