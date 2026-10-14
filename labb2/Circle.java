// created the class acesses modifier is public like so
    public class Circle {
        private double radius;
        private static int count = 0;          // shared by ALL Circle objects

        // No-argument constructor: delegates to the other one with default 1.0
        public Circle() {
            this(1.0);
        }

        public Circle(double radius) {
            this.radius = radius;
            count++;                            // every construction path ends here
        }

        public static int getCount() {
            return count;
        }

        public double getRadius() {
            return radius;
        }

        public double getArea() {
            return Math.PI * radius * radius;
        }
    }

// my code here will not execute because it does not have a main method