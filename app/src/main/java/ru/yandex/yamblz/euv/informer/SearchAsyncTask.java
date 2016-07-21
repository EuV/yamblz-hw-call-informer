package ru.yandex.yamblz.euv.informer;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import ru.yandex.yamblz.euv.informer.SearchAsyncTask.Response;

public class SearchAsyncTask extends AsyncTask<String, Void, Response> {
    private static final String TAG = SearchAsyncTask.class.getSimpleName();

    private final AlertWindow alertWindow;

    public SearchAsyncTask(AlertWindow alertWindow) {
        this.alertWindow = alertWindow;
    }


    @Override
    protected Response doInBackground(String... phoneNumbers) {
        Document doc;
        try {
            doc = Jsoup.connect("https://yandex.ru/search/?text=" + phoneNumbers[0]).get();
        } catch (IOException e) {
            Log.d(TAG, Log.getStackTraceString(e));
            return null;
        }

        Elements textPreviews = doc.getElementsByClass("extended-text__short");
        if (textPreviews.isEmpty()) {
            return null; // We have been 'banned' in Yandex (or they've changed HTML structure)
        }

        Elements links = doc.getElementsByClass("serp-item__title-link");
        if (links.isEmpty()) {
            return null;
        }

        String textPreview = textPreviews.get(0).text() + "...";
        String link = links.get(0).attr("href");

        return new Response(textPreview, link);
    }


    @Override
    protected void onPostExecute(Response response) {
        alertWindow.setPreview(response);
    }


    public static class Response {
        public final String textPreview;
        public final String link;

        public Response(String textPreview, String link) {
            this.textPreview = textPreview;
            this.link = link;
        }
    }
}
