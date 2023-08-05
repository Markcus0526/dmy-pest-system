using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;

namespace BingchongService.SvcManager
{
    public class DataClasses
    {
        [DataContract]
        public class STItem
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }
        }

        [DataContract]
        public class STSheng
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "status", Order = 1)]
            private long _status = 0;
            public long status
            {
                get { return _status; }
                set { _status = value; }
            }
        }

        [DataContract]
        public class STShi
        {
            [DataMember(Name = "sheng_id", Order = 1)]
            private long _sheng_id = 0;
            public long sheng_id
            {
                get { return _sheng_id; }
                set { _sheng_id = value; }
            }

            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "status", Order = 1)]
            private long _status = 0;
            public long status
            {
                get { return _status; }
                set { _status = value; }
            }
        }

        [DataContract]
        public class STXian
        {
            [DataMember(Name = "shi_id", Order = 1)]
            private long _shi_id = 0;
            public long shi_id
            {
                get { return _shi_id; }
                set { _shi_id = value; }
            }

            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "status", Order = 1)]
            private long _status = 0;
            public long status
            {
                get { return _status; }
                set { _status = value; }
            }
        }

        [DataContract]
        public class STPoint
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "nickname", Order = 1)]
            private String _nickname = "";
            public String nickname
            {
                get { return _nickname; }
                set { _nickname = value; }
            }

            [DataMember(Name = "longitude", Order = 1)]
            private Decimal _longitude = 0;
            public Decimal longitude
            {
                get { return _longitude; }
                set { _longitude = value; }
            }

            [DataMember(Name = "latitude", Order = 1)]
            private Decimal _latitude = 0;
            public Decimal latitude
            {
                get { return _latitude; }
                set { _latitude = value; }
            }

            [DataMember(Name = "type", Order = 1)]
            private int _type = 0;
            public int type
            {
                get { return _type; }
                set { _type = value; }
            }

            [DataMember(Name = "level", Order = 1)]
            private int _level = 0;
            public int level
            {
                get { return _level; }
                set { _level = value; }
            }

            [DataMember(Name = "sheng_id", Order = 1)]
            private long _sheng_id = 0;
            public long sheng_id
            {
                get { return _sheng_id; }
                set { _sheng_id = value; }
            }

            [DataMember(Name = "shi_id", Order = 1)]
            private long _shi_id = 0;
            public long shi_id
            {
                get { return _shi_id; }
                set { _shi_id = value; }
            }

            [DataMember(Name = "xian_id", Order = 1)]
            private long _xian_id = 0;
            public long xian_id
            {
                get { return _xian_id; }
                set { _xian_id = value; }
            }

            [DataMember(Name = "info1", Order = 1)]
            private String _info1 = "";
            public String info1
            {
                get { return _info1; }
                set { _info1 = value; }
            }

            [DataMember(Name = "info2", Order = 1)]
            private String _info2 = "";
            public String info2
            {
                get { return _info2; }
                set { _info2 = value; }
            }

            [DataMember(Name = "info3", Order = 1)]
            private String _info3 = "";
            public String info3
            {
                get { return _info3; }
                set { _info3 = value; }
            }

            [DataMember(Name = "info4", Order = 1)]
            private String _info4 = "";
            public String info4
            {
                get { return _info4; }
                set { _info4 = value; }
            }

            [DataMember(Name = "info5", Order = 1)]
            private String _info5 = "";
            public String info5
            {
                get { return _info5; }
                set { _info5 = value; }
            }

            [DataMember(Name = "info6", Order = 1)]
            private String _info6 = "";
            public String info6
            {
                get { return _info6; }
                set { _info6 = value; }
            }

            [DataMember(Name = "note", Order = 1)]
            private String _note = "";
            public String note
            {
                get { return _note; }
                set { _note = value; }
            }

            [DataMember(Name = "task_count", Order = 1)]
            private int _task_count = -1;
            public int task_count
            {
                get { return _task_count; }
                set { _task_count = value; }
            }
        }

        [DataContract]
        public class STBlight
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "kind", Order = 1)]
            private int _kind = 0;
            public int kind
            {
                get { return _kind; }
                set { _kind = value; }
            }

            [DataMember(Name = "info1", Order = 1)]
            private String _info1 = "";
            public String info1
            {
                get { return _info1; }
                set { _info1 = value; }
            }

            [DataMember(Name = "info2", Order = 1)]
            private String _info2 = "";
            public String info2
            {
                get { return _info2; }
                set { _info2 = value; }
            }

            [DataMember(Name = "info3", Order = 1)]
            private String _info3 = "";
            public String info3
            {
                get { return _info3; }
                set { _info3 = value; }
            }

            [DataMember(Name = "info4", Order = 1)]
            private String _info4 = "";
            public String info4
            {
                get { return _info4; }
                set { _info4 = value; }
            }

            [DataMember(Name = "info5", Order = 1)]
            private String _info5 = "";
            public String info5
            {
                get { return _info5; }
                set { _info5 = value; }
            }

            [DataMember(Name = "info6", Order = 1)]
            private String _info6 = "";
            public String info6
            {
                get { return _info6; }
                set { _info6 = value; }
            }

            [DataMember(Name = "photo_list", Order = 1)]
            private String _photo_list = "";
            public String photo_list
            {
                get { return _photo_list; }
                set { _photo_list = value; }
            }

            [DataMember(Name = "serial", Order = 1)]
            private String _serial = "";
            public String serial
            {
                get { return _serial; }
                set { _serial = value; }
            }

            [DataMember(Name = "form_ids", Order = 1)]
            private String _form_ids = "";
            public String form_ids
            {
                get { return _form_ids; }
                set { _form_ids = value; }
            }
        }

        [DataContract]
        public class STForm
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "arrFieldIds", Order = 1)]
            private List<String> _arrFieldIds = null;
            public List<String> arrFieldIds
            {
                get { return _arrFieldIds; }
                set { _arrFieldIds = value; }
            }

            [DataMember(Name = "fields", Order = 1)]
            private List<STField> _fields = null;
            public List<STField> fields
            {
                get { return _fields; }
                set { _fields = value; }
            }
        }

        [DataContract]
        public class STReport
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "sheng_id", Order = 1)]
            private long _sheng_id = 0;
            public long sheng_id
            {
                get { return _sheng_id; }
                set { _sheng_id = value; }
            }

            [DataMember(Name = "sheng_name", Order = 1)]
            private String _sheng_name = "";
            public String sheng_name
            {
                get { return _sheng_name; }
                set { _sheng_name = value; }
            }

            [DataMember(Name = "shi_id", Order = 1)]
            private long _shi_id = 0;
            public long shi_id
            {
                get { return _shi_id; }
                set { _shi_id = value; }
            }

            [DataMember(Name = "shi_name", Order = 1)]
            private String _shi_name = "";
            public String shi_name
            {
                get { return _shi_name; }
                set { _shi_name = value; } 
            }

            [DataMember(Name = "xian_id", Order = 1)]
            private long _xian_id = 0;
            public long xian_id
            {
                get { return _xian_id; }
                set { _xian_id = value; }
            }

            [DataMember(Name = "xian_name", Order = 1)]
            private String _xian_name = "";
            public String xian_name
            {
                get { return _xian_name; }
                set { _xian_name = value; }
            }

            [DataMember(Name = "point_id", Order = 1)]
            private long _point_id = 0;
            public long point_id
            {
                get { return _point_id; }
                set { _point_id = value; }
            }

            [DataMember(Name = "point_name", Order = 1)]
            private String _point_name = "";
            public String point_name
            {
                get { return _point_name; }
                set { _point_name = value; }
            }

            [DataMember(Name = "form_id", Order = 1)]
            private long _form_id = 0;
            public long form_id
            {
                get { return _form_id; }
                set { _form_id = value; }
            }

            [DataMember(Name = "form_name", Order = 1)]
            private String _form_name = "";
            public String form_name
            {
                get { return _form_name; }
                set { _form_name = value; }
            }

            [DataMember(Name = "user_name", Order = 1)]
            private String _user_name = "";
            public String user_name
            {
                get { return _user_name; }
                set { _user_name = value; }
            }

            [DataMember(Name = "blight_kind", Order = 1)]
            private int _blight_kind = 0;
            public int blight_kind
            {
                get { return _blight_kind; }
                set { _blight_kind = value; }
            }

            [DataMember(Name = "blight_name", Order = 1)]
            private String _blight_name = "";
            public String blight_name
            {
                get { return _blight_name; }
                set { _blight_name = value; }
            }

            [DataMember(Name = "report_time", Order = 1)]
            private String _report_time = "";
            public String report_time
            {
                get { return _report_time; }
                set { _report_time = value; }
            }

            [DataMember(Name = "review_status", Order = 1)]
            private int _review_status = 0;
            public int review_status
            {
                get { return _review_status; }
                set { _review_status = value; }
            }

            [DataMember(Name = "form_data", Order = 1)]
            private List<STFieldVal> _form_data = null;
            public List<STFieldVal> form_data
            {
                get { return _form_data; }
                set { _form_data = value; }
            }
        }

        [DataContract]
        public class STReportDetail
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            private long _field_id = 0;
            public long field_id
            {
                get { return _field_id; }
                set { _field_id = value; }
            }

            [DataMember(Name = "field_name", Order = 1)]
            private String _field_name = "";
            public String field_name
            {
                get { return _field_name; }
                set { _field_name = value; }
            }

            [DataMember(Name = "value_integer", Order = 1)]
            private long? _value_integer = -1;
            public long? value_integer
            {
                get { return _value_integer; }
                set { _value_integer = value; }
            }

            [DataMember(Name = "value_real", Order = 1)]
            private double? _value_real = -1;
            public double? value_real
            {
                get { return _value_real; }
                set { _value_real = value; }
            }

            [DataMember(Name = "value_text", Order = 1)]
            private String _value_text = "";
            public String value_text
            {
                get { return _value_text; }
                set { _value_text = value; }
            }

            [DataMember(Name = "field_type", Order = 1)]
            private String _field_type = "";
            public String field_type
            {
                get { return _field_type; }
                set { _field_type = value; }
            }

            [DataMember(Name = "field_unit", Order = 1)]
            private String _field_unit = "";
            public String field_unit
            {
                get { return _field_unit; }
                set { _field_unit = value; }
            }

            [DataMember(Name = "report_time", Order = 1)]
            private DateTime _report_time = new DateTime(1970,1,1);
            public DateTime report_time
            {
                get { return _report_time; }
                set { _report_time = value; }
            }

            [DataMember(Name = "report_date", Order = 1)]
            private String _report_date = "";
            public String report_date
            {
                get { return _report_date; }
                set { _report_date = value; }
            }

            [DataMember(Name = "sheng_name", Order = 1)]
            private String _sheng_name = "";
            public String sheng_name
            {
                get { return _sheng_name; }
                set { _sheng_name = value; }
            }

            [DataMember(Name = "shi_name", Order = 1)]
            private String _shi_name = "";
            public String shi_name
            {
                get { return _shi_name; }
                set { _shi_name = value; }
            }

            [DataMember(Name = "xian_name", Order = 1)]
            private String _xian_name = "";
            public String xian_name
            {
                get { return _xian_name; }
                set { _xian_name = value; }
            }

            [DataMember(Name = "point_name", Order = 1)]
            private String _point_name = "";
            public String point_name
            {
                get { return _point_name; }
                set { _point_name = value; }
            }
        }

        [DataContract]
        public class STField
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "type", Order = 1)]
            private String _type = "";
            public String type
            {
                get { return _type; }
                set { _type = value; }
            }

            [DataMember(Name = "parent_fieldid", Order = 1)]
            private long? _parent_fieldid = 0;
            public long? parent_fieldid
            {
                get { return _parent_fieldid; }
                set { _parent_fieldid = value; }
            }

            [DataMember(Name = "note", Order = 1)]
            private String _note = "";
            public String note
            {
                get { return _note; }
                set { _note = value; }
            }
        }

        [DataContract]
        public class STFieldVal
        {
            [DataMember(Name = "field_id", Order = 1)]
            private long _field_id = 0;
            public long field_id
            {
                get { return _field_id; }
                set { _field_id = value; }
            }

            [DataMember(Name = "field_name", Order = 1)]
            private String _field_name = "";
            public String field_name
            {
                get { return _field_name; }
                set { _field_name = value; }
            }

            [DataMember(Name = "field_val", Order = 1)]
            private String _field_val = "";
            public String field_val
            {
                get { return _field_val; }
                set { _field_val = value; }
            }
        }

        [DataContract]
        public class STExtraTask
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "admin_id", Order = 1)]
            private long _admin_id = 0;
            public long admin_id
            {
                get { return _admin_id; }
                set { _admin_id = value; }
            }

            [DataMember(Name = "watcher_id", Order = 1)]
            private long _watcher_id = 0;
            public long watcher_id
            {
                get { return _watcher_id; }
                set { _watcher_id = value; }
            }

            [DataMember(Name = "notice_date", Order = 1)]
            private DateTime _notice_date = DateTime.Now;
            public DateTime notice_date
            {
                get { return _notice_date; }
                set { _notice_date = value; }
            }

            [DataMember(Name = "report_date", Order = 1)]
            private DateTime _report_date = DateTime.Now;
            public DateTime report_date
            {
                get { return _report_date; }
                set { _report_date = value; }
            }

            [DataMember(Name = "user_name", Order = 1)]
            private String _user_name = "";
            public String user_name
            {
                get { return _user_name; }
                set { _user_name = value; }
            }

            [DataMember(Name = "note", Order = 1)]
            private String _note = "";
            public String note
            {
                get { return _note; }
                set { _note = value; }
            }

            [DataMember(Name = "status", Order = 1)]
            private int _status = 0;
            public int status
            {
                get { return _status; }
                set { _status = value; }
            }
        }

        [DataContract]
        public class STTempBlight
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "kind", Order = 1)]
            private int _kind = 0;
            public int kind
            {
                get { return _kind; }
                set { _kind = value; }
            }

            [DataMember(Name = "longitude", Order = 1)]
            private String _longitude = "";
            public String longitude
            {
                get { return _longitude; }
                set { _longitude = value; }
            }

            [DataMember(Name = "latitude", Order = 1)]
            private String _latitude = "";
            public String latitude
            {
                get { return _latitude; }
                set { _latitude = value; }
            }

            [DataMember(Name = "sheng_id", Order = 1)]
            private long _sheng_id = 0;
            public long sheng_id
            {
                get { return _sheng_id; }
                set { _sheng_id = value; }
            }

            [DataMember(Name = "sheng_name", Order = 1)]
            private String _sheng_name = "";
            public String sheng_name
            {
                get { return _sheng_name; }
                set { _sheng_name = value; }
            }

            [DataMember(Name = "shi_id", Order = 1)]
            private long _shi_id = 0;
            public long shi_id
            {
                get { return _shi_id; }
                set { _shi_id = value; }
            }

            [DataMember(Name = "shi_name", Order = 1)]
            private String _shi_name = "";
            public String shi_name
            {
                get { return _shi_name; }
                set { _shi_name = value; }
            }

            [DataMember(Name = "xian_id", Order = 1)]
            private long _xian_id = 0;
            public long xian_id
            {
                get { return _xian_id; }
                set { _xian_id = value; }
            }

            [DataMember(Name = "xian_name", Order = 1)]
            private String _xian_name = "";
            public String xian_name
            {
                get { return _xian_name; }
                set { _xian_name = value; }
            }

            [DataMember(Name = "info1", Order = 1)]
            private String _info1 = "";
            public String info1
            {
                get { return _info1; }
                set { _info1 = value; }
            }

            [DataMember(Name = "info2", Order = 1)]
            private String _info2 = "";
            public String info2
            {
                get { return _info2; }
                set { _info2 = value; }
            }

            [DataMember(Name = "info3", Order = 1)]
            private String _info3 = "";
            public String info3
            {
                get { return _info3; }
                set { _info3 = value; }
            }

            [DataMember(Name = "photo", Order = 1)]
            private String _photo = "";
            public String photo
            {
                get { return _photo; }
                set { _photo = value; }
            }

            [DataMember(Name = "note", Order = 1)]
            private String _note = "";
            public String note
            {
                get { return _note; }
                set { _note = value; }
            }

            [DataMember(Name = "status", Order = 1)]
            private int _status = 0;
            public int status
            {
                get { return _status; }
                set { _status = value; }
            }

            [DataMember(Name = "watcher_id", Order = 1)]
            private long _watcher_id = 0;
            public long watcher_id
            {
                get { return _watcher_id; }
                set { _watcher_id = value; }
            }

            [DataMember(Name = "admin_id", Order = 1)]
            private long? _admin_id = 0;
            public long? admin_id
            {
                get { return _admin_id; }
                set { _admin_id = value; }
            }            

            [DataMember(Name = "report_date", Order = 1)]
            private DateTime _report_date = DateTime.Now;
            public DateTime report_date
            {
                get { return _report_date; }
                set { _report_date = value; }
            }

            [DataMember(Name = "review_date", Order = 1)]
            private DateTime _review_date = DateTime.Now;
            public DateTime review_date
            {
                get { return _review_date; }
                set { _review_date = value; }
            }
        }

        [DataContract]
        public class STNotice
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "title", Order = 1)]
            private String _title = "";
            public String title
            {
                get { return _title; }
                set { _title = value; }
            }

            [DataMember(Name = "pubuser_name", Order = 1)]
            private String _pubuser_name = "";
            public String pubuser_name
            {
                get { return _pubuser_name; }
                set { _pubuser_name = value; }
            }

            [DataMember(Name = "year", Order = 1)]
            private long _year = 0;
            public long year
            {
                get { return _year; }
                set { _year = value; }
            }

            [DataMember(Name = "serial", Order = 1)]
            private String _serial = "";
            public String serial
            {
                get { return _serial; }
                set { _serial = value; }
            }

            [DataMember(Name = "content", Order = 1)]
            private String _content = "";
            public String content
            {
                get { return _content; }
                set { _content = value; }
            }

            [DataMember(Name = "pub_date", Order = 1)]
            private String _pub_date = "";
            public String pub_date
            {
                get { return _pub_date; }
                set { _pub_date = value; }
            }
        }

        [DataContract]
        public class STReportLineItem
        {
            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "int_value", Order = 1)]
            private long _int_value = 0;
            public long int_value
            {
                get { return _int_value; }
                set { _int_value = value; }
            }

            [DataMember(Name = "float_value", Order = 1)]
            private Double _float_value = 0;
            public Double float_value
            {
                get { return _float_value; }
                set { _float_value = value; }
            }
        }

        [DataContract]
        public class STChartValueItem
        {
            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "value", Order = 1)]
            private double _value = 0;
            public double value
            {
                get { return _value; }
                set { _value = value; }
            }
        }

        [DataContract]
        public class STTaskDetail
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "arr_points", Order = 1)]
            private List<String> _arr_points = new List<String>();
            public List<String> arr_points
            {
                get { return _arr_points; }
                set { _arr_points = value; }
            }

            [DataMember(Name = "startdate", Order = 1)]
            private DateTime _startdate = new DateTime(1970,1,1);
            public DateTime startdate
            {
                get { return _startdate; }
                set { _startdate = value; }
            }

            [DataMember(Name = "enddate", Order = 1)]
            private DateTime _enddate = new DateTime(1970, 1, 1);
            public DateTime enddate
            {
                get { return _enddate; }
                set { _enddate = value; }
            }

            [DataMember(Name = "period", Order = 1)]
            private long _period = 0;
            public long period
            {
                get { return _period; }
                set { _period = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "form_id", Order = 1)]
            private long _form_id = 0;
            public long form_id
            {
                get { return _form_id; }
                set { _form_id = value; }
            }

            [DataMember(Name = "blight_id", Order = 1)]
            private long _blight_id = 0;
            public long blight_id
            {
                get { return _blight_id; }
                set { _blight_id = value; }
            }

            [DataMember(Name = "task_date", Order = 1)]
            private String _task_date = "";
            public String task_date
            {
                get { return _task_date; }
                set { _task_date = value; }
            }

            [DataMember(Name = "blight_name", Order = 1)]
            private String _blight_name = "";
            public String blight_name
            {
                get { return _blight_name; }
                set { _blight_name = value; }
            }
        }

        [DataContract]
        public class STTaskItem
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "form_id", Order = 1)]
            private long _form_id = 0;
            public long form_id
            {
                get { return _form_id; }
                set { _form_id = value; }
            }

            [DataMember(Name = "point_id", Order = 1)]
            private long _point_id = 0;
            public long point_id
            {
                get { return _point_id; }
                set { _point_id = value; }
            }

            [DataMember(Name = "point_name", Order = 1)]
            private String _point_name = "";
            public String point_name
            {
                get { return _point_name; }
                set { _point_name = value; }
            }

            [DataMember(Name = "task_date", Order = 1)]
            private String _task_date = "";
            public String task_date
            {
                get { return _task_date; }
                set { _task_date = value; }
            }

            [DataMember(Name = "dt_task_date", Order = 1)]
            private DateTime _dt_task_date = new DateTime(1970,1,1);
            public DateTime dt_task_date
            {
                get { return _dt_task_date; }
                set { _dt_task_date = value; }
            }

            [DataMember(Name = "blight_id", Order = 1)]
            private long _blight_id = 0;
            public long blight_id
            {
                get { return _blight_id; }
                set { _blight_id = value; }
            }

            [DataMember(Name = "blight_name", Order = 1)]
            private String _blight_name = "";
            public String blight_name
            {
                get { return _blight_name; }
                set { _blight_name = value; }
            }

            [DataMember(Name = "report_id", Order = 1)]
            private long _report_id = 0;
            public long report_id
            {
                get { return _report_id; }
                set { _report_id = value; }
            }
        }

        [DataContract]
        public class STTrackPoint
        {
            [DataMember(Name = "latitude", Order = 1)]
            private Decimal _latitude = 0;
            public Decimal latitude
            {
                get { return _latitude; }
                set { _latitude = value; }
            }

            [DataMember(Name = "longitude", Order = 1)]
            private Decimal _longitude = 0;
            public Decimal longitude
            {
                get { return _longitude; }
                set { _longitude = value; }
            }

            [DataMember(Name = "date", Order = 1)]
            private DateTime _date = new DateTime(1970, 1, 1);
            public DateTime date
            {
                get { return _date; }
                set { _date = value; }
            }
        }
        
        [DataContract]
        public class STUserTrack
        {
            [DataMember(Name = "userid", Order = 1)]
            private long _userid = 0;
            public long userid
            {
                get { return _userid; }
                set { _userid = value; }
            }

            [DataMember(Name = "username", Order = 1)]
            private String _username = "";
            public String username
            {
                get { return _username; }
                set { _username = value; }
            }

            [DataMember(Name = "tracks", Order = 1)]
            private List<STTrackPoint> _tracks = null;
            public List<STTrackPoint> tracks
            {
                get { return _tracks; }
                set { _tracks = value; }
            }
        }

        [DataContract]
        public class STUserInfo
        {
            [DataMember(Name = "uid", Order = 1)]
            private long _uid = 0;
            public long uid
            {
                get { return _uid; }
                set { _uid = value; }
            }

            [DataMember(Name = "name", Order = 1)]
            private String _name = "";
            public String name
            {
                get { return _name; }
                set { _name = value; }
            }

            [DataMember(Name = "phone", Order = 1)]
            private String _phone = "";
            public String phone
            {
                get { return _phone; }
                set { _phone = value; }
            }

            [DataMember(Name = "imgurl", Order = 1)]
            private String _imgurl = "";
            public String imgurl
            {
                get { return _imgurl; }
                set { _imgurl = value; }
            }

            [DataMember(Name = "right_id", Order = 1)]
            private long _right_id = 0;
            public long right_id
            {
                get { return _right_id; }
                set { _right_id = value; }
            }

            [DataMember(Name = "level", Order = 1)]
            private int _level = 0;
            public int level
            {
                get { return _level; }
                set { _level = value; }
            }

            [DataMember(Name = "sheng_id", Order = 1)]
            private long _sheng_id = 0;
            public long sheng_id
            {
                get { return _sheng_id; }
                set { _sheng_id = value; }
            }

            [DataMember(Name = "shi_id", Order = 1)]
            private long _shi_id = 0;
            public long shi_id
            {
                get { return _shi_id; }
                set { _shi_id = value; }
            }

            [DataMember(Name = "xian_id", Order = 1)]
            private long _xian_id = 0;
            public long xian_id
            {
                get { return _xian_id; }
                set { _xian_id = value; }
            }
        }
    }
}