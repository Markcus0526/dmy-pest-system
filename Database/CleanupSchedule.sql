BEGIN

  DECLARE @Period int
  DECLARE @StartTime datetime
  DECLARE @PathName nvarchar(max)

  SELECT @Period = cleanperiod FROM dbbackup_info
  SET @StartTime = DATEADD(dd, -(@Period), GETDATE())

  WHILE EXISTS (SELECT * FROM dbbackup WHERE createtime < @StartTime AND deleted = 0)
    BEGIN
      SELECT TOP 1 @PathName = filepath FROM dbbackup WHERE createtime < @StartTime AND deleted = 0 ORDER BY createtime DESC

      EXECUTE master.dbo.xp_delete_file 0,@PathName
      UPDATE dbbackup SET deleted = 1

    END

END