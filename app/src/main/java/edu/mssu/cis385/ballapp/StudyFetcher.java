package edu.mssu.cis385.ballapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.mssu.cis385.ballapp.model.Player;


public class StudyFetcher {

    // (Component #1 in the slides)
    // "manages worker threads that make HTTP requests and parse HTTP responses"
    private final RequestQueue mRequestQueue;

    public StudyFetcher(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public interface OnStudyDataReceivedListener {
        void onPlayersReceived(List<Player> playerList);
        void onErrorResponse(VolleyError error);
    }

    private final String WEBAPI_BASE_URL = "https://www.balldontlie.io/api/v1/players.php";
    private final String TAG = "PlayerFetcher";

    public void fetchPlayers(final OnStudyDataReceivedListener listener, String search) {

        // Our full URL is https://wp.zybooks.com/study-helper.php?type=players
        // Uri.parse(WEBAPI_BASE_URL) is https://wp.zybooks.com/study-helper.php
        // appendQueryParameter("type", "players" adds the type=players parameter
        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                .appendQueryParameter("search", search).build().toString();

        // Request all players (Component #2 in the slides)
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                // Component #3 in the slides
                response -> listener.onPlayersReceived(jsonToPlayers(response)),
                // Component #5 in the slides
                error -> listener.onErrorResponse(error));

        mRequestQueue.add(request);
    }

    private List<Player> jsonToPlayers(JSONObject json) {

        // Create a list of players
        List<Player> playerList = new ArrayList<>();

        try {
            // Component #4 in the slides
            JSONArray playerArray = json.getJSONArray("data");

            for (int i = 0; i < playerArray.length(); i++) {
                JSONObject playerObj = playerArray.getJSONObject(i);

                Player player = new Player(playerObj.getString("first_name")+" "+playerObj.getString("last_name"));
                //player.setUpdateTime(playerObj.getLong("updatetime"));
                playerList.add(player);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Field missing in the JSON data: " + e.getMessage());
        }

        return playerList;
    }
}