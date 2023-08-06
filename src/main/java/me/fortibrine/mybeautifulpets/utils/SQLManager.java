package me.fortibrine.mybeautifulpets.utils;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import me.fortibrine.mybeautifulpets.MyBeautifulPets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.io.Closeable;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

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
                    "entity TEXT)");

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void saveMob(String uuid, Entity entity) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO entities(uuid, entity) VALUES(?,?) ");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, this.base64Manager.encode(NBTEditor.getNBTCompound(entity).toJson()));
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ignored) {

        }
    }

    public Set<Entity> getMobs(String uuid) {
        try {

            Set<Entity> entities = new HashSet<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT entity FROM entities WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String jsonBase64 = resultSet.getString(1);

                String json = this.base64Manager.decode(jsonBase64);

                World world = Bukkit.getWorld("world");

                Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.COW);
                NBTEditor.NBTCompound compound = NBTEditor.getNBTCompound(json);
                NBTEditor.set(entity, compound);

                entities.add(entity);
            }

            resultSet.close();
            preparedStatement.close();

            return entities;

        } catch (SQLException exception) {
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
