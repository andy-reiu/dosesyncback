-- Role
INSERT INTO role (id, name) VALUES (default, 'user');
INSERT INTO role (id, name) VALUES (default, 'admin');
INSERT INTO role (id, name) VALUES (default, 'observer');

-- Hospital
INSERT INTO hospital (id, name, address) VALUES (default, 'Ida-tallinna keskhaigla', 'Ravi 18');

INSERT INTO dosesync.calculation_settings (id, min_activity, max_activity, min_volume, machine_volume_max, machine_volume_min) VALUES (1, 100.00, 50000.00, 0.20, 4.00, 0.20);

-- User
INSERT INTO "user" (id, role_id, username, password, status) VALUES (default, 1, 'user', '123', 'A');
INSERT INTO "user" (id, role_id, username, password, status) VALUES (default, 2, 'admin', '123', 'A');
INSERT INTO "user" (id, role_id, username, password, status) VALUES (default, 3, 'observer', '123', 'A');
INSERT INTO "user" (id, role_id, username, password, status) VALUES (default, 1, 'olga', '123', 'A');
INSERT INTO "user" (id, role_id, username, password, status) VALUES (default, 1, 'andy', '123', 'A');
INSERT INTO "user" (id, role_id, username, password, status) VALUES (default, 1, 'kevin', '123', 'A');

-- Profile
INSERT INTO profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at)
VALUES (default, 1, 1, 'Technician', '12345678901', 'John', 'Doe', 'john.doe@example.com', '555-1000', '2025-05-16 13:24:38', '2025-05-16 13:24:38');
INSERT INTO profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at)
VALUES (default, 2, 1, 'Administrator', '12345678902', 'Jane', 'Smith', 'jane.smith@example.com', '555-2000', '2025-05-16 13:24:38', '2025-05-16 13:24:38');
INSERT INTO profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at)
VALUES (default, 3, 1, 'Observer', '12345678903', 'Bob', 'Brown', 'bob.brown@example.com', '555-3000', '2025-05-16 13:24:38', '2025-05-16 13:24:38');
INSERT INTO profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at)
VALUES (default, 4, 1, 'Technician', '12345678904', 'Olga', 'Ivanova', 'olga@example.com', '555-4000', '2025-05-16 13:24:38', '2025-05-16 13:24:38');
INSERT INTO profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at)
VALUES (default, 5, 1, 'Technician', '12345678905', 'Andy', 'Miller', 'andy@example.com', '555-5000', '2025-05-16 13:24:38', '2025-05-16 13:24:38');
INSERT INTO profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at)
VALUES (default, 6, 1, 'Technician', '12345678906', 'Kevin', 'Wang', 'kevin@example.com', '555-6000', '2025-05-16 13:24:38', '2025-05-16 13:24:38');

-- User Image
INSERT INTO user_image (id, profile_id, data) VALUES (default, 1, E'\\x89504E470D0A1A0A');

-- Isotope
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'Tc-99m', 6.006, 'MBq', 'A');
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'F18', 1.830, 'MBq', 'A');
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'I131', 192.500, 'MBq', 'A');
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'FPyl', 0.500, 'MBq', 'A');

-- Machine
INSERT INTO dosesync.machine (id, hospital_id, name, serial_number, description, status)
VALUES (default, 1, 'Karl100', 'SN-DC-3000', 'High-precision dose injector', 'A');


-- Injection
INSERT INTO dosesync.injection (id, weight, mbq_kg, injected_time, injected_activity) VALUES (1, 70.0, 2.500, '09:45:00', 175.00);
INSERT INTO dosesync.injection (id, weight, mbq_kg, injected_time, injected_activity) VALUES (2, 70.0, 2.500, '10:15:00', 175.00);
INSERT INTO dosesync.injection (id, weight, mbq_kg, injected_time, injected_activity) VALUES (3, 70.0, 2.500, '10:45:00', 175.00);
INSERT INTO dosesync.injection (id, weight, mbq_kg, injected_time, injected_activity) VALUES (4, 70.0, 2.500, '11:15:00', 175.00);

