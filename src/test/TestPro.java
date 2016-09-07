package test;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.entity.AC;
import com.ex.qi.entity.DC;
import com.ex.qi.utils.TableUtils;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

/**
 * Created by sunline on 2016/8/18.
 */
public class TestPro {
    public static void main(String[] args) {
        //testCreateTable();
//        testCheksum();
//        format2JSON();
        testIsExits();
    }
    private static void testIsExits(){
        String table = "real_Kwh_device03";
        BaseDaoImpl baseDao = new BaseDaoImpl();
        boolean flag = baseDao.tableIsExists(table);
        System.out.println(flag);
    }
    private static void testCreateTable() {
        boolean flag = new TableUtils().newDeviceTable();
        if (flag){
            System.out.println("创建成功！");
        }
    }
    private static void testCheksum(){
        String str = "FDAB";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            System.out.println(i +"="+Integer.parseInt(Integer.toHexString(c)));
        }
    }
    private static void format2JSON(){
        AC AC = new AC();
        System.out.println();
        JSON json = JSONSerializer.toJSON(AC);
        System.out.println(json.toString());
        DC DC = new DC();
        DC.setAddr((byte)0x01);
        DC.setFunction((byte) 0x03);
        DC.setMemeryHi((byte) 0x02);
        DC.setMemeryLo((byte) 0x0e);
        DC.setCountHi((byte) 0x00);
        DC.setCountLo((byte) 0x20);
        DC.parseCRC();
        json = JSONSerializer.toJSON(DC);
        System.out.println(json);
    }
}
