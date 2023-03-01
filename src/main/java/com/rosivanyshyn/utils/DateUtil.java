package com.rosivanyshyn.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Date Util class.
 * It contains methods to manipulate dates
 */
public class DateUtil {

    public DateUtil() {
    }

    /**
     * Convert dates range (date-from/date-till) to list of all dates in this range
     * @param datesMap HashMap contain date-from and date-till as dates range
     * @return list of all dates in input range
     */
    public ArrayList<LocalDate> convertDatesRangeToList(HashMap<Date, Date> datesMap) {
        ArrayList<LocalDate> datesList = new ArrayList<>();
        datesMap.forEach(
                (dateFrom, dateTill)-> {
                    datesList.addAll(
                            getDatesFromRange(convertToLocalDate(dateFrom),
                                    convertToLocalDate(dateTill)) );
                }
        );
        return datesList;
    }

    private List<LocalDate> getDatesFromRange(LocalDate dateFrom, LocalDate dateTill) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(dateFrom, dateTill) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(dateFrom::plusDays)
                .collect(Collectors.toList());
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Optional.ofNullable(dateToConvert)
                .map(Date::toLocalDate)
                .orElse(null);
    }
}
