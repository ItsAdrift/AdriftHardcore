package me.itsadrift.adrifthardcore.api.events;

import me.itsadrift.adrifthardcore.api.HardcorePlayer;

public class LifeAddedEvent extends LifeChangedEvent {
    public LifeAddedEvent(HardcorePlayer player, int amount) {
        super(player, amount, LifeChanged.ADDED);
    }
}
