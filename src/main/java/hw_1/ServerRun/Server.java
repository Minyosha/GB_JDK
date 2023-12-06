package hw_1.ServerRun;

public class Server implements ServerListener {
    boolean isServerWorking;
    private final Listener listener;

    public Server(Listener listener) {
        this.listener = listener;
        this.isServerWorking = false;
    }

    public void start() {
        if (!isServerWorking) {
            isServerWorking = true;
            listener.messageReceived("Server started");
            System.out.println("Статус сервера: " + isServerWorking);
        } else {
            System.out.println("Сервер уже запущен");
            listener.messageReceived("Server already running");
        }

    }

    public void stop(){
        if (isServerWorking) {
            isServerWorking = false;
            System.out.println("Статус сервера: " + isServerWorking);
            listener.messageReceived("Server stopped");
        } else {
            System.out.println("Сервер на работает");
            listener.messageReceived("Server not running");
        }

    }

    @Override
    public void listenKey(boolean status) {
        if (status) {
            start();
        } else {
            stop();
        }
    }


}
