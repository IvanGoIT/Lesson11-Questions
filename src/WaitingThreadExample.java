public class WaitingThreadExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Thread id [%d], started\n", Thread.currentThread().getId());
        System.out.printf("Thread id [%d], additional thread creating\n", Thread.currentThread().getId());

        Thread thread = new Thread(()->{
            System.out.printf("Thread id [%d], started\n", Thread.currentThread().getId());

            long x = 0;
            for(int i = 0; i < 10000000; i++) x += i;

            System.out.printf("Thread id [%d], completed\n", Thread.currentThread().getId());
        });
        thread.start();

        System.out.printf("Thread id [%d], additional thread started\n", Thread.currentThread().getId());
        System.out.printf("Thread id [%d], waiting the thread\n", Thread.currentThread().getId());

        thread.join();

        System.out.printf("Thread id [%d], that thread is done\n", Thread.currentThread().getId());
    }
}
