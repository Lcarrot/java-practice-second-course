import org.junit.Before;

public class Test {


    private ThreadPool pool;

    @Before
    public void prepare() {
        pool = null;
    }

    @org.junit.Test
    public void print_4_task_3_thread() {
        pool = new ThreadPool(3);
        submitForTest(4);
    }

    @org.junit.Test
    public void print_3_task_3_thread() {
        pool = new ThreadPool(3);
        submitForTest(3);
    }

    @org.junit.Test
    public void print_1_task_1_Thread() {
        pool = new ThreadPool(1);
        submitForTest(1);
    }

    @org.junit.Test
    public void print_2_task_1_Thread() {
        pool = new ThreadPool(1);
        submitForTest(2);
    }

    public void submitForTest(int count) {
        for (int k = 0; k < count; k++) {
            char c = (char) (k + 'A');
            pool.submit(() -> {
                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName() + c);
                }
            });
        }
    }
}