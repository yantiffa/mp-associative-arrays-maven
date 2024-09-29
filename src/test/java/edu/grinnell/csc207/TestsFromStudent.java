package edu.grinnell.csc207;

import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.NullKeyException;
import edu.grinnell.csc207.util.KeyNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * A place for you to put your own tests (beyond the shared repo).
 *
 * @author Tiffany Yan
 */
public class TestsFromStudent {
  /**
   * A test for the remove method.
   * We will do this by first add a key-value pair, then remove it.
   * After that we will check if we could still get the removed key-value pair.
   * 
   * @throws NullKeyException
   */
  @Test
  public void TiffanyYan01() throws NullKeyException {
    AssociativeArray<Integer, String> testarr = new AssociativeArray<Integer, String>();
    testarr.set(1, "Tiffany");

    // Make sure that the value has been set.
    try {
      assertEquals("Tiffany", testarr.get(1));
    } catch (KeyNotFoundException e) {
      fail("Could not set 1 to Tiffany");
    } //try/catch

    //Test for remove, where we would expect the following line to throw an expection
    testarr.remove(1);
    try {
      testarr.get(1);
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    } //try/catch
  } //TiffanyYan01()

  /**
  * A test for the clone method.
  * We will do this by first add a key-value pair to an initial associate array.
  * we will then clone the initial associate array.
  * After that we will check if we could find the key-value pair at the cloned array.
  * @throws NullKeyException
  */
  @Test
  public void TiffanyYan02() throws NullKeyException {
    AssociativeArray<String, Double> testarr = new AssociativeArray<String, Double>();
    testarr.set("Pi", 3.1415926535);
    
    // Make sure that the value has been set.
    try {
      assertEquals(3.1415926535, testarr.get("Pi"));
    } catch (KeyNotFoundException e) {
      fail("Could not set Pi to 3.1415926535");
    } //try/catch


    AssociativeArray<String, Double> clonearr = testarr.clone();
    // Make sure that the cloned array has been set and have the same pair as the original
    try {
      assertEquals(3.1415926535, clonearr.get("Pi"));
    } catch (KeyNotFoundException e) {
      fail("Could not clone testarr to clonearr");
    } //try/catch
  } //TiffanyYan02()

    /**
    * A test expand method.
    * We will check if the associate array will expand, given the number of key value pair
    * is greater than 16
    * @throws NullKeyException
    */
    @Test
    public void TiffanyYanEdge03() throws NullKeyException {
      AssociativeArray<Double, Double> testarr = new AssociativeArray<Double, Double>();
      for (int i = 0; i < 18; i++) {
        testarr.set(1.23+i, 1.23 + i);
      } // for
  
      try {
        for (int i = 17; i >= 1; i--) {
          assertEquals(1.23 + i, testarr.get(1.23 + i));
        } // for
      } catch (Exception e) {
        fail("Expansion of the array failed");
      } // try/catch
    } // TiffanyYanEdge03()

} // class TestsFromStudent
