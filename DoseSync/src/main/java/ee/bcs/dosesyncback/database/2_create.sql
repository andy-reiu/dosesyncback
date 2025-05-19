-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-05-16 10:30:42.998

-- tables
-- Table: calculation_profile
CREATE TABLE calculation_profile (
                                     id serial  NOT NULL,
                                     machine_id int  NOT NULL,
                                     isotope_id int  NOT NULL,
                                     activity decimal(8,2)  NOT NULL,
                                     calibration_time time  NOT NULL,
                                     administration_time time  NOT NULL,
                                     activity_before_first int  NOT NULL,
                                     vial_volume int  NOT NULL,
                                     CONSTRAINT calculation_profile_pk PRIMARY KEY (id)
);

-- Table: daily_study
CREATE TABLE daily_study (
                             id serial  NOT NULL,
                             study_id int  NOT NULL,
                             patient_id int  NOT NULL,
                             vial_id int  NOT NULL,
                             injection_id int  NOT NULL,
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
                           vial_id int  NOT NULL,
                           injected_timestamp time  NOT NULL,
                           injected_activity decimal(8,2)  NOT NULL,
                           injection_volume decimal(8,2)  NOT NULL,
                           CONSTRAINT injection_pk PRIMARY KEY (id)
);

-- Table: isotope
CREATE TABLE isotope (
                         id serial  NOT NULL,
                         name varchar(64)  NOT NULL,
                         half_life_hr decimal(8,3)  NOT NULL,
                         unit varchar(10)  NOT NULL,
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

-- Table: patient
CREATE TABLE patient (
                         id serial  NOT NULL,
                         acc varchar(255)  NOT NULL,
                         patient_national_id varchar(11)  NULL,
                         weight decimal(4,1)  NOT NULL,
                         mbq_kg decimal(4,1)  NOT NULL,
                         CONSTRAINT patient_ak_1 UNIQUE (acc, patient_national_id) NOT DEFERRABLE  INITIALLY IMMEDIATE,
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
                       date date  NULL,
                       nr_patients int  NULL,
                       start_time time  NULL,
                       end_time time  NULL,
                       total_activity decimal(8,2)  NULL,
                       comment varchar(255)  NULL,
                       status char  NOT NULL,
                       CONSTRAINT study_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
                        id serial  NOT NULL,
                        role_id int  NOT NULL,
                        username varchar(255)  NOT NULL,
                        password varchar(255)  NOT NULL,
                        status char  NOT NULL,
                        CONSTRAINT account_ak_2 UNIQUE (username) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                        CONSTRAINT user_pk PRIMARY KEY (id)
);

-- Table: user_image
CREATE TABLE user_image (
                            id serial  NOT NULL,
                            profile_id int  NOT NULL,
                            data bytea  NOT NULL,
                            CONSTRAINT id PRIMARY KEY (id)
);

-- Table: vial
CREATE TABLE vial (
                      id serial  NOT NULL,
                      calculation_profiles_id int  NOT NULL,
                      vial_activity_before decimal(8,2)  NOT NULL,
                      vial_activity_after decimal(8,2)  NOT NULL,
                      remaining_volume decimal(8,2)  NOT NULL,
                      CONSTRAINT vial_pk PRIMARY KEY (id)
);

-- foreign keys
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

-- Reference: daily_studies_vial (table: daily_study)
ALTER TABLE daily_study ADD CONSTRAINT daily_studies_vial
    FOREIGN KEY (vial_id)
        REFERENCES vial (id)
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

-- Reference: injection_vial (table: injection)
ALTER TABLE injection ADD CONSTRAINT injection_vial
    FOREIGN KEY (vial_id)
        REFERENCES vial (id)
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

-- Reference: vial_calculation_profiles (table: vial)
ALTER TABLE vial ADD CONSTRAINT vial_calculation_profiles
    FOREIGN KEY (calculation_profiles_id)
        REFERENCES calculation_profile (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: vial_machine (table: calculation_profile)
ALTER TABLE calculation_profile ADD CONSTRAINT vial_machine
    FOREIGN KEY (machine_id)
        REFERENCES machine (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

