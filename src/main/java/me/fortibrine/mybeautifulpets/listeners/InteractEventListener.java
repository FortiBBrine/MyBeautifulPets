package me.fortibrine.mybeautifulpets.listeners;

import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.pets.Pet;
import me.fortibrine.mybeautifulpets.utils.VariableManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        boolean isFoodItem = false;

        for (ItemStack item : plugin.getVariableManager().getFoodItems()) {
            if (itemInMainHand.isSimilar(item)) {
                isFoodItem = true;
                break;
            }
        }

        if (!isFoodItem) return;

        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);

        if (random.nextInt(100) < plugin.getConfig().getInt("chance")) {

            livingEntity.setAI(false);

            VariableManager variableManager = plugin.getVariableManager();

            for (Set<Pet> entities : variableManager.getPets().values())  {
                for (Pet pet : entities) {
                    if (pet.getEntity().equals(entity)) {
                        return;
                    }
                }
            }

            Pet pet = new Pet(UUID.randomUUID(), entity);
            if (variableManager.getPets().containsKey(player.getUniqueId())) {
                variableManager.getPets().get(player.getUniqueId()).add(pet);
            } else {

                Set<Pet> pets = new HashSet<>();
                pets.add(pet);

                variableManager.getPets().put(player.getUniqueId(), pets);
            }

        } else {
            player.damage(plugin.getConfig().getDouble("damage"));
        }

    }

}
