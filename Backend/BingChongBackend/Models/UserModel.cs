using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections.Specialized;
using System.Security.Cryptography;
using System.Text;
using System.Web.Security;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel;
using System.Globalization;
using System.Web.Hosting;
using System.IO;
using BingChongBackend.shipin;



namespace BingChongBackend.Models
{
    public class UserInfo
    {
        public long uid { get; set; }
        public long id { get; set; }
        public string username { get; set; }
        public string realname { get; set; }
        
        public long roleid { get; set; }
        public string role { get; set; }

        public DateTime createtime { get; set; }
        public DateTime? lasttime { get; set; }
        public long serial { get; set; }
        public string phone { get; set; }
        public string place { get; set; }
        public string job { get; set; }
        public string imgurl { get; set; }

        public long sheng_id { get; set; }
        public long shi_id { get; set; }
        public long xian_id { get; set; }
        public CommonModel.UserLevel level { get; set; }
        public string lastip { get; set; }
    }

    //#region LogOnModel


    //[AttributeUsage(AttributeTargets.Field | AttributeTargets.Property, AllowMultiple = false, Inherited = true)]
    //public sealed class ValidatePasswordLengthAttribute : ValidationAttribute
    //{
    //    private const string _defaultErrorMessage = "{0}至少为{1}位.";
    //    private readonly int _minCharacters = 6;

    //    public ValidatePasswordLengthAttribute()
    //        : base(_defaultErrorMessage)
    //    {
    //    }

    //    public override string FormatErrorMessage(string name)
    //    {
    //        return String.Format(CultureInfo.CurrentUICulture, ErrorMessageString,
    //            name, _minCharacters);
    //    }

    //    public override bool IsValid(object value)
    //    {
    //        string valueAsString = value as string;
    //        return (valueAsString != null && valueAsString.Length >= _minCharacters);
    //    }
    //}
    //#endregion



    public class UserModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();


        public List<SearchList> GetRegionOptionsForUserView(CommonModel.UserLevel level)
        {
            List<SearchList> rst = new List<SearchList>();

            switch (level)
            {
                case CommonModel.UserLevel.ADMIN:
                    rst.Add(new SearchList { name = "国家", uid = 4 });
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;

                case CommonModel.UserLevel.XIANJI:
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;
                case CommonModel.UserLevel.SHIJI:
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    rst.Add(new SearchList { name = "国家", uid = 4 });
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    break;
            }


            return rst;
        }

#region LogonModel 

        public void SignIn(string userName, bool createPersistentCookie)
        {
            if (String.IsNullOrEmpty(userName)) throw new ArgumentException("Value cannot be null or empty.", "userName");

            FormsAuthentication.SetAuthCookie(userName, createPersistentCookie);
        }

        public static string GetMD5Hash(string value)
        {
            MD5 md5Hasher = MD5.Create();
            byte[] data = md5Hasher.ComputeHash(Encoding.Default.GetBytes(value));
            StringBuilder sBuilder = new StringBuilder();
            for (int i = 0; i < data.Length; i++)
            {
                sBuilder.Append(data[i].ToString("x2"));
            }
            return sBuilder.ToString();
        }

        public UserInfo GetUserByNamePwd(string uname, string upwd)
        {
            UserInfo rst = null;
            try
            {
                var userinfo = db.users.Where(m => m.deleted == 0 && m.name == uname && m.password == upwd&&m.right_id!=0).FirstOrDefault();

                rst = new UserInfo();
                rst.uid = userinfo.uid;
                rst.username = userinfo.name;
                rst.realname = userinfo.realname;
                rst.sheng_id = userinfo.sheng_id;
                rst.shi_id = userinfo.shi_id;
                rst.xian_id = userinfo.xian_id;
                rst.level = (CommonModel.UserLevel)userinfo.level;
                rst.roleid = userinfo.right_id;
            }
            catch (System.Exception ex)
            {
                return null;
            }

            return rst;
        }

        public void SignOut()
        {
            FormsAuthentication.SignOut();
        }
        #endregion

        public UserInfo ValidateUser(string username, string password, string origin)
        {

            string sha1Pswd = UserModel.GetMD5Hash(password);
            return GetUserByNamePwd(username, sha1Pswd);
        }

