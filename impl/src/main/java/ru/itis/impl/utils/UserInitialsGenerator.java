package ru.itis.impl.utils;

public class UserInitialsGenerator {

    public static String generate(String name) {

        StringBuilder initials = new StringBuilder("");

        String[] fullName = name.split(" ");
        for (String namePart: fullName) {
            initials.append(namePart.charAt(0));
        }

        return initials.toString();
    }

}
