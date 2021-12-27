package me.hopedev.AdvancedManager.utils;

public class LatencyUtil {

    // Small LatencyUtil made by Hope for BetterGitHooks

    private long prevPing;
    private long afterPing;

    public LatencyUtil() {
    }

    public final void start() {
        this.prevPing = System.currentTimeMillis();
    }

    public final void end() {
        this.afterPing = System.currentTimeMillis();
    }

    public final long getPing() {
        return this.afterPing - this.prevPing;
    }

    @Override
    public final String toString() {
        return (this.getPing() + "ms");
    }
}
