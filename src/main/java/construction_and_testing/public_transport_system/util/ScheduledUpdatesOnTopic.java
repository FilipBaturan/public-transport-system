package construction_and_testing.public_transport_system.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import construction_and_testing.public_transport_system.domain.Vehicle;
import construction_and_testing.public_transport_system.service.definition.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScheduledUpdatesOnTopic {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private VehicleService vehicleService;

    private HashMap<Long, Integer> currentVehiclesPositionIndexes;

    private Random random;

    public ScheduledUpdatesOnTopic() {
        this.currentVehiclesPositionIndexes = new HashMap<>();
        this.random = new Random();
    }

    @Scheduled(fixedDelay = 10000)
    public void publishUpdates() {
        template.convertAndSend("/topic/hi", this.updateVehicles());
    }

    /**
     * @return JSON representation of updated vehicles
     */
    private String updateVehicles() {
        List<TrackedVehicle> trackedVehicles = new ArrayList<>();
        List<Vehicle> vehicles = vehicleService.getAllVehiclesWithLines();
        List<Vehicle> notUpdatedVehicles = new ArrayList<>();
        int numberOfNotUpdatedVehicles;
        try {
            numberOfNotUpdatedVehicles = vehicles.size() - (this.random.nextInt(vehicles.size()) + 1);
        } catch (IllegalArgumentException e){
            numberOfNotUpdatedVehicles = 0;
        }
        for (int i = 0; i < numberOfNotUpdatedVehicles; i++) {
            notUpdatedVehicles.add(vehicles.remove((this.random.nextInt(vehicles.size()))));
        }
        try {
            vehicles.forEach(vehicle -> trackedVehicles.add(updateVehiclePosition(vehicle)));
            notUpdatedVehicles.forEach(vehicle -> trackedVehicles.add(
                    new TrackedVehicle(vehicle, getVehiclePositions(vehicle)
                            .get(currentVehiclesPositionIndexes.getOrDefault(vehicle.getId(), 0)))));
            return (new ObjectMapper().writer().withDefaultPrettyPrinter()).writeValueAsString(trackedVehicles);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * @param vehicle that needs to get position
     * @return collection of available vehicle position
     */
    private List<List<Double>> getVehiclePositions (Vehicle vehicle) {
        String content = vehicle.getCurrentLine().getPositions().getContent();
        if (content.contains("(")) {
            content = content.split("\\(")[0].trim();
        }
        return Arrays.stream(content.split("\\s+"))
                .map(s -> Arrays.stream(s.split(","))
                        .map(Double::parseDouble)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

    }

    /**
     * @param vehicle that needs to be updated
     * @return vehicle with updated position
     */
    private TrackedVehicle updateVehiclePosition(Vehicle vehicle) {
        int index = currentVehiclesPositionIndexes.getOrDefault(vehicle.getId(), 0);
        List<List<Double>> positions = this.getVehiclePositions(vehicle);
        try {
            this.currentVehiclesPositionIndexes.put(vehicle.getId(), ++index % positions.size());
            return new TrackedVehicle(vehicle, positions.get(index));
        } catch (IndexOutOfBoundsException e){
            this.currentVehiclesPositionIndexes.put(vehicle.getId(), 0);
            return new TrackedVehicle(vehicle, positions.get(0));
        }

    }
}
