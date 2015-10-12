package com.goldengateway.apps.mijnlocaties.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by rob2 on 3-12-2014.
 */
public class StringFunc {

    public static String QuotedString(String str) {
        String res;
        res = "'" + str + "'";
        return res;
    }

    public static String encodeString(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO : dit is een eerste implementatie; nog uitbreiden, str moet altijd zonder spaties worden geretourneerd.
            e.printStackTrace();
        }
        return str;
    }

    public static String getSQLString( int intValue) {
        return "" + intValue;
    }

    public static String getSQLString( double dblValue) {
        return "" + dblValue;
    }

    public static String getSQLString( String strValue) {
        if (strValue == null) {
            return "NULL";
        } else {
            return QuotedString(strValue);
        }
    }
}
