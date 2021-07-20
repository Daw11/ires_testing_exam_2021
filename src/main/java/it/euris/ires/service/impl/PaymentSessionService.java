package it.euris.ires.service.impl;

import static it.euris.ires.util.PaySessionStatus.CREATED;

import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.database.IPaySessionRepository;
import it.euris.ires.entity.Item;
import it.euris.ires.entity.PaySession;
import it.euris.ires.exception.PaySessionException;
import it.euris.ires.service.IPaymentSessionService;
import it.euris.ires.service.util.RequestToEntityMapper;
import it.euris.ires.util.Amount;
import it.euris.ires.util.PaySessionStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PaymentSessionService implements IPaymentSessionService {

	private final IPaySessionRepository paySessionRepository;

	private final RequestToEntityMapper entityBuilder;

	private final Amount amount;

	public PaymentSessionService(IPaySessionRepository paySessionRepository, RequestToEntityMapper entityBuilder, Amount amount) {
		this.paySessionRepository = paySessionRepository;
		this.entityBuilder = entityBuilder;
		this.amount = amount;
	}

	@Override
	public PaySession createWebPaySession(CreatePaySessionRequest request) throws PaySessionException {
		PaySession paySession = entityBuilder.mapRequestToPaySession(request);
		paySession.setStatus(CREATED);
		List<Item> lineItems = request.getSaleItems().stream()
				.map((saleItem) ->  entityBuilder.mapSaleItemToItem(saleItem, paySession.getUuid()))
				.collect(Collectors.toList());;
		paySession.setItems(lineItems);
		paySession.setItemAmount(calculateItemAmount(paySession));
		paySession.setPaySessionAmount(calculateTotalAmount(paySession));
		savePaySession(paySession);
		return paySession;
	}

	private String calculateItemAmount(PaySession paySession) {
		List<String> itemPrices = paySession.getItems().stream().map(Item::getPrice).collect(Collectors.toList());
		amount.initAmount(paySession.getCurrency());
		return amount.sum(itemPrices);
	}

	private String calculateTotalAmount(PaySession paySession) {
		List<String> amounts = new ArrayList<>();
		amounts.add(paySession.getItemAmount());
		amounts.add(paySession.getTaxAmount());
		amount.initAmount(paySession.getCurrency());
		return amount.sum(amounts);
	}

	@Override
	public PaySession getShoppingCart(String paySessionId) throws PaySessionException {
		Optional<PaySession> paySessionFound = paySessionRepository.findById(UUID.fromString(paySessionId));
		if (!paySessionFound.isPresent()) {
			return paySessionFound.get();
		} else {
			throw new PaySessionException(String.format("Pay-session id %s does not exist", paySessionId));
		}
	}

	@Override
	public void checkTtlExpired(PaySession paySession) throws PaySessionException {
		if (isTTLExpired(paySession)) {
			if (!paySession.getStatus().equals(PaySessionStatus.PAID)) {
				paySession.setStatus(PaySessionStatus.EXPIRED);
			}
			savePaySession(paySession);
		}
	}

	private boolean isTTLExpired(PaySession paySession) {
		long timeToLive = paySession.getSessionTimeToLive();
		return LocalDateTime.now().isAfter(paySession.getCreatedDate().plusHours(timeToLive));
	}

	@Override
	public void savePaySession(PaySession paySession) throws PaySessionException {
		try {
			paySessionRepository.save(paySession);
		}
		catch (IllegalAccessException e) {
			throw new PaySessionException("Access denied to database");
		}
		catch (Exception e) {
			throw new PaySessionException("Unmanaged error");
		}
	}

}
