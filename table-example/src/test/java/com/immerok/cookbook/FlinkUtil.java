package com.immerok.cookbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.flink.table.api.Schema;

public class FlinkUtil {
    private static final ObjectMapper OBJECT_MAPPER =
            JsonMapper.builder().build();

    public byte[] jsonTestEvent(Schema s, List<String> l) throws com.fasterxml.jackson.core.JsonProcessingException {

        Map<String,String> json = new HashMap<String, String>();
        Iterator<String> stringIterator = l.iterator();


        s.getColumns().forEach(
                x -> {
                    json.put(x.getName(), stringIterator.next());
                }
        );
        return OBJECT_MAPPER.writer().writeValueAsBytes(json);
    }
}
