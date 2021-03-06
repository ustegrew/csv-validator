// © 2017 and later: Unicode, Inc. and others.
// License & terms of use: http://www.unicode.org/copyright.html#License
package com.ibm.icu.impl.number.parse;

import java.math.BigDecimal;
import java.util.Comparator;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;

/**
 * @author sffc
 *
 */
public class ParsedNumber {

    public DecimalQuantity_DualStorageBCD quantity;

    /**
     * The index of the last char consumed during parsing. If parsing started at index 0, this is equal
     * to the number of chars consumed. This is NOT necessarily the same as the StringSegment offset;
     * "weak" chars, like whitespace, change the offset, but the charsConsumed is not touched until a
     * "strong" char is encountered.
     */
    public int charEnd;

    /**
     * Boolean flags (see constants below).
     */
    public int flags;

    /**
     * The pattern string corresponding to the prefix that got consumed.
     */
    public String prefix;

    /**
     * The pattern string corresponding to the suffix that got consumed.
     */
    public String suffix;

    /**
     * The currency that got consumed.
     */
    public String currencyCode;

    public static final int FLAG_NEGATIVE = 0x0001;
    public static final int FLAG_PERCENT = 0x0002;
    public static final int FLAG_PERMILLE = 0x0004;
    public static final int FLAG_HAS_EXPONENT = 0x0008;
    public static final int FLAG_HAS_DEFAULT_CURRENCY = 0x0010;
    public static final int FLAG_HAS_DECIMAL_SEPARATOR = 0x0020;
    public static final int FLAG_NAN = 0x0040;
    public static final int FLAG_INFINITY = 0x0080;
    public static final int FLAG_FAIL = 0x0100;

    /** A Comparator that favors ParsedNumbers with the most chars consumed. */
    public static final Comparator<ParsedNumber> COMPARATOR = new Comparator<ParsedNumber>() {
        @Override
        public int compare(ParsedNumber o1, ParsedNumber o2) {
            return o1.charEnd - o2.charEnd;
        }
    };

    public ParsedNumber() {
        clear();
    }

    /**
     * Clears the data from this ParsedNumber, in effect failing the current parse.
     */
    public void clear() {
        quantity = null;
        charEnd = 0;
        flags = 0;
        prefix = null;
        suffix = null;
        currencyCode = null;
    }

    public void copyFrom(ParsedNumber other) {
        quantity = other.quantity == null ? null
                : (DecimalQuantity_DualStorageBCD) other.quantity.createCopy();
        charEnd = other.charEnd;
        flags = other.flags;
        prefix = other.prefix;
        suffix = other.suffix;
        currencyCode = other.currencyCode;
    }

    /**
     * Call this method to register that a "strong" char was consumed. This should be done after calling
     * {@link StringSegment#setOffset} or {@link StringSegment#adjustOffset} except when the char is
     * "weak", like whitespace.
     *
     * <p>
     * <strong>What is a strong versus weak char?</strong> The behavior of number parsing is to "stop"
     * after reading the number, even if there is other content following the number. For example, after
     * parsing the string "123 " (123 followed by a space), the cursor should be set to 3, not 4, even
     * though there are matchers that accept whitespace. In this example, the digits are strong, whereas
     * the whitespace is weak. Grouping separators are weak, whereas decimal separators are strong. Most
     * other chars are strong.
     *
     * @param segment
     *            The current StringSegment, usually immediately following a call to setOffset.
     */
    public void setCharsConsumed(StringSegment segment) {
        charEnd = segment.getOffset();
    }

    /**
     * Returns whether this the parse was successful. To be successful, at least one char must have been
     * consumed, and the failure flag must not be set.
     */
    public boolean success() {
        return charEnd > 0 && 0 == (flags & FLAG_FAIL);
    }

    public boolean seenNumber() {
        return quantity != null || 0 != (flags & FLAG_NAN) || 0 != (flags & FLAG_INFINITY);
    }

    public Number getNumber() {
        return getNumber(false);
    }

    public Number getNumber(boolean forceBigDecimal) {
        boolean sawNegative = 0 != (flags & FLAG_NEGATIVE);
        boolean sawNaN = 0 != (flags & FLAG_NAN);
        boolean sawInfinity = 0 != (flags & FLAG_INFINITY);

        // Check for NaN, infinity, and -0.0
        if (sawNaN) {
            return Double.NaN;
        }
        if (sawInfinity) {
            if (sawNegative) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        if (quantity.isZero() && sawNegative) {
            return -0.0;
        }

        if (quantity.fitsInLong() && !forceBigDecimal) {
            long l = quantity.toLong();
            if (0 != (flags & FLAG_NEGATIVE)) {
                l *= -1;
            }
            return l;
        }

        BigDecimal d = quantity.toBigDecimal();
        if (0 != (flags & FLAG_NEGATIVE)) {
            d = d.negate();
        }
        // Special case: MIN_LONG
        if (d.compareTo(BigDecimal.valueOf(Long.MIN_VALUE)) == 0 && !forceBigDecimal) {
            return Long.MIN_VALUE;
        }
        return d;

    }
}
