import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlugDj extends JavaPlugin {
	private String noperm;
	protected String prefix;
	
	protected boolean toggle = true;
	protected boolean debug = false;
	protected boolean update = false;
	
	protected FileConfiguration config;
	
	
	public void onEnable() {
		new Updater(this);
		loadconfig();
		
		// TODO create Websocket between javascript bot and bukkit plugin ...  (server = plugin / client = javascript)
		
	}

	private void loadconfig(){
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		
		debug = config.getBoolean("debug");
		noperm = ChatColor.translateAlternateColorCodes('&', config.getString("msg.noperm"));
		prefix = ChatColor.translateAlternateColorCodes('&', config.getString("msg.prefix"));

	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		boolean isplayer = false;
		Player p = null;
		
		if ((sender instanceof Player)) {
			p = (Player)sender;
			isplayer = true;
		}
			
			if(cmd.getName().equalsIgnoreCase("plugdj") && args.length == 1){
												
				// reload config
				if(args[0].equalsIgnoreCase("reload")){
					if(isplayer){
						if(p.hasPermission("plugdj.reload")){
							loadconfig();
						return true;
					}
						else{
							p.sendMessage(noperm);
							return true;
						}
					}
					else{
							loadconfig();
						return true;
					}
				}
			
			// reset config
			if(args[0].equalsIgnoreCase("reset")){
				if(isplayer){
					if(p.hasPermission("plugdj.reset")){
						File configFile = new File(getDataFolder(), "config.yml");
					    configFile.delete();
					    saveDefaultConfig();
					    loadconfig();
					return true;
				}
					else{
						p.sendMessage(noperm);
						return true;
					}
				}
				else{
						File configFile = new File(getDataFolder(), "config.yml");
					    configFile.delete();
					    saveDefaultConfig();
					    loadconfig();
					return true;
				}
			}
				
		}
		
		// nothing to do here \o/
		return false;
	}
	
	
	protected void say(Player p, boolean b) {
		if(b) {
			System.out.println(ChatColor.stripColor(prefix + "------------------------------------------------"));
			System.out.println(ChatColor.stripColor(prefix + " PlugDJ is outdated. Get the new version here:"));
			System.out.println(ChatColor.stripColor(prefix + " http://www.pokemon-online.xyz/plugin"));
			System.out.println(ChatColor.stripColor(prefix + "------------------------------------------------"));
		}else {
		   	p.sendMessage(prefix + "------------------------------------------------");
		   	p.sendMessage(prefix + " PlugDJ is outdated. Get the new version here:");
		   	p.sendMessage(prefix + " http://www.pokemon-online.xyz/plugin");
		   	p.sendMessage(prefix + "------------------------------------------------");
		}
	}
	
}
