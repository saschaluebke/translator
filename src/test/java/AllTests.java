

import databaseTests.DBHelperTest;
import databaseTests.DBQueryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import translatorsTests.TranslatorHelperTest;
import translatorsTests.TransltrTest;

@RunWith(Suite.class)
@SuiteClasses({ DBHelperTest.class, DBQueryTest.class, TranslatorHelperTest.class,  TransltrTest.class })
public class AllTests {

}