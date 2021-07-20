package it.euris.ires.service.impl;

import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.database.IPaySessionRepository;
import it.euris.ires.entity.PaySession;
import it.euris.ires.exception.PaySessionException;
import it.euris.ires.service.IPaymentSessionService;
import it.euris.ires.service.util.RequestToEntityMapper;
import it.euris.ires.util.Amount;
import it.euris.ires.util.PaySessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PaymentSessionServiceTest {

    IPaymentSessionService paymentSessionService;

    @Mock
    IPaySessionRepository paySessionRepositoryMock;

    @Mock
    RequestToEntityMapper entityBuilderMock;

    @Mock
    Amount amountMock;

    @BeforeEach
    void setUp(){
        paymentSessionService = new PaymentSessionService(paySessionRepositoryMock, entityBuilderMock, amountMock);
    }

    @Test
    void givenValidRequestWhenCreateWebPaySessionThenShouldReturnPaySession() throws PaySessionException {
        PaySession paySession = Mockito.mock(PaySession.class);
        CreatePaySessionRequest request = Mockito.mock(CreatePaySessionRequest.class);
        Mockito.when(request.getSaleItems()).thenReturn(Arrays.asList());
        Mockito.when(entityBuilderMock.mapRequestToPaySession(request)).thenReturn(paySession);

        PaySession result = paymentSessionService.createWebPaySession(request);

        assertNotEquals(null, result);
    }

    @Test
    void givenValidPaySessionIdWhenGetShoppingCartThenShouldReturnValidPaySession() throws PaySessionException {
        String uuid = UUID.randomUUID().toString();
        PaySession paySession = new PaySession();
        Optional<PaySession> paySessionOptional = Optional.of(paySession);
        Mockito.when(paySessionRepositoryMock.findById(any())).thenReturn(paySessionOptional);

        PaySession result = paymentSessionService.getShoppingCart(uuid);

        assertEquals(paySession, result);
    }

    @Test
    void givenWrongPaySessionIdWhenGetShoppingCartThenShouldThrowPaySessionException() {
        String uuid = UUID.randomUUID().toString();
        Optional<PaySession> paySessionOptional = Optional.empty();
        Mockito.when(paySessionRepositoryMock.findById(any())).thenReturn(paySessionOptional);

        assertThrows(PaySessionException.class, () -> paymentSessionService.getShoppingCart(uuid));
    }

    @Test
    void givenExpiredPaySessionWhenCheckTtlExpiredThenExpire() throws PaySessionException {
        PaySession paySession = Mockito.mock(PaySession.class);
        Mockito.when(paySession.getStatus()).thenReturn(PaySessionStatus.CREATED);
        Mockito.when(paySession.getSessionTimeToLive()).thenReturn(0);
        Mockito.when(paySession.getCreatedDate()).thenReturn(LocalDateTime.now().minusHours(10));
        ArgumentCaptor<PaySessionStatus> args = ArgumentCaptor.forClass(PaySessionStatus.class);

        paymentSessionService.checkTtlExpired(paySession);

        Mockito.verify(paySession, Mockito.times(1)).setStatus(any());
        Mockito.verify(paySession).setStatus(args.capture());
        assertEquals(PaySessionStatus.EXPIRED, args.getValue());
    }
}