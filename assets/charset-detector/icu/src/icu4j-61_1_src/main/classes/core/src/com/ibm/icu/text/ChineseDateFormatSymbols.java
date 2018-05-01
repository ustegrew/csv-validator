// © 2016 and later: Unicode, Inc. and others.
// License & terms of use: http://www.unicode.org/copyright.html#License
/****************************************************************************
 * Copyright (C) 2000-2014, International Business Machines Corporation and
 * others. All Rights Reserved.
 ****************************************************************************
 */

package com.ibm.icu.text;

import java.util.Locale;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.ULocale.Category;

/**
 * A subclass of {@link DateFormatSymbols} for {@link ChineseDateFormat}.
 * This class contains additional symbols corresponding to the
 * <code>ChineseCalendar.IS_LEAP_MONTH</code> field.
 *
 * @see ChineseDateFormat
 * @see com.ibm.icu.util.ChineseCalendar
 * @author Alan Liu
 * @deprecated ICU 50
 */
@Deprecated
public class ChineseDateFormatSymbols extends DateFormatSymbols {
    // Generated by serialver from JDK 1.4.1_01
    static final long serialVersionUID = 6827816119783952890L;

    /*
     * Package-private array that ChineseDateFormat needs to be able to
     * read.
     */
    String[] isLeapMonth;

    /**
     * Construct a ChineseDateFormatSymbols for the default <code>FORMAT</code> locale.
     * @see Category#FORMAT
     * @deprecated ICU 50
     */
    @Deprecated
    public ChineseDateFormatSymbols() {
        this(ULocale.getDefault(Category.FORMAT));
    }

    /**
     * Construct a ChineseDateFormatSymbols for the provided locale.
     * @param locale the locale
     * @deprecated ICU 50
     */
    @Deprecated
    public ChineseDateFormatSymbols(Locale locale) {
        super(ChineseCalendar.class, ULocale.forLocale(locale));
    }

    /**
     * Construct a ChineseDateFormatSymbols for the provided locale.
     * @param locale the locale
     * @deprecated ICU 50
     */
    @Deprecated
    public ChineseDateFormatSymbols(ULocale locale) {
        super(ChineseCalendar.class, locale);
    }

    /**
     * Construct a ChineseDateFormatSymbols for the provided calendar and locale.
     * @param cal the Calendar
     * @param locale the locale
     * @deprecated ICU 50
     */
    @Deprecated
    public ChineseDateFormatSymbols(Calendar cal, Locale locale) {
        // NPE is thrown here when cal is null, like the super class does
        super(cal.getClass(), locale);
    }

    /**
     * Construct a ChineseDateFormatSymbols for the provided calendar and locale.
     * @param cal the Calendar
     * @param locale the locale
     * @deprecated ICU 50
     */
    @Deprecated
    public ChineseDateFormatSymbols(Calendar cal, ULocale locale) {
        // NPE is thrown here when cal is null, like the super class does
        super(cal.getClass(), locale);
    }

    // New API
    /**
     * @deprecated ICU 50
     */
    @Deprecated
    public String getLeapMonth(int leap) {
        return isLeapMonth[leap];
    }

    /**
     * {@inheritDoc}
     * @deprecated ICU 50
     */
    @Deprecated
    @Override
    protected void initializeData(ULocale loc, ICUResourceBundle b, String calendarType) {
        super.initializeData(loc, b, calendarType);
        initializeIsLeapMonth();
    }

    @Override
    void initializeData(DateFormatSymbols dfs) {
        super.initializeData(dfs);
        if (dfs instanceof ChineseDateFormatSymbols) {
            // read-only array, no need to clone
            this.isLeapMonth = ((ChineseDateFormatSymbols)dfs).isLeapMonth;
        } else {
            initializeIsLeapMonth();
        }
    }

    private void initializeIsLeapMonth() {
        // The old way, obsolete:
        //isLeapMonth = calData.getStringArray("isLeapMonth");
        // The new way to fake this for backward compatibility (no longer used to format/parse):

        isLeapMonth = new String[2];
        isLeapMonth[0] = "";
        isLeapMonth[1] = (leapMonthPatterns != null)? leapMonthPatterns[DT_LEAP_MONTH_PATTERN_FORMAT_WIDE].replace("{0}", ""): "";
    }
}
