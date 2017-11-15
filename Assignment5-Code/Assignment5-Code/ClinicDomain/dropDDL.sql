ALTER TABLE TREATMENT DROP CONSTRAINT FK_TREATMENT_provider_fk
ALTER TABLE TREATMENT DROP CONSTRAINT FK_TREATMENT_patient_fk
ALTER TABLE DRUGTREATMENT DROP CONSTRAINT FK_DRUGTREATMENT_ID
ALTER TABLE RADIOLOGYTREATMENT DROP CONSTRAINT FK_RADIOLOGYTREATMENT_ID
ALTER TABLE SURGERYTREATMENT DROP CONSTRAINT FK_SURGERYTREATMENT_ID
ALTER TABLE DATES DROP CONSTRAINT FK_DATES_RadiologyTreatment_ID
DROP TABLE PATIENT CASCADE
DROP TABLE TREATMENT CASCADE
DROP TABLE DRUGTREATMENT CASCADE
DROP TABLE RADIOLOGYTREATMENT CASCADE
DROP TABLE SURGERYTREATMENT CASCADE
DROP TABLE PROVIDER CASCADE
DROP TABLE DATES CASCADE
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN'