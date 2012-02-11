package pl.edu.uj.tcs.kuini.model;

import java.util.Random;

public class RandomGenerator extends Random {
    
    private static final long serialVersionUID = 6923853760013510355L;
   
    private static final int A = 1597;
    private static final int B = 93563;
    private static final int N = 1046527;
    private static final int L = 2011;
    
    private int x = 0;
    
    @Override 
    public float nextFloat() {
        x = (A * x + B) % N;        
        return (x % L) / (float)(L-1);
    }
    
}
