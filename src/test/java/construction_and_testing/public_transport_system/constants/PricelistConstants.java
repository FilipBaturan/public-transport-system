package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.Pricelist;

import java.time.LocalDateTime;

public class PricelistConstants {

    public static final Long DB_ID = 1L;
    public static final Long DB_EXPIRED_ID = 2L;
    public static final Long DB_INTEGR_ID = 2L;
    public static final Long DB_NEW_ID = 3L;
    public static final Long DB_INTEGR_DELETE_ID = 3L;
    public static final Long DB_MODIFIED_ID = 9L;
    public static final int DB_COUNT = 3;

    public static final Long DB_INVALID_ID = 1245L;

    public static final LocalDateTime DB_START_DATE_VALID_INTEGR = LocalDateTime.of(2019, 1,22,0,0);
    public static final LocalDateTime DB_END_DATE_VALID_INTEGR = LocalDateTime.of(2019, 12, 31, 0,0);


    public static final LocalDateTime DB_START_DATE_VALID = LocalDateTime.of(2019, 1, 1, 0, 0);
    public static final LocalDateTime DB_END_DATE_VALID = LocalDateTime.of(2019, 3, 30, 0, 0);

    public static final LocalDateTime DB_START_DATE_EXPIRED = LocalDateTime.of(2018, 5, 1, 0, 0);
    public static final LocalDateTime DB_END_DATE_EXPIRED = LocalDateTime.of(2018, 10, 30, 0, 0);

    public static final LocalDateTime DB_NEW_START_DATE_VALID = LocalDateTime.of(2019, 5,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_VALID = LocalDateTime.of(2019, 10, 30, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_VALID_INTEGR = LocalDateTime.of(2020, 2,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_VALID_INTEGR = LocalDateTime.of(2020, 4, 30, 0,0);


    public static final LocalDateTime DB_NEW_START_DATE_INVALID_1 = LocalDateTime.of(2019, 2,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_1 = LocalDateTime.of(2019, 3, 20, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_2 = LocalDateTime.of(2018, 11,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_2 = LocalDateTime.of(2019, 5, 30, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_3 = LocalDateTime.of(2019, 2,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_3 = LocalDateTime.of(2019, 5, 30, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_4 = LocalDateTime.of(2018, 12,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_4 = LocalDateTime.of(2019, 2, 25, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_INTEGR_1 = LocalDateTime.of(2019, 2,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_INTEGR_1 = LocalDateTime.of(2019, 3, 20, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_INTEGR_2 = LocalDateTime.of(2019, 1,21,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_INTEGR_2 = LocalDateTime.of(2020, 1, 1, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_INTEGR_3 = LocalDateTime.of(2019, 11,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_INTEGR_3 = LocalDateTime.of(2020, 1, 3, 0,0);

    public static final LocalDateTime DB_NEW_START_DATE_INVALID_INTEGR_4 = LocalDateTime.of(2019, 1,15,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_INVALID_INTEGR_4 = LocalDateTime.of(2019, 2, 25, 0,0);

    public static final LocalDateTime DB_NEW_MODIFIED_START_DATE_NOW = LocalDateTime.of(2019, 1, 27, 0,0);

    public static final Pricelist DB_VALID_PRICELIST = new Pricelist(1L, DB_START_DATE_VALID, DB_END_DATE_VALID);
    public static final Pricelist DB_EXPIRED_PRICELIST = new Pricelist(2L, DB_START_DATE_EXPIRED, DB_END_DATE_EXPIRED);

    public static final Pricelist DB_VALID_NEW_PRICELIST = new Pricelist(3L, DB_NEW_START_DATE_VALID, DB_NEW_END_DATE_VALID);
    public static final Pricelist DB_VALID_NEW_PRICELIST_INTEGR = new Pricelist(null, DB_NEW_START_DATE_VALID_INTEGR, DB_NEW_END_DATE_VALID_INTEGR);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_1 = new Pricelist(4L, DB_NEW_START_DATE_INVALID_1, DB_NEW_END_DATE_INVALID_1);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_2 = new Pricelist(5L, DB_NEW_START_DATE_INVALID_2, DB_NEW_END_DATE_INVALID_2);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_3 = new Pricelist(6L, DB_NEW_START_DATE_INVALID_3, DB_NEW_END_DATE_INVALID_3);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_4 = new Pricelist(7L, DB_NEW_START_DATE_INVALID_4, DB_NEW_END_DATE_INVALID_4);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_5 = new Pricelist(8L, DB_NEW_START_DATE_VALID, DB_END_DATE_VALID);

    public static final Pricelist DB_INVALID_NEW_PRICELIST_INTEGR_1 = new Pricelist(4L, DB_NEW_START_DATE_INVALID_INTEGR_1, DB_NEW_END_DATE_INVALID_INTEGR_1);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_INTEGR_2 = new Pricelist(5L, DB_NEW_START_DATE_INVALID_INTEGR_2, DB_NEW_END_DATE_INVALID_INTEGR_2);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_INTEGR_3 = new Pricelist(6L, DB_NEW_START_DATE_INVALID_INTEGR_3, DB_NEW_END_DATE_INVALID_INTEGR_3);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_INTEGR_4 = new Pricelist(7L, DB_NEW_START_DATE_INVALID_INTEGR_4, DB_NEW_END_DATE_INVALID_INTEGR_4);
    public static final Pricelist DB_INVALID_NEW_PRICELIST_INTEGR_5 = new Pricelist(8L, DB_START_DATE_VALID_INTEGR, DB_END_DATE_VALID_INTEGR);

    public static final Pricelist DB_MODIFIED_PRICELIST = new Pricelist(DB_MODIFIED_ID, DB_NEW_MODIFIED_START_DATE_NOW, DB_END_DATE_VALID);

    public static final LocalDateTime DB_NEW_START_DATE_VALID_INTEGR_MOD = LocalDateTime.of(2020, 6,1,0,0);
    public static final LocalDateTime DB_NEW_END_DATE_VALID_INTEGR_MOD = LocalDateTime.of(2020, 12, 31, 0,0);

    public static final Pricelist DB_MODIFIED_PRICELIST_INTEGR = new Pricelist(DB_INTEGR_DELETE_ID, DB_NEW_START_DATE_VALID_INTEGR_MOD, DB_NEW_END_DATE_VALID_INTEGR_MOD);


}