-- Machine Fill (formerly vial)
INSERT INTO dosesync.machine_fill (id, injection_id, vial_activity_before_injection, vial_activity_after_injection, injected_volume, remaining_volume) VALUES (1, 1, 1919.00, 1744.00, 1.00, 10.00);
INSERT INTO dosesync.machine_fill (id, injection_id, vial_activity_before_injection, vial_activity_after_injection, injected_volume, remaining_volume) VALUES (2, 2, 1443.00, 1268.00, 1.21, 8.78);
INSERT INTO dosesync.machine_fill (id, injection_id, vial_activity_before_injection, vial_activity_after_injection, injected_volume, remaining_volume) VALUES (3, 3, 1049.00, 874.00, 1.47, 7.32);
INSERT INTO dosesync.machine_fill (id, injection_id, vial_activity_before_injection, vial_activity_after_injection, injected_volume, remaining_volume) VALUES (4, 4, 723.00, 723.00, 1.77, 5.55);


-- Study
INSERT INTO dosesync.study (id, user_id, machine_id, date, nr_patients, start_time, end_time, total_activity, comment, status, calculation_machine_rinse_volume, calculation_machine_rinse_activity)
VALUES (default, 2, 1, '2025-05-16', 4, '08:30:00', '11:00:00', 4800.00, 'Routine diagnostic', 'D', NULL, NULL);
INSERT INTO dosesync.study (id, user_id, machine_id, date, nr_patients, start_time, end_time, total_activity, comment, status, calculation_machine_rinse_volume, calculation_machine_rinse_activity)
VALUES (default, 3, 1, '2025-04-16', 2, '08:30:00', '21:00:00', 6200.00, 'Andys Study', 'A', 2, 160);
INSERT INTO dosesync.study (id, user_id, machine_id, date, nr_patients, start_time, end_time, total_activity, comment, status, calculation_machine_rinse_volume, calculation_machine_rinse_activity)
VALUES (default, 3, 1, '2025-06-16', 5, '08:30:00', '20:00:00', 2220.00, 'THIS IS NOT THE DROIDS', 'B', 4, 250);

-- Calculation Profile
INSERT INTO dosesync.calculation_profile (id, study_id, isotope_id, calibrated_actity, calibration_time, administration_time, activity_before_first, fill_volume)
VALUES (default, 1, 1, 4500.00, '09:30:00', '11:45:00', 1919, 6);
INSERT INTO dosesync.calculation_profile (id, study_id, isotope_id, calibrated_actity, calibration_time, administration_time, activity_before_first, fill_volume)
VALUES (default, 2, 2, 6600.00, '09:30:00', '17:45:00', 5000, 23);
INSERT INTO dosesync.calculation_profile (id, study_id, isotope_id, calibrated_actity, calibration_time, administration_time, activity_before_first, fill_volume)
VALUES (default, 3, 3, 6600.00, '09:30:00', '13:45:00', 1000, 21);


-- Patient
INSERT INTO dosesync.patient (id, patient_national_id) VALUES (default, '12345678901');
INSERT INTO dosesync.patient (id, patient_national_id) VALUES (default, '98765432101');
INSERT INTO dosesync.patient (id, patient_national_id) VALUES (default, '98765432103');
INSERT INTO dosesync.patient (id, patient_national_id) VALUES (default, '98765432102');

-- Daily Study
INSERT INTO dosesync.daily_study (id, study_id, patient_id, injection_id, machine_fill_id, status, created_at, updated_at)
VALUES (default, 1, 1, 1, 1, 'A', '2025-05-16 13:17:49', '2025-05-16 13:17:49');
INSERT INTO dosesync.daily_study (id, study_id, patient_id, injection_id, machine_fill_id, status, created_at, updated_at)
VALUES (default, 1, 2, 2, 2, 'A', '2025-05-16 13:17:49', '2025-05-16 13:17:49');
INSERT INTO dosesync.daily_study (id, study_id, patient_id, injection_id, machine_fill_id, status, created_at, updated_at)
VALUES (default, 1, 3, 3, 3, 'A', '2025-05-16 13:17:49', '2025-05-16 13:17:49');
INSERT INTO dosesync.daily_study (id, study_id, patient_id, injection_id, machine_fill_id, status, created_at, updated_at)
VALUES (default, 1, 4, 4, 4, 'A', '2025-05-16 13:17:49', '2025-05-16 13:17:49');

