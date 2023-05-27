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
 * This class allows us to implement Java arrays to implement a Shopping Cart as a way of storing
 * several elements and collection of data as well as exceptions when an item is not found in the
 * market or the idea of an item is not found. This program will allow an item from the list of
 * elements from the marketItems array to be added to cart along with several other methods such as:
 * looking up an item by its name, looking up an item by its ID, getting the price of each element,
 * retrieving a copy of the cart, adding an item to the cart, the number of times each item appears
 * in the cart, the items contained in the cart, removing an item from the cart, checking out the
 * final price of the cart, getting the cart summary, loading the cart summary, and saving the cart
 * summary.
 * 
 * @author Harshet Anand
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;
import java.util.Scanner;


public class ExceptionalShoppingCart {

  // Define final parameters (constants)
  private static final double TAX_RATE = 0.05; // sales tax


  // a perfect-size two-dimensional array that stores the list of available items in a given market
  // MarketItems[i][0] refers to a String representation of the item key (unique identifier)
  // MarketItems[i][1] refers the item name
  // MarketItems[i][2] a String representation of the unit price of the item in dollars
  private static String[][] marketItems =
      new String[][] {{"4390", "Apple", "$1.59"}, {"4046", "Avocado", "$0.59"},
          {"4011", "Banana", "$0.49"}, {"4500", "Beef", "$3.79"}, {"4033", "Blueberry", "$6.89"},
          {"4129", "Broccoli", "$1.79"}, {"4131", "Butter", "$4.59"}, {"4017", "Carrot", "$1.19"},
          {"3240", "Cereal", "$3.69"}, {"3560", "Cheese", "$3.49"}, {"3294", "Chicken", "$5.09"},
          {"4071", "Chocolate", "$3.19"}, {"4363", "Cookie", "$9.5"}, {"4232", "Cucumber", "$0.79"},
          {"3033", "Eggs", "$3.09"}, {"4770", "Grape", "$2.29"}, {"3553", "Ice Cream", "$5.39"},
          {"3117", "Milk", "$2.09"}, {"3437", "Mushroom", "$1.79"}, {"4663", "Onion", "$0.79"},
          {"4030", "Pepper", "$1.99"}, {"3890", "Pizza", "$11.5"}, {"4139", "Potato", "$0.69"},
          {"3044", "Spinach", "$3.09"}, {"4688", "Tomato", "$1.79"}, null, null, null, null};


