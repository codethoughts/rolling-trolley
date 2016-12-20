package Model;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Eiger on 6/23/16.
 */
@Entity
public class Receipt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Date purchaseDate;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Customer buyer;
  @OneToMany(cascade = CascadeType.ALL)
  private Collection<Item> items = new ArrayList<>();

  public Receipt( Collection<Item> items ) {
    purchaseDate = new Date();
    for ( Item item : items ) {
      Item cloned = (Item) item.clone();
      this.items.add(cloned);
    }
  }

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate( Date purchaseDate ) {
    this.purchaseDate = purchaseDate;
  }

  public Collection<Item> getItems() {
    return items;
  }

  public void setItems( Collection<Item> items ) {
    this.items = items;
  }

  public Customer getBuyer() {
    return buyer;
  }

  public void setBuyer( Customer buyer ) {
    this.buyer = buyer;
  }

  @Override
  public boolean equals( Object obj ) {
    Receipt o = (Receipt) obj;
    if ( o == null ) return false;
    return o.getId() == getId();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();
    hcb.append(getId());
    hcb.append(getPurchaseDate());
    return hcb.toHashCode();
  }

}
