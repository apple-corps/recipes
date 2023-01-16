package com.immerok.cookbook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat  ;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.flink.table.api.Schema;

public class FlinkUtil {
    private static final ObjectMapper OBJECT_MAPPER =
            JsonMapper.builder().build().registerModule(new JavaTimeModule());
    public static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public byte[] jsonTestEvent(Schema s, List<String> l, Instant i) throws com.fasterxml.jackson.core.JsonProcessingException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.of("UTC"));

        Map<String,String> json = new HashMap<String, String>();
        Iterator<String> stringIterator = l.iterator();


        s.getColumns().forEach(
                x -> {
                    if (!x.getName().equals("ts")) json.put(x.getName(), stringIterator.next());
                }
        );
        InstantWrapped iw = new InstantWrapped(i);
        json.put("ts", Timestamp.from(i).toString());//OBJECT_MAPPER.writeValueAsString(formatter.format(i)));
        return OBJECT_MAPPER.writer().writeValueAsBytes(json);
    }


    private class ObjectMapped{
        Map<Object,Object> map;

        private ObjectMapped(Map map){
            this.map = map;
        }
    }



    private class InstantWrapped{
        //@JsonFormat(
        //        shape = JsonFormat.Shape.STRING,
        //        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        //        timezone = "UTC")
        Instant ts;
        private InstantWrapped(Instant i){
            this.ts = i;
        }
    }

        //@Override
        //public String toString() {
        //    return this.i.toString();
        //}


}
