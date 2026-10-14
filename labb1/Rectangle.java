// I start by creating my Rectangle class as always
public class Rectangle {

    double width;  //  tyhe Width can have decimal values
    double height; //  so as Height can have decimal values

    // Constructor to initialize the rectangle objects or att,,,,
    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    // Method to calculate  AREA = L * W
    double area() {
        return width * height;
    }

    // Method to calculate perimeter
    double perimeter() {
        return 2 * (width + height);
    }

    public static void main(String[] args) {

        // Creating an array of 5 rectangles THE NUMBER OF MY ARRAY IS IN [] SQUARE BRACKETS
        Rectangle[] rectangles = new Rectangle[5];

        rectangles[0] = new Rectangle(4, 5);
        rectangles[1] = new Rectangle(3, 8);
        rectangles[2] = new Rectangle(6, 7);
        rectangles[3] = new Rectangle(2, 9);
        rectangles[4] = new Rectangle(10, 4);

        // AssumPTION NOTICE :::  the first rectangle has the largest area
        Rectangle largest = rectangles[0];

        // LoopING  through the array to find the largest area
        for (int i = 1; i < rectangles.length; i++) {
            if (rectangles[i].area() > largest.area()) {
                largest = rectangles[i];
            }
        }

        // Print the rectangle with the largest area
        System.out.println("Largest Rectangle");
        System.out.println("Width: " + largest.width);
        System.out.println("Height: " + largest.height);
        System.out.println("Area: " + largest.area());
        System.out.println("Perimeter: " + largest.perimeter());
    }
}

// SO FROM THE QUESTION THE ARRAYY WAS CREATED EVALUATED FOR THE LARGEST AREA FORM MY ARRAY THE
// IT WAS THE ONE TO BE PRINTED