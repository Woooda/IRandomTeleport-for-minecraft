import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class IRandomTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("IRandomTeleport has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("IRandomTeleport has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rtp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }

            Player player = (Player) sender;
            if (args.length == 0) {
                randomTeleport(player);
            } else if (args[0].equalsIgnoreCase("near")) {
                teleportNearPlayer(player);
            } else {
                player.sendMessage("Usage: /rtp [near]");
            }
            return true;
        }
        return false;
    }

    private void randomTeleport(Player player) {
        World world = player.getWorld();
        Random random = new Random();
        int x = random.nextInt(20000) - 10000;
        int z = random.nextInt(20000) - 10000;
        int y = world.getHighestBlockYAt(x, z);
        Location location = new Location(world, x, y, z);
        player.teleport(location);
        player.sendMessage("You have been teleported to a random location!");
    }

    private void teleportNearPlayer(Player player) {
        World world = player.getWorld();
        Player[] players = world.getPlayers().toArray(new Player[0]);

        if (players.length <= 1) {
            player.sendMessage("There are no other players nearby!");
            return;
        }

        Player target = players[new Random().nextInt(players.length)];
        Location location = target.getLocation().add(Math.random() * 20 - 10, 0, Math.random() * 20 - 10);

        player.teleport(location);
        player.sendMessage("You have been teleported near " + target.getName() + "!");
    }
}
