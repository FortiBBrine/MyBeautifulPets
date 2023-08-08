package me.fortibrine.mybeautifulpets.utils;

import lombok.Getter;
import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
public class VariableManager {

    private List<ItemStack> foodItems = new ArrayList<>();
    private Map<UUID, Set<Pet>> pets = new HashMap<>();

    public VariableManager(MyBeautifulPets plugin) {
        FileConfiguration config = plugin.getConfig();

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
