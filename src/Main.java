import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "words.txt";
        ArrayList<String> words = new ArrayList<>();

        // Read file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return;
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file.");
            return;
        }

        if (words.isEmpty()) {
            System.out.println("The file is empty. Please add words to the file.");
            return;
        }

        Random random = new Random();
        String word = words.get(random.nextInt(words.size()));
        Scanner scanner = new Scanner(System.in);
        ArrayList<Character> wordState = new ArrayList<>();
        ArrayList<Character> guessedLetters = new ArrayList<>();
        int wrongGuesses = 0;

        for (int i = 0; i < word.length(); i++) {
            wordState.add('_');
        }

        System.out.println("************************");
        System.out.println("\uD83D\uDD7AWelcome to Java Hangman!");
        System.out.println("************************");

        while (wrongGuesses < 6) {
            System.out.print(getHangmanArt(wrongGuesses));
            System.out.println("Word: ");

            for (char c : wordState) {
                System.out.print(c + " ");
            }
            System.out.println();

            System.out.print("Guess a letter: ");
            String input = scanner.next().toLowerCase();
            if (input.length() != 1) {
                System.out.println("Please enter exactly one letter.");
                continue;
            }
            char guess = input.charAt(0);
            if (!Character.isLetter(guess)) {
                System.out.println("Please enter a valid letter.");
                continue;
            }
            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed that letter. Try again.");
                continue;
            }
            guessedLetters.add(guess);

            if (word.indexOf(guess) >= 0) {
                System.out.println("You guessed correctly!\n");

                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess) {
                        wordState.set(i, guess);
                    }
                }

                if (!wordState.contains('_')) {
                    System.out.println(getHangmanArt(wrongGuesses));
                    System.out.println("YOU WIN!");
                    System.out.println("The word was: " + word);
                    break;
                }
            } else {
                wrongGuesses++;
                System.out.println("Wrong guess!\n");
            }
        }

        if (wrongGuesses >= 6) {
            System.out.println(getHangmanArt(wrongGuesses));
            System.out.println("Game OVER!");
            System.out.println("The word was: " + word);
        }

        scanner.close();
    }

    static String getHangmanArt(int wrongGuesses) {
        return switch (wrongGuesses) {
            case 0 -> """
                    
                    
                    
                    """;
            case 1 -> """
                    o
                    
                    
                    """;
            case 2 -> """
                    o
                    |
                    
                    """;
            case 3 -> """
                     o
                    /|
                    
                    """;
            case 4 -> """
                    o
                   /|\\
                    
                    """;
            case 5 -> """
                    o
                   /|\\
                   /
                    """;
            case 6 -> """
                    o
                   /|\\
                   / \\
                    
                    """;
            default -> "";
        };
    }
}