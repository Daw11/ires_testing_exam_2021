package it.euris.ires.service.util;

import it.euris.ires.dataObject.CreatePaySessionRequest;
import it.euris.ires.dataObject.SaleItem;
import it.euris.ires.entity.Item;
import it.euris.ires.entity.PaySession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RequestToEntityMapperTest {

    RequestToEntityMapper requestToEntityMapper;

    @BeforeEach
    void setUp(){
        requestToEntityMapper = new RequestToEntityMapper();
    }

    @Test
    void givenCreatePaySessionRequestWhenMapRequestToPaySessionThenReturnPaySession() {
        CreatePaySessionRequest request = new CreatePaySessionRequest();

        PaySession result = requestToEntityMapper.mapRequestToPaySession( request );

        assertNotEquals(null, result);
    }

    @Test
    void givenSaleItemAndUuidWhenMapSaleItemToItemThenReturnItem() {
        SaleItem lineItem = new SaleItem();
        UUID uuid = UUID.randomUUID();

        Item result = requestToEntityMapper.mapSaleItemToItem(lineItem, uuid);

        assertNotEquals(null, result);
    }
}