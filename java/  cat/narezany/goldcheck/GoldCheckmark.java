
package cat.narezany.goldcheck;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import cat.narezany.margyt.plugin.MargyPlugin;

public final class GoldCheckmark extends MargyPlugin {

    private static final String MARK = "  ✓";
    private static final int GOLD = Color.rgb(230, 175, 35);

    private volatile String activeUid;

    @Override
    public void onStart(android.content.Context context) {
        activeUid = margyt().accountId();
        margyt().log("Gold Checkmark enabled");
    }

    @Override
    public void onAccountChanged(String uid) {
        activeUid = uid;
    }

    @Override
    public String onName(String uid, String name) {
        if (uid == null || !uid.equals(activeUid) || name == null) {
            return name;
        }

        if (name.endsWith(MARK)) {
            return name;
        }

        return name + MARK;
    }

    @Override
    public CharSequence onText(CharSequence text) {
        if (text == null) {
            return null;
        }

        String value = text.toString();

        if (!value.endsWith(MARK)) {
            return text;
        }

        SpannableString styled = new SpannableString(value);

        int start = value.length() - 1;

        styled.setSpan(
            new ForegroundColorSpan(GOLD),
            start,
            value.length(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        return styled;
    }
}
