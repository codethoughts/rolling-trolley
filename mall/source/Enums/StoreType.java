package Enums;

/**
 * Created by Eiger on 6/25/16.
 */
public enum StoreType {
  GAME_STORE("Game Store"),
  SHOE_STORE("Shoe Store"),
  BOOK_STORE("Book Store");

  private String name = "";

  StoreType( String name ) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
