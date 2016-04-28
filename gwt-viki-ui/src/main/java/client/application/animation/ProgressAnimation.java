package client.application.animation;

import com.google.gwt.animation.client.Animation;

import gwt.material.design.client.ui.MaterialProgress;

public class ProgressAnimation extends Animation {

    private final MaterialProgress progress;

    public ProgressAnimation(MaterialProgress progress) {
        this.progress = progress;
    }

    @Override
    protected void onUpdate(double progress) {
        System.out.println(progress);
        this.progress.setPercent(progress % 0.1 * 100);
    }
}
