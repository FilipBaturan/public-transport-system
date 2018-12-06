package construction_and_testing.public_transport_system.repository;

import construction_and_testing.public_transport_system.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    @Query("SELECT s FROM Schedule s WHERE schedule_id = :schedule_id and day_of_week = :day_of_week")
    List<Schedule> findAllSchedulesByTransportLineIdAndDayOfWeek(@Param("schedule_id") Long schedule_id, @Param("day_of_week") Integer day_of_week);
}
