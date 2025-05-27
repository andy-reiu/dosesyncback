-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-19 10:36:42.448

-- tables
-- Table: calculation_profile
CREATE TABLE calculation_profile (
                                     id serial  NOT NULL,
                                     study_id int  NOT NULL,
                                     isotope_id int  NOT NULL,
                                     calibrated_activity decimal(8,2)  NOT NULL,
                                     calibration_time time  NOT NULL,
                                     --administration_time time  NOT NULL, -- kas seda on ikkagi vaja?
                                     --activity_before_first int  NOT NULL, -- kas seda on ikkagi vaja?
                                     fill_volume int  NOT NULL,
                                     CONSTRAINT calculation_profile_pk PRIMARY KEY (id)
);

-- Table: calculation_settings
CREATE TABLE calculation_settings (
                                      id serial  NOT NULL,
                                      min_activity decimal(8,2)  NOT NULL,
                                      max_activity decimal(8,2)  NOT NULL,
                                      min_volume decimal(8,2)  NOT NULL,
                                      machine_volume_max decimal(8,2)  NOT NULL,
                                      machine_volume_min decimal(8,2)  NOT NULL,
                                      injection_interval TIME NOT NULL,
                                      default_patient_weight double precision  NOT NULL,
                                      activity_per_kg decimal(8,2)  NOT NULL,
                                      CONSTRAINT calculation_settings_pk PRIMARY KEY (id)
);

-- Table: daily_study
CREATE TABLE daily_study (
                             id serial  NOT NULL,
                             study_id int  NOT NULL,
                             patient_id int  NOT NULL,
                             injection_id int  NOT NULL,
                             machine_fill_id int  NOT NULL,
                             status char  NOT NULL,
                             created_at timestamp  NOT NULL,
                             updated_at timestamp  NOT NULL,
                             CONSTRAINT daily_study_pk PRIMARY KEY (id)
);

-- Table: hospital
CREATE TABLE hospital (
                          id serial  NOT NULL,
                          name varchar(255)  NOT NULL,
                          address varchar(255)  NOT NULL,
                          CONSTRAINT hospital_pk PRIMARY KEY (id)
);

-- Table: injection
CREATE TABLE injection (
                           id serial  NOT NULL,
                           acc varchar(16) NOT NULL,
                           weight decimal(4,1)  NOT NULL,
                           mbq_kg decimal(7,3)  NOT NULL,
                           injected_time time  NOT NULL,
                           injected_activity decimal(8,2)  NOT NULL,
                           CONSTRAINT injection_pk PRIMARY KEY (id)
);

