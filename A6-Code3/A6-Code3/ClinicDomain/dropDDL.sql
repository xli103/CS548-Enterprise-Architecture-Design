ALTER TABLE TREATMENT DROP CONSTRAINT FK_TREATMENT_provider_fk
ALTER TABLE TREATMENT DROP CONSTRAINT FK_TREATMENT_patient_fk
ALTER TABLE DRUGTREATMENT DROP CONSTRAINT FK_DRUGTREATMENT_ID
ALTER TABLE RADIOLOGY DROP CONSTRAINT FK_RADIOLOGY_ID
ALTER TABLE SURGERY DROP CONSTRAINT FK_SURGERY_ID
DROP TABLE PATIENT CASCADE
DROP TABLE TREATMENT CASCADE
DROP TABLE DRUGTREATMENT CASCADE
DROP TABLE PROVIDER CASCADE
DROP TABLE RADIOLOGY CASCADE
DROP TABLE SURGERY CASCADE
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN'
