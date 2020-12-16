package com.dbuzin.hashcalculator.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPassword {
    private static final String lower = "abcdefghijklmnopqrstuvwxyz";
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String nums = "0123456789";
    private static final String specials = "!#$&+-_";
    // Параметры добавления символов в пароль
    private boolean useUpper;
    private boolean useLower;
    private boolean useNums;
    private boolean useSpecial;

    private RandomPassword() {
        throw new UnsupportedOperationException("Empty constructor");
    }

    // Конструктор
    private RandomPassword(RandomPasswordBuilder builder) {
        this.useLower = builder.useLower;
        this.useUpper = builder.useUpper;
        this.useNums = builder.useNums;
        this.useSpecial = builder.useSpecial;
    }

    // Внутренний класс строителя
    public static class RandomPasswordBuilder {

        private boolean useLower;
        private boolean useUpper;
        private boolean useNums;
        private boolean useSpecial;

        // Инициализация параметров
        public RandomPasswordBuilder() {
            this.useLower = true;
            this.useUpper = false;
            this.useNums = false;
            this.useSpecial = false;
        }

        // Сеттеры
        public RandomPasswordBuilder useLower(boolean useLower) {
            this.useLower = useLower;
            return this;
        }
        public RandomPasswordBuilder useUpper(boolean useUpper) {
            this.useUpper = useUpper;
            return this;
        }
        public RandomPasswordBuilder useNums(boolean useNums) {
            this.useNums = useNums;
            return this;
        }
        public RandomPasswordBuilder useSpecial(boolean useSpecial) {
            this.useSpecial = useSpecial;
            return this;
        }

        //Сборка
        public RandomPassword build() {
            return new RandomPassword(this);
        }
    }

    //Метод генерации пароля
    public String generatePassword(int length){
        StringBuilder password = new StringBuilder(length);
        Random random = new Random();
        List<String> charCategories = new ArrayList<>(4);
        if (useLower) {
            charCategories.add(lower);
        }
        if (useUpper) {
            charCategories.add(upper);
        }
        if (useNums) {
            charCategories.add(nums);
        }
        if (useSpecial) {
            charCategories.add(specials);
        }
        try {
            for (int i = 0; i < length; i++) {
                String charCategory = charCategories.get(random.nextInt(charCategories.size()));
                int position = random.nextInt(charCategory.length());
                password.append(charCategory.charAt(position));
            }
        } catch (IllegalArgumentException e) {
            return "Choose at least one parameter";
        }
        return new String(password);
    }
}
