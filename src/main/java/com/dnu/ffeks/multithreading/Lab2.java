package com.dnu.ffeks.multithreading;

import com.dnu.ffeks.lab2.passwordvalidation.EmptyRowException;
import com.dnu.ffeks.lab2.passwordvalidation.PasswordComplexityChecker;
import com.dnu.ffeks.lab2.passwordvalidation.PasswordComplexityCheckerImpl;
import com.dnu.ffeks.lab2.passwordvalidation.PasswordValidator;
import com.dnu.ffeks.lab2.passwordvalidation.PasswordValidatorImpl;
import com.dnu.ffeks.lab2.passwordvalidation.UtilityClass;
import com.dnu.ffeks.lab2.passwordvalidation.WeakPasswordException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Перевірити, чи надійно складений пароль. Пароль уважається надійним, якщо він складається з 8 або більш символів.
 * Де символом може бути англійська буква, цифра й знак підкреслення. Пароль повинен містити хоча б одну заголовну букву, одну маленьку букву й одну цифру.
 * Приклад правильних виражень: C00l_Pass, Supperpas1.
 * Приклад неправильних виражень: Cool_pass, C00l
 */


public class Lab2 {

    /*
    ^				        matches the beginning of the line
    (?=(.*\d)) 		        a look-ahead expression, requires at least one digit to be present
    .*				        matches n characters, where n >= 0
    (?=.*[A-Z])(?=.*[a-z])  a capturing group of look-ahead expressions, requires at least one uppercase letter and
                            one lowercase letter to be present
    [\da-zA-Z_]	            match any numbers or letters or underline symbol
    {8,}				    matches at least 8 symbols
    $				        matches the end of the line
    */
    private static final String DEFAULT_PASSWORD = "default";

    public static void main(String [] args) throws IOException {

        Runner runner = new Runner();
        runner.isPasswordStrong(DEFAULT_PASSWORD);
    }

    public static class Runner {

        public boolean isPasswordStrong(String password) throws IOException {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter the password:");
            password = input.readLine();


            try {
                UtilityClass.checkPasswordExistence(password);
            } catch (com.dnu.ffeks.lab2.passwordvalidation.EmptyRowException exc) {
                System.err.println("Password '" + password + "' is invalid. Try again: ");
                return isPasswordStrong(password);
            }

            PasswordComplexityChecker complexityChecker = new PasswordComplexityCheckerImpl(password);
            PasswordValidator validator = new PasswordValidatorImpl(complexityChecker);

            try {
                complexityChecker.checkEmptiness();
                validator.validate();
            } catch (WeakPasswordException | EmptyRowException exc) {
                System.err.printf("The password '%s' is weak. Try again: %n", password);
                return isPasswordStrong(password);
            }

            System.out.printf("The password '%s' is strong!", password);
            return true;
        }
    }

    public static void forMultithreading() throws IOException {

        Runner runner = new Runner();
        runner.isPasswordStrong(DEFAULT_PASSWORD);
    }
}

/**
 * Группа с положительной опережающей проверкой (positive lookahead assertion).
 * Продолжает поиск только если справа от текущей позиции в тексте находится заключённое в скобки выражение.
 * При этом само выражение не захватывается. Например, говор(?=ить) найдёт «говор» в «говорить», но не в «говорит».
 * Иными словами, ищет в строке «говор», после которого сразу идут символы «ить» — если находит, выдает истину,
 * иначе — ложь (FALSE).
 */
