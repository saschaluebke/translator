import databaseTests.DBHelperTest;
import databaseTests.DBQueryTest;
import databaseTests.WordnetHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import translatorsTests.TranslatorHelperTest;
import translatorsTests.TransltrTest;

@RunWith(Suite.class)
@SuiteClasses({ TranslatorHelperTest.class,  TransltrTest.class, DBQueryTest.class, DBHelperTest.class, WordnetHelperTest.class})
public class AllTests {

}