        public List<UserInfo> GetUserList()
        {
            List<UserInfo> adminrst = db.users.Where(m => m.deleted == 0&&m.right_id!=0).Select(m =>
                                    new UserInfo
                                    {
                                        uid = m.uid,
                                        id = m.uid,
                                        username=m.name,
                                        serial=m.serial,
                                        level = (CommonModel.UserLevel)m.level,
                                        sheng_id=m.sheng_id,
                                        shi_id=m.shi_id,
                                        xian_id=m.xian_id,
                                        realname=m.realname,
                                        roleid=m.right_id,
                                    }).ToList();
            List<UserInfo> reporterrst = db.users.Where(m => m.deleted == 0 && m.right_id == 0).Select(m =>
                                new UserInfo
                                {
                                    uid = m.uid,
                                    id = m.uid,
                                    username = m.name,
                                    serial = m.serial,
                                    level = (CommonModel.UserLevel)m.level,
                                    sheng_id = m.sheng_id,
                                    shi_id = m.shi_id,
                                    xian_id = m.xian_id,
                                    realname = m.realname,
                                    roleid = m.right_id,
                                    role="测报员"
                                }).ToList();
            
                foreach(var n in adminrst){ 
                n.role = db.tbl_rights.Where(m => m.uid == n.roleid).Select(m => m.name).FirstOrDefault();
                }
                var rst = adminrst.Concat(reporterrst).ToList();

                return rst;
        }

        public List<UserInfo> SearchUserList(int level, int sheng, int shi, int xian, long roleid, string username)
        {
            var rst= GetUserList();
            rst = rst.Where(m => m.level == (CommonModel.UserLevel)level).ToList();

            if (sheng !=0)
            {
                rst = rst.Where(m => m.sheng_id == sheng).ToList();
            }
            if (shi!= 0)
            {
                rst = rst.Where(m => m.shi_id == shi).ToList();
            }
            if (xian != 0)
            {
                rst = rst.Where(m => m.xian_id == xian).ToList();
            }
            if (roleid!=-1)
            {
                rst = rst.Where(m => m.roleid== roleid).ToList();

            }
            if (!string.IsNullOrWhiteSpace(username))
            {
                rst = rst.Where(m => m.username.Contains(username)).ToList();

            }
            return rst;
        }


        public string InserteUser(string level, long role, string username, string userpwd, string realname,
                                     string company, string phone, string duty, string imgurl, string xianid, string shiid, string shengid)
        {
            var rst = "";
            try
            {
                user newitem = new user();

                string savepath = "Content/uploads/img/" + String.Format("{0:yyyyMMdd}", DateTime.Now) + "/";
                string orgbase = HostingEnvironment.MapPath("~/Content/uploads/temp/");
                string targetbase = HostingEnvironment.MapPath("~/" + savepath);

                newitem.name = username;
                newitem.password = GetMD5Hash(userpwd);

                newitem.level = int.Parse(level);
                newitem.right_id = role;

                newitem.realname = realname;
                newitem.phone = phone;
                newitem.place = company;
                newitem.job = duty;
                if (role==0)
                {
                    long maxwatcherid = 0;
                    var watherlist = db.users.Where(m => m.right_id == 0).ToList();
                    if (watherlist.Count!=0)
                    {
                        maxwatcherid = watherlist.Max(m => m.serial);
                    }
                    newitem.serial = maxwatcherid+1;
                } 
                else
                {
                    long maxadminid = 0;
                    var adminlist = db.users.Where(m => m.right_id != 0).ToList();
                    if (adminlist.Count != 0)
                    {
                        maxadminid = adminlist.Max(m => m.serial);
                    }
                    newitem.serial = maxadminid + 1;
                }
                try
                {
                    switch (int.Parse(level))
                    {
                        case 1:
                            newitem.xian_id = long.Parse(xianid);
                            newitem.sheng_id = long.Parse(shengid);
                            newitem.shi_id = long.Parse(shiid);
                            break;
                        case 2:
                            newitem.sheng_id = long.Parse(shengid);
                            newitem.shi_id = long.Parse(shiid);
                            break;
                        case 3:
                            newitem.sheng_id = long.Parse(shengid);
                            break;
                        case 4:
                            break;
                    }

                }
                catch (System.Exception ex)
                {
                    rst = "请选择所辖区域";                	
                }
                if (!string.IsNullOrWhiteSpace(imgurl))
                {
                    if (File.Exists(orgbase + imgurl))
                    {
                        if (!Directory.Exists(targetbase))
                        {
                            Directory.CreateDirectory(targetbase);
                        }
                        File.Move(orgbase + imgurl, targetbase + imgurl);
                    }

                    newitem.imgurl = savepath + imgurl;

                }

                newitem.deleted = 0;

                db.users.InsertOnSubmit(newitem);

                db.SubmitChanges();

                BingChongBackend.shipin.shipinSoapClient c = new BingChongBackend.shipin.shipinSoapClient();
                var returnrst=c.AddBCUser(username,userpwd,"lvyunxinxi2014");
                if (returnrst==-1)
                {
                    rst = "视频服务器添加用户失败！原因：秘钥不正确。";
                }
                else if (returnrst == 0)
                {
                    rst = "视频服务器添加用户失败！原因：该用户存在。";
                }
            }
            catch (System.Exception ex)
            {
                rst = "操作失败！";
            }
           

            return rst;
        }

