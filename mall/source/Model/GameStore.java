package Model;

import Enums.StoreType;

import javax.persistence.Entity;

/**
 * Created by Eiger on 6/23/16.
 */
@Entity
public class GameStore extends Store {
  public GameStore( String name ) {
    super(name, StoreType.GAME_STORE);
  }
}
