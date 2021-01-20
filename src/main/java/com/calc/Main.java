package com.calc;

import com.calc.enums.RomanDigit;
import com.calc.enums.TypeDigit;
import com.calc.exeptions.LineIsNotIllegalException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.Arrays;
import java.util.List;

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
            System.out.println("Ответ: " + result);
            return;
        }

        System.out.println("Ответ: " + getRomanString(result));
    }
    
    private static String getRomanString(int romanInt) {

        if ((romanInt <= 0) || (romanInt > 4000)) {
            throw new IllegalArgumentException(romanInt + " выходит за предел римских чисел.");
        }

        List<RomanDigit> romanNumerals = RomanDigit.getRevSortValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((romanInt > 0) && (i < romanNumerals.size())) {
            RomanDigit currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getInt() <= romanInt) {
                sb.append(currentSymbol.name());
                romanInt -= currentSymbol.getInt();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
