package hw_3;

public class Main {
    public static void main(String[] args) {
        GenericCollection<Integer> intCollection = new GenericCollection<>();
        intCollection.add(1);
        intCollection.add(2);
        intCollection.add(3);
        intCollection.forEachPrint();
        System.out.println();

        Calculator calc = new Calculator();
        calc.sum("1", 2);
        calc.multiply("2", 3.0);
        calc.divide(3, 0);
        calc.divide(3, "0");
        calc.divide(3, 5);
        calc.subtract(4.0, 5.0);
        System.out.println();


        ArrayComparator.compare(new Integer[]{1, 2, 3}, new Integer[]{1, 2, 3});
        ArrayComparator.compare(new Integer[]{1, 2, 3}, new Integer[]{1, 2, 3, 4});
        ArrayComparator.compare(new Integer[]{1, 2, 3}, new String[]{"1", "2", "3"});
        System.out.println();

        Pair<Integer, Integer> pair = new Pair<>(1, 2);
        Pair<Integer, String> pair2 = new Pair<>(1, "2");
        Pair<String, Integer> pair3 = new Pair<>("1", 2);
        Pair<String, String> pair4 = new Pair<>("1", "2");
        System.out.println(pair.toString());
        System.out.println(pair2.toString());
        System.out.println(pair3.toString());
        System.out.println(pair4.toString());

    }
}
