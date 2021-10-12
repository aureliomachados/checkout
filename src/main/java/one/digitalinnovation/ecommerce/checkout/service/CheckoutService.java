package one.digitalinnovation.ecommerce.checkout.service;

import one.digitalinnovation.ecommerce.checkout.entity.CheckoutEntity;
import one.digitalinnovation.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
}
