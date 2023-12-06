package hw_1.Devs;

public class Backender extends Developer implements BackendAction{
    @Override
    public void back() {
        System.out.println("Back");
    }
}
