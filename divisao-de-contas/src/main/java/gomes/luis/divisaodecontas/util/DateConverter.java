package gomes.luis.divisaodecontas.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateConverter {
    public static Date localDateToDate(LocalDate data){
        Date dataConvertida;
        ZoneId defaultZoneId = ZoneId.systemDefault();
        ZonedDateTime dataComTimeZone = data.atStartOfDay(defaultZoneId);
        dataConvertida = Date.from(dataComTimeZone.toInstant());
        return dataConvertida;
    }
}
