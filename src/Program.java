import java.util.Random;

public class Program {
    private final static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Создаем поток");
        new Thread(() -> {
            while (true) {
                int millisecondsToSleep = r.nextInt(4000) + 1000;
                try {
                    System.out.printf("%d поток засыпает на %d миллисекунд\n",
                            Thread.currentThread().getId(),
                            millisecondsToSleep);

                    Thread.sleep(millisecondsToSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("Запустили поток");
    }
}
