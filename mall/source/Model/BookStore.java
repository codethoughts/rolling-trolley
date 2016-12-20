package Model;

import Enums.StoreType;

import javax.persistence.Entity;

/**
 * Created by Eiger on 6/23/16.
 */
@Entity
public class BookStore extends Store {
  public BookStore( String name ) {
    super(name, StoreType.BOOK_STORE);
  }
}
