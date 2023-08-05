USE [master]
GO
/****** Object:  StoredProcedure [dbo].[BingChongBackup]    Script Date: 10/03/2013 23:47:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[BingChongBackup]
	-- Add the parameters for the stored procedure here
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	DECLARE @CurrentTime datetime
	DECLARE @FileName nvarchar(max)
	DECLARE @PathName nvarchar(max)
	
	SET @CurrentTime = GETDATE()
    SET @FileName = CONVERT(varchar, @CurrentTime,112) + REPLACE(CONVERT(varchar, @CurrentTime,108),':','')  + N'.bak'
    SET @PathName = N'D:\BingChong\Database\Backup\' + @FileName
    
	BACKUP DATABASE BingChong TO DISK = @PathName WITH NOFORMAT, NOINIT, NAME = N'BingChongBackup', SKIP, NOREWIND, NOUNLOAD,  STATS = 10
	
	INSERT INTO BingChong.dbo.dbbackup (filename, filepath, filesize, createtime, deleted) SELECT @FileName, @PathName, 1, @CurrentTime, 0
END
