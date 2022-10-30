ALTER TABLE IF EXISTS grid
ADD CONSTRAINT name_uniq
UNIQUE (name);

ALTER TABLE grid_column
    DROP CONSTRAINT grid_column_grid_id_fkey,
    ADD CONSTRAINT grid_column_grid_id_fkey
        FOREIGN KEY (grid_id)
            REFERENCES grid(id)
            ON DELETE CASCADE;


ALTER TABLE grid_row
    DROP CONSTRAINT grid_row_grid_id_fkey,
    ADD CONSTRAINT grid_row_grid_id_fkey
        FOREIGN KEY (grid_id)
            REFERENCES grid(id)
            ON DELETE CASCADE;

ALTER TABLE grid_cell
    DROP CONSTRAINT grid_cell_grid_column_id_fkey,
    ADD CONSTRAINT grid_cell_grid_column_id_fkey
        FOREIGN KEY (grid_column_id)
            REFERENCES grid_column(id)
            ON DELETE CASCADE;

ALTER TABLE grid_cell
    DROP CONSTRAINT grid_cell_grid_row_id_fkey,
    ADD CONSTRAINT grid_cell_grid_row_id_fkey
        FOREIGN KEY (grid_row_id)
            REFERENCES grid_row(id)
            ON DELETE CASCADE;