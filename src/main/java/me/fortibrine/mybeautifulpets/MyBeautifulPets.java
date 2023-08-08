package me.fortibrine.mybeautifulpets;

import lombok.Getter;
import me.fortibrine.mybeautifulpets.listeners.InteractEventListener;
import me.fortibrine.mybeautifulpets.listeners.JoinEventListener;
import me.fortibrine.mybeautifulpets.listeners.LeaveEventListener;
import me.fortibrine.mybeautifulpets.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class MyBeautifulPets extends JavaPlugin {

    private VariableManager variableManager;
    private RunnableManager runnableManager;
    private SQLManager sqlManager;
    private Base64Manager base64Manager;

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        this.variableManager = new VariableManager(this);
        this.runnableManager = new RunnableManager(this);
        this.base64Manager = new Base64Manager();
        this.sqlManager = new SQLManager(this);

        Bukkit.getPluginManager().registerEvents(new InteractEventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinEventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LeaveEventListener(this), this);

    }


}
