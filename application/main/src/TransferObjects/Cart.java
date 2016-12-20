package TransferObjects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Eiger on 5/8/16.
 */
@Entity
public class Cart implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany
  Collection<Item> items;

  public Long getId() {
    return id;
  }

  public void setId( Long id ) {
    this.id = id;
  }
}
