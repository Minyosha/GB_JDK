package hw_1.Devs;

public class Main {
    public static void main(String[] args) {
        Developer dev1 = new Frontender();
        if (dev1 instanceof FrontAction) {
            ((FrontAction) dev1).front();
            ((FrontAction) dev1).developGUI();
        }

        Developer dev2 = new Backender();
        if (dev2 instanceof BackendAction) {
            ((BackendAction) dev2).back();
        }
        if (!(dev2 instanceof FullStack)) {
            System.out.println("123");
        }


        Developer dev3 = new FullStack();
        if (dev3 instanceof FrontAction) {
            ((FrontAction) dev3).front();
        }
        if (dev3 instanceof BackendAction) {
            ((BackendAction) dev3).back();
        }
    }
}
