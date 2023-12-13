package hw_3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericCollection<T> {
    private final List<T> arrayList;
    MyIterator myIterator;

    public GenericCollection(List<T> list) {
        arrayList = list;
        myIterator = new MyIterator();
    }

    public GenericCollection() {
        arrayList = new ArrayList<>();
        myIterator = new MyIterator();
    }

    public void forEachPrint() {
        while (myIterator.hasNext()) {
            Object current = myIterator.next();
            if (myIterator.hasNext()) {
                System.out.print(current + "; ");
            } else {
                System.out.print(current);
            }
        }
        System.out.println();
    }

    public void add(T t) {
        arrayList.add(t);
    }

    public void remove(T t) {
        arrayList.remove(t);
    }

    public List<T> getArrayList() {
        return arrayList;
    }

    public void print() {
        for (T t : arrayList)
            System.out.print(t + " | ");
        System.out.println();
    }

    public class MyIterator implements Iterator<T> {
        private int cursor;
        private final List<T> tList;

        public MyIterator() {
            this.tList = arrayList;
        }

        @Override
        public boolean hasNext() {
            return cursor < tList.size();
        }

        @Override
        public T next() {
            return tList.get(cursor++);
        }

        @Override
        public void remove() {
            this.tList.remove(cursor);
        }
    }
}
