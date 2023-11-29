package ru.prakticum;

import java.util.Random;

public class UserGenerator {
    public static User getDefault() {
        String[] login = new String[]{"anton543@google.com", "5693sem@yandex.ru", "123ddk@mail.com", "434kek@gmail.com"};
        String[] password = new String[]{"123456", "LOLOLOLO", "@#!$^", "573834990245"};
        String[] firstName = new String[]{"Tanya", "Semen", "i", "911"};
        int randomLogin = new Random().nextInt(login.length);
        int randomPassword = new Random().nextInt(password.length);
        int randomFirstName = new Random().nextInt(firstName.length);
        return new User(login[randomLogin], password[randomPassword], firstName[randomFirstName]);
    }
}
