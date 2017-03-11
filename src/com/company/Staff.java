package com.company;

import java.util.Date;

public class Staff extends Customer {

    // Class constructor
    public Staff(String firstname, String surname, String email, String address, String username, String password, Date dateOfBirth) {
        super(firstname, surname, email, address, username, password, dateOfBirth);
    }
}
