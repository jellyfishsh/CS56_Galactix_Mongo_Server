package net.jellypond.Server;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import org.bson.conversions.Bson;
import static com.mongodb.client.model.Sorts.*;

import java.util.ArrayList;
import java.util.List;


public class DatabaseMain {
    private static DatabaseMain databaseinstance;

    String uri;

    private DatabaseMain(){
        this.uri = "mongodb+srv://galactix_project:2ObPIGOo5dyaB9RA@schoolcluster.rtdmbpt.mongodb.net/?retryWrites=true&w=majority&appName=SchoolCluster";

    }
    public static synchronized DatabaseMain getInstance(){
        if (databaseinstance == null){
            databaseinstance = new DatabaseMain();
        }
        return databaseinstance;
    }

    //CRUD here in DatabaseMain
    //Create - POST
    public void createNewEntry(Document doc){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here

            documents.insertOne(doc);

        }
    }
    public void createEntry(Document doc){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            if(checkEntry(doc)){
                updateEntry(doc);
            }
            createNewEntry(doc);

        }

    }

    //Read - GET
    public Document readEntry(String username){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            Bson filter = Filters.eq("username", username);

            return documents.find(filter).first();

        }
    }
    public List<Document> readEntries(String field, int top){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            List<Document> results = new ArrayList<>();
            Bson sortDirection = ascending(field);
            FindIterable<Document> iterable = documents.find().sort(sortDirection).limit(top);
            iterable.into(results);
            return results;

        }

    }
    public List<Document> readEntries(String field, int top, boolean descending){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            List<Document> results = new ArrayList<>();
            Bson sortDirection = ascending(field);
            if(descending){
                sortDirection = descending(field);
            }
            FindIterable<Document> iterable = documents.find().sort(sortDirection).limit(top);
            iterable.into(results);
            return results;


        }

    }
    //Update - PUT
    public void updateEntry(Document doc){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            Bson filter = Filters.eq("username", doc.get("username"));
            Document entry = documents.find(filter).sort(ascending("score")).first();

            assert entry != null;
            documents.replaceOne(entry, doc);

        }

    }

    //Delete - DELETE
    public void deleteEntry(Document doc){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            Bson filter = Filters.eq("username", doc.get("username"));
            Document entry = documents.find(filter).sort(ascending("score")).first();

            assert entry != null;
            documents.deleteOne(filter);

        }

    }
    //Check - GET
    public boolean checkEntry(Document doc){
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("galactix");
            MongoCollection<Document> documents = database.getCollection("scoreboard");
            //Implement function here
            Document check = readEntry(doc.getString("username"));
            if (check == null){
                return false;
            }
            return true;

        }

    }
}
