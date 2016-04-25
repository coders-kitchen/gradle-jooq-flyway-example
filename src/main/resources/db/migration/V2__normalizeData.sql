CREATE TABLE race_place(
  race_place_id INT AUTO_INCREMENT NOT NULL,
  race_place VARCHAR NOT NULL
);

ALTER TABLE race_date ADD COLUMN race_place_id INT;
ALTER TABLE race_date ADD FOREIGN KEY (race_place_id) REFERENCES public.race_place(race_place_id);

INSERT INTO race_place (race_place) SELECT race_place FROM race_date;

UPDATE race_date d SET race_place_id = (SELECT race_place_id FROM race_place p WHERE p.race_place = d.race_place);

ALTER TABLE race_date DROP COLUMN race_place;