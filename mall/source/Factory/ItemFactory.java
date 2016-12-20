package Factory;

import Model.Item;
import Model.Store;

/**
 * Created by Eiger on 6/23/16.
 */
public class ItemFactory {
  private static ItemFactory instance = new ItemFactory();

  public static ItemFactory getInstance() {
    return instance;
  }

  private ItemFactory() {

  }

  public Item createItem( String name, Store store, Double price ) {
    return new Item(name, store, price);
  }

}
