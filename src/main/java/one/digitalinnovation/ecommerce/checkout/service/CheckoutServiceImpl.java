package one.digitalinnovation.ecommerce.checkout.service;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.ecommerce.checkout.entity.CheckoutEntity;
import one.digitalinnovation.ecommerce.checkout.event.CheckoutCreatedEvent;
import one.digitalinnovation.ecommerce.checkout.repository.CheckoutRepository;
import one.digitalinnovation.ecommerce.checkout.resource.checkout.CheckoutRequest;
import one.digitalinnovation.ecommerce.checkout.streaming.CheckoutCreatedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService{

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {

        CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(UUID.randomUUID().toString())
                .status(CheckoutEntity.STATUS.CREATED)
                .build();

        CheckoutEntity entity = checkoutRepository.save(checkoutEntity);

        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());

        return Optional.of(entity);
    }
}
