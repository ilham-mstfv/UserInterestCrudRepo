package com.example.userinterestcrudrepo.models;

public class InterestResponse {
    private InterestResponse.InterestData interest;

    public InterestResponse(int id) {
        this.interest = new InterestResponse.InterestData(id);
    }

    public InterestResponse.InterestData getInterest() {
        return interest;
    }

    public void setInterest(InterestResponse.InterestData interest) {
        this.interest = interest;
    }

    public static class InterestData {
        private int id;

        public InterestData(int id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
