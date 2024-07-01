package me.jumper251.replay.database;

import com.mengcraft.simpleorm.MongoWrapper;
import com.mengcraft.simpleorm.ORM;
import me.jumper251.replay.database.utils.Database;
import me.jumper251.replay.database.utils.DatabaseService;

public class MongoDBDatabase extends Database {
    private MongoDBService service;
    private MongoWrapper wrapper;
    private final String database, bucket;
    public MongoDBDatabase(String database, String bucket) {
        super();
        this.database = database;
        this.bucket = bucket;
        this.connect();
    }

    @Override
    public void connect() {
        wrapper = ORM.globalMongoWrapper();
        service = new MongoDBService(wrapper, database, bucket);
    }

    @Override
    public void disconnect() {
        wrapper.close();
    }

    @Override
    public DatabaseService getService() {
        return this.service;
    }

    @Override
    public String getDataSourceName() {
        return null;
    }
}
