package me.jumper251.replay.database;

import com.mengcraft.simpleorm.MongoWrapper;
import heypixel.com.mongodb.BasicDBObject;
import heypixel.com.mongodb.gridfs.GridFS;
import heypixel.com.mongodb.gridfs.GridFSDBFile;
import heypixel.com.mongodb.gridfs.GridFSInputFile;
import me.jumper251.replay.ReplaySystem;
import me.jumper251.replay.database.utils.DatabaseService;
import me.jumper251.replay.replaysystem.data.ReplayInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBService extends DatabaseService {
    private final GridFS gridFS;
    private final MongoWrapper.MongoDatabaseWrapper databaseWrapper;
    public MongoDBService(MongoWrapper wrapper, String database, String bucket) {
        super();
        this.gridFS = wrapper.openFileSystem(database, bucket);
        this.databaseWrapper = wrapper.open(database, bucket);
    }

    @Override
    public void createReplayTable() {
        ReplaySystem.getInstance().getLogger().info("MongoDB does not need create replay table");
    }

    @Override
    public void addReplay(String id, String creator, int duration, Long time, byte[] data) {
        BasicDBObject replayObj = new BasicDBObject("_id", id)
                .append("creator", creator)
                .append("duration", duration)
                .append("time", time);
        databaseWrapper.open(dbCollection -> dbCollection.save(replayObj));
        GridFSInputFile file = gridFS.createFile(data);
        file.setFilename(id);
        file.save();
    }

    @Override
    public byte[] getReplayData(String id) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GridFSDBFile replayFile = gridFS.findOne(id);
        try {
            replayFile.writeTo(byteArrayOutputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void deleteReplay(String id) {
        databaseWrapper.remove(id);
        gridFS.remove(id);
    }

    @Override
    public boolean exists(String id) {
        GridFSDBFile file = gridFS.findOne(id);
        return file != null;
    }

    @Override
    public List<ReplayInfo> getReplays() {
        //TODO: 支持Mongo列出, 简化列表 直接列出所有的不明智
        return new ArrayList<>();
    }
}
