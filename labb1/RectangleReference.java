public class RectangleReference {

    double width;
    double height;

    // Constructor
    RectangleReference(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public static void main(String[] args) {

        RectangleReference r1 = new RectangleReference(5, 10);

        // r2 points to the same object as r1
        RectangleReference r2 = r1;

        // Change width through r2
        r2.width = 20;

        // Print width through r1
        System.out.println("r1 width = " + r1.width);
    }
}