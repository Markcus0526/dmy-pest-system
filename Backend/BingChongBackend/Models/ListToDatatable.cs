using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
//using FSSite.Models;
using System.Data;
using System.Reflection;
using System.Collections;

namespace BingChongBackend.Models
{
    /*
  * 考核的基本情况
  */
    public class ListToDatatable
    {
        public DataTable listToDatatable(IList ld,int d)
        {           
                DataTable result = new DataTable();
                if (ld.Count > 0)
                {
                    PropertyInfo[] propertys = ld[0].GetType().GetProperties();
                    for (int i = 0; i < d; i++)
                    {
                        result.Columns.Add(propertys[i].Name, propertys[i].PropertyType);
                    }
                        //foreach (PropertyInfo pi in propertys)                
                        //{                    
                        //    result.Columns.Add(pi.Name, pi.PropertyType);                
                        //}

                        for (int i = 0; i < ld.Count; i++)
                        {
                            ArrayList tempList = new ArrayList();
                            for (int j = 0; j < d; j++)
                            {
                                object obj = propertys[j].GetValue(ld[i], null);
                                tempList.Add(obj);
                            }
                            object[] array = tempList.ToArray();
                            result.LoadDataRow(array, true);
                        }            
                }            
                return result;        
            
        }

    }
}
