package com.example.sqliteex;

import android.provider.BaseColumns;

import java.security.PrivateKey;
import java.util.PriorityQueue;

public class ContactContract {
    public static int DB_VERSION=1;
    public static String DB_NAME="mydb.db";

    private ContactContract()
    {

    }
    public static class Contact implements BaseColumns {
        public static String _ContactTable="myCOntacts";
        public static String _Name="name";
        public static String _Phno="phone_number";
        public static String _Address="address";
        public static String _Email="email";
    }

}
