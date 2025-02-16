-- Enable partitioning on the parent table
SELECT partman.create_parent(
    p_parent_table := 'public.availability'
    , p_control := 'minute'
    , p_type    := 'range'
    , p_interval := 'day'
    -- , p_premake := 5
--	, p_start_partition := (NOW() AT TIME ZONE 'UTC' - interval '1 minute')::timestamp::text
    ,p_date_trunc_interval  := 'day'

);


-- Create an index on the timestamp column
CREATE INDEX ON "availability" ("minute");

-- Configure database archive
CREATE SCHEMA backup;

SET search_path TO backup;

-- Set the retention policy (optional)
UPDATE partman.part_config
SET infinite_time_partitions=true,
retention='7 days',
retention_schema='backup',
retention_keep_table=true,
retention_keep_index=true
WHERE parent_table='public.availability';

CALL partman.run_maintenance_proc();
-- SELECT cron.schedule('@hourly', $$CALL partman.run_maintenance_proc()$$);


