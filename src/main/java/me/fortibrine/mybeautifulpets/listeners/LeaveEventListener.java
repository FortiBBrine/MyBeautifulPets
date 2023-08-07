package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.utils.VariableManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
        UUID uuid = event.getPlayer().getUniqueId();
        VariableManager variableManager = plugin.getVariableManager();
        Set<Entity> entities = variableManager.getPets().get(uuid);

        entities.forEach(entity -> {
            ((LivingEntity) entity).setHealth(0);
        });
    }

}
