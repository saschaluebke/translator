package databaseTests;

import database.DBQuery;
import org.junit.Before;
import org.junit.BeforeClass;

public class DBQueryTest {

    static DBQuery dbq = null;

    @Before
    public void executedBeforeEach() {
        dbq.truncate("wordsde");
        dbq.truncate("wordsen");
        dbq.truncate("rel_de_en");
    }

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        dbq = new DBQuery("jdbc:mysql://localhost/world","org.gjt.mm.mysql.Driver","root","dsa619");
        dbq.dropAllTables();
        dbq.truncate("languages");
    }




}
