USE [master]
GO
/****** Object:  StoredProcedure [dbo].[BathServeRestore]    Script Date: 09/28/2013 10:35:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[BingChongRestore]
	-- Add the parameters for the stored procedure here
	@PathName NVARCHAR(300)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	ALTER DATABASE BingChong SET OFFLINE WITH ROLLBACK IMMEDIATE

	RESTORE DATABASE BingChong FROM DISK = @PathName WITH REPLACE, STATS = 10
	
	ALTER DATABASE BingChong SET ONLINE
END