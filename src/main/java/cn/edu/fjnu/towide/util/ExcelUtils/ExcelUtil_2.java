/**
 * Project Name:excelutil
 * File Name:ExcelUtil.java
 * Package Name:com.lkx.util
 * Date:2017年6月7日上午9:44:58
 * Copyright (c) 2017~2020, 934268568@qq.com All Rights Reserved.
 */

package cn.edu.fjnu.towide.util.ExcelUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

/**
 *
 *
 * @author likaixuan
 * @version V1.0
 * @see
 * @since JDK 1.7
 */
public class ExcelUtil_2 implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * readXlsPart:(根据传进来的map集合读取Excel) 传进来4个参数 <String,String>类型，第二个要反射的类的具体路径)
     *
     * @param param param.filePath  Excel文件路径
     *              param.map       表头和属性的Map集合,其中Map中Key为Excel列的名称，Value为反射类的属性
     *              param.classPath 需要映射的model的路径
     * @return
     * @throws Exception
     * @author likaixuan
     * @since JDK 1.7
     */
    public static <T> List<T> readXlsPart(ExcelParam param) throws Exception {

        Set keySet = param.getMap().keySet();// 返回键的集合

        /** 反射用 **/
        Class<?> demo = null;
        Object obj = null;
        /** 反射用 **/
        List<Object> list = new ArrayList<Object>();
        demo = Class.forName(param.getClassPath());

        String filePath = param.getFilePath();
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        InputStream is = new FileInputStream(filePath);
        Workbook wb = null;

        if (ExcelTypeEnum.EXCEL_THREE.getText().equals(fileType)) {
            wb = new HSSFWorkbook(is);
        } else if (ExcelTypeEnum.EXCEL_SEVEN.getText().equals(fileType)) {
            wb = new XSSFWorkbook(is);
        } else {
            throw new Exception("您输入的excel格式不正确");
        }
        int startSheetNum = 0;
        int endSheetNum = 1;
        if (null != param.getSheetIndex()) {
            startSheetNum = param.getSheetIndex() - 1;
            endSheetNum = param.getSheetIndex();
        }
        for (int sheetNum = startSheetNum; sheetNum < endSheetNum; sheetNum++) {// 获取每个Sheet表

            int rowNum_x = -1;// 记录第x行为表头
            Map<String, Integer> cellmap = new HashMap<String, Integer>();// 存放每一个field字段对应所在的列的序号
            List<String> headlist = new ArrayList();// 存放所有的表头字段信息

            Sheet hssfSheet = wb.getSheetAt(sheetNum);

            // 设置默认最大行为2w行
            if (hssfSheet != null && hssfSheet.getLastRowNum() > 60000) {
                throw new Exception("Excel 数据超过60000行,请检查是否有空行,或分批导入");
            }

            int rowCount = hssfSheet.getLastRowNum();
            if (rowCount >= 2000){

                int threadCount = (rowCount / 2000) + 1;
            }

            // 循环行Row
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

                if (param.getRowNumIndex() != null && rowNum_x == -1) {// 如果传值指定从第几行开始读，就从指定行寻找，否则自动寻找
                    Row hssfRow = hssfSheet.getRow(param.getRowNumIndex());
                    if (hssfRow == null) {
                        throw new RuntimeException("指定的行为空，请检查");
                    }
                    rowNum = param.getRowNumIndex() - 1;
                }
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                boolean flag = false;
                for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
                    if (hssfRow.getCell(i) != null && !("").equals(hssfRow.getCell(i).toString().trim())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    continue;
                }

                if (rowNum_x == -1) {
                    // 循环列Cell
                    for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {

                        Cell hssfCell = hssfRow.getCell(cellNum);
                        if (hssfCell == null) {
                            continue;
                        }

                        String tempCellValue = hssfSheet.getRow(rowNum).getCell(cellNum).getStringCellValue();

                        tempCellValue = StringUtils.remove(tempCellValue, (char) 160);
                        tempCellValue = tempCellValue.trim();

                        headlist.add(tempCellValue);

                        Iterator it = keySet.iterator();

                        while (it.hasNext()) {
                            Object key = it.next();
                            if (StringUtils.isNotBlank(tempCellValue)
                                    && StringUtils.equals(tempCellValue, key.toString())) {
                                rowNum_x = rowNum;
                                cellmap.put(param.getMap().get(key).toString(), cellNum);
                            }
                        }
                        if (rowNum_x == -1) {
                            throw new Exception("没有找到对应的字段或者对应字段行上面含有不为空白的行字段");
                        }
                    }

                } else {
                    obj = demo.newInstance();
                    //
                    Class<?>[] objClasses = new Class[]{obj.getClass()};
                    Iterator it = keySet.iterator();
                    while (it.hasNext()) {
                        Object key = it.next();
                        Integer cellNum_x = cellmap.get(param.getMap().get(key).toString());
                        if (cellNum_x == null || hssfRow.getCell(cellNum_x) == null) {
                            continue;
                        }
                        String attr = param.getMap().get(key).toString();// 得到属性

                        Class<?> attrType = BeanUtils.findPropertyType(attr, objClasses);

                        Cell cell = hssfRow.getCell(cellNum_x);
                        getValue(cell, obj, attr, attrType, rowNum, cellNum_x, key);

                    }
                    list.add(obj);
                }

            }
        }
        is.close();
        // wb.close();
        return (List<T>) list;
    }

    /**
     * setter:(反射的set方法给属性赋值)
     *
     * @param obj      具体的类
     * @param att      类的属性
     * @param value    赋予属性的值
     * @param type     属性是哪种类型 比如:String double boolean等类型
     * @param isString
     * @throws Exception
     * @author likaixuan
     * @since JDK 1.7
     */
    public static void setter(Object obj, String att, Object value, Class<?> type, int row, int col,
                              Object key, boolean isString) throws Exception {
        try {
            Object valueTemp = value;
            if (value != null && isString) {

                if (type == BigDecimal.class) {

                    valueTemp = new BigDecimal(value.toString());
                } else if (type == long.class || type == Long.class) {

                    valueTemp = Long.parseLong(value.toString());
                } else if (type == Double.class || type == double.class) {

                    valueTemp = Double.parseDouble(value.toString());
                } else if (type == Float.class || type == float.class) {

                    valueTemp = Float.parseFloat(value.toString());
                } else if (type == int.class || type == Integer.class) {

                    valueTemp = Integer.parseInt(value.toString());
                } else if (type == Short.class || type == short.class) {

                    valueTemp = Short.parseShort(value.toString());
                } else if (type == Date.class) {
                    SimpleDateFormat sdf = null;
                    // 可改用try循环
                    try {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                        valueTemp = sdf.parse(value.toString());
                    } catch (ParseException e) {
                        sdf = new SimpleDateFormat("yyyy年MM月dd日");
                        valueTemp = sdf.parse(value.toString());
                    }

                }
            }

            Method method = obj.getClass().getMethod("set" + StringUtil.toUpperCaseFirstOne(att), type);
            method.invoke(obj, valueTemp);
        } catch (Exception e) {
            throw new Exception("第" + (row + 1) + " 行  " + (col + 1) + "列   属性：" + key + " 赋值异常  " + e);
        }

    }

    /**
     * getAttrVal:(反射的get方法得到属性值)
     *
     * @param obj  具体的类
     * @param att  类的属性
     *             value 赋予属性的值
     * @param type 属性是哪种类型 比如:String double boolean等类型
     * @throws Exception
     * @author likaixuan
     * @since JDK 1.7
     */
    public static Object getAttrVal(Object obj, String att, Class<?> type) throws Exception {
        try {
            Method method = obj.getClass().getMethod("get" + StringUtil.toUpperCaseFirstOne(att));

            Object value = method.invoke(obj);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * getValue:(得到Excel列的值)
     *
     * @return
     * @throws Exception
     * @author likaixuan
     * @since JDK 1.7
     */
    public static void getValue(Cell cell, Object obj, String attr, Class attrType, int row, int col, Object key)
            throws Exception {
        Object val = null;
        // 标记该单元格类型是不是String
        boolean isString = true;
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            val = cell.getBooleanCellValue();
            isString = false;

        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            isString = false;
            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    if (attrType == String.class) {
                        val = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                    } else {
                        val = dateConvertFormat(sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())));
                    }
                } catch (ParseException e) {
                    throw new Exception("第" + (row + 1) + " 行  " + (col + 1) + "列   属性：" + key + " 日期格式转换错误  ");
                }
            } else {
                if (attrType == String.class) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    val = cell.getStringCellValue();
                } else if (attrType == BigDecimal.class) {
                    val = new BigDecimal(cell.getNumericCellValue());
                } else if (attrType == long.class || attrType == Long.class) {
                    val = (long) cell.getNumericCellValue();
                } else if (attrType == Double.class || attrType == double.class) {
                    val = cell.getNumericCellValue();
                } else if (attrType == Float.class || attrType == float.class) {
                    val = (float) cell.getNumericCellValue();
                } else if (attrType == int.class || attrType == Integer.class) {
                    val = (int) cell.getNumericCellValue();
                } else if (attrType == Short.class || attrType == short.class) {
                    val = (short) cell.getNumericCellValue();
                } else {
                    val = cell.getNumericCellValue();
                }
            }

        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            val = cell.getStringCellValue();
        }

        setter(obj, attr, val, attrType, row, col, key, isString);
    }







    /**
     * String类型日期转为Date类型
     *
     * @param dateStr
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date dateConvertFormat(String dateStr) throws ParseException {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = format.parse(dateStr);
        return date;
    }

}
