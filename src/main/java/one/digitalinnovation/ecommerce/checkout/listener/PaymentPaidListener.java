package one.digitalinnovation.ecommerce.checkout.listener;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.ecommerce.checkout.entity.CheckoutEntity;
import one.digitalinnovation.ecommerce.checkout.event.PaymentCreatedEvent;
import one.digitalinnovation.ecommerce.checkout.repository.CheckoutRepository;
import one.digitalinnovation.ecommerce.checkout.streaming.PaymentPaidSink;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPaidListener {

    private CheckoutRepository checkoutRepository;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event){
        final CheckoutEntity checkoutEntity= checkoutRepository.findByCode(event.getCheckoutCode().toString()).orElseThrow();
        checkoutEntity.setStatus(CheckoutEntity.STATUS.APPROVED);
        checkoutRepository.save(checkoutEntity);
        event.getCheckoutCode();
    }
}
