package org.uninstal.skywars.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.uninstal.skywars.util.Utils;
import org.uninstal.skywars.util.Values;

public class GameScoreboard {

	private GamePlayer gamePlayer;
	private Game game;
	
	private String[] currentLines;
	private Map<Integer, Team> scores;
	private Scoreboard scoreboard;
	private Objective objective;
	private Player player;

	public GameScoreboard(GamePlayer player) {
		this.currentLines = Values.SCOREBOARD_LOBBY;
		this.scores = new HashMap<>();
		this.player = player.getBukkit();
		this.game = player.getGame();
		this.gamePlayer = player;
		
		this.initScoreboard();
		this.initLines();
	}
	
	public void show() {
		player.setScoreboard(scoreboard);
	}
	
	public void hide() {
		player.setScoreboard(null);
	}
	
	public void update() {
		int size = currentLines.length;
		
		for(int i = size; i >= 1; i--) {
			
			String text = currentLines[size - i];
			Team team = this.scores.get(i);
			setText(team, text);
			
			continue;
		}
	}
	
	public void switchLines(String[] lines) {
		this.currentLines = lines.clone();
		this.initLines();
	}
	
	private void initScoreboard() {
		
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = scoreboard.registerNewObjective("z", "dummy");
		this.objective.setDisplayName("DISPLAYNAME");
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	private void initLines() {
		int changed = 0;
		
		for(int line = 15; line > (15 - currentLines.length); line--) {
			
			String text = currentLines[15 - line];
			String entry = "§" + Integer.toHexString(line);
			Team team = null;
			
			if(scores.containsKey(line)) {
				
				team = scores.get(line);
				setText(team, text);
			}
			
			else {
				
				team = scoreboard.registerNewTeam(entry);
				team.addEntry(entry);
				setText(team, text);
				
				objective.getScore(entry).setScore(line);
				scores.put(line, team);
			}
			
			++changed;
			continue;
		}
		
		if(changed < scores.size()) {
			int size = scores.size();
			
			for(int i = size; i > changed; i--)
				scores.get(i).unregister();
		}
	}
	
	private void setText(Team team, String text) {
		text = api(text);
		
		if(text.length() > 16) {
			
			String prefix = text.substring(0, 16);
			String color = ChatColor.getLastColors(prefix);
			String suffix = (color.isEmpty() ? "§f" : color) + text.substring(16, Math.min(text.length(), 30));
			
			team.setPrefix(prefix);
			team.setSuffix(suffix);
		}
		
		else {
			
			team.setPrefix(text);
			team.setSuffix(new String());
		}
	}
	
	private String api(String string) {
		
		return string.replace("&", "§").replace("<id>", game.getId())
			.replace("<kills>", String.valueOf(gamePlayer.getKills()))
			.replace("<remain-players>", String.valueOf(game.getRemainPlayers().size()))
			.replace("<remain-time>", Utils.timeFormat(game.getThread().getRemainTime(), "mm:ss"));
	}
}