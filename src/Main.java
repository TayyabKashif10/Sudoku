
public class Main {
    public static void main(String[] args) {
        Window newWindow = new Window();
        Thread t1 = new Thread(newWindow);
        t1.start();
    }
}