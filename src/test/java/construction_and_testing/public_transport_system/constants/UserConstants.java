package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.Reservation;
import construction_and_testing.public_transport_system.domain.User;
import construction_and_testing.public_transport_system.domain.Validator;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;

import java.util.HashSet;

public class UserConstants {

    public static final int DB_REG_USERS_COUNT = 1;
    public static final int DB_VAL_COUNT = 1;
    public static final int DB_UNCHECKED_COUNT = 1;
    public static final int DB_TOTAL_COUNT = 2;

    public static final Long DB_USER_ID = 1L;
    public static final Long DB_USER_ID_INVALID = 999L;
    public static final Long DB_VAL_ID = 1L;
    public static final Long DB_REG_ID = 718L;


    public static final String DB_FIRST_NAME = "FirstName";
    public static final String DB_LAST_NAME = "LastName";
    public static final String DB_USERNAME = "username";
    public static final String DB_PASSWORD = "password";
    public static final String DB_EMAIL = "email@gmail.com";
    public static final String DB_TELEPHONE = "00213123";

    public static final String DB_VAL_FIRST_NAME = "ValFirstName";
    public static final String DB_VAL_LAST_NAME = "ValLastName";
    public static final String DB_VAL_USERNAME = "valUsername";
    public static final String DB_VAL_PASSWORD = "valPassword";
    public static final String DB_VAL_EMAIL = "email@gmail.com";


    public static final String DB_NEW_FIRST_NAME = "newFirstName";
    public static final String DB_NEW_LAST_NAME = "newLastName";
    public static final String DB_NEW_USERNAME = "newUsername";
    public static final String DB_NEW_PASSWORD = "newPassword";
    public static final String DB_NEW_EMAIL = "newEmail@gmail.com";
    public static final String DB_NEW_TELEPHONE = "00213124";

    public static final String DB_INVALID_USER_NAME = "invalidUsername";


    public static final User DB_USER = new User(DB_USER_ID, DB_FIRST_NAME, DB_LAST_NAME, DB_USERNAME, DB_PASSWORD,
            DB_EMAIL, DB_TELEPHONE, true);

    public static final User DB_CHANGED_USER = new User(DB_USER_ID, DB_FIRST_NAME, DB_LAST_NAME, DB_USERNAME, DB_PASSWORD,
            "newEmail@gmail.com", DB_TELEPHONE, true);

    public static final RegisteredUser DB_NEW_USER = new RegisteredUser(null, DB_NEW_FIRST_NAME, DB_NEW_LAST_NAME, DB_NEW_USERNAME, DB_NEW_PASSWORD
            , DB_NEW_EMAIL, DB_NEW_TELEPHONE, UsersDocumentsStatus.UNCHECKED, new HashSet<Reservation>(), 100.0, "document");

    public static final RegisteredUser DB_INVALID_USER = new RegisteredUser(null, DB_NEW_FIRST_NAME, null, DB_NEW_USERNAME, DB_NEW_PASSWORD
            , DB_NEW_EMAIL, DB_NEW_TELEPHONE, UsersDocumentsStatus.UNCHECKED, new HashSet<Reservation>(), 100.0, "docum");

    public static final Validator DB_VALIDATOR = new Validator(DB_VAL_ID, DB_NEW_FIRST_NAME, DB_NEW_LAST_NAME, DB_NEW_USERNAME, DB_NEW_PASSWORD
            , DB_NEW_EMAIL, DB_TELEPHONE, true);

    public static final RegisteredUser DB_REG_USER = new RegisteredUser(DB_REG_ID, DB_NEW_FIRST_NAME, DB_NEW_LAST_NAME, DB_NEW_USERNAME, DB_NEW_PASSWORD
            , DB_NEW_EMAIL, DB_NEW_TELEPHONE, UsersDocumentsStatus.UNCHECKED, new HashSet<Reservation>(), 100.0, "document");

}
