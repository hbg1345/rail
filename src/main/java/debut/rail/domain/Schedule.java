package debut.rail.domain;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chainName, TrainName, trainNumber, fromStation, toStation, day;
    private LocalTime departureTime, arrivalTime;
    
    @Override
    public String toString() {
        return chainName + TrainName + trainNumber + fromStation + toStation + day + 
            departureTime.getHour() +":" +departureTime.getMinute() + " "+ arrivalTime.getHour() + ":"+arrivalTime.getMinute();
    }
    
    public Schedule(String chainName, String trainName, String trainNumber, String fromStation, String toStation,
            String day, LocalTime departureTime, LocalTime arrivalTime) {
        this.chainName = chainName;
        TrainName = trainName;
        this.trainNumber = trainNumber;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.day = day;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

  
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
