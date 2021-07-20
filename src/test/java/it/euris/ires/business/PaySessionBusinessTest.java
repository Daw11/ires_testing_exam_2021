package it.euris.ires.business;

import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.dataObject.CreatePaySessionResponse;
import it.euris.ires.entity.PaySession;
import it.euris.ires.exception.PaySessionException;
import it.euris.ires.service.IPaymentSessionService;
import it.euris.ires.util.PaySessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PaySessionBusinessTest {

    PaySessionBusiness paySessionBusiness;

    @Mock
    IPaymentSessionService paymentSessionServiceMock;

    @Mock
    PaySession paySessionMock;

    @BeforeEach
    void setUp(){
        paySessionBusiness = new PaySessionBusiness(paymentSessionServiceMock);
    }

    @Test
    void GivenValidRequestWhenCreatePaySessionThenShouldReturnValidResponse() throws PaySessionException {
        CreatePaySessionRequest request = new CreatePaySessionRequest();
        PaySessionStatus status = PaySessionStatus.PAID;
        Mockito.when(paySessionMock.getStatus()).thenReturn(status);
        UUID uuid = UUID.randomUUID();
        Mockito.when(paySessionMock.getUuid()).thenReturn(uuid);
        Mockito.when(paymentSessionServiceMock.createWebPaySession(any())).thenReturn(paySessionMock);

        CreatePaySessionResponse response = paySessionBusiness.createPaySession(request);

        Mockito.verify(paymentSessionServiceMock, times(1)).createWebPaySession(any());
        assertEquals(true, response.isSuccess());
        assertEquals(status.name(), response.getStatus());
        assertEquals(uuid.toString(), response.getPaySessionId());
    }

    @Test
    void GivenWrongRequestWhenCreatePaySessionThenShouldReturnBadResponse() throws PaySessionException {
        CreatePaySessionRequest request = new CreatePaySessionRequest();
        Mockito.when(paymentSessionServiceMock.createWebPaySession(any())).thenThrow(PaySessionException.class);

        CreatePaySessionResponse response = paySessionBusiness.createPaySession(request);

        assertEquals(false, response.isSuccess());
    }

    @Test
    void GivenPaySessionIdWhenGetPaySessionThenShouldCheckTtlExpired() throws PaySessionException {
        String uuid = UUID.randomUUID().toString();
        Mockito.when(paymentSessionServiceMock.getShoppingCart(uuid)).thenReturn(paySessionMock);

        paySessionBusiness.getPaySession(uuid);

        Mockito.verify(paymentSessionServiceMock, times(1)).checkTtlExpired(paySessionMock);
    }
}
