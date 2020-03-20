package smallmart.service;

import org.springframework.core.convert.converter.Converter;
import smallmart.model.Part;

public class StringToPartConverter implements Converter<String, Part> {

    @Override
    public Part convert(String s) {
        String[] data = s.split(",");
        return new Part(Integer.valueOf(data[0]), Integer.valueOf(data[1]));
    }
}
