package assignment.barclays.trade.store.model;

import java.time.LocalDate;
import java.util.Comparator;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author Kunal Varade
 */
@Component
@Getter
public class TradeStore implements Comparable<TradeStore>, Comparator<TradeStore> {

  private String tradeId;
  private int version;
  private String counterPartyId;
  private String bookId;
  private LocalDate maturityDate;
  private LocalDate createdDate;
  private boolean isExpired;

  @Override
  public int compareTo(TradeStore o) {
    return this.tradeId.compareTo(o.tradeId);
  }

  @Override
  public int compare(TradeStore o1, TradeStore o2) {

    if (o1.getTradeId().compareTo(o2.tradeId) == 0) {

      if (o1.version > o2.version) {
        return -1;
      } else if (o1.version < o2.version) {
        return 1;
      } else if (o1.version == o2.version) {
        return 0;
      }
    }
    return o1.getTradeId().compareTo(o2.tradeId);
  }

  @Override
  public String toString() {



    String str =
        "" + this.tradeId + "\t\t" + this.version + "\t\t" + this.counterPartyId + "\t\t\t\t" + this.bookId
        + "\t\t" + this.maturityDate + "\t\t" + this.createdDate + "\t\t"
        + (this.isExpired ? "Y" : "N") + "\n";

    return str;
  }

  public String getTradeId() {
    return tradeId;
  }

  public TradeStore setTradeId(String tradeId) {
    this.tradeId = tradeId;
    return this;
  }

  public int getVersion() {
    return version;

  }

  public TradeStore setVersion(int version) {
    this.version = version;
    return this;
  }

  public String getCounterPartyId() {
    return counterPartyId;
  }

  public TradeStore setCounterPartyId(String counterPartyId) {
    this.counterPartyId = counterPartyId;
    return this;
  }

  public String getBookId() {
    return bookId;

  }

  public TradeStore setBookId(String bookId) {
    this.bookId = bookId;
    return this;
  }

  public LocalDate getMaturityDate() {
    return maturityDate;
  }

  public TradeStore setMaturityDate(LocalDate maturityDate) {
    this.maturityDate = maturityDate;
    return this;
  }

  public LocalDate getCreatedDate() {
    return createdDate;
  }

  public TradeStore setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  public boolean getIsExpired() {
    return isExpired;
  }

  public TradeStore setIsExpired(boolean expired) {
    this.isExpired = expired;
    return this;
  }

}
