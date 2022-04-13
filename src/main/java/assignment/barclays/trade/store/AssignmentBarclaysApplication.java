package assignment.barclays.trade.store;

import assignment.barclays.trade.store.exception.TradeServiceException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentBarclaysApplication {

  public static void main(String[] args) throws TradeServiceException {

    SpringApplication.run(AssignmentBarclaysApplication.class, args);

  }

}