-- Machine Fill depending on calculation profile
INSERT INTO dosesync.machine_fill_calculation_profile (id, machine_fill_id, calculation_profile_id) VALUES (default, 1, 1);
INSERT INTO dosesync.machine_fill_calculation_profile (id, machine_fill_id, calculation_profile_id) VALUES (default, 2, 1);
INSERT INTO dosesync.machine_fill_calculation_profile (id, machine_fill_id, calculation_profile_id) VALUES (default, 3, 1);
INSERT INTO dosesync.machine_fill_calculation_profile (id, machine_fill_id, calculation_profile_id) VALUES (default, 4, 1);









































-- -- Roles
-- INSERT INTO dosesync.role (id, name) VALUES (default, 'user');
-- INSERT INTO dosesync.role (id, name) VALUES (default, 'admin');
-- INSERT INTO dosesync.role (id, name) VALUES (default, 'observer');
--
-- -- Hospital
-- INSERT INTO dosesync.hospital (id, name, address) VALUES (default, 'Ida-tallinna keskhaigla', 'Ravi 18');
--
-- -- User
-- INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'user', '123', 'A');
-- INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 2, 'admin', '123', 'A');
-- INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 3, 'observer', '123', 'A');
-- INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'olga', '123', 'A');
-- INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'andy', '123', 'A');
-- INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'kevin', '123', 'A');
--
-- -- Profile
-- INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 1, 1, 'Technician', '12345678901', 'John', 'Doe', 'john.doe@example.com', '555-1000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
-- INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 2, 1, 'Administrator', '12345678902', 'Jane', 'Smith', 'jane.smith@example.com', '555-2000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
-- INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 3, 1, 'Observer', '12345678903', 'Bob', 'Brown', 'bob.brown@example.com', '555-3000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
-- INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 4, 1, 'Technician', '12345678904', 'Olga', 'Ivanova', 'olga@example.com', '555-4000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
-- INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 5, 1, 'Technician', '12345678905', 'Andy', 'Miller', 'andy@example.com', '555-5000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
-- INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 6, 1, 'Technician', '12345678906', 'Kevin', 'Wang', 'kevin@example.com', '555-6000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
--
-- -- user-1-profile-image
-- INSERT INTO dosesync.user_image (id, profile_id, data) VALUES (default, 1, E'\\x89504E470D0A1A0A');
--
-- -- Isotope
-- INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'Tc-99m', 6.006, 'MBq', 'A');
-- INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'F18', 1.830, 'MBq', 'A');
-- INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'I131', 192.500, 'MBq', 'A');
-- INSERT INTO dosesync.isotope (id, name, half_life_hr, unit, status) VALUES (default, 'FPyl', 0.500, 'MBq', 'A');
--
-- -- Machine
-- INSERT INTO dosesync.machine (id, hospital_id, name, serial_number, description, status) VALUES (default, 1,'Karl100', 'SN-DC-3000', 'High-precision dose injector', 'A');
--
-- -- Calculation Profile
-- INSERT INTO dosesync.calculation_profile (id, machine_id, isotope_id, activity, calibration_time, administration_time, activity_before_first, vial_volume) VALUES (default, 1, 1, 4500.00, '09:30:00', '11:45:00', 1919, 6);
--
--
-- -- Vials
-- INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 1919.00, 1744.00, 1.82);
-- INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 1443.00, 1443.00, 1.60);
-- INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 1049.00, 1049.00, 1.33);
-- INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 723.00, 723.00, 1.01);
--
-- -- Injections
-- INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 3, '09:45:00', 175.00, 0.27);
-- INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 4, '10:15:00', 175.00, 0.32);
-- INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 2, '09:15:00', 175.00, 0.22);
-- INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 1, '08:45:00', 175.00, 0.18);
--
-- -- Study
-- INSERT INTO dosesync.study (id, user_id, date, nr_patients, start_time, end_time, total_activity, comment, status) VALUES (default, 2, '2025-05-16', 4, '08:30:00', '11:00:00', 480.00, 'Routine diagnostic', 'A');
--
-- -- Patients
-- INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC001', '12345678901', 70.0, 2.5);
-- INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC002', '98765432101', 70.0, 2.5);
-- INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC004', '98765432103', 70.0, 2.5);
-- INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC003', '98765432102', 70.0, 2.5);
--
-- -- Daily Study
-- INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 1, 1, 1, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
-- INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 2, 2, 2, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
-- INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 3, 3, 3, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
-- INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 4, 4, 4, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
