package client.application.animation;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
