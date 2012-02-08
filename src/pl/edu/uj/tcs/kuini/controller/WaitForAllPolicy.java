package pl.edu.uj.tcs.kuini.controller;

import java.util.concurrent.Semaphore;

public class WaitForAllPolicy implements TempoPolicyInterface {

    private final int N;
    private final Semaphore semaphore;
    
    public WaitForAllPolicy(int N) {
        this.N = N;
        this.semaphore = new Semaphore(N);
    }
    
    @Override
    public void ready(int playerId) {
        semaphore.release(1);
    }

    @Override
    public void nextTurn() throws InterruptedException {
        semaphore.acquire(N);
    }

}
