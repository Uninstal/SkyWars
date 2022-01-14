package org.uninstal.skywars;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Files {

  private JavaPlugin plugin;
	
	private HashMap<String, File> files;
	private HashMap<String, YamlConfiguration> yamls;

	public Files(JavaPlugin plugin) {
		this.plugin = plugin;
		this.files = new HashMap<>();
		this.yamls = new HashMap<>();
	}
	
	public YamlConfiguration registerNewFile(String name) {
		
		if(files.containsKey(name)) return yamls.get(name);
    File file = new File(plugin.getDataFolder() + File.separator + name + ".yml");
		
    if(!file.exists()) {
      plugin.saveResource(name + ".yml", false);
    }
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

		yamls.put(name, yaml);
		files.put(name, file);
		return yaml;
	}
	
	public File getFile(String name) {
		return files.get(name);
	}
	
	public YamlConfiguration getYaml(String name) {
		return yamls.get(name);
	}
	
	public void save(String name, YamlConfiguration yaml) {
		
		try {
			yaml.save(getFile(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reload() {
		
		for(String fileName : files.keySet()) {
			
			File file = new File(plugin.getDataFolder() + File.separator + fileName + ".yml");
			
			if(!file.exists()) {
	        	plugin.saveResource(fileName + ".yml", false);
	        }
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

			yamls.put(fileName, yaml);
			files.put(fileName, file);
		}
	}
}
