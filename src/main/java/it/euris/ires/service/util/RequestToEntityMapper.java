package it.euris.ires.service.util;

import it.euris.ires.dataObject.BusinessData;
import it.euris.ires.dataObject.SaleItem;
import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.dataObject.PaySessionSettings;
import it.euris.ires.entity.Item;
import it.euris.ires.entity.PaySession;
import java.util.ArrayList;
import java.util.UUID;

public class RequestToEntityMapper {

	public PaySession mapRequestToPaySession(CreatePaySessionRequest request) {
		PaySession paySession = new PaySession();
		BusinessData businessData = request.getBusinessData();
		if (businessData != null) {
			paySession.setAccountId(businessData.getAccountId());
			paySession.setPaymentConfigId(businessData.getPaymentConfigId());
			paySession.setConsumerId(businessData.getConsumerId());
		}
		PaySessionSettings paySessionSettings = request.getPaySessionSettings();
		if (paySessionSettings != null) {
			paySession.setSessionTimeToLive(paySession.getSessionTimeToLive());
			paySession.setCollectBillingAddress(paySessionSettings.getCollectBillingAddress());
			paySession.setCollectShippingAddress(paySessionSettings.getCollectShippingAddress());
			paySession.setCurrency(paySessionSettings.getCurrency());
			paySession.setLanguage(paySessionSettings.getLanguage());
		}
		paySession.setItems(new ArrayList<>());
		paySession.setTaxAmount(paySession.getTaxAmount());
		return paySession;
	}

	public Item mapSaleItemToItem(SaleItem lineItem, UUID uuid) {
		Item item = new Item();
		item.setPaySessionId(uuid);
		item.setName(lineItem.getName());
		item.setDescription(lineItem.getDescription());
		item.setProductId(lineItem.getProductId());
		item.setVariantId(lineItem.getVariantId());
		item.setPrice(lineItem.getPrice());
		return item;
	}

}
