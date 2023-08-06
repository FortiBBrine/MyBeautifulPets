package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;

public class LeaveEventListener implements Listener {

    private MyBeautifulPets plugin;
    public LeaveEventListener(MyBeautifulPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Set<Entity> entities = plugin.getVariableManager().getPets().get(event.getPlayer().getUniqueId());

        entities.forEach(entity -> {
            plugin.getSqlManager().saveMob(event.getPlayer().getUniqueId().toString(), entity);
            entity.remove();
        });

    }

}
