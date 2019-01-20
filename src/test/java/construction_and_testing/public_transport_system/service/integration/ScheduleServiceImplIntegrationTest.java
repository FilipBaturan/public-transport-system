package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.domain.TransportLine;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.implementation.ScheduleServiceImpl;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.ScheduleConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleServiceImplIntegrationTest {

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private TransportLineRepository transportLineRepository;

    /**
     * Test fetching all schedules from the database.
     */
    @Test
    public void testGetAllSchedules() {

        List<Schedule> schedules = scheduleService.getAll();
        assertThat(schedules).hasSize(DB_COUNT);

    }

    /**
     * Test finding a schedule with a valid id.
     */
    @Test
    public void testFindById() {
        Schedule schedule = scheduleService.findById(DB_VALID_ID);
        assertThat(schedule).isNotNull();

        assertThat(schedule.getId()).isEqualTo(DB_VALID_ID);
        //assertThat(schedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(schedule.getDayOfWeek()).isEqualTo(DB_VALID_DAY_OF_WEEK);
        //assertThat(schedule.getDepartures()).isEqualTo(DB_VALID_DEPARTURES);
        assertThat(schedule.getDepartures().size()).isEqualTo(DB_VALID_DEPARTURES_SIZE);
        assertThat(schedule.isActive()).isEqualTo(DB_VALID_ACTIVE);
    }

    /**
     * Test with invalid id
     */
    @Test(expected = GeneralException.class)
    public void findByInvalidId() {
        scheduleService.findById(DB_INVALID_ID);
    }

    /**
     * Test with null id
     */
    @Test(expected = GeneralException.class)
    public void findByNullId() {
        Schedule schedule = scheduleService.findById(null);
        assertThat(schedule).isNotNull();

        assertThat(schedule.getId()).isEqualTo(DB_VALID_ID);
        //assertThat(schedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(schedule.getDayOfWeek()).isEqualTo(DB_VALID_DAY_OF_WEEK);
        assertThat(schedule.getDepartures().size()).isEqualTo(DB_VALID_DEPARTURES_SIZE);
        assertThat(schedule.isActive()).isEqualTo(DB_VALID_ACTIVE);
    }

    /**
     * Test saving a valid schedule.
     */
    @Test
    @Transactional
    public void save() {
        TransportLine transportLine = transportLineRepository.findById(DB_NEW_TL_ID).get();
        Schedule schedule = new Schedule(null, transportLine, DB_VALID_DAY_OF_WEEK, DB_NEW_DEPARTURES, true);

        int countBefore = scheduleService.getAll().size();

        Schedule newSchedule = scheduleService.save(schedule);
        assertThat(newSchedule).isNotNull();

        assertThat(scheduleService.getAll().size()).isEqualTo(countBefore + 1);
        //assertThat(newSchedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(newSchedule.getDayOfWeek()).isEqualTo(schedule.getDayOfWeek());
        assertThat(newSchedule.getDepartures()).isEqualTo(schedule.getDepartures());
        assertThat(newSchedule.getDepartures().size()).isEqualTo(schedule.getDepartures().size());
        assertThat(newSchedule.isActive()).isEqualTo(schedule.isActive());
    }

    /**
     * Test updating a schedule.
     */
    @Test
    @Transactional
    public void update() {
        Schedule schedule = scheduleService.findById(DB_UPDATE_ID);
        schedule.setDayOfWeek(DB_UPDATE_DAYOFWEEK);
        Schedule updatedSchedule = this.scheduleService.save(schedule);

        int countBefore = scheduleService.getAll().size();
        assertThat(updatedSchedule).isNotNull();

        assertThat(scheduleService.getAll().size()).isEqualTo(countBefore);
        //assertThat(updatedSchedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(updatedSchedule.getDayOfWeek()).isEqualTo(DB_UPDATE_DAYOFWEEK);
        assertThat(updatedSchedule.getDepartures()).isEqualTo(schedule.getDepartures());
        assertThat(updatedSchedule.getDepartures().size()).isEqualTo(schedule.getDepartures().size());
        assertThat(updatedSchedule.isActive()).isEqualTo(schedule.isActive());
    }

    /**
     * Test saving a null schedule.
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void saveWithNullValues() {
        Schedule schedule = new Schedule(null, null, null, null, true);
        int countBefore = scheduleService.getAll().size();

        Schedule newSchedule = scheduleService.save(schedule);
        assertThat(newSchedule).isNotNull();

        assertThat(scheduleService.getAll().size()).isEqualTo(countBefore + 1);
        //assertThat(newSchedule.getTransportLine().getName()).isEqualTo(DB_TL_NAME);
        assertThat(newSchedule.getDayOfWeek()).isEqualTo(schedule.getDayOfWeek());
        assertThat(newSchedule.getDepartures()).isEqualTo(schedule.getDepartures());
        assertThat(newSchedule.getDepartures().size()).isEqualTo(schedule.getDepartures());
        assertThat(newSchedule.isActive()).isEqualTo(schedule.isActive());
    }

    /**
     * Test logically removing a schedule.
     */
    @Test
    @Transactional
    public void remove() {
        scheduleService.remove(DB_DEL_ID);
        Schedule station = scheduleService.findById(DB_DEL_ID);
        assertThat(station.isActive()).isFalse();
    }

    /**
     * Test logically removing a schedule that doesn't exist in the database.
     */
    @Test(expected = GeneralException.class)
    @Transactional
    public void removeInvalidId() {
        scheduleService.remove(DB_DEL_INVALID_ID);
    }
}