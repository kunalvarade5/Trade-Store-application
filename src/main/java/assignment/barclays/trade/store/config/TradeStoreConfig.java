package assignment.barclays.trade.store.config;


import assignment.barclays.trade.store.model.TradeStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"assignment.barclays.trade.store"})
public class TradeStoreConfig {

 /* @Bean
  public TradeStore getTradeBean() {
    return new TradeStore();
  }*/
}
