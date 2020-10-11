class Main {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(3);
        pool.submit(() -> {
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " A");
            }
        });
        pool.submit(() -> {
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " B");
            }
        });
        pool.submit(() -> {
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " C");
            }
        });

    }
}