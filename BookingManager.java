
import java.sql.*;
import java.util.Scanner;

public class BookingManager {

    static final String DB_URL = "jdbc:mysql://localhost:3306/travel_db"; // Replace with your DB
    static final String USER = "root"; // Replace with your username
    static final String PASS = "your_password"; // Replace with your password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Travel Booking Manager ---");
            System.out.println("1. Add Booking");
            System.out.println("2. View All Bookings");
            System.out.println("3. Confirm Booking");
            System.out.println("4. Delete Booking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBooking(scanner);
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    updateBooking(scanner);
                    break;
                case 4:
                    deleteBooking(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private static void addBooking(Scanner scanner) {
        System.out.print("Enter type (flight/hotel/cab): ");
        String type = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String customer = scanner.nextLine();
        System.out.print("Enter details: ");
        String details = scanner.nextLine();
        System.out.print("Enter status (pending/confirmed): ");
        String status = scanner.nextLine();

        String sql = "INSERT INTO bookings (type, customerName, details, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            stmt.setString(2, customer);
            stmt.setString(3, details);
            stmt.setString(4, status);
            stmt.executeUpdate();
            System.out.println("Booking added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }

    private static void viewBookings() {
        String sql = "SELECT * FROM bookings";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- All Bookings ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Type: %s | Name: %s | Details: %s | Status: %s\n",
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("customerName"),
                        rs.getString("details"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
    }

    private static void updateBooking(Scanner scanner) {
        System.out.print("Enter booking ID to confirm: ");
        int id = scanner.nextInt();

        String sql = "UPDATE bookings SET status = 'confirmed' WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Booking status updated to confirmed.");
            } else {
                System.out.println("Booking ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating booking: " + e.getMessage());
        }
    }

    private static void deleteBooking(Scanner scanner) {
        System.out.print("Enter booking ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Booking deleted successfully.");
            } else {
                System.out.println("Booking ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
        }
    }
}
