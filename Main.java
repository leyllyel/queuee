import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    private static ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        Thread generatorThread = new Thread(() -> {
            String letters = "abc";
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                StringBuilder text = new StringBuilder();
                for (int j = 0; j < 100000; j++) {
                    text.append(letters.charAt(random.nextInt(letters.length())));
                }
                try {
                    queueA.put(text.toString());
                    queueB.put(text.toString());
                    queueC.put(text.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadA = new Thread(() -> {
            int maxCount = 0;
            String maxText = "";
            try {
                while (true) {
                    String text = queueA.take();
                    int count = countCharacter(text, 'a');
                    if (count > maxCount) {
                        maxCount = count;
                        maxText = text;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Максимальное количество символов 'a' в строке: " + maxCount);
            System.out.println(maxText);
        });

        Thread threadB = new Thread(() -> {
            int maxCount = 0;
            String maxText = "";
            try {
                while (true) {
                    String text = queueB.take();
                    int count = countCharacter(text, 'b');
                    if (count > maxCount) {
                        maxCount = count;
                        maxText = text;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Максимальное количество символов 'b' в строке: " + maxCount);
            System.out.println(maxText);
        });

        Thread threadC = new Thread(() -> {
            int maxCount = 0;
            String maxText = "";
            try {
                while (true) {
                    String text = queueC.take();
                    int count = countCharacter(text, 'c');
                    if (count > maxCount) {
                        maxCount = count;
                        maxText = text;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Максимальное количество символов 'c' в строке: " + maxCount);
            System.out.println(maxText);
        });

        generatorThread.start();
        threadA.start();
        threadB.start();
        threadC.start();
    }
    private static int countCharacter(String text, char ch) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }
}