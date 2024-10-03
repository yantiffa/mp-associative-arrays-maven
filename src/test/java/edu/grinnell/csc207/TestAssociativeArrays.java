package edu.grinnell.csc207;

import static java.lang.reflect.Array.newInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.beans.Transient;
import java.math.BigInteger;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.PackageNameFilter;

import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

/**
 * Tests of the AssociativeArray class and related classes.
 *
 * @author CSC-207 2024Fa
 * @author Samuel A. Rebelsky
 */
public class TestAssociativeArrays {
  // +------------------------------+--------------------------------
  // | Tests by Rebelsky, Samuel A. |
  // +------------------------------+

  // Keep these at the top.

  /**
   * A non-test. Here mostly so that we don't get nasty messages about unused classes.
   */
  public void rebelskySamuelPlaceholder() {
    assertFalse(false, "false is false");
    assertNull(null, "null is null");
    BigInteger i = new BigInteger("123");
    assertEquals(i, i, "i think i is i");
  } // rebelskySamuelPlaceholder()

  /**
   * A test of cloning.
   * 
   * @throws NullKeyException
   */
  @Test
  public void rebelskySamuelTest01() throws NullKeyException {
    // Build an array
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("A", "Apple");
    try {
      assertEquals("Apple", arr.get("A"));
    } catch (Exception e) {
      fail("Original array does not contain expected value");
    }
    // Make a copy
    AssociativeArray<String, String> arr2 = arr.clone();
    // Make sure it contains the appropriate value
    try {
      assertEquals("Apple", arr2.get("A"));
    } catch (Exception e) {
      fail("Clone does not contain original value");
    } // try/catch
    // Change the original array
    arr.set("A", "aardvark");
    // Make sure we haven't changed the clone.
    try {
      assertEquals("Apple", arr2.get("A"));
    } catch (Exception e) {
      fail("Change to original changes clone.");
    }
    // Change the clone
    arr2.set("A", "Ant");
    // And look for values
    try {
      assertEquals("Ant", arr2.get("A"));
    } catch (Exception e) {
      fail("Cannot change clone");
    }
    try {
      assertEquals("aardvark", arr.get("A"));
    } catch (Exception e) {
      fail("Change to clone changes original");
    }
  } // rebelskySamuelTest01()

  /**
   * Can we successfully add a bunch of values? (Checks array expansion.)
   * 
   * @throws NullKeyException
   */
  @Test
  public void rebelskySamuelTest02() throws NullKeyException {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // Add a bunch of values
    for (int i = 10; i < 50; i++) {
      arr.set(i, i * i);
    } // for
    try {
      for (int i = 49; i >= 10; i--) {
        assertEquals(i * i, arr.get(i));
      } // for
    } catch (Exception e) {
      fail("Exception in call to get");
    } // try/catch
  } // rebelskySamuelest02()

  /**
   * Do we get exceptions when grabbing a deleted value from the array?
   * 
   * @throws NullKeyException
   */
  @Test
  public void rebelskySamuelTest03() throws NullKeyException {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Add an element to the array
    arr.set("A", "Apple");
    // Make sure that it's there.
    try {
      assertEquals("Apple", arr.get("A"));
    } catch (KeyNotFoundException e) {
      fail("Could not set A to Apple");
    }
    // Remove it.
    arr.remove("A");
    // Make sure it's no longer there.
    try {
      // The following line should throw an exception
      arr.get("A");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
  } // rebelskySamuelTest03

  /**
   * Does `toString` work correctly on multi-element arrays? Intended to demonstrate how complicated
   * writing such a test might be.
   *
   * @throws NullKeyException In unexpected situations.
   */
  @Test
  public void rebelskySamuelTest04() throws NullKeyException {
    AssociativeArray<String, Integer> si = new AssociativeArray<String, Integer>();
    si.set("A", 1);
    si.set("B", 2);
    si.set("C", 3);
    String result = si.toString();
    System.err.println(result);
    assertTrue(result.equals("{A:1, B:2, C:3}") || result.equals("{A:1, C:3, B:2}")
        || result.equals("{B:2, A:1, C:3}") || result.equals("{B:2, C:3, A:1}")
        || result.equals("{C:3, A:1, B:2}") || result.equals("{C:3, B:2, A:1}"));
  } // rebelskySamuelTest04()

  /**
   * Do we get exceptions when grabbing a value from the empty array?
   */
  @Test
  public void rebelskySamuelEdge01() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    try {
      // The following line should throw an exception
      arr.get("A");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    } // try/catch
  } // rebelskySamuelEdge01

  /**
   * A helper for the next test. Computes a key given a size and an iterator
   * variable
   *
   * @param size
   *   The size of the array we're building (in practice). But could be
   *   any integer.
   * @param i
   *   The index variable for a loop (in practice). But could be any
   *   integer.
   *
   * @return a key to use in `set`, `get`, etc.
   */
  String rebelskySamuelComputeKey(int size, int i) {
    return size + ":" + i;
  } // rebelskySamuelComputeKey(int, int)

  /**
   * A helper for the next test. Computes a value given a size, a loop
   * index, and an extra value.
   *
   * @param size
   *   The size of the array we're building (in practice). But could be
   *   any integer.
   * @param i
   *   The index variable for a loop (in practice). But could be any
   *   integer.
   * @param extra
   *   Any integer you want.
   *
   * @return a value to use in set (and the corresponding calls to
   * get etc.)
   */
   BigInteger rebelskySamuelComputeVal(int i, int size, int extra) {
     return BigInteger.valueOf(i*size + extra);
   } // rebelskySamuelComputeVal(int, int, int)

  /**
   * Just a lot of changes to the array.
   */
  @Test
  public void rebelskySamuelCrazy01() {
    // Here's the array we'll work with
    AssociativeArray<String, BigInteger> vals = 
         new AssociativeArray<String, BigInteger>();

    // Here are the sizes of arrays we'll work with. 
    int[] sizes = new int[] { 15, 30, 20, 50, 40, 100, 20 };

    // For each size
    for (int size : sizes) {
      // Fill the array, working from size to 1.
      for (int i = size; i > 0; i--) {
        String key = rebelskySamuelComputeKey(size, i);
        BigInteger val = rebelskySamuelComputeVal(size, i, 2);
        // Store the value
        try {
          vals.set(key, val);
        } catch (Exception e) {
          fail(String.format("vals.set(%s, %s) FAILED", key, val.toString()));
        } // try/catch
      } // for i

      // Verify that the elements are all there.
      for (int i = 1; i <= size; i++) {
        String key = rebelskySamuelComputeKey(size, i);
        BigInteger val = rebelskySamuelComputeVal(size, i, 2);
        // Get the value
        try {
          assertEquals(val, vals.get(key), String.format("vals.get(%s)", key));
        } catch (Exception e) {
          fail(String.format("vals.get(%s) FAILED", key));
        } // try/catch
      } // for i

      // Remove some of the elements.
      for (int i = 1; i <= size; i += 3) {
        String key = rebelskySamuelComputeKey(size, i);
        vals.remove(key);
      } // for i

      // Verify that they are removed and nothing else is
      for (int i = size; i >= 1; i--) {
        String key = rebelskySamuelComputeKey(size, i);
        if (i % 3 == 1) {
          assertFalse(vals.hasKey(key), String.format("hasKey(%s)", key));
        } else {
          assertTrue(vals.hasKey(key), String.format("hasKey(%s)", key));
        } // if/else
      } // for i

      // Change all of the elements
      for (int i = 1; i <= size; i++) {
        String key = rebelskySamuelComputeKey(size, i);
        BigInteger val = rebelskySamuelComputeVal(size, i, -3);
        try {
          vals.set(key, val);
        } catch (Exception e) {
          fail(String.format("vals.set(key, val) FAILED", key, val.toString()));
        } // try/catch
      } // for

      // Verify that they've changed.
      for (int i = 1; i <= size; i++) {
        String key = rebelskySamuelComputeKey(size, i);
        BigInteger val = rebelskySamuelComputeVal(size, i, -3);
        try {
          assertEquals(val, vals.get(key), String.format("vals.get(%s)", key));
        } catch (Exception e) {
          fail(String.format("vals.get(key) FAILED", key));
        } // try/catch
      } // for

      // Remove them all
      for (int i = size; i >= 1; i--) {
        String key = rebelskySamuelComputeKey(size, i);
        vals.remove(key);
      } // for i

      // It should be empty again
      assertEquals(0, vals.size(), 
          String.format("Array of former size %s is now empty", size));

    } // for size
  } // rebelskySamuelCrazy01()

