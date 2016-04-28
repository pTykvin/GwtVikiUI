package gwt.material.design.viki.client.share.dialog.simple;

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


import com.google.gwt.user.client.rpc.IsSerializable;

import gwt.material.design.viki.client.share.DoubtButtonSkin;

public class DoubtingInfo implements IsSerializable {

    private DoubtButtonSkin skin;
    private String doubtText;
    private String noDoubtText;

    public DoubtButtonSkin getSkin() {
        return skin;
    }

    public void setSkin(DoubtButtonSkin skin) {
        this.skin = skin;
    }

    public String getDoubtText() {
        return doubtText;
    }

    public void setDoubtText(String doubtText) {
        this.doubtText = doubtText;
    }

    public String getNoDoubtText() {
        return noDoubtText;
    }

    public void setNoDoubtText(String noDoubtText) {
        this.noDoubtText = noDoubtText;
    }
}
