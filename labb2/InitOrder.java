public class InitOrder {

    static int log(String msg) {
        System.out.println(msg);
        return 0;
    }

    static int a = log("static field a");

    static {
        log("static block 1");
    }

    int x = log("instance field x");

    {
        log("instance block 1");
    }

    static {
        log("static block 2");
    }

    {
        log("instance block 2");
    }

    InitOrder() {
        log("constructor");
    }

    public static void main(String[] args) {
        log("main start");
        new InitOrder();
        log(" second object ");
        new InitOrder();
    }
}
//the static lines print before "main start"
        //    main() is inside InitOrder, so the JVM must initialize the class
   //  before it can call main()