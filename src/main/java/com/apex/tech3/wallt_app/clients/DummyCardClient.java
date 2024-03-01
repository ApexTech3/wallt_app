package com.apex.tech3.wallt_app.clients;

import com.apex.tech3.wallt_app.models.dtos.CardDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "dummy-service",url = "localhost")
public interface DummyCardClient {
    @RequestMapping(method = RequestMethod.POST, value = "/dummy/payments")
    boolean tryPay(CardDetails cardDetails);
}
