package ticket.booking.util;

import ticket.booking.entities.Train;

import java.util.List;

public class TrainDetailsPrinter {
    public static void printTrainDetails(List<Train> trains) {
        if (trains.isEmpty()) {
            System.out.println("No trains found for this route.");
            return;
        }
        System.out.println("Available trains:");
        int index = 1;
        for (Train train : trains) {
            System.out.println(index++ + ". Train ID: " + train.getTrainId() +
                    ", From: " + train.getSource() + " To: " + train.getDestination());
        }
    }
}
