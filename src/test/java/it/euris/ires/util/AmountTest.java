package it.euris.ires.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
    // arrange
    Amount amount = new Amount();
    amount.initAmount("USD");
    List<String> priceList = new ArrayList<>();
    priceList.add("13.2");
    priceList.add("19.05");
    priceList.add("123.65");
    priceList.add("50");
    priceList.add("87.1");
    priceList.add("111");
    // act
    String totalAmount = amount.sum(priceList);
    // assert
    assertEquals("404.00", totalAmount);
  }

}