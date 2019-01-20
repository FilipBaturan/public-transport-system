package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Schedule;
import construction_and_testing.public_transport_system.repository.ScheduleRepository;
import construction_and_testing.public_transport_system.repository.TransportLineRepository;
import construction_and_testing.public_transport_system.service.implementation.ScheduleServiceImpl;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.ScheduleConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleServiceImplUnitTest {

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private TransportLineRepository transportLineRepository;

    @Before
    public void setUp() throws Exception {
        when(scheduleRepository.findAll()).thenReturn(DB_SCHEDULES);
        when(scheduleRepository.findById(DB_VALID_ID)).thenReturn(Optional.of(DB_SCHEDULE));
        when(scheduleRepository.findById(DB_DEL_ID)).thenReturn(Optional.of(DB_SCHEDULE_1));
        when(scheduleRepository.findById(DB_UPDATE_ID)).thenReturn(Optional.of(DB_SCHEDULE_1));
        when(scheduleRepository.save(DB_NEW_SCHEDULE)).thenReturn(DB_NEW_SCHEDULE);
        when(scheduleRepository.save(DB_UPDATE_SCHEDULE)).thenReturn(DB_UPDATE_SCHEDULE);
        when(scheduleRepository.save(DB_NULL_SCHEDULE)).thenReturn(DB_NULL_SCHEDULE);
        //when(ticketRepository.save(DB_CHANGED_TICKET)).thenReturn(DB_CHANGED_TICKET);
    }

    /**
     * Test fetching all schedules from the database.
     */
    @Test
    public void testGetAllSchedules() {

        List<Schedule> schedules = scheduleService.getAll();
        assertThat(schedules).hasSize(DB_SCHEDULES.size());

    }

    /**
     * Test finding a schedule with a valid id.
     */
    @Test
    public void testFindById() {
        Schedule schedule = scheduleService.findById(DB_VALID_ID);
        assertThat(schedule).isNotNull();

        assertThat(schedule.getId()).isEqualTo(DB_VALID_ID);
        //assertThat(schedule.getTransportLine().getFirstName()).isEqualTo(DB_TL_NAME);
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
        //assertThat(schedule.getTransportLine().getFirstName()).isEqualTo(DB_TL_NAME);
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
        int countBefore = scheduleService.getAll().size();

        Schedule newSchedule = scheduleService.save(DB_NEW_SCHEDULE);
        assertThat(newSchedule).isNotNull();

        //assertThat(scheduleService.getAll().size()).isEqualTo(countBefore + 1);
        //assertThat(newSchedule.getTransportLine().getFirstName()).isEqualTo(DB_TL_NAME);
        assertThat(newSchedule.getDayOfWeek()).isEqualTo(DB_NEW_SCHEDULE.getDayOfWeek());
        assertThat(newSchedule.getDepartures()).isEqualTo(DB_NEW_SCHEDULE.getDepartures());
        assertThat(newSchedule.getDepartures().size()).isEqualTo(DB_NEW_SCHEDULE.getDepartures().size());
        assertThat(newSchedule.isActive()).isEqualTo(DB_NEW_SCHEDULE.isActive());

        Mockito.verify(scheduleRepository, Mockito.times(1)).save(any(Schedule.class));
    }

    /**
     * Test updating a schedule.
     */
    @Test
    @Transactional
    public void update() {
        DB_UPDATE_SCHEDULE.setDayOfWeek(DB_UPDATE_DAYOFWEEK);
        Schedule updatedSchedule = this.scheduleService.save(DB_UPDATE_SCHEDULE);

        int countBefore = scheduleService.getAll().size();
        assertThat(updatedSchedule).isNotNull();
        System.out.println(updatedSchedule.getId());

        assertThat(scheduleService.getAll().size()).isEqualTo(countBefore);
        //assertThat(updatedSchedule.getTransportLine().getFirstName()).isEqualTo(DB_TL_NAME);
        assertThat(updatedSchedule.getDayOfWeek()).isEqualTo(DB_UPDATE_DAYOFWEEK);
        assertThat(updatedSchedule.getDepartures()).isEqualTo(DB_UPDATE_SCHEDULE.getDepartures());
        assertThat(updatedSchedule.getDepartures().size()).isEqualTo(DB_UPDATE_SCHEDULE.getDepartures().size());
        assertThat(updatedSchedule.isActive()).isEqualTo(DB_UPDATE_SCHEDULE.isActive());

        Mockito.verify(scheduleRepository, Mockito.times(1)).save(any(Schedule.class));
    }

    /**
     * Test saving a null schedule.
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Transactional
    public void saveWithNullValues() {
        Schedule schedule = DB_NULL_SCHEDULE;
        int countBefore = scheduleService.getAll().size();

        Schedule newSchedule = scheduleService.save(schedule);
        assertThat(newSchedule).isNotNull();

        //assertThat(scheduleService.getAll().size()).isEqualTo(countBefore + 1);
        //assertThat(newSchedule.getTransportLine().getFirstName()).isEqualTo(DB_TL_NAME);
        assertThat(newSchedule.getDayOfWeek()).isEqualTo(schedule.getDayOfWeek());
        assertThat(newSchedule.getDepartures()).isEqualTo(schedule.getDepartures());
        assertThat(newSchedule.isActive()).isEqualTo(schedule.isActive());

        Mockito.verify(scheduleRepository, Mockito.times(1)).save(any(Schedule.class));
    }

    /**
     * Test logically removing a schedule.
     */
    @Test
    @Transactional
    public void remove() {
        scheduleService.remove(DB_DEL_ID);

        Mockito.verify(scheduleRepository, Mockito.times(1)).findById(DB_DEL_ID);
        Mockito.verify(scheduleRepository, Mockito.times(1)).save(any(Schedule.class));

        Schedule schedule = scheduleService.findById(DB_DEL_ID);
        assertThat(schedule.isActive()).isFalse();
        Mockito.verify(scheduleRepository, Mockito.times(1)).save(any(Schedule.class));
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
