public class ImplicitMidpoint {

    static double[] step(double x, double y, double h) {
        double A = (2 + h) / (2 - h);
        double B = ((1 + A) * x + y) / 2 - 1;
        double C = A / 2;

        double a = h * C / 2;
        double b = 1 - h * (B - C * y) / 2;
        double c = -y - h * y * B / 2;

        double d = b * b - 4 * a * c;

        if (d < 0) {
            throw new ArithmeticException("Negative discriminant");
        }

        double r1 = (-b + Math.sqrt(d)) / (2 * a);
        double r2 = (-b - Math.sqrt(d)) / (2 * a);

        // Select the root that approaches the previous y as h tends to 0.
        double yn = Math.abs(r1 - y) < Math.abs(r2 - y) ? r1 : r2;
        double xn = A * (x - yn) + y;

        return new double[] {xn, yn};
    }

    static void analyze(String name, double[] values, double h) {
        double maxTime = -1;

        for (int i = 1; i < values.length - 1; i++) {
            if (values[i] > values[i - 1] && values[i] > values[i + 1]) {
                maxTime = i * h;
            }

            if (maxTime >= 0 && values[i] < values[i - 1] && values[i] < values[i + 1]) {
                System.out.printf(
                        "%s: max at %.4f, min at %.4f, difference = %.4f%n",
                        name, maxTime, i * h, i * h - maxTime
                );
                return;
            }
        }

        System.out.println(name + ": no complete max-to-min interval found");
    }

    public static void simulate() {
        double h = 0.01;
        int steps = 100000;

        double[] x = new double[steps + 1];
        double[] y = new double[steps + 1];

        x[0] = 0.5;
        y[0] = 1.0;

        for (int i = 0; i < steps; i++) {
            double[] next = step(x[i], y[i], h);
            x[i + 1] = next[0];
            y[i + 1] = next[1];
        }

        analyze("x", x, h);
        analyze("y", y, h);
    }
}
