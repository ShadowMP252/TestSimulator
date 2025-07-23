
import java.util.Scanner;
import java.util.List;
import java.util.Collections;

import java.lang.Runtime;
import java.lang.Thread;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    @SuppressWarnings("resource")
    
    public static void main(String[] args) {
        int correct = 0;
        int incorrect = 0;
        int total = 0;
        Process linuxProcess = null;
        long javaPid = ProcessHandle.current().pid();

    
        System.out.println("\u001B[32mWelcome to the WGU OA Test Simulator!\u001B[0m");

        // Load flashcards from JSON file
        Scanner scanner = new Scanner(System.in);
        System.out.println("\u001B[31mEnter the absolute filePath to the questions file: \u001B[0m");
        String filePath = scanner.nextLine();
        System.out.println();

        // System.out.println("[DEBUG] File Name: " + filePath);
        
        if (filePath.startsWith("~")) {
            filePath = filePath.replaceFirst("~", System.getProperty("user.home"));
        }

        List<Generator> questions = Generator.loadFlashcardsFromJson(filePath);
        if (questions.isEmpty()) {
            System.out.println("\u001B[31mNo questions found. Please check the file path.\u001B[0m \n");
            return;
        }
        Collections.shuffle(questions);

        // Get Operating System
        System.out.println("\u001B[34mChoose your operating system?\u001B[0m");
        System.out.println("1) Linux");
        System.out.println("2) Windows");
        int osInput = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        if (osInput == 2) {
            // Windows OS
            try {
                System.out.println("\u001B[33mYou will be presented with a series of questions. Please answer them by entering the number corresponding to your choice.\u001B[0m");
        
                System.out.println("\u001B[31mPress [Enter] to begin or type 'exit' to quit...\u001B[0m");
                String cmdBtn = scanner.nextLine();
                if (cmdBtn.equalsIgnoreCase("exit")){
                    System.exit(1);
                } else {
                    System.out.println("Get ready...");
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                String script = "scripts/timer.ps1";
                long windowsPid = ProcessHandle.current().pid();
                String timerPath = new File(script).getAbsolutePath();
                File timerScript = new File(timerPath);

                if (!timerScript.exists()) {
                    System.err.println("[Timer] PowerShell timer script not found: " + timerPath);
                    System.exit(1);
                }

                ProcessBuilder windowProcessBuilder = new ProcessBuilder(
                    "powershell",
                    "-WindowStyle", "Hidden",
                    "-ExecutionPolicy", "Bypass",
                    "-File", timerPath,
                    String.valueOf(windowsPid)
                );
                windowProcessBuilder.redirectOutput(ProcessBuilder.Redirect.DISCARD);
                windowProcessBuilder.redirectError(ProcessBuilder.Redirect.DISCARD);
                windowProcessBuilder.start();
                System.out.println("[Timer] Timer script launched in background (Windows).");

            } catch (Exception e) {
                System.err.println("[Timer] Failed to launch PowerShell timer script.");
                e.printStackTrace();
            }
        } else {
            // Linux OS
            try {
                System.out.println("\u001B[33mYou will be presented with a series of questions. Please answer them by entering the number corresponding to your choice.\u001B[0m");
        
                System.out.println("\u001B[31mPress [Enter] to begin or type 'exit' to quit...\u001B[0m");
                String cmdBtn = scanner.nextLine();
                if (cmdBtn.equalsIgnoreCase("exit")){
                    System.exit(1);
                } else {
                    System.out.println("Get ready...");
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                File script = new File("scripts/timer.sh");

                // 1. Check if script exists and is executable
                if (!script.exists()) {
                    System.err.println("[Timer] timer.sh does not exist at path: " + script.getAbsolutePath());
                    return;
                }
                if (!script.canExecute()) {
                    System.err.println("[Timer] timer.sh is not executable. Run `chmod +x` on it.");
                    return;
                }

                Process checkProcess = new ProcessBuilder("pgrep", "-f", "timer.sh").start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(checkProcess.getInputStream()));
                String existingPid = reader.readLine();
                if (existingPid != null && !existingPid.isEmpty()) {
                    System.out.println("[Timer] Timer is already running. PID: " + existingPid);
                    return;
                }

                linuxProcess = new ProcessBuilder("bash", "scripts/timer.sh", String.valueOf(javaPid))
                    .redirectErrorStream(true)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .start();
                System.out.println("[Timer] Timer started silently in background.");

                Process finalTimer = linuxProcess;
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (finalTimer.isAlive()) {
                        finalTimer.destroy();
                        System.out.println("[Timer] Timer process terminated on shutdown.");
                    }
                }));
    
            } catch (IOException e) {
                System.err.println("[Timer] Failed to launch timer.");
                e.printStackTrace();
            }
        }

        System.out.println(); // Empty line for styling 
        System.out.println("Let's begin!\n");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e){
            System.err.println("Sleep was interrupted: " + e);
            Thread.currentThread().interrupt();
        }

        System.out.print("\033[H\033[2J");  
        System.out.flush();

        // Loop through Flashcards.
        for (Generator generator : questions) {
            generator.shuffleAnswers(); // Shuffle answers for each flashcard
            System.out.println(generator.question);
            System.out.println();
            for (int i = 0; i < generator.answers.length; i++) {
                System.out.println((i + 1) + ". " + generator.answers[i]);
            }

            System.out.println();
            
            int userAnswer = -1;
            while (true){
                System.out.print("Your answer (number): ");
                if (scanner.hasNextInt()){
                    userAnswer = scanner.nextInt();
                    scanner.nextLine(); // Consume newline 
                    if (userAnswer == generator.correctAnswer) {
                        correct++;
                    } else {
                        incorrect++;
                    }
                    total++;
                    System.out.println();
                    break; // Exit the loop if a valid answer is given
                } else {
                    String badInput = scanner.next();
                    System.out.println("'" + badInput + "' is not a valid number. Try Again.");
                }
            }
        }
        // Display results
        System.out.println("\u001B[34mQuiz completed!\u001B[0m");
        System.out.println("\u001B[1mTotal questions:\u001B[0m " + "\u001B[33m" + total + "\u001B[0m");
        System.out.println("\u001B[1mCorrect answers:\u001B[0m " + "\u001B[33m" + correct + "\u001B[0m");
        System.out.println("\u001B[1mIncorrect answers:\u001B[0m " + "\u001B[33m" + incorrect + "\u001B[0m");
        double score = (double) correct / total * 100;
        System.out.printf("Your score: \u001B[33m%.2f%%\u001B[0m%n", score);
        if (score > 75) {
            System.out.println("\u001B[32mYou passed the quiz!\u001B[0m");
        } else {
            System.out.println("\u001B[31mYou did not pass the quiz. Better luck next time!\u001B[0m");
        }
        scanner.close();
        if (linuxProcess != null && linuxProcess.isAlive()) {
            linuxProcess.destroy();
            System.out.println("[Timer] Timer process terminated.");
        }

    }
}
