package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.utils.VariableManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InteractEventListener implements Listener {

    private MyBeautifulPets plugin;
    public InteractEventListener(MyBeautifulPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (entity.getType() == EntityType.PLAYER) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;

        Random random = new Random();

        Player player = event.getPlayer();

        if (!plugin.getVariableManager().getFoodItems().contains(player.getInventory().getItemInMainHand())) return;

        if (random.nextInt(100) < plugin.getConfig().getInt("chance")) {
            livingEntity.setAI(false);

            VariableManager variableManager = plugin.getVariableManager();

            if (variableManager.getPets().containsKey(player.getUniqueId())) {
                variableManager.getPets().get(player).add(livingEntity);
            } else {

                List<LivingEntity> pets = new ArrayList<>();
                pets.add(livingEntity);

                variableManager.getPets().put(player.getUniqueId(), pets);
            }
        } else {
            player.damage(plugin.getConfig().getDouble("damage"));
        }

    }

}
