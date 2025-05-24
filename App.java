package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.InputUtil;
import ticket.booking.util.TrainDetailsPrinter;
import ticket.booking.util.UserDetailsPrinter;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {

    public String getGreeting() {
        return "Running Train Booking System";
    }

    public static void main(String[] args) {
        System.out.println("Running Train Booking System");

        UserBookingService userBookingService;
        User currentUser = null;

        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println("Failed to start the booking system.");
            return;
        }

        int option = 0;
        Train selectedTrain = null;

        while (option != 7) {
            System.out.println("\nChoose option:");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch My Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel My Booking");
            System.out.println("7. Exit");

            option = InputUtil.getInt("Option: ");

            switch (option) {
                case 1: {
                    String username = InputUtil.getString("Enter username to sign up: ");
                    String password = InputUtil.getString("Enter password: ");
                    String hashedPassword = UserServiceUtil.hashPassword(password);
                    User newUser = new User(username, password, hashedPassword, new ArrayList<>(), UUID.randomUUID().toString());

                    boolean signedUp = userBookingService.signUp(newUser);
                    if (signedUp)
                        System.out.println("Signup successful! You can now login.");
                    else
                        System.out.println("Signup failed! User might already exist.");
                    break;
                }
                case 2: {
                    String username = InputUtil.getString("Enter username to login: ");
                    String password = InputUtil.getString("Enter password: ");
                    currentUser = new User(username, password, UserServiceUtil.hashPassword(password), new ArrayList<>(), UUID.randomUUID().toString());

                    try {
                        userBookingService = new UserBookingService(currentUser);
                        System.out.println("Login successful! Welcome, " + username);
                    } catch (IOException e) {
                        System.out.println("Login failed! Check credentials.");
                        currentUser = null;
                    }
                    break;
                }
                case 3: {
                    if (currentUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Fetching your bookings...");
                    UserDetailsPrinter.printUserDetails(currentUser);
                    break;
                }
                case 4: {
                    String source = InputUtil.getString("Enter source station: ");
                    String destination = InputUtil.getString("Enter destination station: ");
                    List<Train> trains = userBookingService.getTrains(source, destination);
                    TrainDetailsPrinter.printTrainDetails(trains);

                    if (trains.isEmpty()) {
                        System.out.println("No trains available for your route.");
                        break;
                    }

                    int trainChoice = InputUtil.getInt("Select train number to book (or 0 to cancel): ");
                    if (trainChoice <= 0 || trainChoice > trains.size()) {
                        System.out.println("Cancelled train selection.");
                    } else {
                        selectedTrain = trains.get(trainChoice - 1);
                        System.out.println("Selected train: " + selectedTrain.getTrainId());
                    }
                    break;
                }
                case 5: {
                    if (currentUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    if (selectedTrain == null) {
                        System.out.println("Please select a train first (option 4).");
                        break;
                    }
                    List<List<Integer>> seats = userBookingService.fetchSeats(selectedTrain);
                    System.out.println("Available seats (0 = free, 1 = booked):");
                    for (int i = 0; i < seats.size(); i++) {
                        System.out.print("Row " + (i + 1) + ": ");
                        for (int seat : seats.get(i)) {
                            System.out.print(seat + " ");
                        }
                        System.out.println();
                    }
                    int row = InputUtil.getInt("Enter seat row number: ") - 1;
                    int col = InputUtil.getInt("Enter seat column number: ") - 1;

                    if (userBookingService.bookTrainSeat(selectedTrain, row, col)) {
                        System.out.println("Booking successful! Enjoy your journey.");
                    } else {
                        System.out.println("Seat already booked or invalid selection.");
                    }
                    break;
                }
                case 6: {
                    if (currentUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    String bookingId = InputUtil.getString("Enter booking ID to cancel: ");
                    if (userBookingService.cancelBooking(bookingId)) {
                        System.out.println("Booking cancelled successfully.");
                    } else {
                        System.out.println("Failed to cancel booking. Check booking ID.");
                    }
                    break;
                }
                case 7: {
                    System.out.println("Exiting system. Goodbye!");
                    break;
                }
                default: {
                    System.out.println("Invalid option. Please try again.");
                    break;
                }
            }
        }
    }
}
