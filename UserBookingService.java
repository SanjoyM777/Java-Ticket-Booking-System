package ticket.booking.service;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserBookingService {
    private List<User> users;
    private List<Train> trains;
    private User loggedInUser;

    public UserBookingService() throws IOException {
        users = new ArrayList<>();
        trains = new ArrayList<>();
        loadTrains();
    }

    public UserBookingService(User user) throws IOException {
        this();
        this.loggedInUser = user;
    }

    private void loadTrains() {
        // Hardcoded trains for demo
        trains.add(new Train("T001", "Kolkata", "Delhi", 5, 4));
        trains.add(new Train("T002", "Mumbai", "Pune", 3, 3));
        trains.add(new Train("T003", "Chennai", "Bangalore", 4, 4));
    }

    public boolean signUp(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false; // User exists
            }
        }
        users.add(user);
        return true;
    }

    public List<Train> getTrains(String source, String destination) {
        List<Train> result = new ArrayList<>();
        for (Train train : trains) {
            if (train.getSource().equalsIgnoreCase(source) && train.getDestination().equalsIgnoreCase(destination)) {
                result.add(train);
            }
        }
        return result;
    }

    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    public boolean bookTrainSeat(Train train, int row, int col) {
        if (loggedInUser == null) return false;

        if (train.bookSeat(row, col)) {
            String bookingId = UUID.randomUUID().toString();
            loggedInUser.addBooking(bookingId);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(String bookingId) {
        if (loggedInUser == null) return false;

        if (loggedInUser.getBookings().contains(bookingId)) {
            loggedInUser.removeBooking(bookingId);
            // Here you can also add logic to free the seat in train — if you store mapping bookingId -> seat
            return true;
        }
        return false;
    }

}
