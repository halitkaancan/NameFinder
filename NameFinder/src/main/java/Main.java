import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class Main {

    public static void main(String[] args) {

        String url= "";

        try { // jsoup--------------------------------------------------------------------------------------------------
            url = args[0];
            Document document = Jsoup.connect(url).get();
            String x =document.text();

            //Sentence detector-----------------------------------------------------------------------------------------
            InputStream modelIn1 = new FileInputStream("src\\main\\resources\\en-sent.bin"); ////C:\Users\halit\IdeaProjects\NameFinder\
            SentenceModel  model = new SentenceModel(modelIn1);
            SentenceDetectorME sd = new SentenceDetectorME(model);

            String sentences[] = sd.sentDetect(x);

            // tokenizer---------------------------------------------------------------------------------------------------
            InputStream  modelIn2 = new FileInputStream("src\\main\\resources\\en-token.bin");
            TokenizerModel model2 = new TokenizerModel(modelIn2);
            Tokenizer tokenizer = new TokenizerME(model2);
            //String tokens[] = tokenizer.tokenize(sentences);
            String tokens[];
            Span[] name;

            //name finder-------------------------------------------------------------------------------------------------------
            InputStream modelIn3 = new FileInputStream("src\\main\\resources\\en-ner-person.bin");
            TokenNameFinderModel model3 = new TokenNameFinderModel(modelIn3);
            NameFinderME nameFinder = new NameFinderME(model3);
            for (String s : sentences) {
                tokens =tokenizer.tokenize(s);
                name = nameFinder .find(tokens);

                for( Span s2 : name){
                    System.out.println(tokens[s2.getStart()]+"  "+tokens[s2.getStart()+1]);
                }

                nameFinder.clearAdaptiveData();
            }
            }catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an Exception");
        }
    }
}