BEGIN

  DECLARE @CurrentTime datetime
  DECLARE @BackTime datetime
  DECLARE @Backuped tinyint
  DECLARE @FileName nvarchar(max)
  DECLARE @PathName nvarchar(max)

  SET @CurrentTime = GETDATE()
  SELECT @BackTime = backuptime, @Backuped = backuped FROM dbbackup_info

  IF REPLACE(CONVERT(varchar, @CurrentTime,108),':','') > REPLACE(CONVERT(varchar, @BackTime,108),':','')  AND @Backuped = 0
    BEGIN
      SET @FileName = CONVERT(varchar, @CurrentTime,112) + REPLACE(CONVERT(varchar, @CurrentTime,108),':','')  + '.bak'
      SET @PathName = 'D:\BingChong\Database\Backup\' + @FileName
      BACKUP DATABASE BingChong TO  DISK = @PathName WITH NOFORMAT, NOINIT,  NAME = N'BingChongBackup', SKIP, NOREWIND, NOUNLOAD,  STATS = 10
      
      UPDATE dbbackup_info SET backuped = 1

      INSERT INTO dbbackup (filename, filepath, filesize, createtime, deleted) SELECT @FileName, @PathName, 1, @CurrentTime, 0
    END
  ELSE IF REPLACE(CONVERT(varchar, @CurrentTime,108),':','') < REPLACE(CONVERT(varchar, @BackTime,108),':','')  AND @Backuped = 1
    BEGIN
      UPDATE dbbackup_info SET backuped = 0
    END

END