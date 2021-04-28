create table language (
  id integer primary key autoincrement not null,
  name varchar(255)
);

-- таблица, в которой хранится информация о пользователе приложения
create table user_app (
    name varchar(255) not null,
    email varchar(255) unique not null,
    birthday date,
    notification_time DateTime default '10:00:00',
    every_X_minute int not null default 30,
    language_id integer not null references language(id) default 1
);

-- таблица возможных этапов в жизни растения (прорастание семян, всходы, вегетативный период,
-- период цветения и т.д) P.S. Может быть нужно заменить на enum, ведь не так уж часто
-- предполагается добавлять новые этапы жизни
create table period_of_life (
    id integer primary key autoincrement not null,
    name varchar(255)
);

-- таблица, в которой хранятся название и описание разных почв
create table soil (
    id integer primary key autoincrement not null,
    name varchar(255) not null,
    description text
);

-- таблица всех растений с описанием
create table plants (
    id integer primary key autoincrement not null,
    name varchar(255) not null unique,
    description text
);

-- таблица, в которой по периоду жизни растения и времени года находятся все характеристики
-- для его хорошего самочувствия
--create type seasons as enum ('summer', 'fall', 'winter', 'spring');
create table season_enum (
    id integer primary key autoincrement not null,
    season varchar(255) not null,
    check (season = 'summer' || season = 'fall' || season = 'winter' || season = 'spring')
);

create table characteristics_by_period (
    plant_id int not null references plants(id),
    period_id int not null references period_of_life(id),
    season_id int not null references season_enum(id),
    light int not null, -- 0: не любит свет, 1: тенелюбивое, 2: неприхотливое,
    -- 3: любит рассеянный свет, 4: любит много света (прямые лучи)
    temperature_up int not null, -- верхний предел температуры
    temperature_down int not null, -- нижний предел температуры
    humidity_description int not null, -- 0: любит сухость, 1: неприхотливое, 2: любит влажность
    humidity_up int not null, -- верхний предел влажности
    humidity_down int not null, -- нижний предел влажности
    pot_size int, -- 0: любит тесноту, 1: неприхотливое, 2: любит много места
    check (light >= 0 and light <= 4),
    check (temperature_up > temperature_down),
    check (humidity_description >= 0 and humidity_description <= 2),
    check (humidity_up >= 25 and humidity_up <= 100),
    check (humidity_down >= 25 and humidity_down <= 100),
    check (humidity_up > humidity_down),
    check (pot_size >= 0 or pot_size <= 2),
    primary key(plant_id, period_id, season)
);

-- таблица с информацией о том, какая почва подходит для этого растения
create table soil_for_plant (
    plant_id int not null references plants(id),
    soil_id int not null references soil(id),
    primary key(plant_id)
);

-- таблица, из которой можно получить все его растения
create table plants_of_user (
    id integer primary key autoincrement not null,
    plant_id int not null references plants(id),
    name varchar(255) unique not null,
    room_id int not null references rooms(id),
    watering_day date not null,
    transplant_day date not null,
    fertilization_day date not null,
    period_id integer references period_of_life(id),
    coord_x decimal not null,
    coord_y decimal not null,
    primary key(name)
);

-- таблица, в которой содержится информация о поливе растений летом
--create type irrigation as enum ('weak', 'middle', 'strong');

create table irrigation_power_enum (
    id integer primary key autoincrement not null,
    irrigation_power varchar(255),
    check (irrigation_power = 'weak' || irrigation_power = 'middle' || irrigation_power = 'strong')
);

create table watering_summer (
    plant_id int not null references plants(id),
    every_X_day int not null,
    description text,
    irrigation_power_id integer not null references irrigation_power_enum(id) default 1,
    primary key(plant_id)
);

-- таблица, в которой содержится информация о поливе растений зимой
create table watering_winter (
    plant_id int not null references plants(id),
    every_X_day int not null,
    irrigation_power_id integer not null references irrigation_power_enum(id) default 1,
    description text,
    primary key(plant_id)
);

-- таблица, в которой содержится информация о поливе растений осенью
create table watering_fall (
    plant_id int not null references plants(id),
    every_X_day int not null,
    irrigation_power_id integer not null references irrigation_power_enum(id) default 1,
    primary key(plant_id)
);

-- таблица, в которой содержится информация о поливе растений весной
create table watering_spring (
    plant_id int not null references plants(id),
    every_X_day int not null,
    irrigation_power_id integer not null references irrigation_power_enum(id) default 1,
    description text,
    primary key(plant_id)
);

-- таблица, в которой содержится информация о пересадке растений и какую почву использовать и как часто пересаживать
create table plant_transplant (
    plant_id int not null references plants(id),
    soil_id int not null references soil(id),
    every_X_month int not null,
    description text,
    primary key(plant_id)
);

-- таблица, в которой хранится информация об удобрениях и их описание
create table fertilizers (
    id integer primary key autoincrement not null,
    name varchar(255) not null unique,
    description text
);

-- таблица, в которой содержится информация об удобрении растений и какое удобрение использовать и как часто удобрять
create table plant_fertilization (
    plant_id int not null references plants(id),
    fertilizer_id int not null references fertilizers(id),
    every_X_month int not null,
    description text,
    primary key(plant_id, fertilizer_id)
);

-- таблица, в которой написано как часто нужно рыхлить землю у растения
create table plant_loosening (
    plant_id int not null references plants(id),
    every_X_day int not null,
    description text,
    primary key(plant_id)
);

-- таблица, в которой написано как часто нужно опрыскивать растение
create table plant_spraying (
    plant_id int not null references plants(id),
    every_X_day int not null,
    description text,
    primary key(plant_id)
);

-- таблица с возможными заболеваниями растений и путями лечения
create table illnesses (
    id integer primary key autoincrement not null,
    name text not null,
    description text not null,
    treatment text not null
);

-- таблица, в которой по растению можно посмотреть болезни, которые часто у него бывают
create table illnesses_plants (
    plant_id int not null references plants(id),
    illnesses_id int not null references illnesses(id),
    primary key(plant_id, illnesses_id)
);

-- таблица всех комнат пользователя, которые задаются координатами двух точек - левый верхний угол
-- и правый нижний
create table rooms(
    id integer primary key autoincrement not null,
    left_up decimal not null,
    right_down decimal not null
);


-- Для календаря. Я думаю, что изначально у каждого пользователя это будет пустая таблица,
-- а по мере того, как он будет добавлять растения в эту таблицу будет
-- записываться информация по датам о делах всяких
--create type works_with_plant as enum ('watering', 'transplant', 'fertilizers', 'loosening', 'spraying');

create table work_enum (
    id integer primary key autoincrement not null,
    work varchar(255),
    check (work = 'watering' || work = 'transplant' || work = fertilizers || work = 'loosening' || work = 'spraying')
);

create table calendar (
    day date not null,
    plant_id integer not null references plants_of_user(id),
    work_id int not null references work_enum(id),
    status int not null default 0,
    check (status = 1 or status = 0), -- 1 - дело сделано, -- 0 - дело не сделано
    primary key(date, plant_id, work)
);