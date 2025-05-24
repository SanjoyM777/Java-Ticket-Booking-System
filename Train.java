package ticket.booking.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Train {
    @Getter
    private String trainId;
    @Getter
    private String source;
    @Getter
    private String destination;
    private final int rows;
    private final int cols;
    @Getter
    private List<List<Integer>> seats;
    @Getter
    private List<String> stations;  // station list initialized now

    // Updated constructor to accept stations list as well
    public Train(String trainId, String source, String destination, int rows, int cols, List<String> stations) {
        this.trainId = trainId;
        this.source = source;
        this.destination = destination;
        this.rows = rows;
        this.cols = cols;
        this.stations = stations != null ? stations : new ArrayList<>();

        // Initialize seats to 0 (free)
        this.seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Integer> rowSeats = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                rowSeats.add(0);
            }
            seats.add(rowSeats);
        }
    }

    // Overloaded constructor if you want to create train without stations at start
    public Train(String trainId, String source, String destination, int rows, int cols) {
        this(trainId, source, destination, rows, cols, new ArrayList<>());
    }

    public boolean isSeatAvailable(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return false;
        return seats.get(row).get(col) == 0;
    }

    public boolean bookSeat(int row, int col) {
        if (!isSeatAvailable(row, col)) return false;
        seats.get(row).set(col, 1);
        return true;
    }

    public void cancelSeat(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            seats.get(row).set(col, 0);
        }
    }
    public String getTrainId() {
        return trainId;
    }
    public List<String> getStations() {
        return stations;
    }
    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
    public List<List<Integer>> getSeats() {
        return seats;
    }

}
