package Price;

import Interfaces.IPriceStrategy;

/**
 * Created by Eiger on 6/25/16.
 */
public class Original implements IPriceStrategy {
  @Override
  public Double calculatePrice( Double original ) {
    return original;
  }
}
