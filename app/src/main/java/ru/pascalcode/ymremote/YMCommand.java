package ru.pascalcode.ymremote;

/**
 * Команды для Yandex Music
 */
public class YMCommand {

    public static final String PLAYPAUSE = "playpause";

    public static final String PREVIOUS = "previous";

    public static final String NEXT = "next";

    public static final String DISLIKE = "dislike";

    public static final String LIKE = "like";

    /**
     * После слеша задаём порядок команд с клавиатуры:
     * L - Стрелка влево
     * R - Стрелка вправо
     * U - Стрелка вверх
     * D - Стрелка вниз
     * T - Tab
     * Y - Комбинация Alt + Tab
     * E - Enter
     * цифры от 1 до 9 - пауза между командами в секундах.
     * цифра 0 - пауза между командами 100 миллисекунд.
     */
    public static final String RADIO = "radio/3e3e";

    public static final String FAVORITE = "radio/3yyyuuue00tte0eyyyye";

}
