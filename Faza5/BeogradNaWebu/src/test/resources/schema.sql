CREATE SCHEMA IF NOT EXISTS testdb;
create table korisnik
(
    idkorisnik    int auto_increment
        primary key,
    korisnickoime varchar(30)          not null,
    email         varchar(100)         not null,
    sifra         varchar(30)          not null,
    uloga         varchar(10)          not null,
    epredlog      tinyint(1) default 0 not null,
    constraint email_UNIQUE
        unique (email),
    constraint korisnickoime_UNIQUE
        unique (korisnickoime)
);

create table recalgdata
(
    idkorisnik           int              not null
        primary key,
    range_min_broj_soba  double default 0 null,
    range_max_broj_soba  double default 0 null,
    range_min_spratnost  int    default 0 null,
    range_max_spratnost  int    default 0 null,
    range_min_kvadratura int    default 0 null,
    range_max_kvadratura int    default 0 null,
    range_min_cena       double default 0 null,
    range_max_cena       double default 0 null,
    weight_broj_soba     double default 0 null,
    weight_spratnost     double default 0 null,
    weight_kvadratura    double default 0 null,
    weight_cena          double default 0 null,
    constraint recalgdata_korisnik_idkorisnik_fk
        foreign key (idkorisnik) references korisnik (idkorisnik)
            on update cascade
);

create table tip_smestaja
(
    idtip_smestaja int auto_increment
        primary key,
    ime_tipa       varchar(50) not null
);

create table smestaj
(
    idsmestaj      int auto_increment
        primary key,
    org_putanja    varchar(300)         not null,
    broj_lajkova   int        default 0 not null,
    lokacija       varchar(100)         null,
    broj_soba      double               null,
    spratonost     int                  null,
    ima_lift       tinyint(1)           null,
    idtip_smestaja int                  not null,
    kvadratura     int                  null,
    cena           double               null,
    postoji        tinyint(1) default 1 not null,
    broj_sajta     tinyint(1)           not null,
    slika          varchar(300)         null,
    constraint idtip_smestaja_fk
        foreign key (idtip_smestaja) references tip_smestaja (idtip_smestaja)
            on update cascade
);

create table komentar
(
    idkomentar      int auto_increment
        primary key,
    tekst_komentara varchar(281)  not null,
    idkorisnik      int           null,
    idsmestaj       int           not null,
    broj_lajkova    int default 0 not null,
    constraint idkorisnik_komentar_fk
        foreign key (idkorisnik) references korisnik (idkorisnik)
            on update cascade,
    constraint idsmestaj_komentar_fk
        foreign key (idsmestaj) references smestaj (idsmestaj)
            on update cascade on delete cascade
);

create index idkorisnik_komentar_fk_idx
    on komentar (idkorisnik);

create index idsmestaj_komentar_fk_idx
    on komentar (idsmestaj);

create table lajk_komentara
(
    idkomentar int not null,
    idkorisnik int not null,
    primary key (idkomentar, idkorisnik),
    constraint idkomentar_lajk2_fk
        foreign key (idkomentar) references komentar (idkomentar)
            on update cascade on delete cascade,
    constraint idkorisnikj_lajk2_fk
        foreign key (idkorisnik) references korisnik (idkorisnik)
            on update cascade
);

create index idkorisnikj_lajk2_fk_idx
    on lajk_komentara (idkorisnik);

create table lajk_smestaja
(
    idkorisnik int not null,
    idsmestaj  int not null,
    primary key (idkorisnik, idsmestaj),
    constraint idkorisnik_lajk_fk
        foreign key (idkorisnik) references korisnik (idkorisnik)
            on update cascade,
    constraint idsmestaj_lajk_fk
        foreign key (idsmestaj) references smestaj (idsmestaj)
            on update cascade on delete cascade
);

create index idkorisnik_lajk_fk_idx
    on lajk_smestaja (idkorisnik);

create index idsmestaj_lajk_fk_idx
    on lajk_smestaja (idsmestaj);

create index idtip_smestaja_fk_idx
    on smestaj (idtip_smestaja);

