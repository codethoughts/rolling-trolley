package Model;


import Enums.DiscountType;
import Factory.StoreFactory;
import Interfaces.IPriceStrategy;
import Price.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Item implements Serializable, Cloneable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private Store store;
  @Enumerated
  private Enums.DiscountType discount;
  private Double price;
  private String name;
  @Transient
  private IPriceStrategy priceStrategy;

  public Item() {
    setDiscount(DiscountType.None);
    setPrice(0.0);
    setName("");
    priceStrategy = new Original();
  }

  public Item( String name, Store store, Double price ) {
    super();
    setStore(store);
    setName(name);
    setPrice(price);
    loadPriceStrategy();
  }

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public Store getStore() {
    return store;
  }

  public void setStore( Store store ) {
    this.store = store;
  }

  public DiscountType getDiscount() {
    return discount;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice( Double price ) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public void setDiscount( Enums.DiscountType discount ) {
    this.discount = discount;
    loadPriceStrategy();
  }

  public Double calculateTotalPrice() {
    return priceStrategy.calculatePrice(price);
  }

  @Override
  protected Object clone() {
    Item cloned = null;
    Store store_identity =
            StoreFactory.getInstance().createStore(
                    getName(), getStore().getType());
    try {
      cloned = (Item) super.clone();
      cloned.setStore(store_identity);
    } catch ( CloneNotSupportedException e ) {
      e.printStackTrace();
    }
    return cloned;
  }

  @Override
  public boolean equals( Object obj ) {
    Item o = (Item) obj;
    if ( o == null ) return false;
    return o.getId() == getId();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();
    hcb.append(getId());
    hcb.append(getName());

    return hcb.toHashCode();
  }

  @PostLoad
  public void onLoad() {
    loadPriceStrategy();
  }

  private void loadPriceStrategy() {

//    if ( getDiscount() == DiscountType.Discount_10 )
//      priceStrategy = new Discount10();
//    else if ( getDiscount() == DiscountType.Discount_30 )
//      priceStrategy = new Discount30();
//    else if ( getDiscount() == DiscountType.Discount_50 )
//      priceStrategy = new Discount50();
//    else if ( getDiscount() == DiscountType.Discount_70 )
//      priceStrategy = new Discount70();
//    else
//      priceStrategy = new Original();

    switch ( getDiscount() ) {
      case Discount_10:
        priceStrategy = new Discount10();
        break;
      case Discount_30:
        priceStrategy = new Discount30();
        break;
      case Discount_50:
        priceStrategy = new Discount50();
        break;
      case Discount_70:
        priceStrategy = new Discount70();
        break;
      default:
        priceStrategy = new Original();
        break;
    }
  }

}
