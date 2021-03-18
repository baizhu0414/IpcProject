package com.example.beans;

import java.io.Serializable;

public class UserSerializable implements Serializable {
    private static final long serialVersionUID = 8711368828010083044L;

    private String mUserName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
