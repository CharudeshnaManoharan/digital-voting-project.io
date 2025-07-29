import java.sql.*;
import java.util.*;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost:3306/voting_system";
    static final String DB_USER = "root";
    static final String DB_PASS = "";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Digital Voting System");

        while (true) {
            System.out.println("\n1. Login and Vote\n2. View Result (Admin)\n3. Exit");
            System.out.print("Choose option: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    loginAndVote();
                    break;
                case 2:
                    adminView();
                    break;
                case 3:
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public static void loginAndVote() {
        System.out.print("Enter Voter ID: ");
        String voterId = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        try {
            // ✅ Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Connect to database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT * FROM voters WHERE voter_id=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, voterId);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getBoolean("voted")) {
                    System.out.println("You have already voted!");
                } else {
                    System.out.println("Candidates:\n1. Alice\n2. Bob\n3. Charlie");
                    System.out.print("Enter your vote (1-3): ");
                    int vote = sc.nextInt();

                    String candidate = switch (vote) {
                        case 1 -> "Alice";
                        case 2 -> "Bob";
                        case 3 -> "Charlie";
                        default -> null;
                    };

                    if (candidate != null) {
                        String updateVote = "UPDATE votes SET count = count + 1 WHERE name=?";
                        PreparedStatement voteStmt = conn.prepareStatement(updateVote);
                        voteStmt.setString(1, candidate);
                        voteStmt.executeUpdate();

                        String markVoted = "UPDATE voters SET voted=1 WHERE voter_id=?";
                        PreparedStatement updateStmt = conn.prepareStatement(markVoted);
                        updateStmt.setString(1, voterId);
                        updateStmt.executeUpdate();

                        System.out.println("Vote casted for " + candidate + " successfully!");
                    } else {
                        System.out.println("Invalid candidate number.");
                    }
                }
            } else {
                System.out.println("Invalid credentials.");
            }

            conn.close(); // ✅ Close connection

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

    public static void adminView() {
        System.out.print("Enter Admin Password: ");
        String adminPass = sc.next();

        if (!adminPass.equals("admin123")) {
            System.out.println("Incorrect password.");
            return;
        }

        try {
            // ✅ Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String sql = "SELECT * FROM votes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Voting Results:");
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getInt("count") + " votes");
            }

            conn.close(); // ✅ Close connection

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }
}
