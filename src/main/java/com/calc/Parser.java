package com.calc;

import com.calc.enums.Operator;
import com.calc.enums.RomanDigit;
import com.calc.enums.TypeDigit;
import com.calc.exeptions.LineIsNotIllegalException;

import java.util.List;

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

    /**
     * Получить int из римской цифры
     * @param line строка с римской цифрой
     * @param start индекс строки начала цифры
     * @param stop индекс строки перед которым цифра заканчивается
     * @return int из римской цифры
     * @throws LineIsNotIllegalException -
     */
    private int parseRoman(String line, int start, int stop) throws LineIsNotIllegalException {

//        final int repeatMax = 3;
        String romanNumeral = line.substring(start, stop);
        int result = 0;
        int i = 0;
        List<RomanDigit> romanNumerals = RomanDigit.getRevSortValues();

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanDigit symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getInt();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(line + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    private int parseArabic(String line, int start, int stop) throws LineIsNotIllegalException, NumberFormatException {
        int result = 0;

        for (int i = start; i < stop; i++) {
            int digit = Integer.parseInt(String.valueOf(line.charAt(i)));
            int offset = (i > 0) ? 10 : 1;
            result *= offset;
            result += digit;
        }

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
                operator = Operator.getOperator(ch);
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
                localTypeDigit = (isRomanDigit(ch)) ? TypeDigit.ROMAN : TypeDigit.ARABIC;
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

        for (Operator operator : Operator.values()) {
            if (String.valueOf(operator.getOperatorChar()).equals(chString))
                return true;
        }

        return false;
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

        for (RomanDigit rd :
                RomanDigit.values()) {
            if (rd.name().equals(chString)) return true;
        }

        return false;
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
