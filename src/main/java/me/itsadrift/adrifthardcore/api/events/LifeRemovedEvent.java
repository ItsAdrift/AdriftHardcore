package me.itsadrift.adrifthardcore.api.events;

import me.itsadrift.adrifthardcore.api.HardcorePlayer;

public class LifeRemovedEvent extends LifeChangedEvent {
    public LifeRemovedEvent(HardcorePlayer player, int amount, boolean death) {
        super(player, amount, death ? LifeChanged.DEATH_REMOVED : LifeChanged.REMOVED);
    }
}
