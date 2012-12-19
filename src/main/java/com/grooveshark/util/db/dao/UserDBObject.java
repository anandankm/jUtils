package com.grooveshark.util.db.dao;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBList;
import java.util.TreeSet;

@Entity
public class UserDBObject {

    @Id
    private int id;
    private TreeSet<Integer> followers;

    public UserDBObject() {
        this.id = 0;
        this.followers = new TreeSet<Integer>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setFollowers(TreeSet<Integer> followers) { 
        this.followers = followers;
    }

    public TreeSet<Integer> getFollowers() {
        return this.followers;
    }

    public UserDBObject(int id, TreeSet<Integer> followers) {
        this.id = id;
        this.followers = followers;
    }

}
