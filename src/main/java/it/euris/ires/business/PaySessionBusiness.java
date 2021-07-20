package it.euris.ires.business;

import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.dataObject.CreatePaySessionResponse;
import it.euris.ires.entity.PaySession;
import it.euris.ires.exception.PaySessionException;
import it.euris.ires.service.IPaymentSessionService;

public class PaySessionBusiness {

  final IPaymentSessionService paymentSessionService;

  public PaySessionBusiness(IPaymentSessionService paymentSessionService) {
    this.paymentSessionService = paymentSessionService;
  }

  public CreatePaySessionResponse createPaySession(CreatePaySessionRequest request) {
    CreatePaySessionResponse response = new CreatePaySessionResponse();
    try {
      PaySession paySession = paymentSessionService.createWebPaySession(request);
      response.setSuccess(true);
      response.setStatus(paySession.getStatus().name());
      response.setPaySessionId(paySession.getUuid().toString());
    } catch (PaySessionException e) {
      response.setSuccess(false);
    }
    return response;
  }

  public PaySession getPaySession(String paySessionId) throws PaySessionException {
    PaySession paySession = paymentSessionService.getShoppingCart(paySessionId);
    paymentSessionService.checkTtlExpired(paySession);
    return paySession;
  }

}
