package translators;

import components.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Welchen Translator, was soll mit einem Wort passieren? soll es dann in die Datenbank gelegt werden? Einfache Tokens
 */
public class TranslatorHelper {
    Transltr transltr;

    public TranslatorHelper(){
        transltr = new Transltr();
    }

    public void setFrom(String from){
        transltr.setFrom(from);
    }

    public String getFrom(){
        return transltr.getFrom();
    }

    public void setTo(String to){
        transltr.setTo(to);
    }

    public String getTo(){
        return transltr.getTo();
    }

    public String[] translate(String sentence){
        String[] tokens = tokenizer(sentence);
        if (transltr != null){
            for(int i=0;i<tokens.length;i++){
                String t = transltr.translate(tokens[i]);
                tokens[i] = t;
            }
        }

       return tokens;
    }

    public String[] tokenizer(String sentence){
        sentence = sentence.replace(".","");
        sentence = sentence.replace("-"," ");

        String[] array = sentence.split(" ");

        for (int i=0; i<array.length; i++){
            array[i].trim();
        }
           return array;
    }

    public String retokenizer(String[] tokens){
        String translation = "";
        StringBuilder builder = new StringBuilder();
        for(String s : tokens) {
            builder.append(s);
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
//TODO: gibt noch nicht alle Languages weil zu umständlich viele :( Siehe transltr.getLanguages
    public List<Language> getAllLanguages(){
        List<Language> responseList = new ArrayList<>();
        if (transltr!=null){
            responseList = transltr.getLanguages();
        }
        return responseList;
    }

    public List<String> getTranslationModes(){
        List<String> responseList = new ArrayList<>();
        responseList.add("Transltr");
        responseList.add("Database");
        return responseList;
    }
}
