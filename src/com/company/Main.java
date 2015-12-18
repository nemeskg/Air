package com.company;

import parser.AirportFinder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter first country/code");
                String countryA = reader.readLine();

                System.out.println("Enter second country/code");
                String countryB = reader.readLine();

                AirportFinder.find(countryA);
                AirportFinder.find(countryB);

                AirportFinder.closeWriter();

    }
}
