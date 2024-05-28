package net.jellypond.Server;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

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

    public static ScoreObj fromJson(String json){
        BsonDocument doc = BsonDocument.parse(json);
        ScoreObj score = new ScoreObj();
        score.setUsername(doc.getString("username").getValue());
        score.setScore(doc.getInt32("score").getValue());
        score.setNumOfShots(doc.getInt32("numOfShots").getValue());
        return score;
    }
    public static List<ScoreObj> fromJsonArray(String jsonArray){
        BsonArray bsonResult = BsonArray.parse(jsonArray);
        Object[] test = bsonResult.toArray();
        List<ScoreObj> result = new ArrayList<>();
        for(Object score : test){
            result.add(fromJson(score.toString()));
        }

        return result;
    }

    @Override
    public String toString(){
        String result = String.format(
                "Score of user %s: %s with %s shots fired.", username, score, numOfShots
        );
        return result;
    }



}
