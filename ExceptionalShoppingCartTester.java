
//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P03 Exceptional Shopping Cart
// Course: CS 300 Spring 2022
//
// Author: Harshet Anand
// Email: hanand2@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////


/**
 * This class tests for the methods used in ExceptionalShoppingCart.java to check that all methods
 * run successfully and complete each task required. The methods tested here are saveCarySummary(),
 * loadCartSummary(), lookupMethods(), addItemToMarketCatalog() and will return true if all tests
 * have successfully passed when run in the runAllTests() method.
 * 
 * @author Harshet Anand
 */


import java.io.File;
import java.io.PrintWriter;
import java.util.NoSuchElementException;


public class ExceptionalShoppingCartTester {

  /**
   * The main method checks whether ShoppingCart.saveCartSummary(), and
   * ShoppingCart.loadCartSummary(), lookup methods, and ShoppingCart.addItemToMarketCatalog()
   * methods work as expected by running the runAllTests() method and printing out the result of
   * that method. In addition, exceptions will be caught for each tester methods. If all tests run
   * successfully, this will return true else it will return false with the failed method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("runAllTests(): " + runAllTests());
  }


  /**
   * Checks the correctness of all test methods works successfully by running all tests
   * 
   * @return true if all test methods pass else false if one or more tests fail printing out the
   *         test and the method which did not pass as well as the item that was used as input.
   */
  public static boolean runAllTests() {
    boolean allTestsPassed = testLookupMethods() && testAddItemToMarketCatalog()
        && testSaveCartSummary() && testLoadCartSummary();
    // Can only return true if all test cases return true. Will go back to main method to return
    // final result
    return allTestsPassed;
  }


  /**
   * Checks the correctness of the two methods lookupProductById() and lookupProductByName() by
   * running one scenario for lookupProductByName() and two scenarios for lookupProductById() with
   * different inputs.
   * 
   * @return true if all scenarios pass else false if one of the scenarios fails printing out the
   *         exception if the scenario is false.
   */
  public static boolean testLookupMethods() {
    // Tests the correctness of lookupProductByName().

    // (1) This scenario checks if the item is found and if not, throws the correct exception.
    try {
      String name = "Salt"; // Unknown item
      ExceptionalShoppingCart.lookupProductByName(name);
    } catch (NoSuchElementException e) {
    } catch (Exception e) {
      System.out.println(
          "Problem detected: Your lookupProductByName() method did not return the expected "
              + "exception when passed the identifier. " + e.getMessage());
      return false;
    }

    // Tests the correctness of lookupProductById().

    // (1) This scenario checks if the key is 4-digits and if not, throws the correct exception.
    try {
      int key = 9801; // Unknown key
      ExceptionalShoppingCart.lookupProductById(key);
    } catch (IllegalArgumentException e) {
      System.out
          .println("Problem detected: Your lookupProductById() method did not return the expected "
              + "exception when passed the identifier. (4-digits) " + e.getMessage());
      return false;

      // (2) This scenario checks if the key is found and if not, throws the correct exception.
    } catch (NoSuchElementException e) {
    } catch (Exception e) {
      System.out
          .println("Problem detected: Your lookupProductById() method did not return the expected "
              + "exception when passed the identifier. " + e.getMessage());
      return false;
    }
    return true;
  }


  /**
   * Checks the correctness of the method addItemToMarketCatalog() by running three different
   * scenarios with different inputs.
   * 
   * @return true is all scenarios pass else false if one of the scenarios fails printing out the
   *         exception if the scenario is false.
   */
  public static boolean testAddItemToMarketCatalog() {
    // (1) This scenario uses an unknown key and an unknown item and should throw an exception once
    // it detects that these two are not true
    try {
      String key = "980I"; // Unknown key
      String name = "Salt"; // Unknown item
      String price = "$87.45";
      ExceptionalShoppingCart.addItemToMarketCatalog(key, name, price);
      System.out.println(
          "Problem detected: Your addItemToMarketCatalog() method did not return the expected "
              + "exception when passed the identifier.888");
      return false;
    } catch (IllegalArgumentException e) {
    } catch (Exception e) {
      return false;
    }

    // (2) This scenario is unable to detect the name of the item as it is null and should throw an
    // exception once it detects the null item
    try {
      String key = "980"; // Unknown key
      String name = null; // Null item
      String price = "$87.45";
      ExceptionalShoppingCart.addItemToMarketCatalog(key, name, price);
      System.out.println(
          "Problem detected: Your addItemToMarketCatalog() method did not return the expected "
              + "exception when passed the identifier.");
      return false;
    } catch (IllegalArgumentException e) {
    }

    // (3) This scenario is unable to parse the price to an integer and should throw an exception
    // once it detects it is not parsable.
    try {
      String key = "9801"; // Unknown key
      String name = "Salt"; // Unknown item
      String price = "$87.s545";
      ExceptionalShoppingCart.addItemToMarketCatalog(key, name, price);
      System.out.println(
          "Problem detected: Your addItemToMarketCatalog() method did not return the expected "
              + "exception when passed the identifier.");
      return false;
    } catch (IllegalArgumentException e) {
    }
    return true;
  }


  /**
   * Checks the correctness of the saveCartSummary() method which calls the getCartSummary() method.
   * 
   * @return true if the method works correctly, and false otherwise.
   */
  public static boolean testSaveCartSummary() {
    try {
      File file = new File("ExceptionalShoppingCartTest.txt");
      String[] cart = {"Mushroom", "Cookie", "Mushroom", "Spinach", null, null};
      ExceptionalShoppingCart.saveCartSummary(cart, -6, file);
      System.out
          .println("Problem detected: Your saveCartSummary() method did not return the expected "
              + "exception when passed the identifier.");
      return false;
    } catch (IllegalArgumentException e) {
    }
    return true;
  }


  /**
   * Checks the correctness of the loadCartSummary() method which calls the parseCartSummaryLine()
   * method.
   * 
   * @return true if the method works correctly, and false otherwise.
   */
  public static boolean testLoadCartSummary() {
    try {
      File file = new File("ExceptionalShoppingCartTest.txt");
      String[] cart = {"Grape", null, null, null, null, null};
      ExceptionalShoppingCart.loadCartSummary(file, cart, -1);
      System.out
          .println("Problem detected: Your loadCartSummary() method did not return the expected "
              + "exception when passed the identifier.");
      return false;
    } catch (Exception e) {
    }
    return true;
  }
}

