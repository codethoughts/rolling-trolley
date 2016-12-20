package Storage;


/**
 * Created by Eiger on 5/10/16.
 */

@FunctionalInterface
public interface ITransaction {
  void run() throws Exception;
}
