package ticket.booking.util;

import ticket.booking.entities.User;

import java.util.List;

public class UserDetailsPrinter {

    public static void printUserDetails(User user) {
        if (user == null) {
            System.out.println("No user logged in.");
            return;
        }
        System.out.println("User: " + user.getUsername());
        List<String> bookings = user.getBookings();
        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("Bookings:");
            bookings.forEach(booking -> System.out.println("  Booking ID: " + booking));
        }
    }
}
