package me.a8kj.test.listener.custom;

import org.bukkit.Server;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

// This Class made to save data !

public class ServerReloadEvent extends Event implements Cancellable {

	private boolean cancelled;

	private Server server;

	public Server getServer() {
		return server;
	}

	public ServerReloadEvent(Server server) {
		this.server = server;
	}

	private static final HandlerList handlerList = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public HandlerList getHandlers() {
		return handlerList;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean b) {
		this.cancelled = b;
	}
}
