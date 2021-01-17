package com.calc;

import com.calc.enums.Operator;
import com.calc.enums.RomanDigit;
import com.calc.enums.TypeDigit;
import com.calc.exeptions.LineIsNotIllegalException;

public class Parser {

    /**
     * Парсит и заполняет поля MathProblem
     * @param line строка с цифрами и оператором действия
     * @throws IllegalArgumentException -
     * @throws LineIsNotIllegalException -
     */
    public void parseLine(String line) throws IllegalArgumentException, LineIsNotIllegalException {

        line = line.toUpperCase().replace(" ", "");
        checkChars(line);

        MathProblem mathProblem = MathProblem.getInstance();
        mathProblem.setTypeDigit(parseTypeDigit(line));
        mathProblem.setOperator(parseOperator(line));

        char operatorChar = mathProblem.getOperator().getOperatorChar();
        int operatorPosition = line.indexOf(String.valueOf(operatorChar));

        mathProblem.setNumber1(parseDigit(mathProblem.getTypeDigit(), line, 0, operatorPosition));
        mathProblem.setNumber2(parseDigit(mathProblem.getTypeDigit(), line, operatorPosition + 1, line.length()));
    }

    private int parseDigit(
            TypeDigit typeDigit,
            String line,
            int start, int stop) throws LineIsNotIllegalException, NumberFormatException {

        if (typeDigit.equals(TypeDigit.ARABIC))
            return parseArabic(line, start, stop);

        return parseRoman(line, start, stop);
    }

    // TODO: check
    /**
     * Получить int из римской цифры
     * @param line строка с римской цифрой
     * @param start индекс строки начала цифры
     * @param stop индекс строки перед которым цифра заканчивается
     * @return int из римской цифры
     * @throws LineIsNotIllegalException -
     */
    private int parseRoman(String line, int start, int stop) throws LineIsNotIllegalException {

        final int repeatMax = 3;
        int result = 0;
        int repeat = 1;
        int previousDigit;
        int currentDigit = 0;
        int repeatedArg;

        for (int i = start; i < stop; i++) {
            previousDigit = currentDigit;
            currentDigit = RomanDigit.valueOf(String.valueOf(line.charAt(i))).getInt();

            if (previousDigit == currentDigit) {
                repeat++;
            }
            else {
                repeatedArg = previousDigit * repeat;
                repeat = 1;

                if (previousDigit < currentDigit) {
//                    currentDigit -= repeatedArg;
                    result = currentDigit - repeatedArg;
                } else {
                    result += previousDigit;
                }
            }

            if (repeatMax < repeat) throw new LineIsNotIllegalException("Incorrect number notation!");

            if (previousDigit >= currentDigit) {
                result += currentDigit;
            }
        }

        if (result > 10 || result < 1)
            throw new LineIsNotIllegalException("The number must be between 1 and 10!");

        return result;
    }

    private int parseArabic(String line, int start, int stop) throws LineIsNotIllegalException, NumberFormatException {
        int result = 0;

        for (int i = start; i < stop; i++)
            result = result * (10 ^ i) + Integer.parseInt(String.valueOf(line.charAt(i)));

        if (result > 10 || result < 1)
            throw new LineIsNotIllegalException("The number must be between 1 and 10!");

        return result;
    }

    private Operator parseOperator(String line) throws LineIsNotIllegalException {
        Operator operator = null;

        for (char ch : line.toCharArray()) {
            if (operator != null && isOperator(ch))
                throw new LineIsNotIllegalException();

            if (isOperator(ch))
                operator = Operator.valueOf(String.valueOf(ch));
        }

        if (operator == null)
            throw new LineIsNotIllegalException("Operator not found!");

        return operator;
    }

    private TypeDigit parseTypeDigit(String line) throws LineIsNotIllegalException {
        TypeDigit typeDigit = null;
        TypeDigit localTypeDigit;

        for (char ch : line.toCharArray()) {
            if (isDigit(ch))
                localTypeDigit = TypeDigit.valueOf(String.valueOf(ch));
            else continue;

            if (typeDigit == null)
                typeDigit = localTypeDigit;

            if (typeDigit != localTypeDigit) {
                throw new LineIsNotIllegalException();
            }
        }

        if (typeDigit == null)
            throw new LineIsNotIllegalException("Digit is not found!");

        return typeDigit;
    }


    private boolean isSpace(char ch) {
        return ch == ' ';
    }

    private boolean isOperator(char ch) {
        String chString = String.valueOf(ch);

        try {
            Operator.valueOf(chString);
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }

        return true;
    }

    private boolean isIntegerDigit(char ch) {
        String chString = String.valueOf(ch);

        try {
            Integer.parseInt(chString);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }

        return true;
    }

    private boolean isRomanDigit(char ch) {
        String chString = String.valueOf(ch);

        try {
            RomanDigit.valueOf(chString);
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }

        return true;
    }

    private boolean isDigit(char ch) {
        return isIntegerDigit(ch) || isRomanDigit(ch);
    }


    /**
     * Проверить символ
     * @param ch символ для проверки
     * @return true if ch is a valid, else throw IllegalArgumentException
     * @throws IllegalArgumentException -
     */
    private void checkChar(char ch) throws IllegalArgumentException {

        if ( isSpace(ch) || isOperator(ch) || isIntegerDigit(ch))
            return;

        RomanDigit.valueOf(String.valueOf(ch));
    }

    /**
     * Проверить все символы в строке.
     * @param line строка математического примера
     * @throws IllegalArgumentException -
     * @throws LineIsNotIllegalException -
     */
    private void checkChars(String line) throws IllegalArgumentException, LineIsNotIllegalException {
        if (line.isEmpty())
            throw new LineIsNotIllegalException("Line is empty!");

        for (char ch : line.toCharArray())
            checkChar(ch);
    }
}
