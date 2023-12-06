package hw_1.Devs;

public class FullStack extends Developer implements FrontAction, BackendAction {
    @Override
    public void front() {
        System.out.println("Front");
    }

    @Override
    public void back() {
        System.out.println("Back");
    }

    @Override
    public void developGUI() {
        System.out.println("Develop GUI");
    }
}
