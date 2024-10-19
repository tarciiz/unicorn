package com.unicorn.api.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.unicorn.api.domain.unicornApp.UnicornApp;
import com.unicorn.api.domain.user.User;
import com.unicorn.api.infra.configs.UserFolderCreator;
import com.unicorn.api.services.UnicornAppService;
import com.unicorn.api.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/unicorn/object")
public class UnicornObjectController {

    @PostMapping("/user/{userId}/{appId}/save")
    public ResponseEntity<?> save(@PathVariable("userId") Long userId,
            @PathVariable("userId") Long appId, @RequestBody Map<String, Object> object) {
        String path = UserFolderCreator.createObjectsFolder(appId, userId);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(object);

        String objectName = (String) object.get("name");

        try (FileWriter writer = new FileWriter(path + "/" + objectName + ".json")) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to save JSON: " + e.getMessage());
        }

        return ResponseEntity.ok().body("JSON saved successfully at: " + path);
    }

    @GetMapping("/user/{userId}/{appId}/load/{objectName}")
    public ResponseEntity<?> load(
            @PathVariable("userId") Long userId,
            @PathVariable("appId") Long appId,
            @PathVariable("objectName") String objectName) {

        String path = UserFolderCreator.createObjectsFolder(appId, userId) + "/" + objectName + ".json";

        // Usar Gson para ler o arquivo JSON
        Gson gson = new GsonBuilder().create();
        Map<String, Object> object;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            object = gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to read JSON: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("JSON syntax error: " + e.getMessage());
        }

        return ResponseEntity.ok().body(object);
    }

}