  /**
   * Creates a deep copy of the market catalogue
   * 
   * @return Returns a deep copy of the market catalogue 2D array of strings
   */
  public static String[][] getCopyOfMarketItems() {
    String[][] copy = new String[marketItems.length][];
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null) {
        copy[i] = new String[marketItems[i].length];
        for (int j = 0; j < marketItems[i].length; j++)
          copy[i][j] = marketItems[i][j];
      }
    }
    return copy;
  }


  /**
   * Returns a string representation of the item whose name is provided as input
   * 
   * @param name name of the item to find
   * @return "itemId name itemPrice" if an item with the provided name was found
   * @throws NoSuchElementException with descriptive error message if no match found
   * 
   */
  public static String lookupProductByName(String name) throws NoSuchElementException {
    String s = "No match found";
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null && name.equals(marketItems[i][1])) {
        return marketItems[i][0] + " " + marketItems[i][1] + " " + marketItems[i][2];
      } else {
        throw new NoSuchElementException("Error! No match found.");
      }
    }
    return s; // Returns string
  }



  /**
   * Returns a string representation of the item whose id is provided as input
   *
   * @param key id of the item to find
   * @return "itemId name itemPrice" if an item with the provided name was found
   * @throws IllegalArgumentException with descriptive error message if key is not a 4-digits int
   * @throws NoSuchElementException   with descriptive error message if no match found
   */
  public static String lookupProductById(int key)
      throws IllegalArgumentException, NoSuchElementException {
    String s = "No match found";
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null) {
        if (marketItems[i][0].equals(String.valueOf(key)))
          return marketItems[i][0] + " " + marketItems[i][1] + " " + marketItems[i][2];
      } else if (key > 9999 || key < 1000) {
        throw new IllegalArgumentException("Error! Key is not 4-digits.");
      } else {
        throw new NoSuchElementException("Error! No match found.");
      }
    }
    return s; // Returns string
  }


  /**
   * Returns the index of the first null position that can be used to add new market items returns
   * the length of MarketItems if no available null position is found
   * 
   * @return returns an available position to add new market items or the length of market items if
   *         no available positions are found
   */
  private static int indexOfInsertionPos() {
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] == null)
        return i;
    }
    return marketItems.length;
  }


  /**
   * add a new item to market items array, expand the capacity of marketItems if it is full when
   * trying to add new item, use indexofInsertionPos() to find the position to add
   * 
   * @param id    id of the item to add
   * @param name  name of the item to add
   * @param price price of the item to add
   * @throws IllegalArgumentException with descriptive error message if id not parsable to 4-digits
   *                                  int, name is null or empty string, and price not parsable to
   *                                  double
   */
  public static void addItemToMarketCatalog(String id, String name, String price)
      throws IllegalArgumentException {
    int next = indexOfInsertionPos();
    try {
      Integer.parseInt(id);
      Double.parseDouble(price.substring(1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error! ID is not 4-digits.");
    }
    if (name == null || name.equals("")) { // If name is null or string is empty
      throw new IllegalArgumentException("Error! No match found.");
    } else if (!(price.charAt(0) == '$')) {
      throw new IllegalArgumentException("Error! Price of item not found.");
    } else if (Integer.parseInt(id) > 9999 || Integer.parseInt(id) < 1000) {
      throw new IllegalArgumentException("Error! ID is not found.");
    } else if (next == marketItems.length) {
      System.out.println("Full catalog! No further item can be added!");
    } else {
      marketItems[next] = new String[] {id, name, price};
    }
  }


  /**
   * Returns the price in dollars (a double value) of a market item given its name. If no match was
   * found in the market catalogue, this method returns -1.0
   * 
   * @param name name of the item to get the price
   * @return the price of the item
   * @throws NoSuchElementException with descriptive error message if price not found
   */
  public static double getProductPrice(String name) throws NoSuchElementException {
    double price = -1.0;
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] != null && name.equals(marketItems[i][1])) {
        return Double.valueOf(marketItems[i][2].substring(1));
      } else {
        throw new NoSuchElementException("Error! No price found.");
      }
    }
    return price; // Returns price of individual item
  }


  /**
   * Appends an item to a given cart (appends means adding to the end). If the cart is already full
   * (meaning its size equals its length), IllegalStateException will be thrown.
   * 
   * @param item the name of the product to be added to the cart
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the size of the oversize array cart after trying to add item to the cart.
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   * @throws IllegalStateException    with descriptive error message if this cart is full
   */
  public static int addItemToCart(String item, String[] cart, int size)
      throws IllegalArgumentException, IllegalStateException {
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    } else if (!(size <= cart.length)) {
      throw new IllegalStateException("Error! The size of the cart is full.");
    } else {
      cart[size] = item;
      size++;
    }
    return size;
  }


  /**
   * Returns the number of occurrences of a given item within a cart. This method must not make any
   * changes to the contents of the cart.
   * 
   * @param item the name of the item to search
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the number of occurrences of item (exact match) within the oversize array cart. Zero or
   *         more occurrences of item can be present in the cart.
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   */
  public static int nbOccurrences(String item, String[] cart, int size)
      throws IllegalArgumentException {
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    }
    int count = 0;
    for (int i = 0; i < size; i++) {
      if (cart[i].equals(item)) {
        count++;
      }
    }
    return count; // Returns count of number of occurrences of item
  }


  /**
   * Checks whether a cart contains at least one occurrence of a given item. This method must not
   * make any changes to the contents of the cart.
   * 
   * @param item the name of the item to search
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns true if there is a match (exact match) of item within the provided cart, and
   *         false otherwise.
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   */
  public static boolean contains(String item, String[] cart, int size)
      throws IllegalArgumentException {
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    } else {
      for (int i = 0; i < size; i++) {
        if (cart[i].equals(item)) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * Removes one occurrence of item from a given cart.
   * 
   * @param item the name of the item to remove
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the oversize array cart after trying to remove item from the cart.
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   * @throws NoSuchElementException   with descriptive error message if item not found in the cart
   */
  public static int removeItem(String[] cart, String item, int size)
      throws IllegalArgumentException, NoSuchElementException {
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    }
    for (int i = 0; i < size; i++) {
      if (cart[i].equals(item)) {
        cart[i] = cart[size - 1];
        cart[size - 1] = null;
        size--;
        return size - 1;
      }
    }
    throw new NoSuchElementException("Error! Item not found in the cart.");
  }


  /**
   * Removes all items from a given cart. The array cart must be empty (contains only null
   * references) after this method returns.
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the cart after removing all its items.
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   * @throws NullPointerException     with descriptive error message if cart is null
   */
  public static int emptyCart(String[] cart, int size)
      throws IllegalArgumentException, NullPointerException {
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    } else if (cart == null) {
      throw new NullPointerException("Error! The cart is null.");
    } else {
      for (int j = 0; j < size; j++) {
        cart[j] = null;
      }
    }
    return 0;
  }


  /**
   * This method returns the total value in dollars of the cart. All products in the market are
   * taxable (subject to TAX_RATE).
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the total value in dollars of the cart accounting taxes.
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   */
  public static double checkout(String[] cart, int size) throws IllegalArgumentException {
    double total = 0.0;
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    } else {
      for (int i = 0; i < size; i++) {
        total += Double.valueOf(marketItems[i][2].substring(1)) * TAX_RATE;
      }
    }
    return total;
  }


  /**
   * Returns a string representation of the summary of the contents of a given cart. The format of
   * the returned string contains a set of lines where each line contains the number of occurrences
   * of a given item, between spaces and parentheses, followed by one space followed by the name of
   * a unique item in the cart. ( #occurrences ) name1 ( #occurrences ) name2 etc.
   * 
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns a string representation of the summary of the contents of the cart
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   */
  public static String getCartSummary(String[] cart, int size) throws IllegalArgumentException {
    String s = "";
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    } else {
      for (int i = 0; i < size; i++) {
        if (!contains(cart[i], cart, i)) {
          s = s + "( " + nbOccurrences(cart[i], cart, size) + " ) " + cart[i] + "\n";
        }
      }
    }
    return s.trim();
  }



  /**
   * Save the cart summary to a file
   *
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @param file the file to save the cart summary
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   */
  public static void saveCartSummary(String[] cart, int size, File file)
      throws IllegalArgumentException {
    PrintWriter writer = null;
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    }
    try {
      writer = new PrintWriter(file);
      writer.print(getCartSummary(cart, size)); // Calls getCartSummary() method
    } catch (FileNotFoundException e) {
      System.out.println("Error! The file does not exist.");
    } finally {
      if (!(writer == null)) {
        writer.close(); // Closes PrintWriter file so file does not corrupt
      }
    }
  }


  /**
   * Parse one line of cart summary and add nbOccurrences of item to cart correct formatting for
   * line:"( " + nbOccurrences + " ) " + itemName delimiter: one space (multiple spaces: wrong
   * formatting)
   *
   * @param line a line of the cart summary to be parsed into one item to be added
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the cart after adding items and parsing the summary line
   * @throws DataFormatException      with descriptive error message if wrong formatting (including
   *                                  nbOccurrences not parsable to a positive integer less or equal
   *                                  to 10)
   * @throws IllegalArgumentException with descriptive error message if itemName not found in
   *                                  marketItems
   * @throws IllegalStateException    with descriptive error message if cart reaches its capacity
   */
  protected static int parseCartSummaryLine(String line, String[] cart, int size)
      throws DataFormatException, IllegalArgumentException, IllegalStateException {
    String[] categorized = line.split(" ");
    if (!(size <= cart.length)) {
      throw new IllegalStateException("Error! The size of the cart is full.");
    }
    if (categorized.length != 4) {
      throw new DataFormatException("Error! The cart is not categorized into 4 sections.");
    }
    try {
      if (!(Integer.parseInt(categorized[1]) > 0 || (Integer.parseInt(categorized[1]) > 10))) {
        throw new NumberFormatException("Error! The number of occurrences is not between 1 & 10.");
      }
      lookupProductByName(categorized[3]);
    } catch (NumberFormatException e) {
      throw new DataFormatException(
          "Error! The number of occurrences can not be parsed to an integer");
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error! Item not found in the cart.");
    } // Specific item not found in cart

    int itemCategorized = Integer.parseInt(categorized[1]);
    for (int j = itemCategorized; j > 0; j--) {
      try {
        size = addItemToCart(categorized[3], cart, size);
      } catch (IllegalStateException e) {
        throw new IllegalStateException("Error! The size of the cart is full."); // Cart is full
      }
    }
    return size;
  }


  /**
   * Load the cart summary from the file. For each line of summary, add nbOccurrences of item to
   * cart. Must call parseCartSummaryLine to operate
   *
   * @param file file to load the cart summary from
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return Returns the size of the cart after adding items to the cart
   * @throws IllegalArgumentException with descriptive error message if size is less than zero
   * @throws IllegalStateException    with descriptive error message if cart reaches its capacity
   */
  public static int loadCartSummary(File file, String[] cart, int size)
      throws IllegalArgumentException, IllegalStateException {
    Scanner scnr = null;
    if (size < 0) {
      throw new IllegalArgumentException("Error! The size of the cart is less than 0.");
    }
    try {
      scnr = new Scanner(file);
      if (scnr.hasNextLine()) {
        do {
          try {
            String cartLine = scnr.nextLine();
            size = parseCartSummaryLine(cartLine, cart, size); // Calls parseCartSummaryLine()
                                                               // method
          } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
          } catch (DataFormatException e) {
          } catch (IllegalArgumentException e) {
          }
        } while (scnr.hasNextLine());
      }
      scnr.close(); // Closes scanner
    } catch (FileNotFoundException e) {
      System.out.println("Error! The file does not exist."); // File not found
    } catch (IllegalStateException e) {
      throw new IllegalStateException(e); // Cart is full
    } catch (Exception e) {
      System.out.print(e); // Other exceptions when loading summary
    }
    return size;
  }
}


