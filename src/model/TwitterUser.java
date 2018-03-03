package model;

import java.io.Serializable;

public class TwitterUser implements Serializable {
    private long id;

    public TwitterUser(){}

    public TwitterUser(long l) {
		this.id = l;
    }

    public long getId() {
        return id;
    }
}