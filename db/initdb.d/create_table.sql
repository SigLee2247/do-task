create table cpu_daily_usage (
                                 max_usage integer not null,
                                 min_usage integer not null,
                                 sampling_date date not null,
                                 cpu_daily_usage_id bigint not null auto_increment,
                                 avg_usage varchar(255) not null,
                                 primary key (cpu_daily_usage_id)
);
create table cpu_hourly_usage (
                                  max_usage integer not null,
                                  min_usage integer not null,
                                  sampling_count integer not null,
                                  total_usage integer not null,
                                  cpu_hourly_usage_id bigint not null auto_increment,
                                  sampling_date datetime(6) not null,
                                  avg_usage varchar(255) not null,
                                  primary key (cpu_hourly_usage_id)
);

create table cpu_minute_usage (
                                  usages integer not null,
                                  cpu_minute_usage_id bigint not null auto_increment,
                                  sampling_date datetime(6) not null,
                                  primary key (cpu_minute_usage_id)
);