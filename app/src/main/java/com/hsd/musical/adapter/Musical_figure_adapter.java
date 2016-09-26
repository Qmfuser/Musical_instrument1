package com.hsd.musical.adapter;

/**
 * Created by 秦孟飞 on 2016/9/22.
 */

public class Musical_figure_adapter {
    private int iconResId;
    private String intro;

    public Musical_figure_adapter(int iconResId, String intro) {
        super();
        this.iconResId = iconResId;
        this.intro = intro;
    }
    public int getIconResId() {
        return iconResId;
    }

    public String getIntro() {
        return intro;
    }

}