package cn.edu.fjnu.towide.util;

/**
 * @Author: YuLin
 *  数字转换工具类, 主要负责检查并转换前端传递过来的数值
 * @Date: Created in 16:27 2018/8/28
 * @Modified By:
 */
public class MyNumberTranformUtil {

    private MyNumberTranformUtil() {

    }

    /**
     *  获取Long类型数值
     */
    public static Long getLong(Number number) {
        if (number==null){
            return null;
        }
        return number.longValue();
    }

    /**
     *  获取Integer类型的数值
     */
    public static Integer getInteger(Number number) {
        if (number==null){
            return null;
        }
        return new Integer(number.intValue());
    }
}
