package assignment4;

import java.io.IOException;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Bonus {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://www.ece.utexas.edu/people/faculty/edison-thomaz").get();
        Element p = doc.select("p").get(1);
        String messyData = p.text();
        String data = new String(messyData.replaceAll("[^a-zA-Z0-9\\s]", ""));

        File bonus = new File("bonus.txt");

        try(FileOutputStream fos = new FileOutputStream(bonus);
            BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] bytes = data.getBytes();
            bos.write(bytes);
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        final GraphPoet scrape = new GraphPoet(new File(("bonus.txt")));
        System.out.println(scrape.poem(new File("bonus_input.txt")));
    }




}
