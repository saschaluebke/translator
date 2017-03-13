package web.controller;

import components.Language;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import translators.TranslatorHelper;
import web.data.WordsRepository;
import web.model.Word;

import java.util.List;

/**
 * Created by Saschbot on 11.03.2017.
 */
@Controller
public class WordController {
    static Logger log = Logger.getLogger(WordController.class.getName());

    @Autowired
    private WordsRepository wordRepo;

    @RequestMapping(value="/translated", method=RequestMethod.POST)
    public String recoverPass(@RequestParam("word") String word,@RequestParam("to") String to,@RequestParam("from") String from,final ModelMap modelMap) {
        //TODO: Hier rein muss die Logik der Übersetzung
        //TODO: Das ist nur ein provisorium zu Testzwecken


        TranslatorHelper translator = new TranslatorHelper();
        List<Language> languages =  translator.getAllLanguages();
        modelMap.addAttribute("languages", languages);

        translator.setFrom(from);
        translator.setTo(to);
        String[] translations = translator.translate(word);
        String translation = translator.retokenizer(translations);
        System.out.println("To translate: "+word+". Translation: "+translation);
        Word w = new Word(0,translation);
        modelMap.addAttribute("word",w);
        modelMap.addAttribute("to",to);
        modelMap.addAttribute("from",from);
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
        modelMap.addAttribute("languages", languages);
        modelMap.addAttribute("to","en");
        modelMap.addAttribute("from","de");
        Word w = new Word(0,"-");
        modelMap.addAttribute("word",w);
        return "home";
    }

}
