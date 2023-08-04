package me.fortibrine.mybeautifulpets.utils;

import lombok.Getter;
import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
public class VariableManager {

    private List<ItemStack> foodItems = new ArrayList<>();
    private Map<UUID, Set<LivingEntity>> pets = new HashMap<>();

    public VariableManager(MyBeautifulPets plugin) {
        FileConfiguration config = plugin.getConfig();

        System.out.println(config.getConfigurationSection("pets").getKeys(false).size());

        for (String key : config.getConfigurationSection("pets").getKeys(false)) {
            List<String> entitiesID = config.getStringList("pets." + key);
            Set<LivingEntity> livingEntities = new HashSet<>();

            entitiesID.forEach(entity -> livingEntities.add((LivingEntity) Bukkit.getEntity(UUID.fromString(entity))));

            pets.put(UUID.fromString(key), livingEntities);

        }

        for (String key : config.getConfigurationSection("food").getKeys(false)) {
            ConfigurationSection configurationSection = config.getConfigurationSection("food." + key);

            ItemStack item = new ItemStack(Material.matchMaterial(configurationSection.getString("type")));

            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(configurationSection.getString("name"));
            meta.setLore(configurationSection.getStringList("lore"));

            item.setItemMeta(meta);

            List<String> craftIngredients = configurationSection.getStringList("craft");
            List<Material> ingredients = new ArrayList<>();

            craftIngredients.forEach(ingredient -> ingredients.add(Material.matchMaterial(ingredient)));

            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);

            recipe.shape("ABC", "DEF", "GHI");

            recipe.setIngredient('A', ingredients.get(0));
            recipe.setIngredient('B', ingredients.get(1));
            recipe.setIngredient('C', ingredients.get(2));
            recipe.setIngredient('D', ingredients.get(3));
            recipe.setIngredient('E', ingredients.get(4));
            recipe.setIngredient('F', ingredients.get(5));
            recipe.setIngredient('G', ingredients.get(6));
            recipe.setIngredient('H', ingredients.get(7));
            recipe.setIngredient('I', ingredients.get(8));

            Bukkit.addRecipe(recipe);

            this.foodItems.add(item);
        }
    }

}
