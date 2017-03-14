package web.controller;

import components.Language;
import components.Word;
import database.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import translators.TranslatorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saschbot on 11.03.2017.
 */
@Controller
public class WordController {
    static Logger log = Logger.getLogger(WordController.class.getName());


    @RequestMapping(value="/translated", method=RequestMethod.POST)
    public String recoverPass(@RequestParam("word") String word,@RequestParam("to") String to,@RequestParam("from") String from,
                              @RequestParam("request") String request,@RequestParam("translation") String translate,@RequestParam("descPut") String descPut,
                              @RequestParam("wordPut") String wordPut,@RequestParam("fromPut") String fromPut,final ModelMap modelMap) {
        //TODO: liste anzeigen lassen immer!
        TranslatorHelper translator = new TranslatorHelper();
        translator.setFrom(from);
        translator.setTo(to);

        //Create Language Lists
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

        //Create translationModeList
        List<String> translationModes = new ArrayList<>();
        translationModes.addAll(translator.getTranslationModes());
        List<String> translationList = new ArrayList<>();
        for (int i = 0;i<translationModes.size();i++){
            if (translationModes.get(i).equals(translate)){
                translationList.add(translationModes.get(i));
            }
        }
        translationList.addAll(translationModes);

        //execute Actions
        System.out.println("Translate: "+translate);
        Word selectedWord = new Word (0,"!No Word found!","en");
        ArrayList<Word> wordList = new ArrayList<>();
        Word initialWord = new Word(0,word,from);
        DBHelper dbh = new DBHelper("jdbc:mysql://localhost/translation?useSSL=false","org.gjt.mm.mysql.Driver","root","dsa619");
        Word putIn = new Word(0,wordPut,fromPut);
        putIn.setDescription(descPut);
        System.out.println("Request: "+request);

        if (request.equals("Put Word")){
            System.out.println("WordPut initialized");
            putIn.setId(dbh.putWordList(putIn));
            selectedWord = putIn;
        }else if(request.equals("Get Word")) {
            System.out.println("WordGet initialized");
            wordList = dbh.getWordList(wordPut,fromPut);
            if (wordList.size()>0){
                selectedWord = wordList.get(0);
            }else{
                selectedWord = new Word(0,"!No Word found!","en");
            }

        }else {
            if (translate.equals("Transltr")){
                System.out.println("Use transltr");
                String[] translations = translator.translate(word);
                String translation = translator.retokenizer(translations);
                selectedWord = new Word(0,translation,to);
                wordList.add(selectedWord);
                //TODO: Was soll passieren wenns schon in der Datenbank ist? und Beschreibung gleich....
                if (request.equals("cache result")){
                    System.out.println("begin caching");
                    initialWord.setId(dbh.putWordList(initialWord));
                    selectedWord.setId(dbh.putWordList(selectedWord));
                    dbh.putRelation(initialWord,selectedWord);
                }
            }else{
                //TODO: Aus Datenbank aber bis jetzt nehme immer erste gefundene Übersetzung und Homonym...
                int initialWordId = dbh.getWordList(word,from).get(0).getId();
                initialWord.setId(initialWordId);
                ArrayList<Integer> idList = dbh.getRelation(initialWord,from,to);

                if (idList.size()>0){
                    System.out.println("keine übersetzung gefunden");
                }else{
                    System.out.println(idList+"/"+initialWord.getId()+"/"+from+":"+to);
                    selectedWord = dbh.getWordList(idList.get(0),to);
                    for(int i:idList){
                        wordList.add(dbh.getWordList(idList.get(i),to));
                    }
                }
            }
        }

        modelMap.addAttribute("translationList", translationList);
        modelMap.addAttribute("initialWord",word);
        modelMap.addAttribute("to",to);
        modelMap.addAttribute("from",from);
        modelMap.addAttribute("word",selectedWord);
        modelMap.addAttribute("wordList",wordList);

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
        ArrayList<Word> wordList = new ArrayList<>();
        wordList.add(w);
        modelMap.addAttribute("wordList",wordList);
        return "home";
    }

}
