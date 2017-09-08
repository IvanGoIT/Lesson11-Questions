import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicSplittingOnThreadsExample {
    static final String DONE = "ГОТОВО!";
    static final Random r = new Random();

    static List<Integer> generate(int size) {
        System.out.print("Генерируем лист ... \t\t");
        List<Integer> list = new ArrayList<>(size);
        for(int i = 0; i < size; i++)
            list.add(r.nextInt(10));
        System.out.println(DONE);
        System.out.println();
        return list;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = generate(100000000);

        long time = System.currentTimeMillis();

        System.out.print("Сумируем в 1 поток ... \t\t");
        time = System.currentTimeMillis();
        long singleThreadSum = getSum(list);
        long singleThreadTime = System.currentTimeMillis() - time;
        System.out.println(DONE);
        System.out.println("\tСумма = " + singleThreadSum);
        System.out.println("\tВремя = " + singleThreadTime);
        System.out.println();

        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.printf("Сумируем в %d потоков ... \t", coreCount);
        time = System.currentTimeMillis();
        long multipleThreadsSum = getMultipleThreadsSum(list, coreCount);
        long multipleThreadsTime = System.currentTimeMillis() - time;
        System.out.println(DONE);
        System.out.println("\tСумма = " + multipleThreadsSum);
        System.out.println("\tВремя = " + multipleThreadsTime);
    }

    static long getMultipleThreadsSum(List<Integer> list, int coreCount) throws InterruptedException {
        List<Thread> threads = new ArrayList<>(coreCount);
        final long[] sums = new long[coreCount];

        for(int i = 0; i < coreCount; i++) {
            int length = list.size() / coreCount;
            final int left = length * i;
            final int right = left + length;
            final int index = i;

            Thread thread = new Thread(()->{
                sums[index] = getSum(list, left, right);
            });
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) thread.join();

        long totalSum = 0;
        for (long sum : sums) totalSum += sum;

        return totalSum;
    }

    static long getSum(List<Integer> list) {
        return getSum(list, 0, list.size());
    }

    static long getSum(List<Integer> list, int left, int right) {
        long sum = 0;
        for(int i = left; i < right; i++)
            sum += Math.sqrt(list.get(i)) + Math.sqrt(list.get(i)) + Math.sqrt(list.get(i));
        return sum;
    }
}
