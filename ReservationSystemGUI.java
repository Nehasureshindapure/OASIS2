import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}


class Reservation {
    private String username;
    private String reservationDetails;

    public Reservation(String username, String reservationDetails) {
        this.username = username;
        this.reservationDetails = reservationDetails;
    }

    public String getUsername() {
        return username;
    }

    public String getReservationDetails() {
        return reservationDetails;
    }
}

public class ReservationSystemGUI extends JFrame {
    private User currentUser;
    private List<User> users;
    private List<Reservation> reservations;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea reservationTextArea;

    public ReservationSystemGUI() {
        users = new ArrayList<>();
        users.add(new User("user1", "password1")); 
        users.add(new User("neha", "1017")); 
        reservations = new ArrayList<>();

        setTitle("Online Reservation System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        reservationTextArea = new JTextArea();
        reservationTextArea.setBounds(10, 120, 360, 130);
        panel.add(reservationTextArea);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                for (User user : users) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        currentUser = user;
                        updateReservationTextArea();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        });

        JButton makeReservationButton = new JButton("Make Reservation");
        makeReservationButton.setBounds(100, 80, 150, 25);
        panel.add(makeReservationButton);

        makeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please login first");
                    return;
                }
                String reservationDetails = JOptionPane.showInputDialog("Enter reservation details:");
                reservations.add(new Reservation(currentUser.getUsername(), reservationDetails));
                updateReservationTextArea();
            }
        });

        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setBounds(260, 80, 150, 25);
        panel.add(cancelReservationButton);

        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please login first");
                    return;
                }
                String reservationDetails = JOptionPane.showInputDialog("Enter reservation details to cancel:");
                for (Reservation reservation : reservations) {
                    if (reservation.getUsername().equals(currentUser.getUsername()) &&
                            reservation.getReservationDetails().equals(reservationDetails)) {
                        reservations.remove(reservation);
                        updateReservationTextArea();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Reservation not found or you are not authorized to cancel");
            }
        });
    }

    private void updateReservationTextArea() {
        StringBuilder sb = new StringBuilder();
        for (Reservation reservation : reservations) {
            sb.append(reservation.getUsername()).append(": ").append(reservation.getReservationDetails()).append("\n");
        }
        reservationTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new ReservationSystemGUI();
    }
}
