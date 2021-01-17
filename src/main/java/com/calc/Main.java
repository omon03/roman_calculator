package com.calc;

import com.calc.enums.RomanDigit;
import com.calc.enums.TypeDigit;
import com.calc.exeptions.LineIsNotIllegalException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, LineIsNotIllegalException, IllegalArgumentException {

        BufferedReaderSingleton reader = BufferedReaderSingleton.getInstance();

        printHello();
        System.out.println("Введи пример:");

        while (true) {
            String line = reader.readLine();
            Parser parser = new Parser();
            parser.parseLine(line);
            int result = solveProblem();
            printResult(result, MathProblem.getInstance().getTypeDigit());
            System.out.println("Введи пример:");
        }
    }

    private static void printHello() {
        String hello = "Привет!\n" +
                "Я калькулятор.\n" +
                "Умею выполнять операции сложения, вычитания, умножения и деления с двумя числами: " +
                "a + b, a - b, a * b, a / b.\n" +
                "Данные вводи в одну строку (смотрите пример)!\n" +
                "Умею работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.\n" +
                "Принимаю на вход целые числа от 1 до 10 включительно, не более.\n" +
                "Умею работать либо только с арабскими либо только с римскими цифрами одновременно.\n";

        System.out.println(hello);
    }

    /**
     * Получить решение
     * @return результат решения примера
     * @throws LineIsNotIllegalException -
     */
    private static int solveProblem() throws LineIsNotIllegalException {
        MathProblem mathProblem = MathProblem.getInstance();
        int number1 = mathProblem.getNumber1();
        int number2 = mathProblem.getNumber2();
        int result;

        switch (mathProblem.getOperator()) {
            case PLUS: {
                result = number1 + number2;
                break;
            }
            case MINUS: {
                result = number1 - number2;
                break;
            }
            case MULTIPLY: {
                result = number1 * number2;
                break;
            }
            case DIVIDE: {
                try {
                    result = number1 / number2;
                    break;
                } catch (NullPointerException nullPointerException) {
                    throw new LineIsNotIllegalException("Не дели на \"0\"!");
                }
            }
            default:
                throw new IllegalStateException("Unexpected value: " + mathProblem.getOperator());
        }

        return result;
    }

    private static void printResult(int result, TypeDigit typeDigit) {

        if (typeDigit.equals(TypeDigit.ARABIC)) {
            System.out.println(result);
            return;
        }

        System.out.println(getRomanString(result));
    }
    
    // TODO убрать ночной хард-код
    private static String getRomanString(int romanInt) {

        StringBuffer result = new StringBuffer();
        int num = romanInt;
        RomanDigit[] romanDigits = RomanDigit.values();

        for (int i = romanDigits.length - 1; i >= 0; i--) {

        }

        for (int i = 0; i < romanInt / RomanDigit.M.getInt(); i++) {
            result.insert(result.length(), RomanDigit.M.getChar());
        }
        for (int i = 0; i < romanInt / RomanDigit.D.getInt(); i++) {
            result.insert(result.length(), RomanDigit.D.getChar());
        }
        for (int i = 0; i < romanInt / RomanDigit.C.getInt(); i++) {
            result.insert(result.length(), RomanDigit.C.getChar());
        }
        for (int i = 0; i < romanInt / RomanDigit.L.getInt(); i++) {
            result.insert(result.length(), RomanDigit.L.getChar());
        }
        for (int i = 0; i < romanInt / RomanDigit.X.getInt(); i++) {
            result.insert(result.length(), RomanDigit.X.getChar());
        }
        for (int i = 0; i < romanInt / RomanDigit.V.getInt(); i++) {
            result.insert(result.length(), RomanDigit.V.getChar());
        }
        for (int i = 0; i < romanInt / RomanDigit.I.getInt(); i++) {
            result.insert(result.length(), RomanDigit.I.getChar());
        }

        return result;
    }
}
