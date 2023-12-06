package hw_1.Chat;

import hw_1.ServerRun.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/*
Создать окно клиента чата. Окно должно содержать JtextField для ввода логина, пароля,
IP-адреса сервера, порта подключения к серверу, область ввода сообщений, JTextArea
область просмотра сообщений чата и JButton подключения к серверу и отправки сообщения в чат.
Желательно сразу сгруппировать компоненты, относящиеся к серверу сгруппировать на JPanel
сверху экрана, а компоненты, относящиеся к отправке сообщения – на JPanel снизу
 */
public class Chat extends JFrame {
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    JButton btnSend = new JButton("Отправить");
    JButton btnConnect = new JButton("Подключиться");
    JButton btnDisconnect = new JButton("Отключиться");
    JLabel lblLogin = new JLabel("Login:");
    JLabel lblPassword = new JLabel("Password:");
    JLabel lblIP = new JLabel("IP:");
    JLabel lblPort = new JLabel("Port:");
    JLabel lblMessageHistory = new JLabel("История чата:");
    JTextField txtFieldLogin = new JTextField();
    JTextField txtFieldPort = new JTextField();
    JPasswordField txtFieldPassword = new JPasswordField();
    JTextField txtFieldIP = new JTextField();
    JTextField txtFieldMessage = new JTextField();
    JTextArea areaMessageHistory = new JTextArea();
    JPanel panServer = new JPanel(new GridLayout(2, 4));
    JPanel panClient = new JPanel(new GridLayout(3, 2));
    String login;
    String password;
    String IP;
    String message;
    private String currentUser;

    private Server server;

    public Chat() {
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("ChatWindow");
        setResizable(false);

        // Панель сервера
        panServer.add(lblIP);
        panServer.add(lblPort);
        panServer.add(lblLogin);
        panServer.add(lblPassword);
        panServer.add(btnConnect);
        panServer.add(txtFieldIP);
        panServer.add(txtFieldPort);
        panServer.add(txtFieldLogin);
        panServer.add(txtFieldPassword);
        panServer.add(btnDisconnect);
        btnDisconnect.setEnabled(false);
        add(panServer, BorderLayout.NORTH);

        // Панель клиента
        panClient.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.05;
        panClient.add(lblMessageHistory, gbc);
//        Dimension preferredSize = new Dimension(100, 20);
//        lblMessage.setPreferredSize(preferredSize);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        panClient.add(areaMessageHistory, gbc);
        areaMessageHistory.setText("Введите логин и пароль и нажмите \"Подключиться\"\nДля отключения нажмите \"Отключиться\"\n");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.95;
        gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panClient.add(txtFieldMessage, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.15;
        panClient.add(btnSend, gbc);

        add(panClient, BorderLayout.CENTER);
        setVisible(true);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!btnDisconnect.isEnabled()) {
                    String loginMessage = getFormattedDateTime() + "Для отправки сообщений залогиньтесь\n";
                    System.out.println(getFormattedDateTime() + "Для отправки сообщений залогиньтесь");
                    areaMessageHistory.append(loginMessage);
                    return;  // Выход из метода actionPerformed
                }
                String message = getFormattedDateTime() + txtFieldLogin.getText() + ": " + txtFieldMessage.getText();
                areaMessageHistory.append(message);
                areaMessageHistory.append("\n");
                txtFieldMessage.setText("");
                System.out.println("Отправлено сообщение: " + message);
                writeStringToFile(message);

            }
        });

        // Устанавливаем кнопку "Отправить" по умолчанию по нажатию на кнопку "Enter"
        panClient.getRootPane().setDefaultButton(btnSend);

        // Добавляем действие к кнопке "Подключиться"
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String login = txtFieldLogin.getText();
                String password = txtFieldPassword.getText();
                currentUser = login;
                boolean isLoggedIn = false;

                try (BufferedReader reader = new BufferedReader(new FileReader("Chat/users.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 2 && parts[0].equals(login) && parts[1].equals(password)) {
                            isLoggedIn = true;
                            break;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (isLoggedIn) {
                    System.out.println(getFormattedDateTime() + "Успешный вход пользователя " + currentUser);
                    String loginMessage = getFormattedDateTime() + "Успешный вход пользователя " + currentUser;
                    writeStringToFile(loginMessage);
                    // При нажатии делаем кнопку "Подключиться" неактивной
                    btnConnect.setEnabled(false);
                    // И кнопку "Отключиться" активной
                    btnDisconnect.setEnabled(true);

                    try (BufferedReader reader = new BufferedReader(new FileReader("Chat/history.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            areaMessageHistory.append(line);
                            areaMessageHistory.append("\n");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println(getFormattedDateTime() + "Неверный логин или пароль, попробуйте еще раз");
                    String loginMessage = getFormattedDateTime() + "Неверный логин или пароль, попробуйте еще раз\n";
                    areaMessageHistory.append(loginMessage);
                }
            }
        });

        // Добавляем действие к кнопке "Отключиться"
        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // При нажатии делаем кнопку "Отключиться" неактивной
                btnDisconnect.setEnabled(false);
                // И кнопку "Подключиться" активной
                btnConnect.setEnabled(true);
                System.out.println(getFormattedDateTime() + "Пользователь " + currentUser + " отключился");
                String loginMessage = getFormattedDateTime() + "Пользователь " + currentUser + " отключился";
                areaMessageHistory.append(loginMessage);
                areaMessageHistory.append("\n");
                writeStringToFile(loginMessage);
            }
        });

    }

    private String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'['dd.MM.yyyy']' - HH:mm:ss - ");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    private void writeStringToFile(String str) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Chat/history.txt", true))) {
            writer.write(str);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File chatDir = new File("Chat");
        if (!chatDir.exists()) {
            chatDir.mkdir();
        }

        File usersFile = new File(chatDir, "users.txt");
        File historyFile = new File(chatDir, "history.txt");

        try {
            if (!usersFile.exists()) {
                usersFile.createNewFile();
            }

            if (!historyFile.exists()) {
                historyFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Chat();
    }
}