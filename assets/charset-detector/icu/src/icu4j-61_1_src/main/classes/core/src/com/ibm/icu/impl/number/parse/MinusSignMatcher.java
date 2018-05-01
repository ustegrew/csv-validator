// © 2017 and later: Unicode, Inc. and others.
// License & terms of use: http://www.unicode.org/copyright.html#License
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.text.DecimalFormatSymbols;

/**
 * @author sffc
 *
 */
public class MinusSignMatcher extends SymbolMatcher {

    private static final MinusSignMatcher DEFAULT = new MinusSignMatcher(false);
    private static final MinusSignMatcher DEFAULT_ALLOW_TRAILING = new MinusSignMatcher(true);

    public static MinusSignMatcher getInstance(DecimalFormatSymbols symbols, boolean allowTrailing) {
        String symbolString = symbols.getMinusSignString();
        if (DEFAULT.uniSet.contains(symbolString)) {
            return allowTrailing ? DEFAULT_ALLOW_TRAILING : DEFAULT;
        } else {
            return new MinusSignMatcher(symbolString, allowTrailing);
        }
    }

    private final boolean allowTrailing;

    private MinusSignMatcher(String symbolString, boolean allowTrailing) {
        super(symbolString, DEFAULT.uniSet);
        this.allowTrailing = allowTrailing;
    }

    private MinusSignMatcher(boolean allowTrailing) {
        super(UnicodeSetStaticCache.Key.MINUS_SIGN);
        this.allowTrailing = allowTrailing;
    }

    @Override
    protected boolean isDisabled(ParsedNumber result) {
        return 0 != (result.flags & ParsedNumber.FLAG_NEGATIVE)
                || (allowTrailing ? false : result.seenNumber());
    }

    @Override
    protected void accept(StringSegment segment, ParsedNumber result) {
        result.flags |= ParsedNumber.FLAG_NEGATIVE;
        result.setCharsConsumed(segment);
    }

    @Override
    public String toString() {
        return "<MinusSignMatcher>";
    }
}
