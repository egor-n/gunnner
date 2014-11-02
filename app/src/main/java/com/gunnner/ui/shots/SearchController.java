package com.gunnner.ui.shots;

import android.os.AsyncTask;

import com.gunnner.data.models.Shot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Egor N.
 */
public class SearchController {
    private static SearchController instance;

    private HashMap<String, ArrayList<Shot>> shots = new HashMap<String, ArrayList<Shot>>();
    private HashMap<String, Integer> pages = new HashMap<String, Integer>();

    public static SearchController getInstance() {
        if (instance == null) {
            instance = new SearchController();
        }
        return instance;
    }

    public void init(String query, ShotsController.OnShotsLoadedListener callback) {
        if (shots.containsKey(query)) {
            if (callback != null) {
                callback.onShotsLoaded(shots.get(query));
            }
        } else {
            search(query, callback);
        }
    }

    public void search(String query, ShotsController.OnShotsLoadedListener callback) {
        if (!pages.containsKey(query)) {
            pages.put(query, 1);
        }

        new SearchTask(query, pages.get(query), callback).execute();
    }

    public void loadMore(String reference, ShotsController.OnShotsLoadedListener callback) {
        int page = 1;
        try {
            page = pages.get(reference);
        } catch (NullPointerException ignored) {
        }
        page++;
        pages.put(reference, page);
        search(reference, callback);
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
            ArrayList<Shot> shotsFetched = new ArrayList<Shot>();
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
                    shotsFetched.add(shot);
                }
                if (shots.containsKey(query)) {
                    shots.get(query).addAll(shotsFetched);
                } else {
                    shots.put(query, shotsFetched);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return shots.get(query);
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
