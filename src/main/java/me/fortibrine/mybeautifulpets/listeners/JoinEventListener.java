package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class JoinEventListener implements Listener {

    private MyBeautifulPets plugin;
    public JoinEventListener(MyBeautifulPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String uuid = player.getUniqueId().toString();

        List<EntityType> entities = plugin.getSqlManager().getMobs(uuid);

        Location location = player.getLocation();

        Set<LivingEntity> livingEntities = new HashSet<>();

        entities.forEach(entityType -> livingEntities.add((LivingEntity) location.getWorld().spawnEntity(location, entityType)));
        plugin.getVariableManager().getPets().put(player.getUniqueId(), livingEntities);
    }

}
