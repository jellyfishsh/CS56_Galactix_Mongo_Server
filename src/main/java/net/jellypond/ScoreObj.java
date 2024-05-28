package net.jellypond;

import org.bson.Document;

public class ScoreObj {

    private long score;
    private String username;
    private long numOfShots;


    public ScoreObj() {}
    public ScoreObj(String username, long score, long numOfShots){
        this.score = score;
        this.username = username;
        this.numOfShots = numOfShots;
    }

    //Setter
    public void setScore(long score) {
        this.score = score;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setNumOfShots(long numOfShots) {
        this.numOfShots = numOfShots;
    }

    //Getters
    public long getScore() {
        return score;
    }
    public String getUsername() {
        return username;
    }
    public long getNumOfShots() {
        return numOfShots;
    }

    //Converter
    public static ScoreObj fromDocument(Document doc){
        return new ScoreObj(
                doc.getString("username"),
                doc.getLong("score"),
                doc.getLong("numOfShots")
        );
    }

    public Document toDocument(){
        Document result = new Document();
        result.put("username", username);
        result.put("score", score);
        result.put("numOfShots", numOfShots);
        return result;
    }

}
