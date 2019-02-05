package com.lonchi.andrej.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FragmentNews_viaStream extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewNews = inflater.inflate(R.layout.fragment_news, container, false);

        //  Url of http request
        String newsURL = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=c2d632498330401285525bbeb4a649d2";
        new AsyncHttpTask().execute(newsURL);

        return viewNews;
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL mUrl;
            HttpURLConnection mUrlConnection = null;

            try{
                mUrl = new URL( urls[0] );
                mUrlConnection = (HttpURLConnection) mUrl.openConnection();
                String response = streamToString(mUrlConnection.getInputStream());
                parseResult(response);
                return  result;

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    String streamToString (InputStream stream) throws IOException{
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        String result = "";

        //  Build String
        while( (data = bufferReader.readLine()) != null ){
            result += data;
        }

        //  Close stream
        if( stream != null ){
            stream.close();
        }

        return result;
    }

    private void parseResult(String result){
        JSONObject response = null;

        try {
            response = new JSONObject(result);
            JSONArray articles = response.optJSONArray("articles");

            for(int i=0; i<articles.length(); i++){
                JSONObject article = articles.optJSONObject(i);
                String title = article.getString("title");
                Log.i("Titles", String.valueOf(i) + ". " + title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
