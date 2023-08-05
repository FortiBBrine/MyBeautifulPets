package me.fortibrine.mybeautifulpets.nms;

import com.google.gson.Gson;
import net.minecraft.server.v1_17_R1.EntityLiving;
import net.minecraft.server.v1_17_R1.NBTBase;
import net.minecraft.server.v1_17_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class NBTManager_v1_17_R1 implements NBTManager {

    private Gson gson = new Gson();
    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();

    public String getNBTTagsFromEntity(LivingEntity entity) {
        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();
        NBTTagCompound nbt = new NBTTagCompound();
        entityLiving.c(nbt);

        Map<String, NBTBase> tags = new HashMap<>();

        nbt.getKeys().forEach(key -> tags.put(key, nbt.get(key)));

        String result = gson.toJson(tags);

        String encoded = encoder.encodeToString(result.getBytes());
        return encoded;

    }

    public void setNBTTagsFromString(String base64String, LivingEntity entity) {
        String decoded = new String(decoder.decode(base64String));

        Map<String, NBTBase> tags = gson.fromJson(decoded, Map.class);

        EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();

        NBTTagCompound nbt = new NBTTagCompound();

        tags.forEach(nbt::set);

        entityLiving.a(nbt);
        
    }

}