package com.example.zoommod;

/** Holds all zoom state. Edit the constants below to tweak how zoom feels. */
public final class ZoomHandler {
    public static final double DEFAULT_ZOOM = 3.0;   // zoom level when you press the key
    public static final double MIN_ZOOM = 1.2;       // lowest zoom with the scroll wheel
    public static final double MAX_ZOOM = 30.0;      // highest zoom with the scroll wheel
    public static final double SCROLL_STEP = 1.15;   // how much each scroll notch changes zoom
    public static final double SMOOTHNESS = 14.0;    // higher = faster, snappier zoom

    private static boolean zooming = false;
    private static double targetZoom = DEFAULT_ZOOM;
    private static double currentZoom = 1.0;
    private static long lastNanos = 0L;

    private ZoomHandler() {}

    public static void setZooming(boolean value) {
        if (value && !zooming) {
            targetZoom = DEFAULT_ZOOM;
        }
        zooming = value;
    }

    public static boolean isZooming() {
        return zooming;
    }

    /** Called with the scroll amount (positive = scroll up = zoom in). */
    public static void onScroll(double amount) {
        double next = targetZoom * Math.pow(SCROLL_STEP, amount);
        targetZoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, next));
    }

    /** Smoothly moves toward the goal and returns the number to divide the FOV by. */
    public static double updateAndGetDivisor() {
        long now = System.nanoTime();
        double dt = lastNanos == 0L ? 0.0 : (now - lastNanos) / 1_000_000_000.0;
        lastNanos = now;
        dt = Math.min(dt, 0.1);

        double goal = zooming ? targetZoom : 1.0;
        currentZoom += (goal - currentZoom) * (1.0 - Math.exp(-SMOOTHNESS * dt));

        if (!zooming && Math.abs(currentZoom - 1.0) < 0.001) {
            currentZoom = 1.0;
        }
        return currentZoom;
    }
}
