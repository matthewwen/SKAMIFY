package com.skamify.matthewwen.skamify.network;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.skamify.matthewwen.skamify.objects.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SongLyrics {

    private static final String TAG = SongLyrics.class.getSimpleName();

    public static ArrayList<Track> fetchTrackData(String mUrl){
        Log.v(TAG, "This is the URL: " + mUrl);
        URL url = createURL(mUrl);

        String jsonData;
        try {
            jsonData = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
            jsonData = "";
            Log.e(TAG, "Activity does not recognize this url: " + mUrl);
        }
        return extractTracks(jsonData);
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.v(TAG, "response code: " + urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(TAG, "Connection to url failed");
            }
        }catch (IOException e){
            Log.e(TAG, "IOException e is made");
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String urlString){
        URL url;
        try{
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        return url;
    }

    private static ArrayList<Track> extractTracks(String jsonResponse){
        ArrayList<Track> allTracks = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject resObject = jsonObject.getJSONObject("response");
            JSONArray jsonArray = resObject.getJSONArray("hits");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject temp = jsonArray.getJSONObject(i);
                if (temp.getString("type").equals("song")){
                    JSONObject result = temp.getJSONObject("result");
                    String title = result.getString("title");
                    String imageUrl = result.getString("song_art_image_thumbnail_url");
                    JSONObject primaryArtist = result.getJSONObject("primary_artist");
                    String artist = primaryArtist.getString("name");
                    Drawable drawable = loadImageFromWebOperations(imageUrl);
                    Track listing = new Track(drawable, title, artist);
                    allTracks.add(listing);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "all tracks");

        return allTracks;
    }

    public static Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            return null;
        }
    }

}
