package me.fortibrine.mybeautifulpets.utils;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RunnableManager {

    private VariableManager variableManager;

    public RunnableManager(MyBeautifulPets plugin) {

        this.variableManager = plugin.getVariableManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> teleportPet(), 20L, 20L);
    }

    private void teleportPet() {
        Map<UUID, Set<Pet>> pets = variableManager.getPets();

        for (Map.Entry<UUID, Set<Pet>> entry : pets.entrySet()) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());

            if (!player.isOnline()) continue;

            Player onlinePlayer = (Player) player;

            for (Pet pet : entry.getValue()) {

                Entity entity = pet.getEntity();

                if (entity == null) {
                    continue;
                }

                if (entity.getLocation() == null) {
                    continue;
                }

                if (onlinePlayer.getLocation().distance(entity.getLocation()) >= 5) {
                    entity.teleport(onlinePlayer.getLocation());
                }
            }

        }
    }

}
