package org.maktab.digikala.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {

    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_FILTER_COLOR = "filterColor";
    private static final String PREF_LAST_ID = "lastId";

    public static String getSearchQuery(Context context) {
        return getSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setSearchQuery(Context context, String query) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getFilterColor(Context context) {
        return getSharedPreferences(context).getString(PREF_FILTER_COLOR, null);
    }

    public static void setFilterColor(Context context, String color) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_FILTER_COLOR, color)
                .apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
