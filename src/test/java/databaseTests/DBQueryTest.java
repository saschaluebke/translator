package databaseTests;

import database.DBQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBQueryTest {

    static DBQuery dbq = null;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        dbq = new DBQuery();
        dbq.dropAllTables();
        dbq.truncate("languages");
    }

    @Test
    public void dbhConstructorTest() {
        assertEquals(true,dbq.isTable("languages"));
    }




}
