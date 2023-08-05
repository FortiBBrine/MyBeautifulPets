package me.fortibrine.mybeautifulpets.nms;

import com.google.gson.Gson;
import org.bukkit.entity.LivingEntity;
import java.util.Base64;

public interface NBTManager {

    Gson gson = new Gson();
    Base64.Encoder encoder = Base64.getEncoder();
    Base64.Decoder decoder = Base64.getDecoder();

    String getNBTTagsFromEntity(LivingEntity entity);
    void setNBTTagsFromString(String base64String, LivingEntity entity);
}
