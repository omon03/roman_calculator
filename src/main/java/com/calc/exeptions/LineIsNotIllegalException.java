package com.calc.exeptions;

public class LineIsNotIllegalException extends Exception{

    public LineIsNotIllegalException(){
        super();
    }

    public LineIsNotIllegalException(String message) {
        super(message);
    }
}
