package com.lonchi.andrej.newsapp;

import android.app.VoiceInteractor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class FragmentNews extends Fragment {

    private RequestQueue mRequestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewNews = inflater.inflate(R.layout.fragment_news, container, false);

        //  Url of http request
        String newsURL = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=c2d632498330401285525bbeb4a649d2";

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON(newsURL);

        return viewNews;
    }

    private void parseJSON(String newsURL){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray articles = response.getJSONArray("articles");

                    for(int i=0; i<articles.length(); i++){
                        JSONObject article = articles.getJSONObject(i);

                        String author = article.getString("author");
                        String title = article.getString("title");
                        String description = article.getString("description");
                        String publishedAt = article.getString("publishedAt");
                        String url = article.getString("url");
                        String urlToImage = article.getString("urlToImage");

                        Log.i("O_AUTHOR:", author);
                        Log.i("O_TITLE:", title);
                        Log.i("O_DESCRIPTION:", description);
                        Log.i("O_PUBLISHED AT:", publishedAt);
                        Log.i("O_URL:", url);
                        Log.i("O_IMAGE:", urlToImage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

}
