package Price;

import Interfaces.IPriceStrategy;

/**
 * Created by Eiger on 6/23/16.
 */
public class Discount70 implements IPriceStrategy {
  @Override
  public Double calculatePrice( Double original ) {
    return original - original * 0.7;
  }
}
