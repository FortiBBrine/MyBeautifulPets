package me.fortibrine.mybeautifulpets.utils;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RunnableManager {

    private VariableManager variableManager;

    public RunnableManager(MyBeautifulPets plugin) {

        this.variableManager = plugin.getVariableManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> teleportPet(), 20L, 20L);
    }

    private void teleportPet() {
        Map<UUID, List<LivingEntity>> pets = variableManager.getPets();

        for (Map.Entry<UUID, List<LivingEntity>> entry : pets.entrySet()) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());

            if (!player.isOnline()) continue;

            Player onlinePlayer = Bukkit.getPlayer(entry.getKey());

            for (LivingEntity entity : entry.getValue()) {
                if (onlinePlayer.getLocation().distance(entity.getLocation()) >= 5) {
                    entity.teleport(onlinePlayer.getLocation());
                }
            }

        }
    }

}
