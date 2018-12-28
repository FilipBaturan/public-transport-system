package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.DTO.TicketReportDTO;
import construction_and_testing.public_transport_system.domain.*;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TicketConstants {

    public static final int DB_COUNT = 2;
    public static final Long DB_ID = 1L;
    public static final Long DB_ID_INVALID = 999L;
    public static final boolean DB_ACTIVE = true;

    public static final Long DB_LINE_ID = 1L;
    public static final Long DB_TL_INVALID_ID = 324L;
    public static final Long DB_ID_REMOVE = 555L;

    public static final TransportLine DB_LINE =
            new TransportLine(DB_LINE_ID, "R1", VehicleType.BUS, new TransportLinePosition(),
                    new HashSet<>(), new Zone(2L), true);

    public static final TransportLine DB_INVALID_LINE =
            new TransportLine(DB_TL_INVALID_ID, "R1", VehicleType.BUS, new TransportLinePosition(),
                    new HashSet<>(), new Zone(2L), true);


    public static final PricelistItem DB_PRICE_LIST_ITEM = new PricelistItem(1L, new Item(), new Pricelist());

    public static final Long DB_USER_ID = 1l;
    public static final RegisteredUser DB_REG_USER = new RegisteredUser(DB_USER_ID, "LastName", "FirstName", "username", "pass",
            "email@huhuhu.com'", "v0120120012", UsersDocumentsStatus.UNCHECKED, new HashSet<Reservation>(), 12000.0, "");


    public static final LocalDateTime DB_PUR_DATE = LocalDateTime.of(2016, 12, 20, 0, 0, 0);
    public static final LocalDateTime DB_EXP_DATE = LocalDateTime.of(2018, 04, 24, 07, 39, 16);
    public static final LocalDateTime DB_RANDOM_DATE = LocalDateTime.of(2014, 11, 15, 07, 21, 48);


    public static final Long DB_RESERVATION_ID = 1L;
    public static final Long DB_RES_INVALID_ID = 324L;
    public static final Reservation DB_RESERVATION = new Reservation(DB_RESERVATION_ID, new HashSet<Ticket>(), DB_REG_USER);
    public static final Reservation DB_INVALID_RES = new Reservation(DB_RES_INVALID_ID, new HashSet<Ticket>(), DB_REG_USER);


    public static final Ticket DB_TICKET = new Ticket(DB_ID, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_LINE, DB_RESERVATION);
    public static final Ticket DB_NEW_TICKET = new Ticket(null, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_LINE, DB_RESERVATION);
    public static final Ticket DB_SAVED_TICKET = new Ticket(3L, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_LINE, DB_RESERVATION);
    public static final Ticket DB_CHANGED_TICKET = new Ticket(DB_ID, DB_RANDOM_DATE, DB_EXP_DATE, "changed", true, DB_PRICE_LIST_ITEM, DB_LINE, DB_RESERVATION);
    public static final Ticket DB_REMOVED_TICKET = new Ticket(DB_ID_REMOVE, DB_PUR_DATE, DB_EXP_DATE, "", false, DB_PRICE_LIST_ITEM, DB_LINE, DB_RESERVATION);
    public static final Ticket DB_INVALID_TICKET = new Ticket(DB_ID_INVALID, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_LINE, DB_RESERVATION);

    public static final TicketReportDTO DB_TICKET_DTO = new TicketReportDTO(DB_TICKET);
    public static final TicketReportDTO DB_DELETED_TICKET_DTO = new TicketReportDTO(DB_REMOVED_TICKET);
    public static final TicketReportDTO DB_INVALID_TICKET_DTO = new TicketReportDTO(DB_INVALID_TICKET);

    public static final Ticket DB_INVALID_LINE_TICKET = new Ticket(DB_ID, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_INVALID_LINE, DB_RESERVATION);
    public static final Ticket DB_NULL_LINE_TICKET = new Ticket(DB_ID, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, null, DB_RESERVATION);

    public static final Ticket DB_INVALID_RES_TICKET = new Ticket(DB_ID, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_LINE, DB_INVALID_RES);
    public static final Ticket DB_NULL_RES_TICKET = new Ticket(DB_ID, DB_PUR_DATE, DB_EXP_DATE, "", true, DB_PRICE_LIST_ITEM, DB_LINE, null);

    public static final Ticket DB_EMPTY_TICKET = new Ticket();
    public static final List<Ticket> DB_TICKET_LIST = new ArrayList<Ticket>() {{
        add(DB_TICKET);
        add(DB_EMPTY_TICKET);
    }};

    public static final LocalDate DB_DATE1 = LocalDate.of(2015, 1, 1);
    public static final LocalDate DB_DATE2 = LocalDate.of(2018, 12, 12);

}

