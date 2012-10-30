package com.gmail.andrewzorn.dataplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class dataplugin extends JavaPlugin {
	@Override
	public void onEnable(){
		getLogger().info("onEnable has been invoked!");
	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("getLessHungry")) {
			player.setFoodLevel(20);
			player.sendMessage("Does a body good.");
			return true;
		}

		else if (cmd.getName().equalsIgnoreCase("getFixedUp")) {
			player.setHealth(20);
			player.sendMessage("That's the stuff...");
			return true;
		}

		else if (cmd.getName().equalsIgnoreCase("spareChange")) {
			if( player.getInventory().contains(Material.DIAMOND,1) &&
			player.getInventory().contains(Material.GOLD_INGOT,1) ) {
				player.sendMessage("You're no bum!");
				player.setHealth(1);
				player.setMetadata(player,"phony",true);
			}
			else {
				player.getInventory().addItem(new ItemStack(Material.DIAMOND,1));
				player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT,1));
				player.sendMessage("Don't spend it all in once place.");
				player.setMetadata(player,"beggar",true);
			}
			return true;
		}

		else if (cmd.getName().equalsIgnoreCase("getPaid")) {
			if(!(Boolean)this.getMetadata(player,"paid")) {
				player.getInventory().addItem(new ItemStack(Material.DIAMOND,64));
				player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT,64));
				player.sendMessage("Chasin' paper!");
				this.setMetadata(player,"paid",true);
			}
			else	player.sendMessage("You already BEEN paid!");
			return true;
		}

		else if (cmd.getName().equalsIgnoreCase("status")) {
			if((Boolean)this.getMetadata(player,"paid"))	player.sendMessage("Paid.");
			if((Boolean)this.getMetadata(player,"beggar"))	player.sendMessage("Beggar.");
			if((Boolean)this.getMetadata(player,"phony"))	player.sendMessage("Phony.");
			return true;
		}

		else if (cmd.getName().equalsIgnoreCase("reset")) {
			this.setMetadata(player,"paid",false);
			this.setMetadata(player,"beggar",false);
			this.setMetadata(player,"phony",false);
			player.sendMessage("Status reset.");
			return true;
		}

		return false;
	}

	public void setMetadata(Player player, String key, Object value){
		player.setMetadata(key,new FixedMetadataValue(this,value));
	}

	public Object getMetadata(Player player, String key){
		List<MetadataValue> values = player.getMetadata(key);  
		for(MetadataValue value : values) {
			if(value.getOwningPlugin().getDescription().getName().equals(this.getDescription().getName()))	return value.value();
		}
	}
}
