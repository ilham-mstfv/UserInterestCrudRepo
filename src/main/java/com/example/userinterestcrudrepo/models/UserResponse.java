package com.example.userinterestcrudrepo.models;

public class UserResponse {
    private UserData user;

    public UserResponse(long id) {
        this.user = new UserData(id);
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public static class UserData {
        private long id;

        public UserData(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}

