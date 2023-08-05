package me.fortibrine.mybeautifulpets;

import lombok.Getter;
import me.fortibrine.mybeautifulpets.listeners.InteractEventListener;
import me.fortibrine.mybeautifulpets.listeners.JoinEventListener;
import me.fortibrine.mybeautifulpets.listeners.LeaveEventListener;
import me.fortibrine.mybeautifulpets.utils.RunnableManager;
import me.fortibrine.mybeautifulpets.utils.SQLManager;
import me.fortibrine.mybeautifulpets.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MyBeautifulPets extends JavaPlugin {

    @Getter
    private VariableManager variableManager;

    @Getter
    private RunnableManager runnableManager;

    @Getter
    private SQLManager sqlManager;

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        this.variableManager = new VariableManager(this);
        this.runnableManager = new RunnableManager(this);
        this.sqlManager = new SQLManager();

        Bukkit.getPluginManager().registerEvents(new InteractEventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinEventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LeaveEventListener(this), this);

    }

    @Override
    public void onDisable() {

//
//        FileConfiguration config = this.getConfig();
//
//        for (Map.Entry<UUID, Set<LivingEntity>> entry : this.variableManager.getPets().entrySet()) {
//
//            List<String> entitiesID = new ArrayList<>();
//            entry.getValue().forEach(entity -> {
//
//                if (entity == null) {
//                    return;
//                }
//
//                entitiesID.add(entity.getUniqueId().toString());
//            });
//
//            config.set("pets." + entry.getKey(), entitiesID);
//
//        }
//
//        this.saveConfig();
    }
}
