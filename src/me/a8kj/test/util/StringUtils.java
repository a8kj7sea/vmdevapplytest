package me.a8kj.test.util;

import net.md_5.bungee.api.ChatColor;

public class StringUtils {

	public static String colorize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static String format(String text, Object[] objects) {
		return String.format(text, objects);
	}
}
