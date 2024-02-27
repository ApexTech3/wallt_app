package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.dtos.CardDto;
import org.springframework.stereotype.Component;
@Component
public class CardMapper {

    public Card fromDto(CardDto cardDto) {
        Card newCard = new Card();
        newCard.setCardHolderName(cardDto.getCardHolderName());
        newCard.setNumber(cardDto.getCardNumber());
        newCard.setExpirationMonth(cardDto.getExpirationMonth());
        newCard.setExpirationYear(cardDto.getExpirationYear());
        newCard.setCvv(cardDto.getCvv());
        return newCard;
    }

    public static CardDto toDto(Card card) {
        CardDto newCardDto = new CardDto();
        newCardDto.setCardHolderName(card.getCardHolderName());
        newCardDto.setCardNumber(card.getNumber());
        newCardDto.setExpirationYear(card.getExpirationYear());
        newCardDto.setExpirationMonth(card.getExpirationMonth());
        newCardDto.setCvv(card.getCvv());
        return newCardDto;
    }
}
