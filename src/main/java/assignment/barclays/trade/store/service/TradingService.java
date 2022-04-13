package assignment.barclays.trade.store.service;

import java.util.List;

import assignment.barclays.trade.store.exception.TradeServiceException;
import assignment.barclays.trade.store.model.TradeStore;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Kunal Varade
 *
 */

@Service
public interface TradingService {

	boolean addTradeInStore(TradeStore trade) throws TradeServiceException;

	void addAllTrades(List<TradeStore> trades) throws TradeServiceException;
	
	void showTrades();

	TradeStore getTradeById(String tradeId);
}
