package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.Pricelist;

import java.time.LocalDateTime;

public class PricelistConstants {

    public static final Long DB_ID = 1L;
    public static final Long DB_EXPIRED_ID = 2L;

    public static final LocalDateTime DB_START_DATE_VALID = LocalDateTime.of(2019, 1,1,0,0);
    public static final LocalDateTime DB_END_DATE_VALID = LocalDateTime.of(2019, 3, 30, 0,0);

    public static final LocalDateTime DB_START_DATE_EXPIRED = LocalDateTime.of(2018, 5,1,0,0);
    public static final LocalDateTime DB_END_DATE_EXPIRED = LocalDateTime.of(2018, 10, 30, 0,0);


    public static final Pricelist DB_VALID_PRICELIST = new Pricelist(1L, DB_START_DATE_VALID, DB_END_DATE_VALID);
    public static final Pricelist DB_EXPIRED_PRICELIST = new Pricelist(2L, DB_START_DATE_EXPIRED, DB_END_DATE_EXPIRED);
}
