-- Roles
INSERT INTO dosesync.role (id, name) VALUES (default, 'user');
INSERT INTO dosesync.role (id, name) VALUES (default, 'admin');
INSERT INTO dosesync.role (id, name) VALUES (default, 'observer');

-- Hospital
INSERT INTO dosesync.hospital (id, name, address) VALUES (default, 'Ida-tallinna keskhaigla', 'Ravi 18');

-- User
INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'user', '123', 'A');
INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 2, 'admin', '123', 'A');
INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 3, 'observer', '123', 'A');
INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'olga', '123', 'A');
INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'andy', '123', 'A');
INSERT INTO dosesync."user" (id, role_id, username, password, status) VALUES (default, 1, 'kevin', '123', 'A');

-- Profile
INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 1, 1, 'Technician', '12345678901', 'John', 'Doe', 'john.doe@example.com', '555-1000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 2, 1, 'Administrator', '12345678902', 'Jane', 'Smith', 'jane.smith@example.com', '555-2000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 3, 1, 'Observer', '12345678903', 'Bob', 'Brown', 'bob.brown@example.com', '555-3000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 4, 1, 'Technician', '12345678904', 'Olga', 'Ivanova', 'olga@example.com', '555-4000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 5, 1, 'Technician', '12345678905', 'Andy', 'Miller', 'andy@example.com', '555-5000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');
INSERT INTO dosesync.profile (id, user_id, hospital_id, occupation, national_id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES (default, 6, 1, 'Technician', '12345678906', 'Kevin', 'Wang', 'kevin@example.com', '555-6000', '2025-05-16 13:24:38.029110', '2025-05-16 13:24:38.029110');

-- user-1-profile-image
INSERT INTO dosesync.user_image (id, profile_id, data) VALUES (default, 1, E'\\x89504E470D0A1A0A');

-- Isotope
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit) VALUES (default, 'Tc-99m', 6.006, 'MBq');
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit) VALUES (default, 'F18', 1.830, 'MBq');
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit) VALUES (default, 'I131', 192.500, 'MBq');
INSERT INTO dosesync.isotope (id, name, half_life_hr, unit) VALUES (default, 'FPyl', 0.500, 'MBq');

-- Machine
INSERT INTO dosesync.machine (id, hospital_id, name, serial_number, description, status) VALUES (default, 1,'Karl100', 'SN-DC-3000', 'High-precision dose injector', 'A');



-- Calculation Profile
INSERT INTO dosesync.calculation_profile (id, machine_id, isotope_id, activity, calibration_time, administration_time, activity_before_first, vial_volume) VALUES (default, 1, 1, 4500.00, '09:30:00', '11:45:00', 1919, 6);


-- Vials
INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 1919.00, 1744.00, 1.82);
INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 1443.00, 1443.00, 1.60);
INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 1049.00, 1049.00, 1.33);
INSERT INTO dosesync.vial (id, calculation_profiles_id, vial_activity_before, vial_activity_after, remaining_volume) VALUES (default, 1, 723.00, 723.00, 1.01);

-- Injections
INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 3, '09:45:00', 175.00, 0.27);
INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 4, '10:15:00', 175.00, 0.32);
INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 2, '09:15:00', 175.00, 0.22);
INSERT INTO dosesync.injection (id, vial_id, injected_timestamp, injected_activity, injection_volume) VALUES (default, 1, '08:45:00', 175.00, 0.18);

-- Study
INSERT INTO dosesync.study (id, user_id, date, nr_patients, start_time, end_time, total_activity, comment, status) VALUES (default, 2, '2025-05-16', 4, '08:30:00', '11:00:00', 480.00, 'Routine diagnostic', 'A');

-- Patients
INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC001', '12345678901', 70.0, 2.5);
INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC002', '98765432101', 70.0, 2.5);
INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC004', '98765432103', 70.0, 2.5);
INSERT INTO dosesync.patient (id, acc, patient_national_id, weight, mbq_kg) VALUES (default, 'ACC003', '98765432102', 70.0, 2.5);

-- Daily Study
INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 1, 1, 1, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 2, 2, 2, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 3, 3, 3, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
INSERT INTO dosesync.daily_study (id, study_id, patient_id, vial_id, injection_id, status, created_at, updated_at) VALUES (default, 1, 4, 4, 4, 'A', '2025-05-16 13:17:49.227960', '2025-05-16 13:17:49.227960');
