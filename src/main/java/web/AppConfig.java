package web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class AppConfig {
    public static void main(String[] args) {


/*
        //TODO: This is just a Dirty initial call

        DBHelper dbh = new DBHelper("jdbc:mysql://localhost/translation?useSSL=false","org.gjt.mm.mysql.Driver","root","dsa619");
        dbh.dropAllTables();
        dbh.truncate("languages");
        TranslatorHelper th = new TranslatorHelper();
        for (Language l:th.getAllLanguages()){
            dbh.newLanguage(l.getName());
        }


        SpringApplication.run(AppConfig.class, args);
 */   }

}
