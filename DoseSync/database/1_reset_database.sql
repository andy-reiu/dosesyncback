-- Kustutab public schema (mis põhimõtteliselt kustutab kõik tabelid)
DROP SCHEMA IF EXISTS dosesync CASCADE;
-- Loob uue public schema vajalikud õigused
CREATE SCHEMA dosesync
-- taastab vajalikud andmebaasi õigused
    GRANT ALL ON SCHEMA dosesync TO postgres;
GRANT ALL ON SCHEMA dosesync TO PUBLIC;