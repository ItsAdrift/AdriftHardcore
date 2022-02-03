package me.itsadrift.adrifthardcore.api.events;

import me.itsadrift.adrifthardcore.api.HardcorePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LifeChangedEvent extends Event implements Cancellable {

    private HardcorePlayer hardcorePlayer;
    private int amount;
    private LifeChanged lifeChanged;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public LifeChangedEvent(HardcorePlayer player, int amount, LifeChanged lifeChanged) {
        this.hardcorePlayer = player;
        this.amount = amount;
        this.lifeChanged = lifeChanged;

        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public HardcorePlayer getHardcorePlayer() {
        return hardcorePlayer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LifeChanged getLifeChanged() {
        return lifeChanged;
    }
}
