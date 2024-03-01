package com.apex.tech3.wallt_app.controllers.dummy;

import com.apex.tech3.wallt_app.models.dtos.CardDetails;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@RestController
@RequestMapping("/dummy")
public class DummyCardController {
    @PostMapping("/payments")
    public boolean tryPay(@RequestBody CardDetails cardDetails) {
        String dateStr = cardDetails.getExpirationMonth() + "/" + cardDetails.getExpirationYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth parsedDate = YearMonth.parse(dateStr, formatter);
        if (parsedDate.isBefore(YearMonth.now())) {
            return false;
        }
        Random random = new Random();
        return random.nextInt(3) != 0;
    }
}
