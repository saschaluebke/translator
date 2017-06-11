package databaseTests;

import components.Word;
import database.DBHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBHelperTest {
    static DBHelper dbh;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        dbh = new DBHelper();
        dbh.dropAllTables();
        dbh.truncate("languages");
    }

    @Before
    public void executedBeforeEach() {
        dbh.dropAllTables();
        dbh = new DBHelper();
        dbh.truncate("languages");
    }

    @Test
    public void dbhConstructorTest() {
        assertEquals(true,dbh.isTable("languages"));
        assertEquals(false,dbh.isTable("wordlist_en"));
    }

    @Test
    public void newLanguageTest() {
        dbh.newLanguage("en");

        assertEquals(true,dbh.isTable("wordlist_en"));
        assertEquals(true,dbh.isTable("rel_en_en"));

        dbh.newLanguage("de");
        assertEquals(true,dbh.isTable("wordlist_de"));
        assertEquals(true,dbh.isTable("rel_de_de"));
        assertEquals(true,dbh.isTable("rel_en_de"));
        assertEquals(true,dbh.isTable("rel_de_en"));
    }

    @Test
    public void searchWordListTestByName() {
        dbh.newLanguage("en");
        dbh.newLanguage("de");

        dbh.putWordList(new Word(0,"Hallo","de"));

        assertEquals("Hallo",dbh.getWordList("Hallo","de").get(0).getName());
    }

    @Test
    public void getWordListTestByNameMulti() {
        dbh.newLanguage("en");
        dbh.newLanguage("de");

        Word wordGerman1 = new Word(0,"Bank","Sitzgelegenheit","de");
        Word wordGerman2 = new Word(0,"Bank","Kreditinstitut","de");
        wordGerman2.setDescription("Das ist eine Beschreibung");
        wordGerman2.setCount(10);
        wordGerman2.setPrior(10);
        dbh.putWordList(wordGerman1);
        dbh.putWordList(wordGerman2);
        assertEquals("Bank",dbh.getWordList("Bank","de").get(0).getName());
        assertEquals("Bank",dbh.getWordList("Bank","de").get(1).getName());
        assertEquals("Das ist eine Beschreibung",dbh.getWordList("Bank","de").get(1).getDescription());
    }

    @Test
    public void searchWordListTestById() {
        dbh.newLanguage("en");
        dbh.newLanguage("de");

        dbh.putWordList(new Word(0,"Hallo","de"));

        assertEquals(1,dbh.getWordFromId(1,"de").getId());
    }



    @Test
    public void getRelationTestSingle() {
        dbh.newLanguage("en");
        dbh.newLanguage("de");

        Word wordGerman = new Word(0,"Hallo","de");
        Word wordEnglish = new Word(0,"Hello", "en");
        wordGerman.setId(dbh.putWordList(wordGerman));
        wordEnglish.setId(dbh.putWordList(wordEnglish));
        dbh.putRelation(wordGerman,wordEnglish,0,0);

        assertEquals(1,(int)dbh.getRelation(wordGerman,"de","en").get(0).getIdFrom());
    }

    @Test
    public void getRelationTestMultiple() {
        dbh.newLanguage("en");
        dbh.newLanguage("de");

        Word wordGerman = new Word(0,"Hallo","de");
        Word wordEnglish1 = new Word(0,"Hello", "en");
        Word wordEnglish2 = new Word(0,"Hi","en");
        wordGerman.setId(dbh.putWordList(wordGerman));
        wordEnglish1.setId(dbh.putWordList(wordEnglish1));
        wordEnglish2.setId(dbh.putWordList(wordEnglish2));
        dbh.putRelation(wordGerman,wordEnglish1,0,0);
        dbh.putRelation(wordGerman,wordEnglish2,0,0);

        assertEquals(1,(int)dbh.getRelation(wordGerman,"de","en").get(0).getIdTo());
        assertEquals(2,(int)dbh.getRelation(wordGerman,"de","en").get(1).getIdTo());
    }

    @Test
    public void getRelationTestSort() {
        dbh.newLanguage("en");
        dbh.newLanguage("de");

        Word wordGerman = new Word(0,"Hallo","de");
        Word wordEnglish1 = new Word(0,"Hello", "en");
        Word wordEnglish2 = new Word(0,"Hi","en");
        wordGerman.setId(dbh.putWordList(wordGerman));
        wordEnglish1.setId(dbh.putWordList(wordEnglish1));
        wordEnglish2.setId(dbh.putWordList(wordEnglish2));
        dbh.putRelation(wordGerman,wordEnglish1,0,0);
        dbh.putRelation(wordGerman,wordEnglish2,10,0);

        assertEquals(2,(int)dbh.getRelation(wordGerman,"de","en").get(0).getIdTo());
        assertEquals(1,(int)dbh.getRelation(wordGerman,"de","en").get(1).getIdTo());
    }




}
