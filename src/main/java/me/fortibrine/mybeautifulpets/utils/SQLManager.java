package me.fortibrine.mybeautifulpets.utils;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import me.fortibrine.mybeautifulpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SQLManager implements Closeable {

    private Connection connection;
    private MyBeautifulPets plugin;
    private Base64Manager base64Manager;

    public SQLManager(MyBeautifulPets plugin) {
        this.plugin = plugin;
        this.base64Manager = plugin.getBase64Manager();

        try {
            String url = "jdbc:sqlite:plugins/MyBeautifulPets/mobs.db";

            connection = DriverManager.getConnection(url);

            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS entities (" +
                    "uuid TEXT," +
                    "entityUUID TEXT UNIQUE," +
                    "entity TEXT)");

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void saveMob(String uuid, Pet pet) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO entities(uuid, entityUUID, entity) VALUES(?,?,?) ");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, pet.getUuid().toString());

            String json = NBTEditor.getNBTCompound(pet.getEntity()).toJson();
            json = this.base64Manager.encode(json);

            preparedStatement.setString(3, json);
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ignored) {

        }
    }

    public Set<Pet> getMobs(String uuid) {
        try {

            Set<Pet> entities = new HashSet<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT entityUUID,entity FROM entities WHERE uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                UUID entityUUID = UUID.fromString(resultSet.getString("entityUUID"));
                String jsonBase64 = resultSet.getString("entity");

                String json = this.base64Manager.decode(jsonBase64);

                World world = Bukkit.getWorlds().get(0);

                Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.UNKNOWN);

                NBTEditor.NBTCompound compound = NBTEditor.getNBTCompound(json);
                NBTEditor.set(entity, compound);

                entities.add(new Pet(entityUUID, entity));
            }

            resultSet.close();
            preparedStatement.close();

            return entities;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ignored) {

        }
    }
}
