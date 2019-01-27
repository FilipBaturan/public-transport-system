package construction_and_testing.public_transport_system.constants;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.domain.enums.UsersDocumentsStatus;

import java.util.HashSet;

public class RegisteredUserConstants {

    public static final Long DB_VALID_ID = 2L;
    public static final Long DB_INTEGR_ID_2 = 5L;
    public static final Long DB_INVALID_ID = 123L;
    public static final String DB_USERNAME = "username1";
    public static final String DB_PASSWORD = "password1";
    public static final String DB_NAME = "name1";
    public static final String DB_LASTNAME = "lastname1";

    public static final Long DB_INTEGR_ID = 1L;
    public static final String DB_INTEGR_USERNAME = "username";
    public static final String DB_INTEGR_PASSWORD = "password";
    public static final String DB_INTEGR_NAME = "FirstName";
    public static final String DB_INTEGR_LASTNAME = "LastName";
    public static final String DB_INTEGR_EMAIL = "email@gmail.com";
    public static final String DB_INTEGR_TELEPHONE = "00213123";


    public static final RegisteredUser DB_USER_1 =
                    new RegisteredUser(2L, "name1", "lastname1", "username1", "password1", "mail@gmail.com", "0123456789",
                                        UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");

    public static final RegisteredUser DB_USER_2 =
            new RegisteredUser(3L, "name2", "lastname2", "username2", "password2", "mail2@gmail.com", "0123456780",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");

    public static final RegisteredUser DB_INTEGR_USER_2 =
            new RegisteredUser(5L, "name2", "lastname2", "username2", "password2", "mail2@gmail.com", "0123456780",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");


    public static final RegisteredUser DB_USER_3 =
            new RegisteredUser(null, "name2", "lastname2", "username53234", "password2", "mail2@gmail.com", "0123456780",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");


    public static final RegisteredUser DB_INTEGR_MODIFIED_USER_1 =
            new RegisteredUser(DB_INTEGR_ID_2, "Name1", "MyLastName", "username1", "password1", "mail@gmail.com", "0123456789",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");


    public static final RegisteredUser DB_MODIFIED_USER_1 =
            new RegisteredUser(2L, "Name1", "MyLastName", "username1", "password1", "mail@gmail.com", "0123456789",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");

    public static final RegisteredUser DB_MODIFIED_USER_INVALID_ID =
            new RegisteredUser(123L, "Name1", "MyLastName", "username1", "password1", "mail@gmail.com", "0123456789",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "");


    public static final RegisteredUser DB_DEL_USER_1 =
            new RegisteredUser(2L, "name1", "lastname1", "username1", "password1", "mail@gmail.com", "0123456789",
                    UsersDocumentsStatus.UNCHECKED, new HashSet<>(), 0.0, "", false);

}