  // ================================================================

  // +------------------------------+--------------------------------
  // | Tests by Alexander, Princess |
  // +------------------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Bakrac, Mina |
  // +-----------------------+

  // +----------------------------+----------------------------------
  // | Tests by Bat-Erdene, Maral |
  // +----------------------------+

  // +---------------------+-----------------------------------------
  // | Tests by Bell, Jake |
  // +---------------------+
  /**
   * Do we get an element after cloning an array then removing that element?
   * 
   * @throws NullKeyException
   */
  @Test
  public void bellJakeTest01() throws NullKeyException {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("John", "Pork");
    AssociativeArray<String, String> cloneArr = arr.clone();
    cloneArr.remove("John");
    try {
      // The following line should throw an exception
      cloneArr.get("John");
      fail("Failed bellJakeTest01");
    } catch (KeyNotFoundException e) {
      // Do nothing
    } // try/catch
    try {
      arr.get("John");
    } catch (KeyNotFoundException e) {
      fail("Removal from clone removes from original, bellJakeTest01");
    } // try/catch
  } // bellJakeEdge01

  /**
   * Does the array handle multiple changes, then is able to remove?
   * 
   * @throws NullKeyExceptions
   */
  @Test
  public void bellJakeTest02() throws NullKeyException {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("Baby", "Keem");
    try {
      assertEquals("Keem", arr.get("Baby"));
    } catch (Exception e) {
      fail("Array does not contain expected value bellJakeTest02");
    }
    arr.set("Baby", "Lil");
    try {
      assertEquals("Lil", arr.get("Baby"));
    } catch (Exception e) {
      fail("Array does not contain expected value bellJakeTest02");
    }
    arr.set("Baby", "Shark");
    try {
      assertEquals("Shark", arr.get("Baby"));
    } catch (Exception e) {
      fail("Array does not contain expected value bellJakeTest02");
    } // try/catch
    arr.remove("Baby");

    try {
      // The following line should throw an exception
      arr.get("Baby");
      fail("Failed bellJakeTest02");
    } catch (KeyNotFoundException e) {
      // Do nothing
    } // try/catch
  } // bellJakeTest02

  /**
   * Does a cloned array expand if the original array is greater than 16.
   * 
   * @throws NullKeyException
   */
  @Test
  public void bellJakeEdge01() throws NullKeyException {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    for (int i = 1; i < 20; i++) {
      arr.set("key" + i, "val" + i);
    } // for
    AssociativeArray<String, String> clonedArr = arr.clone();
    try {
      for (int i = 19; i >= 1; i--) {
        assertEquals("val" + i, clonedArr.get("key" + i));
      } // for
    } catch (Exception e) {
      fail("Exception in call to get bellJakeEdge01");
    } // try/catch
  } // bellJakeEdge01

  // +--------------------------+------------------------------------
  // | Tests by Blanchard, Lily |
  // +--------------------------+

  /**
   * Test that set(), size(), and get(K) return the appropriate values.
   * 
   * @throws NullKeyException
   */
  @Test
  public void blanchardLilyTest01() throws NullKeyException {
    AssociativeArray<String, BigInteger> arrBl = new AssociativeArray<String, BigInteger>();

    try {
      arrBl.set("one", new BigInteger("1"));
      arrBl.set("two", new BigInteger("2"));
      arrBl.set("thirteen", new BigInteger("13"));
      arrBl.set("eight", new BigInteger("8"));
    } catch (Exception e) {
      fail("blanchardLily01: Fail to set array values.");
    } // try/catch

    try {
      assertEquals(4, arrBl.size(), "size of array");
      arrBl.set("six", new BigInteger("6"));
      assertEquals(5, arrBl.size(), "size with new element");
    } catch (Exception e) {
      fail("blanchardLily01: Fail to return size.");
    } // try/catch

    try {
      assertEquals(BigInteger.valueOf(13), arrBl.get("thirteen"), "get third element");
      assertEquals(BigInteger.valueOf(1), arrBl.get("one"), "get first element");
      arrBl.set("seven", new BigInteger("7"));
      assertEquals(BigInteger.valueOf(7), arrBl.get("seven"), "get newest value");
    } catch (Exception e) {
      fail("blanchardLily01: Fail to get.");
    } // try/catch
    return;
  } // blanchardLilyTest01()

  /**
   * Test that modifying a cloned array doesn't affect the priginal.
   * 
   * @throws NullKeyException
   */
  @Test
  public void blanchardLilyTest02() throws NullKeyException {
    AssociativeArray<String, String> arrBl = new AssociativeArray<String, String>();

    // set some starter values
    for (int i = 0; i < 10; i++) {
      arrBl.set("key" + i, "" + i);
    } // for

    AssociativeArray<String, String> arrLb = arrBl.clone();
    // set more values in new array
    for (int i = 10; i < 5; i++) {
      arrLb.set("key" + i, "" + i);
    } // for

    assertEquals(10, arrBl.size(), "size of original");
    assertEquals(10, arrLb.size(), "size of clone");

    try {
      arrBl.get("key13");
      fail("blanchardLily02: Inappropriately got something with an invalid key.");
    } catch (Exception KeyNotFoundException) {
    } // try/catch

    return;
  } // blanchardLilyTest02()

  /**
   * Edge: Test that using a null key will not set a value pair.
   * 
   * @throws NullKeyException
   */
  @Test
  public void blanchardLilyEdge01() {
    AssociativeArray<String, String> arrBl = new AssociativeArray<String, String>();

    try {
      arrBl.set(null, "hello");
      fail("blanchardLilyEdge: No exception on setting null key.");
    } catch (Exception NullKeyException) {
    } // try/catch

    return;
  } // blanchardLilyedge01()

  // +--------------------------------+------------------------------
  // | Tests by Bohrer-Purnell, Myles |
  // +--------------------------------+

  // +-------------------------------+-------------------------------
  // | Tests by Castleberry, Anthony |
  // +-------------------------------+
  /**
   * Do the methods remove() and set() alter the size field when neccesary.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void castleberryAnthonyTest1() throws Exception {
    AssociativeArray<String, Integer> testArrayOne = new AssociativeArray<String, Integer>();
    int expectedSize = 0;
    assertEquals(expectedSize, testArrayOne.size());
    expectedSize++;
    try {
      testArrayOne.set("1", 1);
      assertEquals(expectedSize, testArrayOne.size());
    } catch (Exception e) {
      fail("set threw an exception");
    } // try/catch
    try {
      testArrayOne.set("1", 2);
      assertEquals(expectedSize, testArrayOne.size());
    } catch (Exception e) {
        fail("set threw an exception");
    } // try/catch
    expectedSize--;
    try {
      testArrayOne.remove("1");
      assertEquals(expectedSize, testArrayOne.size());
    } catch (Exception e) {
      fail("remove threw an exception");
    } // try/catch
    try {
      testArrayOne.remove("1");
      assertEquals(expectedSize, testArrayOne.size());
    } catch (Exception e) {
      fail("remove threw an exception");
    } // try/catch
   } // castleberryAnthonyTest1()

  /**
   * Do the methods throw the appropiate exceptions.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void castleberryAnthonyTest2() throws Exception {
    AssociativeArray<String, Integer> testArrayTwo = new AssociativeArray<String, Integer>();

    // does set throw a NullKeyException?
    try {
      testArrayTwo.set(null, 2);
      fail("Exception was not thrown with null key");
    } catch (NullKeyException e) {
      assertEquals(1, 1);
    } // try/catch

    // does get throw a KeyNotFoundException?
    try {
      testArrayTwo.get("key does not exist");
      fail("get did not report that Key did not exist");
    } catch (KeyNotFoundException e) {
      assertEquals(1, 1);
    } // try/catch
  } // castleberryAnthonyTest2()

  /**
   * does get throw a KeyNotFoundException when null is input
   * 
   * @throws KeyNotFoundException
   */
  @Test
  public void castleberryAnthonyEdge1() throws Exception {
    AssociativeArray<Integer, Integer> edgeArray =  new AssociativeArray<Integer, Integer>();
    try {
      edgeArray.get(null);
      fail("KeyNotFoundException not thrown when null key input");
    } catch (KeyNotFoundException e) {
      assertEquals(1,1);
    } // try/catch
  } // castleberryAnthonyEdge1() 
  // +------------------------+--------------------------------------
  // | Tests by Cyphers, Alex |
  // +------------------------+

