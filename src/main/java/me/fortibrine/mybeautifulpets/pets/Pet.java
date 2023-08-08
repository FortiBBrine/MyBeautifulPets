package me.fortibrine.mybeautifulpets.pets;

import lombok.Getter;
import org.bukkit.entity.Entity;

import java.util.UUID;

@Getter
public class Pet {

    private UUID uuid;
    private Entity entity;

    public Pet(UUID uuid, Entity entity) {
        this.uuid = uuid;
        this.entity = entity;
    }

}
