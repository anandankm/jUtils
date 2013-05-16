package com.grooveshark.util.db;

import com.grooveshark.util.db.dao.UserDBObject;

import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import com.google.code.morphia.Morphia;
import com.mongodb.DBObject;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;


public class MongoAccessTest
{
    public MongoAccess mongoAccess = null;
    public Morphia morphia = null;
    public TreeSet<Integer> testFollowers = new TreeSet<Integer>();

    //Before
    public void setup() {
        try {
            this.mongoAccess = new MongoAccess();
            this.morphia = new Morphia();
            this.morphia.map(UserDBObject.class);
            this.testFollowers = new TreeSet<Integer>();
            testFollowers.add(2);
            testFollowers.add(3);
            testFollowers.add(4);
            testFollowers.add(5);
            testFollowers.add(6);
            testFollowers.add(9);
            testFollowers.add(10);
            testFollowers.add(11);
            testFollowers.add(15);
            testFollowers.add(16);
            testFollowers.add(17);
        } catch (Exception e) {
            fail("Failed to setup mongo DB client " + e.getMessage());
        }

    }

    //Test
    public void saveObjectTest() {
        int id = 1;
        UserDBObject uo = new UserDBObject(id, this.testFollowers);
        try {
            this.mongoAccess.setCollection("knows");
            this.mongoAccess.updateMorphedDBObject(id, uo, UserDBObject.class);
        } catch (Exception e) {
            fail("Failed to save object" + e.getMessage());
        }
    }

    //Test
    public void getObjectTest() {
        int id = 1;
        try {
            this.mongoAccess.setCollection("knows");
            UserDBObject uo = this.mongoAccess.getMorphedDBObject(id, UserDBObject.class);
            TreeSet<Integer> f = uo.getFollowers();
            assertTrue(f.equals(this.testFollowers));
            System.out.println(f);
        } catch (Exception e) {
            fail("Failed to get db object" + e.getMessage());
        }
    }
}
