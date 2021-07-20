package it.euris.ires.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmountTest {

  Amount amount;

  @BeforeEach
  void setUp() {
    amount = new Amount();
  }

  @Test
  void givenCurrencyWhenInitAmountThenAttributeAmountIsZero() {
    amount.initAmount("USD");

    assertEquals("0.00", amount.getAmount());
  }

  @Test
  void givenArrayOfPricesWhenSumThenIncreaseAmountBySum() {
    amount.initAmount("USD");
    List<String> priceList = Arrays.asList("13.2", "19.05", "123.65", "50", "87.1", "111");

    String totalAmount = amount.sum(priceList);

    assertEquals("404.00", totalAmount);
  }
}
