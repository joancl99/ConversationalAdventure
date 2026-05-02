package com.adventure;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        GameManager gameManager = new GameManager(scanner);
        gameManager.startGame();
        scanner.close();
    }
}
