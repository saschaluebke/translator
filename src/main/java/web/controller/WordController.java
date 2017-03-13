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
        String[] translations = translator.translate(word);
        String translation = translator.retokenizer(translations);

        Word w = new Word(0,translation,to);
        modelMap.addAttribute("initialWord",word);
        modelMap.addAttribute("word",w);
        modelMap.addAttribute("to",to);
        modelMap.addAttribute("from",from);
        System.out.println("Request: "+request);
        System.out.println("Translate: "+translate);
        if (translate.equals("transltr")){
            System.out.println("Use transltr");
        }
        if (request.equals("cache result")){
            System.out.println("begin caching");
            DBHelper dbh = new DBHelper("jdbc:mysql://localhost/translation?useSSL=false","org.gjt.mm.mysql.Driver","root","dsa619");
            Word initialWord = new Word(0,word,from);
            Word translatedWord = new Word(0,w.getName(),to);
            dbh.putWordList(initialWord);
            dbh.putWordList(translatedWord);
            dbh.putRelation(initialWord,translatedWord);
        }
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
