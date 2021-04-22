package dataBaseModule

class DBCreate {
    val str = "create table language (\n" +
            "  _id integer primary key,\n" +
            "  name varchar(255)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой хранится информация о пользователе приложения\n" +
            "create table user_app (\n" +
            "    name varchar(255) not null,\n" +
            "    email varchar(255) unique not null,\n" +
            "    birthday date,\n" +
            "    notification_time DateTime default '10:00:00',\n" +
            "    every_X_minute int not null default 30,\n" +
            "    language_id integer not null references language(_id) default 1\n" +
            ");\n" +
            "\n" +
            "-- таблица возможных этапов в жизни растения (прорастание семян, всходы, вегетативный период,\n" +
            "-- период цветения и т.д) P.S. Может быть нужно заменить на enum, ведь не так уж часто\n" +
            "-- предполагается добавлять новые этапы жизни\n" +
            "create table period_of_life (\n" +
            "    _id integer primary key,\n" +
            "    name varchar(255)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой хранятся название и описание разных почв\n" +
            "create table soil (\n" +
            "    _id integer primary key,\n" +
            "    name varchar(255) not null,\n" +
            "    description text\n" +
            ");\n" +
            "\n" +
            "-- таблица всех растений с описанием\n" +
            "create table plants (\n" +
            "    _id integer primary key,\n" +
            "    name varchar(255) not null unique,\n" +
            "    description text\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой по периоду жизни растения и времени года находятся все характеристики\n" +
            "-- для его хорошего самочувствия\n" +
            "--create type seasons as enum ('summer', 'fall', 'winter', 'spring');\n" +
            "create table season_enum (\n" +
            "    _id integer primary key,\n" +
            "    season varchar(255) not null,\n" +
            "    check (season = 'summer' || season = 'fall' || season = 'winter' || season = 'spring')\n" +
            ");\n" +
            "\n" +
            "create table characteristics_by_period (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    period_id int not null references period_of_life(_id),\n" +
            "    season_id int not null references season_enum(_id),\n" +
            "    light int not null, -- 0: не любит свет, 1: тенелюбивое, 2: неприхотливое,\n" +
            "    -- 3: любит рассеянный свет, 4: любит много света (прямые лучи)\n" +
            "    temperature_up int not null, -- верхний предел температуры\n" +
            "    temperature_down int not null, -- нижний предел температуры\n" +
            "    humidity_description int not null, -- 0: любит сухость, 1: неприхотливое, 2: любит влажность\n" +
            "    humidity_up int not null, -- верхний предел влажности\n" +
            "    humidity_down int not null, -- нижний предел влажности\n" +
            "    pot_size int, -- 0: любит тесноту, 1: неприхотливое, 2: любит много места\n" +
            "    check (light >= 0 and light <= 4),\n" +
            "    check (temperature_up > temperature_down),\n" +
            "    check (humidity_description >= 0 and humidity_description <= 2),\n" +
            "    check (humidity_up >= 25 and humidity_up <= 100),\n" +
            "    check (humidity_down >= 25 and humidity_down <= 100),\n" +
            "    check (humidity_up > humidity_down),\n" +
            "    check (pot_size >= 0 or pot_size <= 2),\n" +
            "    primary key(plant_id, period_id, season)\n" +
            ");\n" +
            "\n" +
            "-- таблица с информацией о том, какая почва подходит для этого растения\n" +
            "create table soil_for_plant (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    soil_id int not null references soil(_id),\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, из которой можно получить все его растения\n" +
            "create table plants_of_user (\n" +
            "    _id integer primary key,\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    name varchar(255) unique not null,\n" +
            "    room_id int not null references rooms(_id),\n" +
            "    watering_day date not null,\n" +
            "    transplant_day date not null,\n" +
            "    fertilization_day date not null,\n" +
            "    period_id integer references period_of_life(_id),\n" +
            "    coord_x decimal not null,\n" +
            "    coord_y decimal not null,\n" +
            "    primary key(name)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой содержится информация о поливе растений летом\n" +
            "--create type irrigation as enum ('weak', 'middle', 'strong');\n" +
            "\n" +
            "create table irrigation_power_enum (\n" +
            "    _id integer primary key,\n" +
            "    irrigation_power varchar(255),\n" +
            "    check (irrigation_power = 'weak' || irrigation_power = 'middle' || irrigation_power = 'strong')\n" +
            ");\n" +
            "\n" +
            "create table watering_summer (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    every_X_day int not null,\n" +
            "    description text,\n" +
            "    irrigation_power_id integer not null references irrigation_power_enum(_id) default 1,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой содержится информация о поливе растений зимой\n" +
            "create table watering_winter (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    every_X_day int not null,\n" +
            "    irrigation_power_id integer not null references irrigation_power_enum(_id) default 1,\n" +
            "    description text,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой содержится информация о поливе растений осенью\n" +
            "create table watering_fall (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    every_X_day int not null,\n" +
            "    irrigation_power_id integer not null references irrigation_power_enum(_id) default 1,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой содержится информация о поливе растений весной\n" +
            "create table watering_spring (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    every_X_day int not null,\n" +
            "    irrigation_power_id integer not null references irrigation_power_enum(_id) default 1,\n" +
            "    description text,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой содержится информация о пересадке растений и какую почву использовать и как часто пересаживать\n" +
            "create table plant_transplant (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    soil_id int not null references soil(_id),\n" +
            "    every_X_month int not null,\n" +
            "    description text,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой хранится информация об удобрениях и их описание\n" +
            "create table fertilizers (\n" +
            "    _id integer primary key,\n" +
            "    name varchar(255) not null unique,\n" +
            "    description text\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой содержится информация об удобрении растений и какое удобрение использовать и как часто удобрять\n" +
            "create table plant_fertilization (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    fertilizer_id int not null references fertilizers(_id),\n" +
            "    every_X_month int not null,\n" +
            "    description text,\n" +
            "    primary key(plant_id, fertilizer_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой написано как часто нужно рыхлить землю у растения\n" +
            "create table plant_loosening (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    every_X_day int not null,\n" +
            "    description text,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой написано как часто нужно опрыскивать растение\n" +
            "create table plant_spraying (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    every_X_day int not null,\n" +
            "    description text,\n" +
            "    primary key(plant_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица с возможными заболеваниями растений и путями лечения\n" +
            "create table illnesses (\n" +
            "    _id integer primary key,\n" +
            "    name text not null,\n" +
            "    description text not null,\n" +
            "    treatment text not null\n" +
            ");\n" +
            "\n" +
            "-- таблица, в которой по растению можно посмотреть болезни, которые часто у него бывают\n" +
            "create table illnesses_plants (\n" +
            "    plant_id int not null references plants(_id),\n" +
            "    illnesses_id int not null references illnesses(_id),\n" +
            "    primary key(plant_id, illnesses_id)\n" +
            ");\n" +
            "\n" +
            "-- таблица всех комнат пользователя, которые задаются координатами двух точек - левый верхний угол\n" +
            "-- и правый нижний\n" +
            "create table rooms(\n" +
            "    _id integer primary key,\n" +
            "    left_up decimal not null,\n" +
            "    right_down decimal not null\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Для календаря. Я думаю, что изначально у каждого пользователя это будет пустая таблица,\n" +
            "-- а по мере того, как он будет добавлять растения в эту таблицу будет\n" +
            "-- записываться информация по датам о делах всяких\n" +
            "--create type works_with_plant as enum ('watering', 'transplant', 'fertilizers', 'loosening', 'spraying');\n" +
            "\n" +
            "create table work_enum (\n" +
            "    _id integer primary key,\n" +
            "    work varchar(255),\n" +
            "    check (work = 'watering' || work = 'transplant' || work = fertilizers || work = 'loosening' || work = 'spraying')\n" +
            ");\n" +
            "\n" +
            "create table calendar (\n" +
            "    day date not null,\n" +
            "    plant_id integer not null references plants_of_user(_id),\n" +
            "    work_id int not null references work_enum(_id),\n" +
            "    status int not null default 0,\n" +
            "    check (status = 1 or status = 0), -- 1 - дело сделано, -- 0 - дело не сделано\n" +
            "    primary key(date, plant_id, work)\n" +
            ");"
}
