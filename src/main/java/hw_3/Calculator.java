package hw_3;

public class Calculator {
    public <T> double sum(T a, T b) {
        double parsedA;
        double parsedB;
        if (a instanceof Integer) {
            parsedA = ((Integer) a).doubleValue();
        } else if (a instanceof Double) {
            parsedA = (Double) a;
        } else if (a instanceof String) {
            parsedA = Double.parseDouble((String) a);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }

        if (b instanceof Integer) {
            parsedB = ((Integer) b).doubleValue();
        } else if (b instanceof Double) {
            parsedB = (Double) b;
        } else if (b instanceof String) {
            parsedB = Double.parseDouble((String) b);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }
        System.out.println(parsedA + parsedB);

        return parsedA + parsedB;
    }

    public <T> double multiply(T a, T b) {
        double parsedA;
        double parsedB;
        if (a instanceof Integer) {
            parsedA = ((Integer) a).doubleValue();
        } else if (a instanceof Double) {
            parsedA = (Double) a;
        } else if (a instanceof String) {
            parsedA = Double.parseDouble((String) a);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }

        if (b instanceof Integer) {
            parsedB = ((Integer) b).doubleValue();
        } else if (b instanceof Double) {
            parsedB = (Double) b;
        } else if (b instanceof String) {
            parsedB = Double.parseDouble((String) b);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }
        System.out.println(parsedA * parsedB);

        return parsedA * parsedB;
    }

    public <T> Double divide(T a, T b) {
        double parsedA;
        double parsedB;
        if (a instanceof Integer) {
            parsedA = ((Integer) a).doubleValue();
        } else if (a instanceof Double) {
            parsedA = (Double) a;
        } else if (a instanceof String) {
            parsedA = Double.parseDouble((String) a);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }

        if (b instanceof Integer) {
            parsedB = ((Integer) b).doubleValue();
        } else if (b instanceof Double) {
            parsedB = (Double) b;
        } else if (b instanceof String) {
            parsedB = Double.parseDouble((String) b);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }
        try {
            if (parsedB == 0) {
                throw new IllegalArgumentException("Division by zero");
            }
            System.out.println(parsedA / parsedB);
            return parsedA / parsedB;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public <T> double subtract(T a, T b) {
        double parsedA;
        double parsedB;
        if (a instanceof Integer) {
            parsedA = ((Integer) a).doubleValue();
        } else if (a instanceof Double) {
            parsedA = (Double) a;
        } else if (a instanceof String) {
            parsedA = Double.parseDouble((String) a);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }

        if (b instanceof Integer) {
            parsedB = ((Integer) b).doubleValue();
        } else if (b instanceof Double) {
            parsedB = (Double) b;
        } else if (b instanceof String) {
            parsedB = Double.parseDouble((String) b);
        } else {
            throw new IllegalArgumentException("Unsupported types");
        }
        System.out.println(parsedA - parsedB);

        return parsedA - parsedB;
    }
}
