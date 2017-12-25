package de.phyrone.phybansystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.phyrone.phybansystem.bantemplate.BanTemplate;
import de.phyrone.phybansystem.bantemplate.BanType;
import de.phyrone.phybansystem.util.UUIDFetcher;
import de.phyrone.phybansystem.util.Utils;
import me.leoko.advancedban.bukkit.BukkitMain;
import me.leoko.advancedban.manager.TimeManager;
import me.leoko.advancedban.manager.UUIDManager;
import me.leoko.advancedban.utils.Punishment;

public class Listner implements Listener {
	@EventHandler()
	public void onChat(PlayerCommandPreprocessEvent e) {
		String m = e.getMessage();
		if(m.split(" ")[0].equalsIgnoreCase("/"+Config.getInstance().CMD)) {
			
			Player p = e.getPlayer();
			String[] args = m.split(" ");
			if(p.hasPermission("ban.use")) {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
				return;
			}
			if(args.length != 3) {
				new BukkitRunnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HashMap<String, BanTemplate> resons = Config.getInstance().Reasons;
						ArrayList<String> resonss = new ArrayList<String>(resons.keySet());
						for( String r: resonss) {
							BanTemplate reason = resons.get(r);
							p.sendMessage(("&e"+r+": "+Utils.getPermColor(p, reason.getPerm())
							+reason.getName()+" &8>> &5"+reason.getType()+"&8(&e"+Utils.formatTime(reason.getBanTime())+"&8)"
							).replace("&", "§"));
						}
					}
				}.runTaskAsynchronously(BansysPlugin.getInstance());

			}else {
				new BukkitRunnable() {
					@Override
					public void run() {
						String player = null;
						UUID uuid = null;
						try {
							player = UUIDManager.get().getUUID(args[1]);
							if(player == null)throw new Exception("is Null");
							uuid = UUIDFetcher.getUUID(args[1]);
						}catch (Exception e) {
							p.sendMessage("§cSpieler nicht Gefunden!");
							return;
						}BanTemplate r = Config.getInstance().Reasons.getOrDefault(args[2], null);
						if(r == null) {
							p.sendMessage("§cGrund nich gefunden!");
							return;
						}
						if((r.getPerm() == null || p.hasPermission(r.getPerm())) && !Config.getInstance().ImmunePlayers.contains(uuid)) {
							new Punishment(args[1], player, r.getReason(), p.getName(), Utils.toAbPunishType(r), TimeManager.getTime(), TimeManager.getTime()+TimeUnit.HOURS.toMillis(r.getBanTime()), null, -1).create();
							if(r.getType() == BanType.BAN)
							r.setType(BanType.NOIPBAN);
							new Punishment(args[1], player, r.getReason(), p.getName(), Utils.toAbPunishType(r), TimeManager.getTime(), TimeManager.getTime()+TimeUnit.HOURS.toMillis(r.getBanTime()), null, -1).create();
						}
						else p.sendMessage(Config.getInstance().noPerm.replace("&", "§"));
					}
					
				}.runTaskAsynchronously(BansysPlugin.getInstance());
			}
		}else if(m.split(" ")[0].equalsIgnoreCase("/rlbans")) {
			Player p = e.getPlayer();
			if(p.hasPermission("ban.reload")) {
				e.setCancelled(true);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Config.load(BansysPlugin.conffile);
						Config.getInstance().toFile(BansysPlugin.conffile);
						p.sendMessage("§aConfig Reloaded!");
					}
				},"UpdateThread-"+UUID.randomUUID()).start();;
				p.sendMessage("§creloading Config...");
			}else {
				e.setCancelled(false);
			}
				
		}
	}
}
