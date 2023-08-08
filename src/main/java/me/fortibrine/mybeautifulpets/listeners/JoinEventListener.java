package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class JoinEventListener implements Listener {

    private MyBeautifulPets plugin;
    public JoinEventListener(MyBeautifulPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String uuid = player.getUniqueId().toString();

        Set<Pet> entities = plugin.getSqlManager().getMobs(uuid);

        plugin.getVariableManager().getPets().put(player.getUniqueId(), entities);
    }

}
