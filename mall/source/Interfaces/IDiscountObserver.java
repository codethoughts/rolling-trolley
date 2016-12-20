package Interfaces;

import Model.Item;

/**
 * Created by Eiger on 6/22/16.
 */


public interface IDiscountObserver {
  void onDiscountNotify( Item item );
}
