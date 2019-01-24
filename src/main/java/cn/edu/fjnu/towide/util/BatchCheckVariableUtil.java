package cn.edu.fjnu.towide.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YuLin
 * 批量检查前台参数合法性
 * 如果前台传递的参数非常多,而且使用了类进行封装,就可以使用这个工具类批量检查类实例化对象字段的合法性
 * 依字段类型批检测参数合法性
 * @Date: Created in 20:11 2018/9/12
 * @Modified By:
 */
@SuppressWarnings("all")
public class BatchCheckVariableUtil {

    private BatchCheckVariableUtil() {

    }

    /**
     * 检查类对象中所有String字段的合法性(即适用于类中所有的字段都是必填字段, 需要检测)
     * 如果类中没有String类型的字段,或者类中没有字段,默认返回false
     */
    public static boolean AllStringFieldsAreIllegal(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);//在用反射时访问私有变量,否则无法把private修饰的字段提取出来
            try {
                //判断该字段是否是String类型
                if (field.getGenericType().getTypeName().equals("java.lang.String")) {
                    if (field.get(o) == null) {
                        return true;
                    }
                    //此时必为String类型,直接强制转换
                    if (CheckVariableUtil.stringVariableIsEmpty((String) field.get(o))) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        return false;
    }

    /**
     *  检查类对象中必填String字段的合法性(类含有非必填字段)
     * List<String> flag:非必填字段的名集合
     * 如果类中没有String类型的必填字段,或者类中没有字段,或都是非必填字段,默认返回false
     */
    public static boolean NotAllStringFieldsAreIllegal(Object o, List<String> flag) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> lists = new ArrayList(flag);
        for (Field field : fields) {
            field.setAccessible(true);//在用反射时访问私有变量,否则无法把private修饰的字段提取出来
            //判断该字段是否是必填字段
            if (lists.contains(field.getName())) {
                continue;
            }
            try {
                //判断该字段是否是String类型
                if (field.getGenericType().getTypeName().equals("java.lang.String")) {
                    {
                        if (field.get(o) == null) {
                            return true;
                        }
                        //此时必为String类型,直接强制转换
                        if (CheckVariableUtil.stringVariableIsEmpty((String) field.get(o))) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        return false;
    }
}
