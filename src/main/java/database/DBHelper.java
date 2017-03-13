package database;

import components.Word;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHelper extends DBQuery{
    private DBQuery dbq;

    public DBHelper(String url, String myDriver, String conName, String password) {
        super(url, myDriver, conName, password);
        //find "languages"table, If there is no languages make one
        if (!isTable("languages")){
            queryUpdate("CREATE TABLE languages"
                    + " (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(45) NULL,"
                    + "PRIMARY KEY (id));",new String[0]);
        }else{
            //TODO: search for all other tables necessary
        }

    }

    public Word getWordList(int id, String language){

        ResultSet rs =null;
        String name = "";
        String[] par = {Integer.toString(id)};
        rs = query("SELECT * FROM wordlist_"+language+" WHERE id = ? ",par);

        try {
            if (rs.next() == true){
                id=rs.getInt("id");
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Word responseWord = new Word(id,name,language);
        return responseWord;
    }

    public ArrayList<Word>  getWordList(String name, String language) {
        ArrayList<Word> wordList = new ArrayList<>();

        ResultSet rs = null;
        String[] par = {name};
        rs = query("SELECT * FROM wordlist_" + language + " WHERE name = ? ", par);

        try {
            while (rs.next() == true) {
                int id = rs.getInt("id");
                name = rs.getString("name");
                Word flotsam = new Word(id, name, language);
                wordList.add(flotsam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    public int putWordList(Word word){
        int id = 0;
        ResultSet rs = null;
        try {
            String[] par1 ={word.getName(),word.getDescription()};
            queryUpdate("INSERT INTO wordlist_"+word.getLanguage()+" VALUES (0, ?, ?)",par1);
            String[] par2 ={word.getName()};
            rs = query("SELECT * FROM wordlist_"+word.getLanguage()+" WHERE name = ? ",par2);

            rs.next();
            id = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Integer> getRelation(Word word, String from, String to){
        ArrayList<Integer> resultList = new ArrayList<>();
        String[] par = {Integer.toString(word.getId())};
        ResultSet tmpRs = query("SELECT * FROM rel_"+from+"_"+to+" WHERE id_"+from+" = ? ", par);
        try {
            while(tmpRs.next()){

                resultList.add(tmpRs.getInt("id_"+to));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public void putRelation(Word word1, Word word2){
        if (word1.getLanguage().equals(word2.getLanguage())){
            String[] par1 ={Integer.toString(word1.getId()),Integer.toString(word2.getId())};
            queryUpdate("INSERT INTO rel_"+word1.getLanguage()+"_"+word2.getLanguage()+" VALUES (0, ?, ? , 0 ,0)",par1);
        }else{
            String[] par1 ={Integer.toString(word1.getId()),Integer.toString(word2.getId())};
            queryUpdate("INSERT INTO rel_"+word1.getLanguage()+"_"+word2.getLanguage()+" VALUES (0, ?, ? , 0 ,0)",par1);

        }
        }

    public int getClickCount(String table, int id){
        String[] par = {Integer.toString(id)};
        ResultSet rs = query("SELECT * FROM "+table+" WHERE id = ? ",par);
        int c =0;
        try {
            rs.next();
            return c = rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public int addClickCount(String table, int id){
        String[] par1 = {Integer.toString(id)};
        ResultSet rs = query("SELECT * FROM "+table+" WHERE id = ? ",par1);
        int c =0;
        try {
            rs.next();
            c = rs.getInt("count");
            c++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] par2 = {Integer.toString(id)};
        queryUpdate("UPDATE "+table+" SET count = "+c+" WHERE id = ? ",par2);
        return c;
    }

    public boolean newLanguage(String language){
        String[] languagePar = {language};
        ResultSet rs = query("SELECT * FROM languages WHERE name = ? ",languagePar);

        try {
            if(rs.next()){
                return false;
            }else{
                String[] par = new String[0];
                rs = query("SELECT * FROM languages",par);

                while(rs.next()){
                    String name = rs.getString("name");
                    queryUpdate("CREATE TABLE rel_"+language+"_"+name+" "
                            + "(id INT NOT NULL AUTO_INCREMENT,"
                            + "id_"+language+" INT NOT NULL,"
                            + "id_"+name+" INT NOT NULL,"
                            + "count INT NULL,"
                            + "prior INT NULL,"
                            + "PRIMARY KEY (id));",par);

                    queryUpdate("CREATE TABLE rel_"+name+"_"+language+" "
                            + "(id INT NOT NULL AUTO_INCREMENT,"
                            + "id_"+name+" INT NOT NULL,"
                            + "id_"+language+" INT NOT NULL,"
                            + "count INT NULL,"
                            + "prior INT NULL,"
                            + "PRIMARY KEY (id));",par);
                }

                queryUpdate("CREATE TABLE wordlist_"+language+" "
                        + "(id INT NOT NULL AUTO_INCREMENT,name VARCHAR(45) NULL,"
                        + "description VARCHAR(45) NULL,"
                        + "PRIMARY KEY (id));",par);
                queryUpdate("CREATE TABLE rel_"+language+"_"+language+" "
                        + "(id INT NOT NULL AUTO_INCREMENT,"
                        + "id_"+language+" INT NOT NULL,"
                        + "id_"+language+"2 INT NOT NULL,"
                        + "count INT NULL,"
                        + "prior INT NULL,"
                        + "PRIMARY KEY (id));",par);
                queryUpdate("INSERT INTO languages VALUES (0,'"+language+"' )",par);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}
