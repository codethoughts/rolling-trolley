package Model;

import Enums.StoreType;

import javax.persistence.Entity;

/**
 * Created by Eiger on 6/23/16.
 */
@Entity
public class ShoeStore extends Store {
  public ShoeStore( String name ) {
    super(name, StoreType.SHOE_STORE);
  }
}
