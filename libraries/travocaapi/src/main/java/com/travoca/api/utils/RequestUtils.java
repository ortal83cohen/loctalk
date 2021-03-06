package com.travoca.api.utils;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.travoca.api.model.SearchRequest;

import java.util.ArrayList;

/**
 * @author ortal
 * @date 2015-05-26
 */
public class RequestUtils {

    public static void apply(SearchRequest request) {

    }

    public static String capacity(int persons, int rooms) {
        if (persons == 0 || rooms == 0) {
            return "";
        }
        ArrayList<Integer> capacities = new ArrayList<>();
        for (int i = 1; i <= rooms; i++) {
            capacities.add(persons);
        }
        return list(capacities);
    }

    public static String list(Iterable list) {
        if (list == null) {
            return "";
        }
        return TextUtils.join(",", list);
    }

    public static String list(SparseBooleanArray list) {
        if (list == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (int i = 0; i < list.size(); i++) {
            int key = list.keyAt(i);
            // get the object by the key.
            boolean value = list.get(key);
            if (!value) {
                continue;
            }
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(",");
            }
            sb.append(key);
        }
        return sb.toString();
    }

    public static String list(SparseArray list) {
        if (list == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (int i = 0; i < list.size(); i++) {
            int key = list.keyAt(i);
            // get the object by the key.
            Object token = list.get(key);

            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(",");
            }
            sb.append(token);
        }
        return sb.toString();
    }
}
