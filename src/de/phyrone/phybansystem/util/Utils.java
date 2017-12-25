package de.phyrone.phybansystem.util;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

import de.phyrone.phybansystem.bantemplate.BanTemplate;
import de.phyrone.phybansystem.bantemplate.BanType;
import me.leoko.advancedban.utils.PunishmentType;

public class Utils {
	public static String getPermColor(Player p,String perm) {
		if(perm == null) {
			return "§a";
		}else if(p.hasPermission(perm)) {
			return "§a";
		}else {
			return "§c";
		}
	}public static String formatTime(int hours) {
		if(hours == -1) {
			return "§cPermanent!";
		}else if(hours == 0) {
			return "§cKick";
		}else if(hours == 1) {
			return "§c1. §eStunde";
		}else if(hours < 24) {
			return "§c"+String.valueOf(hours)+". §eStunden";
		}else if(hours == 24) {
			return "§c1. §eTag";
		}else if(hours < 24*30) {
			return "§c"+TimeUnit.HOURS.toDays(hours)+". §eTage";
		}else if(hours == 24*30) {
			return "§c1. §eMonat";
		}else if(hours > 30*24) {
			return "§c"+TimeUnit.HOURS.toDays(hours)/30+". §eMonate";
		}else
		return "§4Error";
	}public static HashMap<String, BanTemplate> getDefaultReasons() {
		HashMap<String, BanTemplate> ret = new HashMap<String,BanTemplate>();
		ret.put("1", new BanTemplate("Hacks | Clientmods").setBanTime(14*24));
		ret.put("2", new BanTemplate("Beleidigung").setType(BanType.MUTE).setBanTime(24));
		ret.put("3", new BanTemplate("Extrem").setBanTime(-1).setPerm("ban.extrem"));
		return ret;
	}public static PunishmentType toAbPunishType(BanTemplate template) {
		BanType cat = template.getType();
		int time = template.getBanTime();
		switch (cat) {
		case BAN:
			if(time == -1) {
				return PunishmentType.IP_BAN;
			}else {
				return PunishmentType.TEMP_IP_BAN;
			}
		case NOIPBAN:
			if(time == -1) {
				return PunishmentType.BAN;
			}else {
				return PunishmentType.TEMP_BAN;
			}
		case MUTE:
			if(time == -1) {
				return PunishmentType.MUTE;
			}else {
				return PunishmentType.TEMP_MUTE;
			}
		case WARN:
			if(time == -1) {
				return PunishmentType.WARNING;
			}else {
				return PunishmentType.TEMP_MUTE;
			}
		case KICK:
			return PunishmentType.KICK;
		default:
			return PunishmentType.KICK;
		}
	}

}