        public bool DeleteUser(long[] items)
        {
            bool rst = false;
            try
            {
                foreach (long uid in items)
                {
                    if (uid!=1)
                    {
                        user deluser = db.users.Where(m => m.uid == uid).FirstOrDefault();
                        deluser.deleted = 1;
                    }
                }
                db.SubmitChanges();
                rst = true;
            }
            catch (System.Exception ex)
            {
                
            }

            return rst;
        }


        public bool CheckDuplicateName(string username, long uid)
        {
            bool rst = true;

            rst = ((from m in db.users
                    where m.deleted == 0 && m.name == username && m.uid != uid
                    select m).FirstOrDefault() == null);

            return rst;
        }
        public UserInfo GetUserInfoById(long uid)
        {
            var rst= db.users
              .Where(m => m.deleted == 0 && m.uid == uid).Select(m => new UserInfo
              {
                  uid=m.uid,
                  username=m.name,
                  level=(CommonModel.UserLevel)m.level,
                  realname=m.realname,
                  phone=m.phone,
                  place=m.place,
                  job=m.job,
                  imgurl=m.imgurl,
                  roleid=m.right_id,
                  sheng_id=m.sheng_id,
                  shi_id=m.shi_id,
                  xian_id=m.xian_id,
                  serial=m.serial,

              })
              .FirstOrDefault();

            if (rst.roleid==0)
            {
                rst.role = "测报员";
            }
            else
            {
                rst.role = db.tbl_rights.Where(m => m.uid == rst.roleid).Select(m => m.name).FirstOrDefault();

            }
            return rst;
        }

        public string UpdateUserInfo(long uid, long role, string newpwd, string realname,
                                     string company, string phone, string duty, string imgurl)
        {
            string savepath = "Content/uploads/img/" + String.Format("{0:yyyyMMdd}", DateTime.Now) + "/";
            string orgbase = HostingEnvironment.MapPath("~/Content/uploads/temp/");
            string targetbase = HostingEnvironment.MapPath("~/" + savepath);

            string rst = "";

            user edititem = db.users.Where(m=>m.uid==uid).FirstOrDefault();

            if (edititem != null)
            {

                edititem.realname = realname;
                if ((edititem.password != newpwd) && (newpwd != ""))
                    edititem.password = GetMD5Hash(newpwd);

                edititem.realname = realname;
                edititem.place = company;
                edititem.phone = phone;
                edititem.job = duty;
                edititem.right_id = role;

                if (!string.IsNullOrWhiteSpace(imgurl))
                {
                    if (File.Exists(orgbase + imgurl))
                    {
                        if (!Directory.Exists(targetbase))
                        {
                            Directory.CreateDirectory(targetbase);
                        }
                        File.Move(orgbase + imgurl, targetbase + imgurl);
                    }

                    edititem.imgurl = savepath + imgurl;

                }

                //edititem.imgurl = "";
                db.SubmitChanges();
                rst = "";
            }
            else
            {
                rst = "用户不存在";
            }

            return rst;
        }

        public string SubmitPass(long uid, string pass)
        {
            var rst = "";
            try
            {
                user edit = db.users.Where(m => m.deleted == 0 && m.uid == uid).FirstOrDefault();
                edit.password = GetMD5Hash(pass);
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                rst = "操作失败！";
            }
            
            return rst;
        }
    }
}