package com.unicorn.api.infra.configs;

import java.io.File;
import java.io.IOException;

import com.unicorn.api.infra.exceptions.UserCreatorException;

public class UserFolderCreator {
    private static final String BASE_DIR = "unicornData";

    public static boolean createUserFolder(Long userId) {
        String userFolderName = "unicorn_user_" + userId;
        File userFolder = new File(BASE_DIR, userFolderName);

        if (!userFolder.exists()) {
            if (userFolder.mkdirs()) {
                System.out.println("Pasta criada com sucesso: " + userFolder.getAbsolutePath());
                return true;
            } else {
                System.out.println("Falha ao criar a pasta: " + userFolder.getAbsolutePath());
                throw new UserCreatorException("Failed to create user folder: " + userFolder.getAbsolutePath());
            }
        } else {
            System.out.println("A pasta já existe: " + userFolder.getAbsolutePath());
            throw new UserCreatorException("Folder already exists: " + userFolder.getAbsolutePath());
        }
    }

    public static boolean createAppFolder(Long appId, Long userId) {
        String appFolderName = "unicorn_app_" + appId;
        File appFolder = new File(BASE_DIR + "/unicorn_user_" + userId, appFolderName);

        if (!appFolder.exists()) {
            if (appFolder.mkdirs()) {
                System.out.println("Pasta criada com sucesso: " + appFolder.getAbsolutePath());

                // Criar o arquivo unicorn_app_database.sqlite dentro da nova pasta
                File databaseFile = new File(appFolder, "unicorn_app_database.sqlite");
                try {
                    if (databaseFile.createNewFile()) {
                        System.out.println("Arquivo criado com sucesso: " + databaseFile.getAbsolutePath());
                        return true;
                    } else {
                        System.out.println("Falha ao criar o arquivo: " + databaseFile.getAbsolutePath());
                        throw new UserCreatorException(
                                "Failed to create database file: " + databaseFile.getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao criar o arquivo: " + e.getMessage());
                    throw new UserCreatorException("Failed to create database file: " + databaseFile.getAbsolutePath());
                }
            } else {
                System.out.println("Falha ao criar a pasta: " + appFolder.getAbsolutePath());
                throw new UserCreatorException("Failed to create app folder: " + appFolder.getAbsolutePath());
            }
        } else {
            System.out.println("A pasta já existe: " + appFolder.getAbsolutePath());
            throw new UserCreatorException("Folder already exists: " + appFolder.getAbsolutePath());
        }
    }

    public static String createObjectsFolder(Long appId, Long userId) {
        String objectsFolderPath = BASE_DIR + "/unicorn_user_" + userId + "/unicorn_app_" + appId + "/objects";
        File objectsFolder = new File(objectsFolderPath);

        if (!objectsFolder.exists()) {
            if (objectsFolder.mkdirs()) {
                System.out.println("Pasta 'objects' criada com sucesso: " + objectsFolder.getAbsolutePath());
            } else {
                System.out.println("Falha ao criar a pasta 'objects': " + objectsFolder.getAbsolutePath());
                throw new UserCreatorException("Failed to create objects folder: " + objectsFolder.getAbsolutePath());
            }
        } else {
            System.out.println("A pasta 'objects' já existe: " + objectsFolder.getAbsolutePath());
        }

        // Retorna o caminho da pasta 'objects'
        return objectsFolder.getAbsolutePath();
    }

    public static boolean createAppSqliteFile(Long appId, Long userId) {
        String appFolderName = "unicorn_app_" + appId;
        File appFolder = new File(BASE_DIR + "/unicorn_user_" + userId, appFolderName);

        if (!appFolder.exists()) {
            if (appFolder.mkdirs()) {
                System.out.println("Pasta criada com sucesso: " + appFolder.getAbsolutePath());
                return true;
            } else {
                System.out.println("Falha ao criar a pasta: " + appFolder.getAbsolutePath());
                throw new UserCreatorException("Failed to create app folder: " + appFolder.getAbsolutePath());
            }
        } else {
            System.out.println("A pasta já existe: " + appFolder.getAbsolutePath());
            throw new UserCreatorException("Folder already exists: " + appFolder.getAbsolutePath());
        }
    }

}
