package hw_1.Devs;

public class Frontender extends Developer implements FrontAction {
    @Override
    public void front() {
        System.out.println("Front");
    }

    @Override
    public void developGUI() {
        System.out.println("Develop GUI");
    }
}
