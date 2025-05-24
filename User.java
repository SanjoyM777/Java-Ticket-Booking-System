package ticket.booking.entities;

import java.util.List;

public class User {
    private String username;
    private String password; // You may not want to store plaintext passwords in real apps!
    private String hashedPassword;
    private List<String> bookings;
    private String userId;

    public User(String username, String password, String hashedPassword, List<String> bookings, String userId) {
        this.username = username;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.bookings = bookings;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<String> getBookings() {
        return bookings;
    }

    public void addBooking(String bookingId) {
        bookings.add(bookingId);
    }

    public void removeBooking(String bookingId) {
        bookings.remove(bookingId);
    }

    public String getUserId() {
        return userId;
    }
}
