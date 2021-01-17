package com.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class BufferedReaderSingleton extends BufferedReader {

    private static volatile BufferedReaderSingleton instance;

    public BufferedReaderSingleton(Reader in) {
        super(in);
    }

    public static BufferedReaderSingleton getInstance() {
        BufferedReaderSingleton localInstance = instance;

        if (localInstance == null) {
            synchronized (BufferedReaderSingleton.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new BufferedReaderSingleton(new InputStreamReader(System.in));
            }
        }
        return localInstance;
    }
}