-- Table: isotope
CREATE TABLE isotope (
                         id serial  NOT NULL,
                         name varchar(64)  NOT NULL,
                         half_life_hr decimal(8,3)  NOT NULL,
                         unit varchar(10)  NOT NULL,
                         status char  NOT NULL,
                         CONSTRAINT name UNIQUE (name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                         CONSTRAINT isotope_pk PRIMARY KEY (id)
);

-- Table: machine
CREATE TABLE machine (
                         id serial  NOT NULL,
                         hospital_id int  NOT NULL,
                         name varchar(255)  NOT NULL,
                         serial_number varchar(255)  NOT NULL,
                         description varchar(500)  NULL,
                         status char  NOT NULL,
                         CONSTRAINT serial_number UNIQUE (serial_number) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                         CONSTRAINT machine_pk PRIMARY KEY (id)
);

-- Table: machine_fill
CREATE TABLE machine_fill (
                              id serial  NOT NULL,
                              injection_id int  NOT NULL,
                              vial_activity_before_injection decimal(8,2)  NULL,
                              vial_activity_after_injection decimal(8,2)  NULL,
                              injected_volume decimal(8,2)  NULL,
                              remaining_volume decimal(8,2)  NULL,
                              CONSTRAINT machine_fill_pk PRIMARY KEY (id)
);

-- Table: machine_fill_caculation_profile
CREATE TABLE machine_fill_calculation_profile (
                                                 id serial  NOT NULL,
                                                 machine_fill_id int  NOT NULL,
                                                 calculation_profile_id int  NOT NULL,
                                                 CONSTRAINT machine_fill_caculation_profile_pk PRIMARY KEY (id)
);

-- Table: patient
CREATE TABLE patient (
                         id serial  NOT NULL,
                         patient_national_id varchar(11)  NULL,
                         CONSTRAINT patient_ak_1 UNIQUE (patient_national_id) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                         CONSTRAINT patient_pk PRIMARY KEY (id)
);

-- Table: profile
CREATE TABLE profile (
                         id serial  NOT NULL,
                         user_id int  NOT NULL,
                         hospital_id int  NOT NULL,
                         occupation varchar(255)  NULL,
                         national_id varchar(11)  NOT NULL,
                         first_name varchar(255)  NOT NULL,
                         last_name varchar(255)  NOT NULL,
                         email varchar(255)  NOT NULL,
                         phone_number varchar(255)  NULL,
                         created_at timestamp  NULL,
                         updated_at timestamp  NULL,
                         CONSTRAINT profile_ak_1 UNIQUE (user_id, phone_number) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                         CONSTRAINT profile_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
                      id serial  NOT NULL,
                      name varchar(10)  NOT NULL,
                      CONSTRAINT role_ak_1 UNIQUE (name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                      CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: study
CREATE TABLE study (
                       id serial  NOT NULL,
                       user_id int  NOT NULL,
                       machine_id int  NOT NULL,
                       isotope_id int  NOT NULL,
                       date date  NULL,
                       nr_patients int  NULL,
                       start_time time  NULL,
                       end_time time  NULL,
                       total_activity decimal(8,2)  NULL,
                       comment varchar(255)  NULL,
                       status char  NOT NULL,
                       calculation_machine_rinse_volume decimal(8,2)  NULL,
                       calculation_machine_rinse_activity decimal(8,2)  NULL,
                       CONSTRAINT study_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
                        id serial  NOT NULL,
                        role_id int  NOT NULL,
                        username varchar(255)  NOT NULL,
                        password varchar(255)  NOT NULL,
                        status char  NOT NULL,
                        CONSTRAINT account_ak_1 UNIQUE (username) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                        CONSTRAINT user_pk PRIMARY KEY (id)
);

-- Table: user_image
CREATE TABLE user_image (
                            id serial  NOT NULL,
                            profile_id int  NOT NULL,
                            data bytea  NOT NULL,
                            CONSTRAINT id PRIMARY KEY (id)
);

-- foreign keys
-- Reference: calculation_profile_study (table: calculation_profile)
ALTER TABLE calculation_profile ADD CONSTRAINT calculation_profile_study
    FOREIGN KEY (study_id)
        REFERENCES study (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: calculation_profiles_isotope (table: calculation_profile)
ALTER TABLE calculation_profile ADD CONSTRAINT calculation_profiles_isotope
    FOREIGN KEY (isotope_id)
        REFERENCES isotope (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: daily_studies_injection (table: daily_study)
ALTER TABLE daily_study ADD CONSTRAINT daily_studies_injection
    FOREIGN KEY (injection_id)
        REFERENCES injection (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: daily_study_machine_fill (table: daily_study)
ALTER TABLE daily_study ADD CONSTRAINT daily_study_machine_fill
    FOREIGN KEY (machine_fill_id)
        REFERENCES machine_fill (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: daily_study_study (table: daily_study)
ALTER TABLE daily_study ADD CONSTRAINT daily_study_study
    FOREIGN KEY (study_id)
        REFERENCES study (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: daily_studyies_patient (table: daily_study)
ALTER TABLE daily_study ADD CONSTRAINT daily_studyies_patient
    FOREIGN KEY (patient_id)
        REFERENCES patient (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: machine_fill_calculation_profile_calculation_profile (table: machine_fill_calculation_profile)
ALTER TABLE machine_fill_calculation_profile ADD CONSTRAINT machine_fill_calculation_profile_calculation_profile
    FOREIGN KEY (calculation_profile_id)
        REFERENCES calculation_profile (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: machine_fill_calculation_profile_machine_fill (table: machine_fill_calculation_profile)
ALTER TABLE machine_fill_calculation_profile ADD CONSTRAINT machine_fill_calculation_profile_machine_fill
    FOREIGN KEY (machine_fill_id)
        REFERENCES machine_fill (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: machine_fill_injection (table: machine_fill)
ALTER TABLE machine_fill ADD CONSTRAINT machine_fill_injection
    FOREIGN KEY (injection_id)
        REFERENCES injection (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: machine_hospital (table: machine)
ALTER TABLE machine ADD CONSTRAINT machine_hospital
    FOREIGN KEY (hospital_id)
        REFERENCES hospital (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: profile_hospital (table: profile)
ALTER TABLE profile ADD CONSTRAINT profile_hospital
    FOREIGN KEY (hospital_id)
        REFERENCES hospital (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: profile_user (table: profile)
ALTER TABLE profile ADD CONSTRAINT profile_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: study_machine (table: study)
ALTER TABLE study ADD CONSTRAINT study_machine
    FOREIGN KEY (machine_id)
        REFERENCES machine (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: study_isotope (table: study)
ALTER TABLE study ADD CONSTRAINT study_isotope
    FOREIGN KEY (isotope_id)
        REFERENCES isotope (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: study_user (table: study)
ALTER TABLE study ADD CONSTRAINT study_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_image_profile (table: user_image)
ALTER TABLE user_image ADD CONSTRAINT user_image_profile
    FOREIGN KEY (profile_id)
        REFERENCES profile (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_role (table: user)
ALTER TABLE "user" ADD CONSTRAINT user_role
    FOREIGN KEY (role_id)
        REFERENCES role (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

