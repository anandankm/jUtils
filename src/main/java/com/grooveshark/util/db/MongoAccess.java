package com.grooveshark.util.db;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

import com.google.code.morphia.Morphia;

public class MongoAccess
{
    public static final Logger log = Logger.getLogger(MongoAccess.class);

    public static Map<String, Mongo> clients = new HashMap<String, Mongo>();

    public static Map<String, DBCollection> colls = new HashMap<String, DBCollection>();

    protected String host;
    protected int port;
    protected String dbname;
    protected String collectionName;
    protected DB db;
    protected DBCollection collection;
    protected Mongo mongoClient;
    protected Morphia morphia;


    public MongoAccess() throws MongoException {
        this.host = DBProperties.DEFAULT_MONGO_HOST;
        this.port = DBProperties.DEFAULT_MONGO_PORT;
        this.mongoClient = MongoAccess.getInstance(this.host, this.port);
        this.dbname = DBProperties.DEFAULT_MONGO_DB;
        this.db = this.mongoClient.getDB(this.dbname);
        this.morphia = new Morphia();
    }

    public MongoAccess(String host, int port, String dbname) throws MongoException {
        this.host = host;
        this.port = port;
        this.mongoClient = MongoAccess.getInstance(this.host, this.port);
        this.dbname = dbname;
        this.db = this.mongoClient.getDB(this.dbname);
        this.morphia = new Morphia();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return this.host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void setDB(String dbname) {
        this.dbname = dbname;
        this.db = this.mongoClient.getDB(this.dbname);
    }

    public DB getDB() {
        return this.db;
    }

    public void setCollection(String collection) throws MongoAccessException {
        this.collectionName = collection;
        this.collection = this.getCollection(this.collectionName);
    }

    public DBCollection getCollection() {
        return this.collection;
    }

    public DBCollection getCollection(String collection) throws MongoAccessException {
        if (this.db == null) {
            throw new MongoAccessException("DB is not initialized. Check Mongo db connection and db name: " + this.dbname);
        }
        DBCollection coll = null;
        String key = this.host + this.port + collection;
        if (MongoAccess.colls.containsKey(key)) {
            coll = MongoAccess.colls.get(key);
        } else {
            coll = this.db.getCollection(collection);
            MongoAccess.colls.put(key, coll);
        }
        return coll;
    }


    public static Mongo getInstance(String host, int port) throws MongoException {
        String key = host + port;
        Mongo client = null;
        if (MongoAccess.clients.containsKey(key)) {
            client = MongoAccess.clients.get(key);
        } else {
            try {
                client = new Mongo(host, port);
                MongoAccess.clients.put(key, client);
            } catch (UnknownHostException e) {
                throw new MongoException("Host unknown: " + host, e);
            }
        }
        return client;
    }

    public <T> T getMorphedDBObject(String id, Class<T> mClass) throws MongoAccessException {
        return this.getMorphedDBObject(new BasicDBObject("_id", id), mClass);
    }

    public <T> T getMorphedDBObject(int id, Class<T> mClass) throws MongoAccessException {
        return this.getMorphedDBObject(new BasicDBObject("_id", id), mClass);
    }


    public <T> T getMorphedDBObject(DBObject query, Class<T> mClass) throws MongoAccessException {
        this.morphia.map(mClass);
        DBObject dbObject = this.getDBObject(query);
        return this.morphia.fromDBObject(mClass, dbObject);
    }

    public DBObject getDBObject(String collection, String id) throws MongoAccessException {
        BasicDBObject query = new BasicDBObject("_id", id);
        return this.getDBObject(collection, query);
    }

    public DBObject getDBObject(String collection, int id) throws MongoAccessException {
        BasicDBObject query = new BasicDBObject("_id", id);
        return this.getDBObject(collection, query);
    }

    public DBObject getDBObject(String collection, DBObject query) throws MongoAccessException {
        return this.getDBObject(this.getCollection(collection), query);
    }

    public DBObject getDBObject(DBCollection coll, DBObject query) throws MongoAccessException {
        DBObject dbObject = coll.findOne(query);
        if (dbObject == null) {
            throw new MongoAccessException("No DBObject found for query: " + query);
        }
        return dbObject;
    }

    public DBObject getDBObject(DBObject query) throws MongoAccessException {
        if (this.collection == null) {
            throw new MongoAccessException("No default collection is set for mongo access. Quitting!");
        }
        return this.getDBObject(this.collection, query);
    }

    public <T> void updateMorphedDBObject(String id, T o, Class<T> mClass) throws MongoAccessException {
        this.updateMorphedDBObject(new BasicDBObject("_id", id), o, mClass);
    }

    public <T> void updateMorphedDBObject(int id, T o, Class<T> mClass) throws MongoAccessException {
        this.updateMorphedDBObject(new BasicDBObject("_id", id), o, mClass);
    }

    public <T> void updateMorphedDBObject(DBObject query, T o, Class<T> mClass) throws MongoAccessException {
        this.morphia.map(mClass);
        this.updateDBObject(query, this.morphia.toDBObject(o));
    }

    public void updateDBObject(String collection, int id, DBObject dbObject) throws MongoAccessException {
        this.updateDBObject(collection, new BasicDBObject("_id", id), dbObject);
    }

    public void updateDBObject(String collection, String id, DBObject dbObject) throws MongoAccessException {
        this.updateDBObject(collection, new BasicDBObject("_id", id), dbObject);
    }

    public void updateDBObject(String collection, DBObject query, DBObject dbObject) throws MongoAccessException {
        this.updateDBObject(this.getCollection(collection), query, dbObject);
    }

    public void updateDBObject(int id, DBObject dbObject) throws MongoAccessException {
        this.updateDBObject(new BasicDBObject("_id", id), dbObject);
    }

    public void updateDBObject(String id, DBObject dbObject) throws MongoAccessException {
        this.updateDBObject(new BasicDBObject("_id", id), dbObject);
    }

    public void updateDBObject(DBObject query, DBObject dbObject) throws MongoAccessException {
        if (this.collection == null) {
            throw new MongoAccessException("No default collection is set for mongo access. Quitting!");
        }
        this.updateDBObject(this.collection, query, dbObject);
    }

    public void updateDBObject(DBCollection coll, DBObject query, DBObject dbObject) throws MongoAccessException {
        coll.update(query, dbObject, true, false);
    }
}
