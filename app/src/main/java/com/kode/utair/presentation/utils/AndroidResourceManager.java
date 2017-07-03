package com.kode.utair.presentation.utils;

import android.content.Context;

import com.kode.utair.domain.commons.ResourceManager;

import javax.inject.Inject;

public class AndroidResourceManager implements ResourceManager {

    private Context context;

    @Inject
    public AndroidResourceManager(Context context) {
        this.context = context;
    }

    @Override
    public String getString(String resourceId) {
        return context.getResources().getString(getIdentifier(resourceId, "string"));
    }

    @Override
    public Integer getInteger(String resourceId) {
        return context.getResources().getInteger(getIdentifier(resourceId, "integer"));
    }

    private int getIdentifier (String name, String defType) {
       return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

}
