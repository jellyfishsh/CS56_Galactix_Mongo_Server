package net.jellypond;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ServerAPI {
    /*
     * the scores are in this format (as of this version)
     * {
     *   _id:
     *   username:
     *   score:
     *   numOfShots:
     * }
     *
     */

    //Read
    @GetMapping("/score")
    public ScoreObj score(@RequestParam(value = "username") String username){

        return ScoreObj.fromDocument(DatabaseMain.getInstance().readEntry(username));
    }
    @GetMapping("/scoreboard")
    public List<ScoreObj> scoreboard(@RequestParam(value = "entries", defaultValue = "10")int entries){
        List<Document> unformatted = DatabaseMain.getInstance().readEntries("score", entries, true);
        List<ScoreObj> formatted = new ArrayList<>();
        for (Document doc : unformatted){
            formatted.add(ScoreObj.fromDocument(doc));
        }
        return formatted;
    }

    //Update/Create
    @PostMapping("/upload")
    public String upload(@NotNull @RequestBody ScoreObj score){

        DatabaseMain.getInstance().createEntry(score.toDocument());
        return "Successfully uploaded";
    }

}
