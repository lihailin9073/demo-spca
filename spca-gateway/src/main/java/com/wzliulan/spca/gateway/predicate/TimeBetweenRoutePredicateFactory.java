package com.wzliulan.spca.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {
    public TimeBetweenRoutePredicateFactory(){
        super(TimeBetweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(config.getStart());
            Date end = dateFormat.parse(config.getEnd());
            return new Predicate<ServerWebExchange>(){
                @Override
                public boolean test(ServerWebExchange serverWebExchange) {
                    Date now = new Date();
                    return now.after(start) && now.before(end); // 当前日期在配置的 start-end之间，返回true
                }
            };
        } catch (ParseException e) {
            e.printStackTrace();
            return new Predicate<ServerWebExchange>(){
                @Override
                public boolean test(ServerWebExchange serverWebExchange) {
                    return false;
                }
            };
        }
    }

    // 控制配置类和配置文件的映射关系
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start","end");
    }
}
