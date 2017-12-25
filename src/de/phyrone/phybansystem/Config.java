package de.phyrone.phybansystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.phyrone.phybansystem.bantemplate.BanTemplate;
import de.phyrone.phybansystem.util.Utils;

public class Config {
	public String CMD = "b";
	public String noPerm ="&cder Computer sagt nein!";
	public HashMap<String, BanTemplate> Reasons = new HashMap<>();
	public ArrayList<UUID> ImmunePlayers = new ArrayList<>(Arrays.asList(
			UUID.fromString("4723c900-4b13-4901-96fb-4baec8e52a84"),UUID.fromString("ee3bc545-c9b5-465d-b2de-5fdc8376c285")
			,UUID.fromString("be3889f8-e71c-4fba-a20a-3a8060c19c67")));

    public Config() {
    	Reasons = Utils.getDefaultReasons();

    }

    // DON'T TOUCH THE FOLLOWING CODE
    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            instance = fromDefaults();
        }
        return instance;
    }

    public static void load(File file) {
        instance = fromFile(file);

        // no config file found
        if (instance == null) {
            instance = fromDefaults();
        }
    }

    public static void load(String file) {
        load(new File(file));
    }

    private static Config fromDefaults() {
        Config config = new Config();
        return config;
    }

    public void toFile(String file) {
        toFile(new File(file));
    }

    public void toFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConfig = gson.toJson(this);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(jsonConfig);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Config fromFile(File configFile) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
new FileInputStream(configFile)));
            return gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}