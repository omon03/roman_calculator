package com.calc.enums;

public enum Operator {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/');

    private final char operator;

    Operator(char operator) {
        this.operator = operator;
    }

    public char getOperatorChar() {
        return this.operator;
    }

    @Override
    public String toString() {
        return String.valueOf(getOperatorChar());
    }

    public static Operator getOperator(char ch) {
        for (Operator operator :
                Operator.values()) {
            if (operator.getOperatorChar() == ch) return operator;
        }

        throw new IllegalArgumentException();
    }
}
