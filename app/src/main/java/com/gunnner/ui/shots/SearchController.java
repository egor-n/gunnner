package com.gunnner.ui.shots;

import android.os.AsyncTask;

import com.gunnner.data.models.Shot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Egor N.
 */
public class SearchController {
    private static SearchController instance;

    public static SearchController getInstance() {
        if (instance == null) {
            instance = new SearchController();
        }
        return instance;
    }

    public void search(String query, ShotsController.OnShotsLoadedListener callback) {
        new SearchTask(query, 1, callback).execute();
    }

    class SearchTask extends AsyncTask<Void, Void, ArrayList<Shot>> {
        String query;
        int page;
        ShotsController.OnShotsLoadedListener callback;

        SearchTask(String query, int page, ShotsController.OnShotsLoadedListener callback) {
            this.query = query;
            this.page = page;
            this.callback = callback;
        }

        @Override
        protected ArrayList<Shot> doInBackground(Void... voids) {
            String url = ("http://dribbble.com/search?q=" + query + "&page=" + page).replaceAll("\\s", "%20");
            ArrayList<Shot> shots = new ArrayList<Shot>();
            try {
                Elements elements = Jsoup.connect(url).get().select(".dribbble");
                for (Element element : elements) {
                    Element shotImgElement = element.select(".dribbble-shot").first();
                    String imageUrl = shotImgElement.select(".dribbble-img a div div").attr("data-src");
                    String shotUrl = shotImgElement.select(".dribbble-img a").attr("href");
                    String title = shotImgElement.select(".dribbble-over string").html();
                    int shotId = Integer.parseInt(shotUrl.substring(7, shotUrl.indexOf("-")));

                    Element shotToolsElement = shotImgElement.select("[class=tools group]").first();
                    int likes = Integer.parseInt(shotToolsElement.select(".fav a").html().replaceAll(",", ""));
                    int comments = Integer.parseInt(shotToolsElement.select(".cmnt a").html().replaceAll(",", ""));
                    int views = Integer.parseInt(shotToolsElement.select(".views").html().replaceAll(",", ""));

                    Element shotExtrasElement = element.select(".extras").first();
                    boolean hasRebounds = Integer.parseInt(shotExtrasElement.select("a span").html().substring(0, 1)) > 0;
                    Shot shot = new Shot(shotId, title, likes, views, comments, imageUrl, hasRebounds);
                    shots.add(shot);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return shots;
        }

        @Override
        protected void onPostExecute(ArrayList<Shot> shots) {
            if (callback != null) {
                if (shots == null || shots.isEmpty()) {
                    callback.onShotsError();
                } else {
                    callback.onShotsLoaded(shots);
                }
            }
        }
    }
}
