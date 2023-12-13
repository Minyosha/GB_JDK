package hw_3;

public class ArrayComparator {
    public static <T> boolean compare(T[] a, T[] b) {
        if (a.length != b.length) {
            System.out.println("Arrays are different in size");
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                System.out.println("Arrays are different");
                return false;
            }
        }
        System.out.println("Arrays are equal");
        return true;
    }
}
