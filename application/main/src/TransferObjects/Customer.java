package TransferObjects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Eiger on 5/8/16.
 */
@Entity
public class Customer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToOne
  private Cart cart;

  @OneToMany
  private Collection<Subscription> subscriptions;

  public Long getId() {
    return id;
  }

  public void setId( Long id ) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  @Override
  public String toString() {
    return getId() + " " + getName();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();
    hcb.append(getId());
    hcb.append(getName());

    return hcb.toHashCode();
  }

  @Override
  public boolean equals( Object obj ) {
    Customer another = (Customer) obj;

    return this.getId().equals(another.getId());
  }
}
