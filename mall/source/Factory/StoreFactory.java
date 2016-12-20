package Factory;

import Enums.StoreType;
import Model.BookStore;
import Model.GameStore;
import Model.ShoeStore;
import Model.Store;

/**
 * Created by Eiger on 6/23/16.
 */
public class StoreFactory {

  private static StoreFactory instance = new StoreFactory();

  public static StoreFactory getInstance() {
    return instance;
  }

  private StoreFactory() {

  }

  public Store createStore( String name, StoreType type ) {
    switch ( type ) {
      case BOOK_STORE:
        return new BookStore(name);
      case SHOE_STORE:
        return new ShoeStore(name);
      case GAME_STORE:
        return new GameStore(name);
    }
    return null;
  }

}
