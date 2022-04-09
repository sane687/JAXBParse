package alex.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateXmlAdapter extends XmlAdapter<String,LocalDate> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate v) {
        return dateFormat.format(v);
    }

    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v, dateFormat);
    }
}
