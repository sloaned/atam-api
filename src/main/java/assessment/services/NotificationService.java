package assessment.services;

import assessment.utilities.FirebaseConstants;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by dsloane on 6/15/2016.
 */
public class NotificationService {

    public void notifyFirebase(String gson, String message, String recipient) {

        JSONObject object = null;
        try {
            object = new JSONObject(gson);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject json = new JSONObject();
        json.put("to", recipient);
        json.put("message", message);
        json.put("data", object);

        System.out.println("notifying firebase");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(FirebaseConstants.FIREBASE_URL);
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("Authorization", FirebaseConstants.FIREBASE_AUTHORIZATION);
            request.setEntity(params);
            httpClient.execute(request);

            System.out.println("request posted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
