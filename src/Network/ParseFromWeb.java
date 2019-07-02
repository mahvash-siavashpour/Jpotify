package Network;


import Logic.Music;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseFromWeb {
    public static String parser(String s2) throws IOException {
        String s1 = "";
        String s3 = "";


        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            //Operating system is based on Windows
            s1 = "https://www.google.com/search?client=firefox-b-d&channel=trow&q=";
            s3 = "+lyrics&ie=utf-8&oe=utf-8";
        } else if (os.contains("osx")) {
            //Operating system is Apple OSX based
            //TODO add code to support mac os;
        } else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) {
            //Operating system is based on Linux/Unix/*AIX
            s1 = "https://www.google.com/search?client=ubuntu&channel=fs&q=";
            s3 = "+lyrics&ie=utf-8&oe=utf-8";
        }

        String s = s1 + s2 + s3;


        Document doc = Jsoup.connect(s).get();
        log(doc.title());

        Element link = doc.select("a").first();

        String text = doc.body().text();

        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
        }
        if (text.contains("Search Results Knowledge result") && text.contains("Source:")) {
            if (text.substring(text.indexOf("Search Results Knowledge result") + 32, text.indexOf("Source:")).trim().length()>5)
            return text.substring(text.indexOf("Search Results Knowledge result") + 32, text.indexOf("Source:"));
        } else if (text.contains("Search Results Web results") && text.contains("Source:")) {
            if (text.substring(text.indexOf("Search Results Web result") + 26, text.indexOf("Source:")).trim().length()>5)
            return text.substring(text.indexOf("Search Results Web result") + 26, text.indexOf("Source:"));
        }
//            return text;
        System.out.println("Failed!");

        return "Unfortunately couldn't find any results :( ";

    }


    private static final Pattern urlPattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    Matcher matcher = urlPattern.matcher("foo bar http://example.com baz");

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }

    public static String makeURL(String absolutePath) {
        Music music = new Music(absolutePath);
        String tmp1, tmp2;
        String s2;
        if (music.getSongData().getSongName().endsWith(".mp3")) {
            s2 = takeSpacesInmiddle(music.getSongData().getSongName().trim().substring(0, music.getSongData().getSongName().trim().length() - 4));
            if (music.getSongData().getArtist().equals("Unknown")) return s2;
            else return s2 + "+" + takeSpacesInmiddle(music.getSongData().getArtist().trim());
        }

        s2 = takeSpacesInmiddle(music.getSongData().getSongName().trim());
        if (music.getSongData().getArtist().equals("Unknown")) return s2;
        else return s2 + "+" + takeSpacesInmiddle(music.getSongData().getArtist().trim());

    }

    private static String takeSpacesInmiddle(String s) {
        String sNew = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                sNew = sNew + "+";
            } else {
                sNew = sNew + s.charAt(i);
            }
        }
        return sNew;
    }

    public static void main(String[] args) {
        try {
            System.out.println(parser(makeURL("/home/niki/Desktop/Kelly Clarkson_Stronger.mp3")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



