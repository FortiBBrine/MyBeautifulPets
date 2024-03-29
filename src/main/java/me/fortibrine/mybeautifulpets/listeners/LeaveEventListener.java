package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.pets.Pet;
import me.fortibrine.mybeautifulpets.utils.VariableManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.UUID;

public class LeaveEventListener implements Listener {

    private MyBeautifulPets plugin;
    public LeaveEventListener(MyBeautifulPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();
        VariableManager variableManager = plugin.getVariableManager();
        Set<Pet> entities = variableManager.getPets().get(uuid);

        variableManager.getPets().remove(uuid);

        entities.forEach(pet -> {
            Entity entity = pet.getEntity();

            Location location = entity.getLocation();
            Chunk chunk = location.getChunk();

            chunk.load();

            entity.remove();
            plugin.getSqlManager().saveMob(player.getUniqueId().toString(), pet);

            chunk.unload();
        });
    }

}
