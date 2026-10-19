import java.util.function.BiFunction;

public class RungeKutta {

    static double[] add(double[] u, double[] k, double scale) {
        double[] result = new double[u.length];

        for (int i = 0; i < u.length; i++) {
            result[i] = u[i] + scale * k[i];
        }

        return result;
    }

    static double[] step(
            BiFunction<Double, double[], double[]> f,
            double t, double h, double[] u) {

        double[] k1 = f.apply(t, u);
        double[] k2 = f.apply(t + h / 2, add(u, k1, h / 2));
        double[] k3 = f.apply(t + h / 2, add(u, k2, h / 2));
        double[] k4 = f.apply(t + h, add(u, k3, h));

        double[] next = new double[u.length];

        for (int i = 0; i < u.length; i++) {
            next[i] = u[i] + h / 6 * (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]);
        }

        return next;
    }

    static double[] f(double t, double[] u) {
        double x = u[0];
        double y = u[1];

        return new double[] {
                x - x * y,
                x * y - y
        };
    }

    public static void simulate() {
        double[] u = {0.5, 1.0};
        double h = 0.01;

        for (int i = 0; i < 1000; i++) {
            u = step(RungeKutta::f, i * h, h, u);
        }

        System.out.printf("x = %.6f, y = %.6f%n", u[0], u[1]);
    }
}
