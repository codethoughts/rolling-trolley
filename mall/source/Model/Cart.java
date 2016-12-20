package Model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Eiger on 6/23/16.
 */
public class Cart {
  private Customer owner;
  private Collection<Item> items = new ArrayList<>();

  public Cart( Customer owner ) {
    this.owner = owner;
  }

  public Collection<Item> getItems() {
    return items;
  }

  public void setItems( Collection<Item> items ) {
    this.items = items;
  }

  public Customer getOwner() {
    return owner;
  }

  public void setOwner( Customer owner ) {
    this.owner = owner;
  }

  public void addItem( Item item ) {
    items.add(item);
  }

  public void removeItem( Item item ) {
    items.remove(item);
  }

  // TODO: undo & redo

  public void undo() {

  }

  public void redo() {

  }
}
