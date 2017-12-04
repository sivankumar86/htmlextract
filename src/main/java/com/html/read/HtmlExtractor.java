package com.html.read;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * Read the google url and extract the label field
 *
 */
public class HtmlExtractor {


    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://gmail.com/").get();
        System.out.println(doc.title());

        Elements refresh = doc.head().select("meta[http-equiv=refresh]");

        //validate whether need to be redirected or not
        if (!refresh.isEmpty()) {
            Element element = refresh.get(0);
            String content = element.attr("content");
            // split the content here
            Pattern pattern = Pattern.compile("^.*URL=(.+)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content);
            if (matcher.matches() && matcher.groupCount() > 0) {
                String redirectUrl = matcher.group(1);
                System.out.println(redirectUrl);
                doc = Jsoup.connect(redirectUrl).get();
            }
        }
        Elements labels= doc.select("label");

        for(Element element:labels){
            System.out.println("label attributes : "+element.html());
        }

    }


}