  // +---------------------------+-----------------------------------
  // | Tests by Deschamps, Sarah |
  // +---------------------------+

  /**
   * Checks whether adding a new value with the same key replaces the original value.
   * @throws Exception
   */
  @Test
  public void deschampsSarahTest1() throws Exception {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Add a bunch of values
    arr.set("The", "Cranberries");
    arr.set("Gillian", "Hills");
    arr.set("Elton", "John");
    try {
      assertEquals("Cranberries", arr.get("The"));
      assertEquals("Hills", arr.get("Gillian"));
      assertEquals("John", arr.get("Elton"));
    } catch (Exception e) {
      fail("Array does not contain expected value deschampsSarahTest1");
    } // try-catch
    arr.set("The", "Bangles");
    try {
      assertEquals("Bangles", arr.get("The"));
    } catch (Exception e) {
      fail("Array does not contain expected value deschampsSarahTest1");
    }
  } // deschampsSarahTest1()

  /**
   * When replacing a value, it does not just add a new value.
   * @throws Exception
   */
  @Test
  public void deschampsSarahTest2() throws Exception {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Add a bunch of values
    arr.set("The", "Cranberries");
    arr.set("Gillian", "Hills");
    arr.set("Elton", "John");
    arr.set("The", "Bangles");
    try {
      assertEquals(3, arr.size());
    } catch (Exception e) {
      fail("Array is not expected length deschampsSarahTest2");
    }
  } // deschampsSarahTest2()

  /**
   * Remove changes the size of the array instead of setting each value to null or empty.
   * @throws Exception
   */
  @Test
  public void deschampsSarahEdge1() throws Exception {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Add a bunch of values
    arr.set("The", "Cranberries");
    arr.set("Gillian", "Hills");
    arr.set("Elton", "John");
    arr.remove("The");
    try {
      assertEquals(2, arr.size());
    } catch (Exception e) {
      fail("Remove does not change length deschampsSarahEdge1");
    }
  } // deschampsSarahEdge1()

  // +--------------------+------------------------------------------
  // | Tests by Do, Khanh |
  // +--------------------+

 /**
   * Test use case: making a student id book.
   *
   * @throws NullKeyException
   */
  @Test
  public void khanhDoTest1() throws NullKeyException {
    AssociativeArray<String, UUID> studentIds = new AssociativeArray<>();
    UUID[] ids = new UUID[30];
    int classSize = 30;
    for (int i = 0; i < classSize; i++) {
      ids[i] = UUID.randomUUID();
      studentIds.set("Student " + i, ids[i]);
    } // for
    assertEquals(30, studentIds.size());
    for (int i = 0; i < classSize; i++) {
      try {
        assertEquals(ids[i], studentIds.get("Student " + i));
      } catch (KeyNotFoundException e) {
        fail("Failed to get student ID. Message: " + e.getMessage());
      } // try/catch
    } // for
  } // khanhDoTest1()

  /**
   * Setting and getting an object as the key should not throw.
   *
   * @throws NullKeyException
   */
  @Test
  public void khanhDoTest2() throws NullKeyException {
    AssociativeArray<Object, String> arr = new AssociativeArray<>();
    Object object = new Object();
    String value = "Object key";
    arr.set(object, value);
    try {
      assertTrue(arr.hasKey(object), "Array should have object key.");
      assertEquals(value, arr.get(object));
    } catch (KeyNotFoundException e) {
      fail("Failed to get object key. Message: " + e.getMessage());
    } // try/catch
  } // khanhDoTest2()

  /**
   * Setting, getting, and removing an empty string key should not throw.
   * 
   * @throws NullKeyException
   */
  @Test
  public void khanhDoEdge1() throws NullKeyException {
    // making the list
    AssociativeArray<String, String> arr = new AssociativeArray<>();
    String key = "";
    String value = "Empty string key";

    // setting empty string as key
    try {
      arr.set(key, value);
    } catch (NullKeyException e) {
      fail("Failed to set empty string key. Message: " + e.getMessage());
    } // try/catch

    // getting empty string key
    try {
      assertEquals(value, arr.get(key), "getting with empty string");
    } catch (KeyNotFoundException e) {
      fail("Failed to get empty string key. Message: " + e.getMessage());
    } // try/catch

    // checking if array has empty string key
    assertTrue(arr.hasKey(key), "Array should have empty string key.");

    // removing empty string key
    arr.remove(key);
    assertFalse(arr.hasKey(key), "Array should not have empty string key anymore.");
  } // khanhDoEdge1()

  // +----------------------+----------------------------------------
  // | Tests by Fargo, Drew |
  // +----------------------+

  /**
   * Does size() work correctly after overwriting an element?
   */
  @Test
  public void fargoAndrewTest01() throws NullKeyException {
    AssociativeArray<String, String> names = new AssociativeArray<String, String>();
    names.set("Andrew", "Fargo");
    names.set("Samuel", "Rebelsky");
    names.set("Garikai", "Gijima");
    assertEquals(3, names.size(), "fargoAndrew: size() works as expected.");
    names.set("Andrew", "Goes Far");
    assertEquals(3, names.size(), "fargoAndrew: size() works on overwrite.");
  } // fargoAndrewTest01

  /**
   * Does size() work correctly after removing elements?
   */
  @Test
  public void fargoAndrewTest02() throws NullKeyException {
    AssociativeArray<String, String> names = new AssociativeArray<String, String>();
    names.set("Andrew", "Fargo");
    names.set("Samuel", "Rebelsky");
    names.set("Garikai", "Gijima");
    assertEquals(3, names.size(), "fargoAndrew: size() works as expected.");
    names.remove("Andrew");
    names.remove("Samuel");
    assertEquals(1, names.size(), "fargoAndrew: size() decrements on remove.");
    names.remove("NaNcy");
    assertEquals(1, names.size(), "fargoAndrew: size() does not change if nonexistent.");
    names.remove("Garikai");
    assertEquals(0, names.size(), "fargoAndrew: size() decrements to zero.");
    names.remove("NaNcy");
    assertEquals(0, names.size(), "fargoAndrew: size() does not go negative.");
  } // fargoAndrewTest02

  /**
   * Does toString() work with null elements? Since this is not specified in the assignment, just
   * tests if an exception is returned.
   */
  @Test
  public void fargoAndrewEdge01() throws NullKeyException {
    AssociativeArray<String, Object> arr = new AssociativeArray<String, Object>();
    arr.set("I am null", null);
    try {
      String out = arr.toString();
      arr.set("It worked?", out); // notice `out` may be null
    } catch (Exception e) {
      fail("fargoAndrew: Null element returned exception in toString()");
    }
  } // fargoAndrewEdge01

