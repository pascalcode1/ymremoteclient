package ru.pascalcode.ymremote;

public class Command {

    public static final String PLAYPAUSE = "playpause";

    public static final String PREVIOUS = "previous";

    public static final String NEXT = "next";

    public static final String DISLIKE = "dislike";

    public static final String LIKE = "like";

    /**
     * После слеша задаём порядок комманд с клавиатуры:
     * L - Стрелка влево
     * R - Стрелка вправо
     * U - Стрелка вверх
     * D - Стрелка вниз
     * T - Tab
     * Y - Комбинация Alt + Tab
     * E - Enter
     * цыфры от 1 до 9 - пауза между коммандами в секундах.
     * цыфра 0 - пауза между коммандами 100 миллисекунд.
     */
    public static final String RADIO = "radio/3e3e";

    public static final String FAVORITE = "radio/3yyyuuue00tte0eyyyye";


    /**
     * Системные комманды
     */
    public static final String VOLUME_UP = "volumeup";

    public static final String VOLUME_DOWN = "volumedown";

    public static final String SCREEN_SAVER = "screensaver";

}
