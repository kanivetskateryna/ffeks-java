package com.dnu.ffeks.lab2.ipvalidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Скласти регулярне вираження, що визначає чи є заданий рядок IP адресою, записаним у десятковому виді.
 * Приклад правильних виражень: 127.0.0.1, 255.255.255.0.
 * Приклад неправильних виражень: 1300.6.7.8, abc.def.gha.bcd.
 */
public class Task8 {

    public static void main(String[] args) throws IOException {
        Task8 task8 = new Task8();
        task8.start();
    }

    private void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        IpExtractor ipExtractor = new IpExtractorImpl(reader);
        ipExtractor.extractFromReader();
        String ip = ipExtractor.getIp();

        IpValidator ipValidator = new IpValidatorImpl(ip);
        ipValidator.validate();
    }
}

interface IpExtractor {

    void extractFromReader() throws IOException;

    String getIp();
}

class IpExtractorImpl implements IpExtractor {

    private final BufferedReader reader;
    private String ip;

    // without keyword "public" the visibility of constructor will be "package" [will be visible only in ipvalidation package]
    IpExtractorImpl(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void extractFromReader() throws IOException {
        System.out.println("Enter the IP:");
        ip = reader.readLine();
    }

    @Override
    public String getIp() {
        return ip;
    }
}

interface IpValidator {

    void validate();
}

class IpValidatorImpl implements IpValidator {
    /*
        ^		   matches the beginning of the line
       \d	       matches a digit (equal to [0-9])
       {1,3}      {1,3} Quantifier — Matches between 1 and 3 times, as many times as possible, giving back as needed (greedy)
       \.         matches the character . literally (case sensitive)
       $		\ matches the end of the line
   */
    private static final String IP_EXP = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
    private String ip;

    IpValidatorImpl(String ip) {
        this.ip = ip;
    }

    @Override
    public void validate() {
        if (isValid(ip)) {
            System.out.println("IP is valid!:)");
        } else {
            System.out.println("IP is not valid!");
        }
    }

    private static boolean isValid(String ip) {
        return ip.matches(IP_EXP);
    }
}
