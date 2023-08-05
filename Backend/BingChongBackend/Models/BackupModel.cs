using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;

namespace BingChongBackend.Models
{
    public class SYSTEM_SUBMITSTATUS
    {
        public const string SUCCESS_SUBMIT = "";
        public const string ERROR_SUBMIT = "操作失败";
        public const string DUPLICATE_CONTENTS = "内容重复";
    }

    public class Backup
    {
        public long uid { set; get; }

        public string filename { set; get; }
        public string filepath { set; get; }
        public int filesize { set; get; }
        public string createtime { set; get; }
    
    }
    public class BackupModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();

        public dbbackup_info GetBackupInfo()
        {
            return db.dbbackup_infos.Where(m => m.deleted == 0).OrderByDescending(m => m.uid).FirstOrDefault();
        }

        public string UpdateBackupinfo(int peroid, string time, int cleanypperiod)
        {
            var rst = "";
            try
            {
                dbbackup_info newitem = new dbbackup_info();
                newitem.backupperiod = peroid;
                //string dateString = "20110526";

                DateTime dt = DateTime.Today.AddHours(Convert.ToInt32(time));

                newitem.backuptime = dt;
                newitem.cleanupperiod= cleanypperiod;
                newitem.backuped = 0;
                db.dbbackup_infos.InsertOnSubmit(newitem);

                db.SubmitChanges();

            }
            catch
            {
               rst= "保存设置失败";
            }
            return rst;
        }

        public List<Backup> GetBackupList()
        {
            return db.dbbackups.Where(m=>m.deleted==0).Select(m=>new Backup{
            filename=m.filename,
            filepath=m.filepath,
            filesize=m.filesize,
            createtime=String.Format("{0:yyyy-MM-dd HH:mm:ss}",m.createtime),
            uid=m.uid
            }).ToList();

        }

        public static string  RestoreByid(long uid)
        {
            try
            {
                BingChongDBDataContext db = new BingChongDBDataContext();
                MasterDBDataContext masterdb = CommonModel.GetMasterDBContext();

                dbbackup bkInfo = db.dbbackups.Where(m => m.deleted == 0).FirstOrDefault();

                int retVal = masterdb.BingChongRestore(bkInfo.filepath);

                return SYSTEM_SUBMITSTATUS.SUCCESS_SUBMIT;
            }
            catch (Exception e)
            {
               // CommonModel.WriteLogFile("SystemModel", "retore()", e.ToString());
                return SYSTEM_SUBMITSTATUS.ERROR_SUBMIT;
            }
        }


        public static string RestoreTo(string flag)
        {
            var path = "";
            if (flag == "base")
            {
                path = "D:\\BingChong2\\Database\\bingchong_basic_backup.bak";
            }
            else
            {
                path = "D:\\BingChong2\\Database\\bingchong_original_backup.bak";
            }
            try
            {
                BingChongDBDataContext db = new BingChongDBDataContext();
                MasterDBDataContext masterdb = CommonModel.GetMasterDBContext();
                int retVal = masterdb.BingChongRestore(path);

                return SYSTEM_SUBMITSTATUS.SUCCESS_SUBMIT;
            }
            catch (Exception e)
            {
                // CommonModel.WriteLogFile("SystemModel", "retore()", e.ToString());
                return SYSTEM_SUBMITSTATUS.ERROR_SUBMIT;
            }
        }

        public static string backup()
        {
            var rst = "";
            try
            {
                MasterDBDataContext masterdb = CommonModel.GetMasterDBContext();
                BingChongDBDataContext db = new BingChongDBDataContext();

                int retVal = masterdb.BingChongBackup();


                IQueryable<dbbackup> dbbackuplist = (from m in db.dbbackups
                                                         where (m.deleted == 0 && m.filesize > 0)
                                                         select m).AsQueryable();

                if (dbbackuplist != null)
                {
                    foreach (dbbackup dbbk in dbbackuplist)
                    {
                        FileInfo fs = new FileInfo(dbbk.filepath);

                        if (fs.Exists)
                            dbbk.filesize = (int)(fs.Length / 1024);
                        else
                            dbbk.deleted = 1;
                    }
                }

                db.SubmitChanges();
            }
            catch (Exception e)
            {
                rst = "操作失败！";
            }
            return rst;
        }

    }
}