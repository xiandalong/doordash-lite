package com.doordash;

import android.support.annotation.Nullable;

import java.util.List;

public class StringUtils {
    private static final String SUFFIX = ", ";

    public static String getTagString(@Nullable List<String> tagList) {
        if (tagList == null && tagList.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tagList.size(); i++) {
            sb.append(tagList.get(i));
            if (i < tagList.size() - 1) {
                sb.append(SUFFIX);
            }
        }
        return sb.toString().trim();
    }
}
