package web.controller;

import components.Language;
import database.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import translators.TranslatorHelper;
import components.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saschbot on 11.03.2017.
 */
@Controller
public class WordController {
    static Logger log = Logger.getLogger(WordController.class.getName());


    @RequestMapping(value="/translated", method=RequestMethod.POST)
    public String recoverPass(@RequestParam("word") String word,@RequestParam("to") String to,@RequestParam("from") String from,@RequestParam("request") String request,@RequestParam("translation") String translate,final ModelMap modelMap) {
        //TODO: Hier rein muss die Logik der Übersetzung
        //TODO: Das ist nur ein provisorium zu Testzwecken


        TranslatorHelper translator = new TranslatorHelper();
        List<Language> languages =  translator.getAllLanguages();

        List<Language> languageFrom =  new ArrayList<>();
        for (int i = 0;i<languages.size();i++){
            if (languages.get(i).getName().equals(from)){
                languageFrom.add(languages.get(i));
            }
        }
        languageFrom.addAll(languages);
        modelMap.addAttribute("languageFrom", languageFrom);

        List<Language> languageTo =  new ArrayList<>();
        for (int i = 0;i<languages.size();i++){
            if (languages.get(i).getName().equals(to)){
                languageTo.add(languages.get(i));
            }
        }
        languageTo.addAll(languages);
        modelMap.addAttribute("languageTo", languageTo);

        List<String> translationModes = new ArrayList<>();
        translationModes.addAll(translator.getTranslationModes());
        List<String> translationList = new ArrayList<>();
        for (int i = 0;i<translationModes.size();i++){
            if (translationModes.get(i).equals(translate)){
                translationList.add(translationModes.get(i));
            }
        }
        translationList.addAll(translationModes);
        modelMap.addAttribute("translationList", translationList);

        translator.setFrom(from);
        translator.setTo(to);

        modelMap.addAttribute("initialWord",word);

        modelMap.addAttribute("to",to);
        modelMap.addAttribute("from",from);

        System.out.println("Translate: "+translate);

        Word initialWord = new Word(0,word,from);
        Word translatedWord;
        DBHelper dbh = new DBHelper("jdbc:mysql://localhost/translation?useSSL=false","org.gjt.mm.mysql.Driver","root","dsa619");

        if (translate.equals("Transltr")){
            System.out.println("Use transltr");
            String[] translations = translator.translate(word);
            String translation = translator.retokenizer(translations);
            translatedWord = new Word(0,translation,to);
            //TODO: Was soll passieren wenns schon in der Datenbank ist? und Beschreibung gleich....
            if (request.equals("cache result")){
                System.out.println("begin caching");
                initialWord.setId(dbh.putWordList(initialWord));
                translatedWord.setId(dbh.putWordList(translatedWord));
                dbh.putRelation(initialWord,translatedWord);
            }
        }else{
            //TODO aus Datenbank holen bis jetzt nehme immer erste gefundene Übersetzung und Homonym...
            int initialWordId = dbh.getWordList(word,from).get(0).getId();
            initialWord.setId(initialWordId);
            ArrayList<Integer> idList = dbh.getRelation(initialWord,from,to);
            System.out.println(idList+"/"+initialWord.getId()+"/"+from+":"+to);
            translatedWord = dbh.getWordList(idList.get(0),to);
        }

        modelMap.addAttribute("word",translatedWord);
        return "home";
    }


  /*  @RequestMapping("/translated")
    public String translate(@PathVariable String name, ModelMap modelMap) {
        Word word = wordRepo.findByName(name);
        modelMap.put("word",word);
        return "home";
    }
*/
    @RequestMapping("/")
    public String home(ModelMap modelMap) {
        TranslatorHelper translator = new TranslatorHelper();

        List<Language> languages =  translator.getAllLanguages();

        List<Language> languageFrom =  new ArrayList<>();
        languageFrom.add(new Language("de","German"));
        languageFrom.addAll(languages);
        modelMap.addAttribute("languageFrom", languageFrom);

        List<Language> languageTo =  new ArrayList<>();
        languageTo.add(new Language("en","English"));

        List<String> translationList = new ArrayList<>();
        translationList.addAll(translator.getTranslationModes());
        modelMap.addAttribute("translationList", translationList);
        languageTo.addAll(languages);
        modelMap.addAttribute("languageTo", languageTo);
        modelMap.addAttribute("to","en");
        modelMap.addAttribute("from","de");
        Word w = new Word(0,"-","en");
        modelMap.addAttribute("word",w);
        return "home";
    }

}
