package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.CocktailBase;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
@Service
public class CocktailServiceImpl implements CocktailService {

    private final TransformationGateway cocktailGateway;

    public void startGenerateCocktailBaseLoop() {

        ForkJoinPool pool = ForkJoinPool.commonPool();

        for (int i = 0; i < new Random().nextInt(4, 30); i++) {

            pool.execute(() -> cocktailGateway.process(generateCocktailBase()));
            delay();
        }
    }

    private CocktailBase generateCocktailBase() {

        CocktailBase[] cocktailBases = CocktailBase.values();
        int cocktailBaseIndex = new Random().nextInt(0, cocktailBases.length);

        return cocktailBases[cocktailBaseIndex];
    }

    private void delay() {

        try {
            Thread.sleep(new Random().nextInt(500, 5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
