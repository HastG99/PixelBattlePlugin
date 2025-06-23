package ru.hastg9.pixelbattle.util;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RGBUtil {
    private static final Pattern hex = Pattern.compile("#[0-9a-fA-F]{6}");
    private static final Pattern fix3 = Pattern.compile("\\&x[\\&0-9a-fA-F]{12}");


    public static String toChatColor(String hexCode) {
        StringBuilder magic = new StringBuilder("§x");
        char[] hexChars = hexCode.substring(1).toCharArray();
        int size = hexChars.length;

        for (int i = 0; i < size; i++) {
            char c = hexChars[i];
            magic.append('§').append(c);
        }

        return magic.toString();
    }

    public static String stripColor(String str) {
        String text = applyFormats(str);
        Matcher m = hex.matcher(text);

        while (m.find()) {
            String hexcode = m.group();
            text = text.replace(hexcode, "");
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        text = text.replaceAll("§.", ""); // Remove legacy colors starting with '&'

        return text;
    }

    public static String toChatColorString(String textInput) {
        String text = applyFormats(textInput);
        Matcher m = hex.matcher(text);

        while (m.find()) {
            String hexcode = m.group();
            text = text.replace(hexcode, toChatColor(hexcode));
        }

        return text;
    }

    private static String fixFormat1(String text) {
        return text.replace("&#", "#");
    }

    private static String fixFormat3(String text) {
        text = text.replace('§', '&');
        Matcher m = fix3.matcher(text);

        while (m.find()) {
            String hexcode = m.group();
            String fixed = String.valueOf(new char[]{
                    hexcode.charAt(3), hexcode.charAt(5), hexcode.charAt(7),
                    hexcode.charAt(9), hexcode.charAt(11), hexcode.charAt(13)
            });
            text = text.replace(hexcode, "#" + fixed);
        }

        return text;
    }

    private static String applyFormats(String text) {
        text = fixFormat1(text);
        text = fixFormat3(text);
        return text;
    }

    public static String color(String text) {
        text = toChatColorString(text);

        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
