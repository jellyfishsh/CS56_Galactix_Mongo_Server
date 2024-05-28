package net.jellypond;


import java.net.HttpURLConnection;
import java.net.URL;

public class ClientAPI {
    private String apiaddress;
    private static ClientAPI client_singleton;

    private ClientAPI(){
        this.apiaddress = "https://galatixapi.jellypond.net";
    }
    public static synchronized ClientAPI getInstance(){
        if (client_singleton == null){
            client_singleton = new ClientAPI();
        }
        return client_singleton;
    }

    public void sendScoreToServer(ScoreObj score){
        try{
            URL api = new URL(apiaddress);
            HttpURLConnection urlconn = (HttpURLConnection) api.openConnection();
            urlconn.addRequestProperty("Content-Type", "application/" + "json");




        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
