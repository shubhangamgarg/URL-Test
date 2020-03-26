package com.example.urltest;

import android.util.Log;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class PrepareSearchResult {
    Elements elements;
    public   ArrayList<SearchResult> list = new ArrayList<>();
    public   ArrayList<Links>links = new ArrayList<>();
    public PrepareSearchResult(Elements elements)
    {
        this.elements = elements;
    }
    public void loadBooks()
    {

        SearchResult s =  new SearchResult();
        Links l = new Links();
        Element table = elements.select("table[class=c]").first();
        Elements rows = table.select("tr");
        Element tr;
        Elements td;
        for(int i =0;i<rows.size();i++)
        {
//            tds[i] = rows.get(i);
            tr =  rows.get(i);
            td = tr.select("td");
            s.setAuthor(td.get(1).text().trim());
            s.setBook_tile(td.get(2).text().trim());
            s.setPublisher(td.get(3).text().trim());
            s.setYear(td.get(4).text().trim());
            s.setLanguage(td.get(6).text().trim());
            s.setExtension(td.get(8).text().trim());
            l.setLink(td.get(9).select("a").attr("href"));
            list.add(s);
            links.add(l);
            l= new Links();
            s =  new SearchResult();
//            Log.v("BOOK","Book "+td.get(2).text());
        }

    }
    public void finalize() throws Throwable {
        super.finalize();
    }
}
