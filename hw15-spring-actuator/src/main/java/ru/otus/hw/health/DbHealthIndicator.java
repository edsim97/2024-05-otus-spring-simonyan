package ru.otus.hw.health;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@AllArgsConstructor(onConstructor_ = @Autowired)
@Component
public class DbHealthIndicator implements HealthIndicator {

    private final EntityManager em;

    @Override
    public Health health() {

        try {

            em.createQuery("SELECT COUNT(a) FROM Author a", Long.class).getResultList();
            return Health.up()
                .withDetail("message", "Everything's OK")
                .build();
        } catch (Exception e) {

            return Health.down()
                .status(Status.DOWN)
                .withDetail("errorMessage", e.getMessage())
                .build();
        }
    }
}
