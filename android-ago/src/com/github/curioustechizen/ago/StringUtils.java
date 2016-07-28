package com.github.curioustechizen.ago;

import android.text.Html;
import android.text.Spanned;

/**
 * @author Piotr Winiarski <piotr.winiarski1@gmail.com>
 */

public class StringUtils {

    public static Spanned getHtmlFormattedString(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }
}
