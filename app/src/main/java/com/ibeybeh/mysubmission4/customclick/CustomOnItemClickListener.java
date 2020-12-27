package com.ibeybeh.mysubmission4.customclick;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener{
    private int position;
    private OnItemClickCallback onItemClickCallBack;
    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback){
        this.position = position;
        this.onItemClickCallBack = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallBack.onItemClicked(view, position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
