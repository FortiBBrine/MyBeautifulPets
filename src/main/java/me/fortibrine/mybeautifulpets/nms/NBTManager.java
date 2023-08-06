package me.fortibrine.mybeautifulpets.nms;

import com.google.gson.Gson;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NBTManager {

    private Gson gson = new Gson();
    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();
    private String sversion;
    private Class<?> entityLivingClass;
    private Class<?> nbtTagCompoundClass;
    private Class<?> nbtBaseClass;

    public NBTManager(String sversion) throws ClassNotFoundException {
        this.sversion = sversion;

        entityLivingClass = Class.forName("net.minecraft.server" + sversion + "EntityLiving");
        nbtTagCompoundClass = Class.forName("net.minecraft.server" + sversion + "NBTTagCompound");
        nbtBaseClass = Class.forName("net.minecraft.server." + sversion + ".NBTBase");
    }

    public String getNBTTagsFromEntity(LivingEntity entity) {
        try {
            Object craftLivingEntity = Class.forName("org.bukkit.craftbukkit." + sversion + ".entity.CraftLivingEntity").cast(entity);

            Object entityLiving = Class.forName("org.bukkit.craftbukkit." + sversion + ".entity.CraftLivingEntity").getMethod("getHandle").invoke(craftLivingEntity);

            Object nbt = nbtTagCompoundClass.getConstructor().newInstance();
            entityLivingClass.getMethod("c", nbtTagCompoundClass).invoke(entityLiving, nbt);

            Map<String, Object> tags = new HashMap<>();

            ((Set<String>) nbtTagCompoundClass.getMethod("getKeys").invoke(nbt)).forEach(key -> {
                try {
                    tags.put(key, nbtTagCompoundClass.getMethod("get", String.class).invoke(key));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });

            String result = gson.toJson(tags);

            String encoded = encoder.encodeToString(result.getBytes());
            return encoded;
        } catch (Exception exception) {
            return "";
        }
    }

    public void setNBTTagsFromString(String base64String, LivingEntity entity) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String decoded = new String(decoder.decode(base64String));

        Map<String, Object> tags = gson.fromJson(decoded, Map.class);

        Object craftLivingEntity = Class.forName("org.bukkit.craftbukkit." + sversion + ".entity.CraftLivingEntity").cast(entity);

        Object entityLiving = Class.forName("org.bukkit.craftbukkit." + sversion + ".entity.CraftLivingEntity").getMethod("getHandle").invoke(craftLivingEntity);

        Object nbt = nbtTagCompoundClass.getConstructor().newInstance();

        tags.forEach((String key, Object tag) -> {
            try {
                nbtTagCompoundClass.getMethod("set", String.class, nbtBaseClass).invoke(key, tag);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        entityLivingClass.getMethod("a", nbtBaseClass).invoke(nbt);
        
    }

}