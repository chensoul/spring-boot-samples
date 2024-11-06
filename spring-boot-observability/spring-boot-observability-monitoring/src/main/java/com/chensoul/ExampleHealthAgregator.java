package com.chensoul;

import org.springframework.boot.actuate.health.SimpleStatusAggregator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class ExampleHealthAgregator extends SimpleStatusAggregator {

    public ExampleHealthAgregator() {
        super(Status.OUT_OF_SERVICE, Status.DOWN, Status.UP, Status.UNKNOWN);
    }

}
