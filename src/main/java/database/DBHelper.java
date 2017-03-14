package database;

import components.Relation;
import components.Word;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        int count=0;
        int prior = 0;
        String description="-";
        ResultSet rs =null;
        String name = "";
        String[] par = {Integer.toString(id)};
        rs = query("SELECT * FROM wordlist_"+language+" WHERE id = ? ",par);

        try {
            if (rs.next() == true){
                id=rs.getInt("id");
                name = rs.getString("name");
                prior = rs.getInt("prior");
                count = rs.getInt("count");
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Word responseWord = new Word(id,name,language);
        responseWord.setPrior(prior);
        responseWord.setCount(count);
        responseWord.setDescription(description);
        return responseWord;
    }

    public ArrayList<Word>  getWordList(String name, String language) {
        ArrayList<Word> wordList = new ArrayList<>();

        ResultSet rs = null;
        String[] par = {name};

        //System.out.println("SELECT * FROM wordlist_" + language + " WHERE name = "+name);

        rs = query("SELECT * FROM wordlist_" + language + " WHERE name = ? ", par);

        try {
            while (rs.next() == true) {
                int id = rs.getInt("id");
                name = rs.getString("name");
                Word flotsam = new Word(id, name, language);
                flotsam.setDescription(rs.getString("description"));
                if (flotsam.getDescription().equals("")){
                    flotsam.setDescription("-");
                }
                flotsam.setPrior(rs.getInt("prior"));
                flotsam.setCount(rs.getInt("count"));
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
            String[] par1 ={word.getName(),word.getDescription(),Integer.toString(word.getPrior()),Integer.toString(word.getCount())};
            queryUpdate("INSERT INTO wordlist_"+word.getLanguage()+" VALUES (0, ?, ?, ?, ?)",par1);
            String[] par2 ={word.getName()};
            rs = query("SELECT * FROM wordlist_"+word.getLanguage()+" WHERE name = ? ",par2);

            rs.next();
            id = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Relation> getRelation(Word word, String from, String to){
        ArrayList<Relation> relationList = new ArrayList<>();
        String[] par = {Integer.toString(word.getId())};
        ResultSet rs = query("SELECT * FROM rel_"+from+"_"+to+" WHERE id_"+from+" = ? ", par);
        try {
            while(rs.next()){
                int id = rs.getInt("id");
                int idFrom = rs.getInt("id_"+from);
                int idTo = rs.getInt("id_"+to);
                Relation relation = new Relation(id,idFrom,idTo);
                relation.setCount(rs.getInt("count"));
                relation.setPrior(rs.getInt("prior"));
                relationList.add(relation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (relationList.size()>1){
            relationList.sort((relation1, relation2) -> relation1.compareTo(relation2));
        }

        return relationList;
    }

    public void putRelation(Word word1, Word word2, int prior, int count){
        //enter Synonym
        if (word1.getLanguage().equals(word2.getLanguage())){
            String[] par1 ={Integer.toString(word1.getId()),Integer.toString(word2.getId()),Integer.toString(prior),Integer.toString(count)};
            queryUpdate("INSERT INTO rel_"+word1.getLanguage()+"_"+word2.getLanguage()+" VALUES (0, ?, ? , ? ,?)",par1);
        }else //enter translation
            {
            String[] par1 ={Integer.toString(word1.getId()),Integer.toString(word2.getId()),Integer.toString(prior),Integer.toString(count)};
            queryUpdate("INSERT INTO rel_"+word1.getLanguage()+"_"+word2.getLanguage()+" VALUES (0, ?, ? , ? ,?)",par1);

        }
        }

    public ArrayList<Integer> getClickCount(String table,ArrayList<Integer> idList){
        ArrayList<Integer> countList = new ArrayList<>();
        int c =0;
        for (int id : idList){
            String[] par = {Integer.toString(id)};
            ResultSet rs = query("SELECT * FROM "+table+" WHERE id = ? ",par);

            try {
                rs.next();
                c = rs.getInt("count");
                countList.add(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return countList;
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
                            + "prior INT NOT NULL,"
                            + "count INT NOT NULL,"
                            + "PRIMARY KEY (id));",par);

                    queryUpdate("CREATE TABLE rel_"+name+"_"+language+" "
                            + "(id INT NOT NULL AUTO_INCREMENT,"
                            + "id_"+name+" INT NOT NULL,"
                            + "id_"+language+" INT NOT NULL,"
                            + "prior INT NOT NULL,"
                            + "count INT NOT NULL,"
                            + "PRIMARY KEY (id));",par);
                }

                queryUpdate("CREATE TABLE wordlist_"+language+" "
                        + "(id INT NOT NULL AUTO_INCREMENT,name VARCHAR(45) NULL,"
                        + "description VARCHAR(45) NULL,"
                        + "prior INT NOT NULL, "
                        + "count INT NOT NULL, "
                        + "PRIMARY KEY (id));",par);
                queryUpdate("CREATE TABLE rel_"+language+"_"+language+" "
                        + "(id INT NOT NULL AUTO_INCREMENT,"
                        + "id_"+language+" INT NOT NULL,"
                        + "id_"+language+"2 INT NOT NULL,"
                        + "prior INT NOT NULL,"
                        + "count INT NOT NULL,"
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
