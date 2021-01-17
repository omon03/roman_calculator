package com.calc;

import com.calc.enums.Operator;
import com.calc.enums.TypeDigit;

public class MathProblem {

    private static volatile MathProblem instance;

    private TypeDigit typeDigit;
    private Operator operator;
    private int number1;
    private int number2;

    public static MathProblem getInstance() {
        MathProblem localInstance = instance;

        if (localInstance == null) {
            synchronized (MathProblem.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new MathProblem();
            }
        }
        return localInstance;
    }

    public TypeDigit getTypeDigit() {
        return typeDigit;
    }

    public void setTypeDigit(TypeDigit typeDigit) {
        this.typeDigit = typeDigit;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }
}
