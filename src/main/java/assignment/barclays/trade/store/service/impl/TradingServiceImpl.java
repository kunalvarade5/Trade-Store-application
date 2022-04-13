package assignment.barclays.trade.store.service.impl;

import assignment.barclays.trade.store.exception.TradeServiceException;
import assignment.barclays.trade.store.model.TradeStore;
import assignment.barclays.trade.store.service.TradingService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Kunal Varade
 *
 */
@Service
public class TradingServiceImpl extends Thread implements TradingService {

	private List<TradeStore> tradeStoreList = new ArrayList<>();
	
	public void run() {

		while (true) {
			
			// If condition will be executed at the mid night (12:01 AM) when the list is not empty
			if (!tradeStoreList.isEmpty() 
					&& LocalDateTime.now().getHour() == 00
					&& LocalDateTime.now().getMinute() == 1) {

				synchronized (tradeStoreList) {
					tradeStoreList.stream().filter(x -> x.getMaturityDate().isBefore(LocalDate.now()))
							.peek(x -> x.setIsExpired(true));
				}

				try {
					// Thread will be in sleeping state for 1 min
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@PostConstruct
	public void init() {
		Thread thread1 = new TradingServiceImpl();
		thread1.start();
	}
	
	@Override
	public boolean addTradeInStore(TradeStore trade) throws TradeServiceException {

		if (trade.getMaturityDate().isBefore(LocalDate.now())) {
			throw new TradeServiceException("Trade is expired");
		}

		if (isExistWithGreaterVersion(trade)) {
			throw new TradeServiceException("Higher version of Trade is already present");
		}
		return tradeStoreList.add(trade);
	}

	@Override
	public void showTrades() {
		System.err.println("TradeId\tVersion\tCounter-Party-Id\tBook-Id\t  Maturity Date\t  " +
				"Created Date\tExpired");
		System.out.print(" "+tradeStoreList.stream().filter(Objects::nonNull).
				sorted(new TradeStore()).collect(Collectors.toList()));
	}

	@Override
	public TradeStore getTradeById(String tradeId) {
		return tradeStoreList.stream().filter(x -> x.getTradeId().equals(tradeId))
				.findFirst()
				.orElse(null);
	}

	private boolean isExistWithGreaterVersion(TradeStore trade) {

		if (tradeStoreList.stream()
				.anyMatch(x -> x.getTradeId().equals(trade.getTradeId()) 
						&& x.getVersion() > trade.getVersion())) {
			return true;
		}

		TradeStore trade2 = tradeStoreList.stream()
				.filter(x -> (x.getTradeId().equals(trade.getTradeId()) 
						&& x.getVersion() == trade.getVersion()))
				.findAny()
				.orElse(null);

		if (Objects.nonNull(trade2)) {
			tradeStoreList.remove(trade2);
		}
		return false;
	}

	@Override
	public void addAllTrades(List<TradeStore> trades) throws TradeServiceException {

		for (TradeStore trade : trades) {
			addTradeInStore(trade);
		}
	}
}
