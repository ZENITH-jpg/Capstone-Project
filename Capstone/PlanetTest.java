import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PlanetTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   @Test public void defaultTest() {
      Planet p = new Planet();
      p.displayConstitution();
      p.addBlock("Clean air", 300);
      p.displayConstitution();
   }
}
