package de.phyrone.phybansystem;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BansysPlugin extends JavaPlugin{
	final public static File conffile = new File("plugins/Ban/config.json");
	static BansysPlugin instance;
	
	public static BansysPlugin getInstance() {
		return instance;
	}
	@Override 
	public void onLoad() {
		conffile.getParentFile().mkdirs();
		Config.load(conffile);
		Config.getInstance().toFile(conffile);
	}
	@SuppressWarnings("resource")
	@Override 
	public void onEnable() {
		if(Bukkit.getPluginManager().getPlugin("AdvancedBan") == null){
			URL website;
			System.out.println("Downloading AdvancedBans...");
			try {
				website = new URL("https://api.spiget.org/v2/resources/8695/download");
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("plugins/AdvancedBan.jar");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				System.out.println("AdvancedBan.jar saved!");
				System.out.println("Loading AdvancedBan...");
				Bukkit.getPluginManager().loadPlugin(new File("plugins/AdvancedBan.jar"));
				System.out.println("Enable AdvancedBan...");
				Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().getPlugin("AdvancedBan"));
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		instance = this;
		Bukkit.getPluginManager().registerEvents(new Listner(),this);
	}

}
