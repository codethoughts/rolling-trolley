package TransferObjects;

import javax.persistence.*;

/**
 * Created by Eiger on 5/8/16.
 */
@Entity
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private float price;

  @ManyToOne
  private Store store;

  public Long getId() {
    return id;
  }

  public void setId( Long id ) {
    this.id = id;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice( float price ) {
    this.price = price;
  }
}
