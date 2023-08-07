package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.utils.VariableManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Set;

public class EntityDropEventListener implements Listener {

    private VariableManager variableManager;
    public EntityDropEventListener(MyBeautifulPets plugin) {
        this.variableManager = plugin.getVariableManager();
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity;

        for (Set<Entity> entities : variableManager.getPets().values()) {
            if (entities.contains(entity)) {
                entities.remove(entity);
                event.getDrops().clear();
                break;
            }
        }

    }

}
