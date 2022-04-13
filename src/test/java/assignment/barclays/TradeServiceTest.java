package assignment.barclays;

import assignment.barclays.trade.store.config.TradeStoreConfig;
import assignment.barclays.trade.store.exception.TradeServiceException;
import assignment.barclays.trade.store.model.TradeStore;
import assignment.barclays.trade.store.service.impl.TradingServiceImpl;
import java.time.LocalDate;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeStoreConfig.class)
public class TradeServiceTest {

  @Autowired
  private  TradingServiceImpl tradingService;

  @Test
  @DisplayName("Shows all the records from the store")
  public void showTrades() throws TradeServiceException {
    addTrades();
    tradingService.showTrades();
  }

  @Test(expected = TradeServiceException.class)
  @DisplayName("Adding the trade which is having low version with the one which already exist " +
      "with same trade id and higher version")
  public void lowerTradeVersionAddingTest() throws TradeServiceException {

    TradeStore t2 = new TradeStore().setTradeId("T2")
        .setVersion(2)
        .setCounterPartyId("CP-1")
        .setBookId("B1")
        .setMaturityDate(LocalDate.now().plusDays(1))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);

    tradingService.addTradeInStore(t2);

     t2 = new TradeStore().setTradeId("T2")
        .setVersion(1) //adding the lower version
        .setCounterPartyId("CP-1")
        .setBookId("B1")
        .setMaturityDate(LocalDate.now().plusDays(1))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);
    tradingService.addTradeInStore(t2);
  }

  @Test
  @DisplayName("Adding the trade with the same version which is already exist")
  public void addingSameVersionTest() throws TradeServiceException {

    TradeStore t1 = new TradeStore().setTradeId("T1")
        .setVersion(1)
        .setCounterPartyId("CP-1")
        .setBookId("B1")
        .setMaturityDate(LocalDate.now().plusDays(1))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);
    tradingService.addTradeInStore(t1);

       t1 = new TradeStore().setTradeId("T1")
        .setVersion(1)
        .setCounterPartyId("CP-2")
        .setBookId("B2")
        .setMaturityDate(LocalDate.now())
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);
    tradingService.addTradeInStore(t1);

    TradeStore trade = tradingService.getTradeById("T1");

    Assert.assertEquals(t1.getVersion(), trade.getVersion());
    Assert.assertEquals(t1.getCounterPartyId(), trade.getCounterPartyId());
    Assert.assertEquals(t1.getBookId(), trade.getBookId());
  }

  @Test(expected = TradeServiceException.class)
  @DisplayName("Adding the trade which has maturity date before than current date")
  public void tradeWithMaturityDateBeforeCurrentDateTest() throws TradeServiceException {
    TradeStore t1 = new TradeStore().setTradeId("T1")
        .setVersion(1)
        .setCounterPartyId("CP-2")
        .setBookId("B2")
        .setMaturityDate(LocalDate.now().minusDays(1))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);
    tradingService.addTradeInStore(t1);
  }


  private void addTrades() throws TradeServiceException {
    TradeStore t1 = new TradeStore().setTradeId("T1")
        .setVersion(1)
        .setCounterPartyId("CP-1")
        .setBookId("B1")
        .setMaturityDate(LocalDate.now().plusDays(10))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);

    TradeStore t2 = new TradeStore().setTradeId("T2")
    .setVersion(2)
        .setCounterPartyId("CP-2")
        .setBookId("B1")
        .setMaturityDate(LocalDate.now().plusDays(30))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);

    TradeStore t22 = new TradeStore().setTradeId("T2")
        .setVersion(3)
        .setCounterPartyId("CP-2")
        .setBookId("B1")
        .setMaturityDate(LocalDate.now().plusDays(30))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);

    TradeStore t3 = new TradeStore().setTradeId("T3")
        .setVersion(3)
        .setCounterPartyId("CP-3")
        .setBookId("B2")
        .setMaturityDate(LocalDate.now().plusDays(100))
        .setCreatedDate(LocalDate.now())
        .setIsExpired(false);

    tradingService.addAllTrades(List.of(t1, t2, t22, t3));
  }
}
