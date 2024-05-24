insert into
    cpu_minute_usage(sampling_date, usage)
values
    (CURRENT_TIMESTAMP, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '1' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '2' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '3' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '4' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '5' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '6' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '7' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '8' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '9' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '10' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '11' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '12' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '13' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '14' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '15' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '16' MINUTE, FLOOR(20 + (RAND() * 61))),
    (CURRENT_TIMESTAMP - INTERVAL '17' MINUTE, FLOOR(20 + (RAND() * 61)));


insert into
    cpu_daily_usage(avg_usage, max_usage, min_usage, sampling_date)
values
    (24.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '1' DAY ),
    (25.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '2' DAY ),
    (26.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '3' DAY ),
    (27.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '4' DAY ),
    (28.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '5' DAY ),
    (29.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '6' DAY ),
    (30.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '7' DAY ),
    (31.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '8' DAY ),
    (32.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '9' DAY ),
    (33.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '10' DAY ),
    (44.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '11' DAY ),
    (24.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '12' DAY ),
    (42.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '13' DAY ),
    (55.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '14' DAY ),
    (11.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '15' DAY ),
    (22.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '16' DAY ),
    (24.6, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)), CURRENT_TIMESTAMP - INTERVAL '17' DAY );

insert into
    cpu_hourly_usage(avg_usage, max_usage, min_usage, sampling_count, sampling_date, total_usage)
values
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '1' HOUR ,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '2' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '3' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '4' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '5' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '6' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '7' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '8' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '9' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '10' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '11' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '12' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '13' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '14' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '15' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '16' HOUR,200),
    (24.5, FLOOR(20 + (RAND() * 61)), FLOOR((RAND() * 20)),60, CURRENT_TIMESTAMP - INTERVAL '17' HOUR,200);