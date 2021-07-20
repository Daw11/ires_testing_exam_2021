package it.euris.ires.service;

import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.entity.PaySession;
import it.euris.ires.exception.PaySessionException;

public interface IPaymentSessionService {

  PaySession createWebPaySession(CreatePaySessionRequest request) throws PaySessionException;

  PaySession getShoppingCart(String paySessionId) throws PaySessionException;

  void checkTtlExpired(PaySession paySession) throws PaySessionException;

  void savePaySession(PaySession paySession) throws PaySessionException;

}