  // +--------------------------+------------------------------------
  // | Tests by Gijima, Garikai |
  // +--------------------------+
  /**
   * Can we successfully set the same key to multiple values and get the last one?
   */
  @Test
  public void garikaiGijimaTest01() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();

    // Add a bunch of values
    for (int i = 0; i < 150; i++) {
      try {
        arr.set(5, i * i);
      } catch (Exception e) {
        fail("Failed to set 5 to " + i * i);
      } // try/catch
    } // for

    try {
      assertEquals(149 * 149, arr.get(5));
    } catch (KeyNotFoundException e) {
      fail("Failed to set to 5");
    }
  } // garikaiGijimaTest01()

  /**
   * Set multiple keys to the same value and return them
   */
  @Test
  public void garikaiGijimaTest02() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // Add a bunch of values
    for (int i = 0; i < 150; i++) {
      try {
        arr.set(i, 5);
      } catch (Exception e) {
        fail("Failed to set " + i + " to 5");
      } // try/catch
    } // for
    for (int i = 0; i < 150; i++) {
      try {
        assertEquals(5, arr.get(i), "Element " + i + " is not 5");
      } catch (Exception e) {
        fail("get(" + i + ") failed");
      } // try catch
    } // for
  } // garikaiGijimaTest02()

  /**
   * Clone an array
   */
  @Test
  public void garikaiGijimaTest03() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // Add a bunch of values
    for (int i = 0; i < 150; i++) {
      try {
        arr.set(i, i * i);
      } catch (Exception e) {
        fail("Could not call arr.set(" + i + ", " + i * i + ")");
      } // try/catch
    } // for

    // make sure size is as intended
    assertEquals(150, arr.size());


    // make clone and check clone size
    AssociativeArray<Integer, Integer> arrClone = arr.clone();
    assertEquals(arr.size(), arrClone.size());

    // check all values to make sure they match
    for (int i = 149; i > -1; i--) {
      try {
        assertEquals(arr.get(i), arrClone.get(i), "Comparing elements with key " + i);
      } catch (Exception e) {
        fail("Failed to get element " + i + " from one of the dicts.");
      } // try/catch
    } // for
  } // garikaiGijimaTest03()

  /**
   * Can we set a key to have a value of null and retrieve it?
   */
  @Test
  public void garikaiGijimaEdge01() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    try {
      arr.set(1, null);
    } catch (Exception e) {
      fail("Could not call arr.set(1, null)");
    } // try/catch
    try {
      assertEquals(null, arr.get(1), "Comparing element 1 to null");
    } catch (KeyNotFoundException e) {
      fail("Failed to get the value for 1");
    } // try/catch
  } // garikaiGijimaEdge01()

  /**
   * Does an invalid get/set throw exceptions?
   */
  @Test
  public void garikaiGijimaEdge02() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    assertThrows(Exception.class, () -> arr.get(5), "Get from empty array");
    assertThrows(Exception.class, () -> arr.set(null, 6), "Null key");
  } // garikaiGijimaEdge02()

  // +-----------------------+---------------------------------------
  // | Tests by Goldman, Leo |
  // +-----------------------+

  /**
   * Check general functionality of size(), hasKey(), set(), get(), and if AA expands properly.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void goldmanLeoTest1() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, Integer> arr = new AssociativeArray<String, Integer>();
    for (int i = 0; i < 17; i++) {
      String str = new String("" + i);
      arr.set(str, i);
      assertEquals(i + 1, arr.size()); // check if size is accurate
      assertEquals(true, arr.hasKey(str)); // check if key exists
      assertEquals(i, arr.get(str)); // check if key contains correct value
    } // add 17 entries to arr

    for (int i = 0; i < 17; i++) {
      String str = new String("" + i);
      arr.remove(str);
      assertEquals(16 - i, arr.size()); // check if size is accurate
      assertEquals(false, arr.hasKey(str)); // check if key does not exist
      try {
        arr.get(str); // check if getting the key fails
        fail("Did not throw an exception");
      } catch (Exception e) {
        // Do nothing
      }
    } // remove 17 entries from arr

  } // Test1

  /**
   * Check if clone() creates a functionally identical copy.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void goldmanLeoTest2() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    for (int i = 0; i < 17; i++) {
      arr.set(i, i);
    } // add 17 entries to arr
    AssociativeArray<Integer, Integer> newArr = arr.clone();
    assertEquals(arr.size(), newArr.size()); // check if sizes are equal
    for (int i = 0; i < arr.size(); i++) {
      assertEquals(arr.get(i), newArr.get(i));
    } // check if entries are equal
  } // Test2

  // @Test
  // public void goldmanLeoEdge1() throws NullKeyException, KeyNotFoundException {
  // AssociativeArray<Integer[], String> arr = new AssociativeArray<Integer[], String>();
  // Integer[] intArray = new Integer[1];
  // arr.set(intArray, null);
  // arr.get(intArray);

  // }

  // +--------------------------+------------------------------------
  // | Tests by Gorrell, Nicole |
  // +--------------------------+

  /**
   * Should we be able to get anything from a null key?
   *
   * Sam says "No".
   *
   * @throws KeyNotFoundException
   */
  public void gorrellNicoleEdge01() throws KeyNotFoundException {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    try{
      arr.set(null, null);
    } catch (Exception e) {
      fail("Could not set a key/value pair to null/null");
    }

    try {
      arr.get(null); // remove method has void return
    } catch (KeyNotFoundException e) {
      fail("Key does not exist in the array");
    } // try/catch
  } // gorrellNicoleEdge01()

  /**
   * Can we replace a previously set value?
   * @throws KeyNotFoundException
   * @throws NullKeyException
   */
  @Test
  public void gorrellNicoleTest01() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<Integer, String> test1 = new AssociativeArray<Integer, String>();

    test1.set(1, "Cheese wheel"); // first set

    assertEquals(true, test1.hasKey(1), "Ensure that first set works"); // make sure there is a key
    // fail("Failed to set first key");
    
    assertEquals("Cheese wheel", test1.get(1), "Ensure that we can get the key we just set"); // make sure there is a value with given key
    // fail("There is no value associated with this key");

    test1.set(1, "Grapes"); // override the value of the first set

    assertEquals("Grapes", test1.get(1), "Ensure that we can get updated value"); // make sure key 1 value got overriden
    // fail("Failed to get new value for this key");

  } // gorrellNicoleTest01()

  /**
   * Assessing general functionality of methods with a grocery list.
   * @throws KeyNotFoundException
   * @throws NullKeyException
   */
  @Test
  public void gorrellNicoleTest02() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<Integer, String> groceryList = new AssociativeArray<Integer, String>();

    // grouping foods in an array to facilitate setting
    String[] food = new String[] {"gala apples", "cinammon", "sugar", "flour", "pie crust", "milk"};
    
    // setting the whole list in one loop
    for(int i = 0; i < food.length; i++) {
      groceryList.set(i + 1, food[i]);

      assertEquals(true, groceryList.hasKey(i + 1), "must have key " + (i+1)); // ensure the set key exists
      // fail("Key has not been set");


      assertEquals(food[i], groceryList.get(i + 1)); // ensure key has the item corresponding to food list
      // fail("There is no item for this key");
    } // for

    // I got the middle thing in the list
    groceryList.remove(food.length / 2);
    
    // I copy the list for my friend
    AssociativeArray<Integer, String> listCopy = groceryList.clone();

    assertEquals(listCopy.size(), groceryList.size()); // ensuring that the new and original lists are the same size
    // fail("Lists do not contain the same amount of key/value pairs");
  } // gorrellNicoleTest02

  // +-----------------------+---------------------------------------
  // | Tests by Houck, Paden |
  // +-----------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Jaljaa, Sara |
  // +-----------------------+

  // +------------------------+--------------------------------------
  // | Tests by Jarrar, Jafar |
  // +------------------------+

  // +-------------------------+-------------------------------------
  // | Tests by Johnston, Cade |
  // +-------------------------+

  // As we have the 'TestsFromSam.java' file, general functionality tests are not fully necessary.

  /**
   * Does using set with a key already in the array replace the value for that key?
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void johnstonCadeTest1() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<Integer, String> miniProjects = new AssociativeArray<Integer, String>();
    String[] miniProjectNames = new String[]{"Classical encryption", "Fun with Fractions", "Exploring blocks of ASCII", "Associative arrays", "Augmentive and Alternative Communication Devices"};
    // Fill an AssociativeArray with unique elements.
    for (int i = 0; i < miniProjectNames.length; i++) {
      miniProjects.set(i+1, miniProjectNames[i]);
    } // for [i]
    // Ensure all of the elements were added to the array.
    for (int i = 0; i < miniProjectNames.length; i++) {
      assertEquals(miniProjectNames[i],miniProjects.get(i+1));
    } // for [i]
    String[] simplifiedNames = new String[]{"Encryption", "Calculator", "ASCII blocks", "Hash maps", "AAC"};
    // Replace the values of each of those elements.
    for (int i = 0; i < simplifiedNames.length; i++) {
      miniProjects.set(i+1, simplifiedNames[i]);
    } // for [i]
    // Ensure all of the values made it into the array.
    for (int i = 0; i < simplifiedNames.length; i++) {
      assertEquals(simplifiedNames[i],miniProjects.get(i+1));
    } // for [i]
    // Check that the length of the array is equal to the count of its original elements.
    assertEquals(miniProjectNames.length, miniProjects.size());
  } // johnstonCadeTest1()

  /**
   * Are the correct errors thrown in the correct circumstances?
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void johnstonCadeTest2() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<String,String> abbottCostello = new AssociativeArray<String,String>();
    String[] positions = new String[]{"First Base","Second Base", "Third Base", null};
    String[] names = new String[]{"Who", "What", "I Don't Know", "Today"};
    // Test for 'NullKeyException' expected from set.
    try {
      for (int i = 0 ; i < positions.length; i++) {
        abbottCostello.set(positions[i],names[i]);
      } // for [i]
      // If the exception has not been thrown yet, this test fails.
      fail("No 'NullKeyException' thrown by set().");
    } catch (NullKeyException e) {
      // Assuming we got the 'NullKeyException', we will now test for the 'KeyNotFoundException'.
      try {
        abbottCostello.get(null);
/*
        for (int i = 0; i < positions.length; i++) {
          if (abbottCostello.get(positions[i]).equals(names[i])) {
            // Should we somehow get here, we have both added 'null' as a key and not thrown the exception.
            fail("Item with key 'null' added to array. No 'KeyNotFoundException' thrown by get().");
          } // if
        } // for [i]
 */
        // If the exception has not been thrown yet, this test fails.
        fail("No 'KeyNotFoundException' thrown by get().");
      } catch (KeyNotFoundException knf) {
        // If we get here, do nothing as all tests were passed.
      } // try / catch
    } // try / catch
  } // johnstonCadeTest2()

  /**
   * Can an AssociatedArray be reduced to zero elements?
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void johnstonCadeEdge1() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<String, Integer> paramCount = new AssociativeArray<String, Integer>();
    String[] methods = new String[]{"set", "get", "hasKey", "remove", "size", "toString", "clone"};
    Integer[] count = new Integer[]{2, 1, 1, 1, 0, 0, 0};
    // Fill the AssociativeArray with values.
    for (int i = 0; i < methods.length; i++) {
      paramCount.set(methods[i],count[i]);
    } // for [i]
    // We will try to empty the AssociativeArray of values.
    for (int i = 0; i < methods.length; i++) {
      paramCount.remove(methods[i]);
    } // for [i]
    // We will now check the size of the Associative array.
    try {
      if(paramCount.size() > 0) {
        fail("AssociativeArray is not empty when it should be.");
      } // if
      // If the size is zero, this test passes.
    // Should a NullPointerException be thrown because 'pairs' is a null pointer...
    } catch (NullPointerException e) {
      // Do nothing, as an implementation that reduces the array to a null pointer has technically emptied the array.
    } // try / catch
  } // johnstonCadeEdge1()

  // +---------------------+-----------------------------------------
  // | Tests by Karki, Sal |
  // +---------------------+

  // +------------------+--------------------------------------------
  // | Tests by Kim, SJ |
  // +------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Lin, Richard |
  // +-----------------------+

  /**
   * A simple test to ensure that the clone method returns a copy and not a reference to the array.
   *
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void linRichardTest1() throws NullKeyException, KeyNotFoundException{
    AssociativeArray<String, String> original = new AssociativeArray<String, String>();

    original.set("hello", "bye");
    original.set("friend", "enemy");
    original.set("good","bad");

    AssociativeArray<String, String> copy = original.clone();

    original.set("hello", "not hello");
    copy.set("hello", "look up");

    assertEquals(false, original.get("hello").compareTo(copy.get("hello")) == 0,
      "Checks to see that clone creates a new array and does not just pass a refrence to the original array");
  }

  /**
   * A simple test for both remove and hasKey.
   *
   * @throws NullKeyException
   */
  @Test
  public void linRichardTest2() throws NullKeyException{
    AssociativeArray<String, String> original = new AssociativeArray<String, String>();

    original.set("first key", "is present");

    boolean previousCheck = original.hasKey("first key");

    original.remove("first key");

    boolean alteredCheck = original.hasKey("first key");

    assertEquals(true, (previousCheck == true) && (alteredCheck == false),
      "Checks that remove and hasKey work properly by checking hasKey before and after remove is called on the same key");
  }

  /**
   * Checks that two arrays, although they contain the same elements, are considered unique and store thus have separate entries,
   * and, because of that, these entires can be accessed using a pointer that references the specific arrays.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void linRichardEdge1() throws NullKeyException, KeyNotFoundException{
    AssociativeArray <int[], String> storage = new AssociativeArray<int[], String>();

    int arr1[] = {1};
    int arr2[] = {1};

    storage.set(arr1, "hello");
    storage.set(arr2, "bye");

    boolean checkArrsDiff = (storage.get(arr1).compareTo(storage.get(arr2)) != 0);

    int[] arrReference = arr1;
    String strArr1 = (storage.get(arrReference));

    arrReference = arr2;
    String strArr2 = (storage.get(arrReference));

    assertEquals("hello", strArr1, "Set with key arr1");
    assertEquals("bye", strArr2, "Set with key arr2");

    // assertEquals(true, ((checkArrsDiff == false) && (strArr1.compareTo(strArr2) != 0)), 
    //   "Checks that two arrays with similar descriptions are stored as different enteries and that these entries can be referenced by a pointer to those arrays");

  }


  // +----------------------+----------------------------------------
  // | Tests by Lopez, Luis |
  // +----------------------+

  // +----------------------+----------------------------------------
  // | Tests by Malik, Yash |
  // +----------------------+

  // +---------------------------+-----------------------------------
  // | Tests by Manza, Sebastian |
  // +---------------------------+

  // +--------------------------+------------------------------------
  // | RTests by Milenge, Moise |
  // +--------------------------+

  // +------------------------+--------------------------------------
  // | Tests by Moreno, Nicky |
  // +------------------------+

  /**
   * A simple test for setting and getting values.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void morenoNickyTest01() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<String, String> map = new AssociativeArray<String, String>();
    map.set("Hello", "World");
    assertEquals("World", map.get("Hello"), "We can get what we just set.");
  } // morenoNickyTest01()

  /**
   * We can update a value with a duplicate key.
   * 
   * @throws NullKeyException
   */
  @Test
  public void morenoNickyTest02() throws NullKeyException {
    AssociativeArray<String, String> map = new AssociativeArray<String, String>();
    map.set("key", "value1");
    map.set("key", "value2");
    try {
      assertEquals("value2", map.get("key"), "Value should be updated to 'value2'.");
    } catch (Exception e) {
      fail("Unexpected exception");
    } // try/catch
    assertEquals(1, map.size(), "Size should remain 1 after updating duplicate key.");
  } // morenoNickyTest02()

  /**
   * Checks behavior when setting a null value for an existing key.
   * 
   * @throws NullKeyException
   */
  @Test
  public void morenoNickyEdge01() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, Integer> map = new AssociativeArray<String, Integer>();
    map.set("one", 1);
    map.set("one", null); // Updating existing key with null value
    assertNull(map.get("one"), " Value for key 'one' should be null after update.");
    assertTrue(map.hasKey("one"), " The key 'one' should still exist after setting null value.");
  } // morenoNickyEdge01()

  // +-----------------------------+---------------------------------
  // | Tests by Muligande, Sheilla |
  // +-----------------------------+

  // +---------------------------+-----------------------------------
  // | Tests by Nardone, Natalie |
  // +---------------------------+

  // +---------------------+-----------------------------------------
  // | Tests by Nunes, Leo |
  // +---------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Paiva, Mitch |
  // +-----------------------+

  /**
   * Tests if copying an array works properly.
   * 
   * @throws NullKeyException 
   * @throws KeyNotFoundException
   */
    @Test
    public void paivaMitchTest01() throws NullKeyException, KeyNotFoundException {
      AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();
      testArray.set("Grinnell", "College");
      AssociativeArray<String, String> testArrayClone = testArray.clone();
      assertEquals(1, testArrayClone.size(), "Cloned array Size not consistent with original array size.");
      assertEquals(false , (testArray == testArrayClone), "Cloned array should not be the same exact object.");
      assertEquals("College", (testArrayClone.get("Grinnell")), "Cloned array should have the same value as the original.");
      assertEquals(true, testArrayClone.hasKey("Grinnell"), "Cloned array does not have the same key like the original.");
    } //paivaMitchTest01

  /**
   * Tests if adding and removing works correctly for key type string and int.
   * 
   * @throws NullKeyException 
   * @throws KeyNotFoundException
   */
  @Test
  public void paivaMitchTest02() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();
    AssociativeArray<Integer, String> testArray2 = new AssociativeArray<Integer, String>();
    testArray.set("hello", "others");
    testArray2.set(1, "yep");
    assertEquals(true, testArray.hasKey("hello"), "String key not properly set.");
    assertEquals(true, testArray2.hasKey(1), "Integer key not properly set.");
    testArray.remove("hello");
    testArray2.remove(1);
    assertEquals(false, testArray.hasKey("hello"), "String key not properly removed after creation.");
    assertEquals(false, testArray2.hasKey(1), "Integer key not properly removed after creation.");
  } //paivaMitchTest02

  /**
   * Tests an empty array that has had nothing added to it.
   * 
   * @throws NullKeyException 
   * @throws KeyNotFoundException
   */
  @Test
  public void paivaMitchEdge01() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<Integer, String> testArray = new AssociativeArray<Integer, String>();
    AssociativeArray<Integer, String> copy = testArray.clone();
    assertEquals(0, testArray.size(), "Array should be empty.");
    assertEquals(false, testArray.hasKey(1), "An empty array should not have a key.");
    assertEquals(0, copy.size(), "Copying an empty array should result in an array that is empty.");
    assertEquals(false, copy.hasKey(1), "Copying an empty array should result in an array that does not have a key.");
  } //paivaMitchEdge01

  // +------------------------+--------------------------------------
  // | Tests by Pollock, Alex |
  // +------------------------+

  /**
   * Can we add an element, check that it's there, remove it and then check for it again.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void pollockAlexTest1() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<Integer, String> testArray = new AssociativeArray<Integer, String>();
    testArray.set(1, "Hello");
    assertEquals(true, testArray.hasKey(1), "Does the key '1' exist?");
    assertEquals("Hello", testArray.get(1), "What is at the key '1'?");
    testArray.remove(1);
    assertEquals(false, testArray.hasKey(1), "The key should no longer exist.");
    try {
      testArray.get(1);
      System.err.println("This key shouldn't exist.");
    } catch (KeyNotFoundException e) {
      // This is supposed to happen
    } // try/catch
  } // pollockAlexTest1()

  /**
   * Try removing non-existant elements. Then see if changing keys works as well as size. Then try
   * removing a valid element.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void pollockAlexTest2() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, String> testArray2 = new AssociativeArray<String, String>();
    testArray2.remove("Hello");
    assertEquals(0, testArray2.size(), "There should be nothing in the array.");
    testArray2.set("Hello", "World");
    testArray2.set("Hello", "Samuel");
    assertEquals("Samuel", testArray2.get("Hello"),
        "Test to see if 'set' can change the value of pairs.");
    assertEquals(1, testArray2.size());
    testArray2.remove("Hello");
    assertEquals(0, testArray2.size());
    try {
      testArray2.get("Hello");
      System.err.println("Error: Value should not exist");
    } catch (KeyNotFoundException e) {
      // Supposed to happen
    } // try/catch
  } // pollockAlexTest2()

  /**
   * Check if you can properly remove the only value in a list. Certain implementations will fail.
   * 
   * @throws NullKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void pollockAlexEdge1() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, String> edgeArray1 = new AssociativeArray<String, String>();
    edgeArray1.set("Edge", "Cases");
    edgeArray1.remove("Edge");
    try {
      edgeArray1.get("Edge");
      System.err.println("Error: Failed pollockAlexEdge1");
    } catch (KeyNotFoundException e) {
      // Supposed to happen
    } // try/catch
  } // pollockAlexEdge1()

  // +----------------------------+----------------------------------
  // | Tests by Rajbhandari, Slok |
  // +----------------------------+

  // +---------------------------+-----------------------------------
  // | Tests by Rodriguez, Maria |
  // +---------------------------+
  // test that set, get, and clone work
  @Test
  public void mariaRodriguezTest01() {
    AssociativeArray<String, String> test1 = new AssociativeArray<String, String>();
    AssociativeArray<String, String> clone = new AssociativeArray<String, String>();

    try {
      test1.set("a", "apple");
    } catch (NullKeyException e) {
      fail("Failed to set 'a' and 'apple'");
    }
    try {
      test1.set("b", "balloon");
    } catch (NullKeyException e) {
      fail("Failed to set 'b' and 'balloon'");
    }
    clone = test1.clone();
    try {
      clone.set("c", "cartoon");
    } catch (NullKeyException e) {
      fail("Failed to set 'c' and 'cartoon'");

    }

    try {
      assertEquals("cartoon", clone.get("c"));
      assertEquals("balloon", clone.get("b"));
      assertEquals("apple", clone.get("a"));

    } catch (KeyNotFoundException e) {
    }

    // the original array should not have a key that was added to the cloned array
    try {
      test1.get("c");
      fail("Did not throw an exception");
    } catch (Exception e) {
    }

  }// mariaRodriguezTest01

  // tests to see that associativeArray functions work as intended
  @Test
  public void mariaRodriguezTest02() {
    AssociativeArray<String, String> test2 = new AssociativeArray<String, String>();
    try {
      test2.set("a", "apple");
    } catch (NullKeyException e) {
      fail("Failed to set 'a' and 'apple'");
    }
    try {
      test2.set("b", "balloon");
    } catch (NullKeyException e) {
      fail("Failed to set 'b' and 'balloon'");
    }

    try {
      assertEquals("apple", test2.get("a"));
      assertEquals("balloon", test2.get("b"));
    } catch (KeyNotFoundException e) {
    }

    assertEquals(2, test2.size());
    assertEquals(true, test2.hasKey("b"));
    test2.remove("a");
    assertEquals(false, test2.hasKey("a"));
    assertEquals(1, test2.size());
  }// mariaRodriguezTest02

  // Tests if an empty list is properly empty
  @Test
  public void mariaRodriguezTest03() {
    AssociativeArray<String, String> test3 = new AssociativeArray<String, String>();

    try {
      test3.set("Hi", "bye");
    } catch (Exception e) {
      fail("Did not properly set");
    }
    try {
      test3.remove("Hi");
      assertEquals(0, test3.size());
    } catch (Exception e) {
      fail("That key does not exist");
    }

  }



  // tests to make sure the code treats " " and " " as two separate keys (tests
  // empty space)
  @Test
  public void mariaRodriguezEdge01() {
    AssociativeArray<String, String> edge = new AssociativeArray<String, String>();
    try {
      edge.set(" ", "apple");
    } catch (NullKeyException e) {
      fail("Did not set ' ' and 'apple'");
    }
    try {
      edge.set("  ", "balloon");
    } catch (NullKeyException e) {
      fail("Did not set '  ' and 'balloon'");

    }

    try {
      assertEquals("apple", edge.get(" "));
      assertEquals("balloon", edge.get("  "));
    } catch (KeyNotFoundException e) {

    }
  }// mariaRodriguezEdge01

  // +-----------------------+---------------------------------------
  // | Tests by Ryan, Alyssa |
  // +-----------------------+

  // +--------------------------+------------------------------------
  // | Tests by Sackmann, Grant |
  // +--------------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Schmidt, Sam |
  // +-----------------------+

  /**
   * Will test that set works with a fairly straightforward string.
   * @throws NullKeyException
   */
  public void schmidtSamTest01() throws NullKeyException {
    AssociativeArray<String, String> sampleArray = new AssociativeArray<String, String>();
    sampleArray.set("Sample", "Array");
    assertEquals("Array", sampleArray.hasKey("Sample"), "Simple setting of an array");
  } // schmidtSamTest01()

  /**
   * Tests that remove works with a fairly simple string.
   * @throws NullKeyException
   */
  public void schmidtSamTest02() throws NullKeyException {
    AssociativeArray<String, String> sampleArray = new AssociativeArray<String, String>();
    sampleArray.set("Sample", "Array");
    assertEquals("Array", sampleArray.hasKey("Sample"), 
    "Did it set the array?");
    sampleArray.remove("Sample");
    assertFalse(sampleArray.hasKey("Sample"), 
    "Did it remove a key?");
  } // schmidtsSamTest02()

  /**
   * Tests how set and get handle special characters like newline.
   * @throws NullKeyException
   * @throws KeyNotFoundException 
   */
  public void schmidtSamEdge01() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<String, String> sampleArray = new AssociativeArray<String, String>();
    sampleArray.set("/nhi/n", "/nthere/n");
    assertEquals("/nthere/n", sampleArray.get("/n/hi/n"), 
    "Do the newlines hinder the ability to get a value?");
  } // schmidtSamEdge01()

  // +-----------------------+---------------------------------------
  // | Tests by Sheeley, Ben |
  // +-----------------------+

  // +-------------------------+-------------------------------------
  // | Tests by Silva, Jenifer |
  // +-------------------------+

  // +------------------------+--------------------------------------
  // | Tests by Stroud, David |
  // +------------------------+

  /**
   * This test checks whether the .remove method works as expected and does not cause issues with
   * the internal state of the object.
   *
   * @throws NullKeyException This exception is thrown if any of the keys used are null. This should
   *         not happen.
   * @throws KeyNotFoundException This exception is thrown if one of the keys used is missing. This
   *         indicates a failing test.
   */
  @Test
  public void stroudDavidTestRemove() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<String, String> assocArr = new AssociativeArray<>();

    assocArr.set("hello", "world");
    assocArr.set("good", "morning");
    assocArr.set("amazing", "afternoon");
    assocArr.set("frabjous", "day");

    assocArr.remove("amazing");
    assertEquals("day", assocArr.get("frabjous"),
        "Key 'frabjous' should not be affected by removal of 'amazing'");
    assertEquals("morning", assocArr.get("good"),
        "Key 'good' should not be affected by removal of 'amazing'");

    assocArr.set("good", "evening");
    assertEquals("evening", assocArr.get("good"),
        "Setting 'good' should work after removal of 'amazing'");

    assocArr.set("frabjous", "the jabberwocky");
    assertEquals("the jabberwocky", assocArr.get("frabjous"),
        "Setting 'frabjous' should work after removal of 'amazing'");

    assocArr.remove("good");
    assertFalse(assocArr.hasKey("good"),
        "Removing 'good' should continue to work after removing 'amazing' and setting 'good'");
  } // stroudDavidTestRemove()

  /**
   * This test checks whether toString displays both keys and values.
   *
   * Sam notes that in order to pass this test, you may need to fetch from upstream to get an
   * updated version of KVPair.
   *
   * @throws NullKeyException This exception is thrown if any of the keys used are null. This should
   *         not happen.
   */
  @Test
  public void stroudDavidTestToString() throws NullKeyException {
    AssociativeArray<String, String> assocArr = new AssociativeArray<>();

    String[] literals = new String[] {"frabjous1337-2448", "the-jAbBeRwoCKy!!!_3559-4660",
        "The-Key HAS SPACES 4nd NumB3rS", "key(value, \"result\")", "/VALUE/=null"};
    assocArr.set(literals[0], literals[1]);
    assocArr.set(literals[2], literals[3]);
    assocArr.set("I will never", "see the light of day.");
    assocArr.set(literals[4], null);

    assocArr.remove("I will never");

    String stringified = assocArr.toString();
    for (String literal : literals) {
      assertTrue(stringified.contains(literal),
          "String '" + literal + "' should be contained in .toString() result");
    } // for
  } // stroudDavidTestToString()

  /**
   * This test checks whether the associative array is expandable.
   * 
   * @throws NullKeyException This exception is thrown if any of the keys used are null. This should
   *         not happen.
   * @throws KeyNotFoundException This exception is thrown if any of the keys used do not exist.
   *         This indicates a failing test.
   */
  @Test
  public void stroudDavidTestExpandability() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<Integer, Integer> assocArr = new AssociativeArray<>();

    for (int i = 0; i < 1337; i++) {
      assocArr.set(Integer.valueOf(i), Integer.valueOf(i + 1));
    } // for

    for (int j = 0; j < 1337; j++) {
      assertTrue(assocArr.hasKey(Integer.valueOf(j)),
          "Key '" + j + "' should exist in the associative array");
      assertEquals(j + 1, assocArr.get(Integer.valueOf(j)),
          "Key '" + j + "' should contain value '" + (j + 1) + "'");
    } // for
  } // stroudDavidTestExpandability()

  // +----------------------+----------------------------------------
  // | Tests by Tang, Kevin |
  // +----------------------+

  /**
   * Check general functionality of set() and hasKey().
   * 
   * @throws NullKeyException
   */
  @Test
  public void kevinTangTest1() throws NullKeyException {
    AssociativeArray<String, Integer> testArray = new AssociativeArray<String, Integer>();
    testArray.set("mango", 1);
    assertEquals(true, testArray.hasKey("mango"), "setted key should be found");
    assertEquals(false, testArray.hasKey("Mango"), "edge case of key");
    testArray.remove("mango");
    assertFalse(testArray.hasKey("Mango"), "removed key couldn't be found");
  } // kevinTangTest1

  /**
   * Check general functionality of set() and hasKey().
   * 
   * @throws NullKeyException
   */
  @Test
  public void kevinTangTest2() throws NullKeyException {
    AssociativeArray<Character, Integer> testArray = new AssociativeArray<Character, Integer>();
    testArray.set('a', 65);
    assertTrue(testArray.hasKey('a'), "setted key should be found");
    assertFalse(testArray.hasKey('A'), "edge case of key");
    testArray.remove('a');
    assertFalse(testArray.hasKey('a'), "removed key couldn't be found");
  } // kevinTangTest2

  /**
   * Check general functionality of repeated set() and hasKey().
   * 
   * @throws NullKeyException
   */
  @Test
  public void kevinTangEdge() throws NullKeyException {
    AssociativeArray<Boolean, Integer> testArray = new AssociativeArray<Boolean, Integer>();
    testArray.set(true, 1);
    testArray.set(true, 1);
    assertTrue(testArray.hasKey(true), "setted key should be found");
    assertFalse(testArray.hasKey(false), "edge case of key");
    testArray.remove(true);
    assertFalse(testArray.hasKey(true), "removed key couldn't be found");
  } // kevinTangEdge

  // +------------------------+--------------------------------------
  // | Tests by Tang, Tiffany |
  // +------------------------+

  // +----------------------+----------------------------------------
  // | Tests by Trimble, AJ |
  // +----------------------+
  //

  /**
   * Checks if set, get, remove, and size work with normal input.
   * 
   * @throws NullKeyException
   */
  @Test
  public void trimbleAjTest1() throws NullKeyException {
    AssociativeArray<String, String> testAddGet = new AssociativeArray<String, String>();
    testAddGet.set("a", "Hang Loose");
    try {
      assertEquals("Hang Loose", testAddGet.get("a"));
    } catch (Exception e) {
      fail("Array does not contain expected value");
    } // try catch

    AssociativeArray<String, String> testSize = new AssociativeArray<String, String>();
    testSize.set("a", "Hang Loose");
    testSize.set("b", "Peace & Love");
    testSize.set("c", "Bad Vibes");
    testSize.remove("c");
    try {
      assertEquals(2, testSize.size());
    } catch (Exception e) {
      fail("Array does not have the expected length");
    } // try catch
  } // trimbleAjTest1()

  /**
   * Checks if set, get, and size work with many inputs.
   * 
   * @throws NullKeyException
   */
  @Test
  public void trimbleAjTest2() throws NullKeyException {
    AssociativeArray<String, String> testLooping = new AssociativeArray<String, String>();
    for (int i = 0; i < 100; i++) {
      testLooping.set(String.valueOf(i), "Hang Loose");
    } // for
    try {
      assertEquals(100, testLooping.size());
    } catch (Exception e) {
      fail("Array does not have the expected length (100)");
    } // try catch

    for (int i = 0; i < 50; i++) {
      testLooping.remove(String.valueOf(i));
    } // for
    try {
      assertEquals(50, testLooping.size());
    } catch (Exception e) {
      fail("Array does not have the expected length (50)");
    } // try catch

  } // trimbleAjTest2()

  /**
   * Checks if it can handle odd keys and inputs
   * 
   * @throws NullKeyException
   */
  @Test
  public void trimbleAjEdge1() throws NullKeyException {
    AssociativeArray<String, String> test = new AssociativeArray<String, String>();

    test.set("", "Hang Loose");
    try {
      assertEquals("Hang Loose", test.get(""));
    } catch (Exception e) {
      fail("Array does not contain expected value");
    } // try catch

    test.set("a", "");
    try {
      assertEquals("", test.get("a"));
    } catch (Exception e) {
      fail("Array does not contain expected value");
    } // try catch

    test.set("Hang Loose".substring(1, 2), "Hello World");
    try {
      assertEquals("Hello World", test.get("a"));
    } catch (Exception e) {
      fail("Array does not contain expected value");
    } // try catch

    test.set("a", "Hello World".substring(0, 5));
    try {
      assertEquals("Hello", test.get("a"));
    } catch (Exception e) {
      fail("Array does not contain expected value");
    } // try catch

  } // trimbleAjTest2()


  // +-------------------------+-------------------------------------
  // | Tests by Tsymbal, Koast |
  // +-------------------------+

  // +------------------------+--------------------------------------
  // | Tests by Vadillo, Jana |
  // +------------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Yan, Tiffany |
  // +-----------------------+
  /**
   * A test for the remove method. We will do this by first add a key-value pair, then remove it.
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
    } // try/catch
    // Test for remove, where we would expect the following line to throw an expection
    testarr.remove(1);
    try {
      testarr.get(1);
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    } // try/catch
  } // TiffanyYan01()

  /**
   * A test for the clone method. We will do this by first add a key-value pair to an initial
   * associate array. we will then clone the initial associate array. After that we will check if we
   * could find the key-value pair at the cloned array.
   * 
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
    } // try/catch
    AssociativeArray<String, Double> clonearr = testarr.clone();
    // Make sure that the cloned array has been set and have the same pair as the original
    try {
      assertEquals(3.1415926535, clonearr.get("Pi"));
    } catch (KeyNotFoundException e) {
      fail("Could not clone testarr to clonearr");
    } // try/catch
  } // TiffanyYan02()

  /**
   * A test expand method. We will check if the associate array will expand, given the number of key
   * value pair is greater than 16
   * 
   * @throws NullKeyException
   */
  @Test
  public void TiffanyYanEdge01() throws NullKeyException {
    AssociativeArray<Double, Double> testarr = new AssociativeArray<Double, Double>();
    for (int i = 0; i < 18; i++) {
      testarr.set(1.23 + i, 1.23 + i);
    } // for

    try {
      for (int i = 17; i >= 1; i--) {
        assertEquals(1.23 + i, testarr.get(1.23 + i));
      } // for
    } catch (Exception e) {
      fail("Expansion of the array failed");
    } // try/catch
  } // TiffanyYanEdge01()

  // +------------------------+--------------------------------------
  // | Tests by Yusuf, Bonsen |
  // +------------------------+

  // +------------------------+--------------------------------------
  // | Tests by Zhu, Harrison |
  // +------------------------+

  /**
   * Tests adding and deleting. Tests if toString handles null values.
   */
  @Test
  public void zhuHarrisonTestAddingDeleting() throws Exception {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("a", "apple");
    arr.set("b", null);
    try {
      arr.toString();
    } catch (Exception e) {
      fail("To string should not throw exception");
    } // try/catch toString

    arr.remove("b");
    try {
      // remove non-existed keys
      arr.remove("b");
    } catch (Exception e) {
      fail("remove should not throw exception");
    } // try/catch remove

    assertEquals(1, arr.size());

    arr.set("b", "HIII");
    assertEquals(2, arr.size());
    assertEquals("HIII", arr.get("b"));

  }

  /**
   * Test to see if associative arrays are correctly cloned
   * 
   * @throws Exception
   */
  @Test
  public void zhuHarrisonTestClone() throws Exception {
    AssociativeArray<Integer, Boolean> arr = new AssociativeArray<Integer, Boolean>();
    for (int i = 0; i < 128; ++i) {
      arr.set(Integer.valueOf(i), false);
    } // fill values
    assertEquals(128, arr.size());
    AssociativeArray<Integer, Boolean> arr1 = arr.clone();
    arr.set(1, true);
    assertEquals(128, arr1.size());
    // test cloning
    assertNotEquals(arr.get(1), arr1.get(1));
    arr.remove(1);
    assertNotEquals(arr.size(), arr1.size());
  }

  /**
   * Test to see if exceptions are thrown correctly.
   * 
   * @throws Exception
   */
  @Test
  public void zhuHarrisonTestExceptions() throws Exception {
    AssociativeArray<Integer, Boolean> arr = new AssociativeArray<Integer, Boolean>();
    for (int i = 0; i < 128; ++i) {
      arr.set(Integer.valueOf(i), false);
    } // add some values

    try {
      arr.get(129);
      fail("should throw KeyNotFoundException");
    } catch (KeyNotFoundException e) {

    } // try/catch

    try {
      arr.set(null, false);
      fail("should throw NullKeyException");
    } catch (NullKeyException e) {

    } // try/catch
  } // znuHarrisonTestExceptions()

} // class TestAssociativeArrays
