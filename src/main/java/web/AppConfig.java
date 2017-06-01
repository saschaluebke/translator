package web;

import components.Language;
import database.DBHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import translators.TranslatorHelper;

@EnableAutoConfiguration
@ComponentScan
public class AppConfig {
    public static void main(String[] args) {



        //TODO: This is just a Dirty initial call

        DBHelper dbh = new DBHelper();
        dbh.dropAllTables();
        dbh.truncate("languages");
        TranslatorHelper th = new TranslatorHelper();
        for (Language l:th.getAllLanguages()){
            dbh.newLanguage(l.getName());
        }


        SpringApplication.run(AppConfig.class, args);
   }

}
