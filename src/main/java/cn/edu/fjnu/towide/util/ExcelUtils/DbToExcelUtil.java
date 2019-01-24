package cn.edu.fjnu.towide.util.ExcelUtils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据库表导出到excel表
 *
 * 使用说明
 * 1.获取表中信息，设置excel存放路径
 *      String fileName = "D://book.xls";
 * 2.设置一个list<String>存放，存放标题和对应的属性名
 *      String[] titles = {"身份证号id", "用户名username", "密码password", "电子邮件email", "手机号phone",
 *           "问题question", "答案answer", "权限role", "创建时间createTime", "更新时间updateTime"};
 *       List<String> titleList = Arrays.asList(titles);
 *
 * 3. 创建对象，传入对象
 *      DbToExcelUtil<StuEntity> dbExcelUtil = new DbToExcelUtil<StuEntity>();
 *      dbExcelUtil.dbToExcel(fileName, stulist, titleList, StuEntity.class);
 *
 * @param <T>
 */
public class DbToExcelUtil<T> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private DbToExcelUtil(){

    }
    // 基于枚举的单例模式
    private enum Singleton{
        INSTANCE;

        private DbToExcelUtil singleton;

        Singleton(){
            singleton = new DbToExcelUtil();
        }

        public DbToExcelUtil getSingleton() {
            return singleton;
        }
    }

    public static DbToExcelUtil getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    /**
     * 导出到excel
     *
     * @param fileName      文件路径
     * @param dataList      导出数据
     * @param firstRowTitle 第一行标题，按数据库存放顺序
     * @param className     实体对象class
     * @throws Exception 通过捕获该异常，判断是否成功
     */
    public void dbToExcel(String fileName, List<T> dataList, List<String> firstRowTitle, Class className)
            throws Exception {

        int rowIndex = 0;
        CellStyle cellStyle;
        // 创建excel
        Workbook book = new HSSFWorkbook();
        // sheet名
        String sheetName = "sheet1";
        // 获取属性
        Field[] fields = className.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);


        Sheet sheet = book.createSheet(sheetName);

        // 生成第一行
        Row row = sheet.createRow(rowIndex++);

        // 给第一行赋值
        cellStyle = getCellStyle(book, (short) 240, (short) 600);
        createFirstRowTitle(sheet, firstRowTitle, row, cellStyle);
        // 创建数据
        cellStyle = getCellStyle(book, (short) 220, (short) 500);
        insertData(sheet, fieldList, dataList, firstRowTitle, rowIndex, cellStyle);
        rowIndex++;
        // 格式化标题
        formatTitle(fieldList, row, firstRowTitle);


        File file = new File(fileName);
        if (file.exists())
            file.delete();
        FileOutputStream fos = new FileOutputStream(file);

        book.write(fos);
        fos.close();


    }

    /**
     * 格式化标题行，去除包含的属性名
     *
     * @param fieldList     属性名列表
     * @param row           标题行
     * @param firstRowTitle 标题列表
     */
    private void formatTitle(List<Field> fieldList, Row row, List<String> firstRowTitle) {
        int index = 0;
        int titleCount = firstRowTitle.size();
        int fieldNum = fieldList.size();
        // 标记该标题在属性中是否存在
        boolean[] marked = new boolean[titleCount];
        for (int i = 0; i < titleCount; i++) {
            marked[i] = false;
        }

//        List<String> fieldName = fieldList.stream().map(e -> e.getName()).collect(Collectors.toList());

        // 用表格中的title来匹配类的属性名，匹配成功则将title中包含的属性名替换替换为空
        for (int j = 0; j < titleCount; j++) {
            String cellValue = row.getCell(j).getStringCellValue();
            for (int i = 0; i < fieldNum; i++) {
                String reg = fieldList.get(i).getName();

                if (cellValue.contains(reg)) {
                    cellValue = cellValue.replaceAll(reg, "");
                    row.getCell(j).setCellValue(cellValue);
                    marked[j] = true;
                    break;
                }
            }
        }

//        for (Field field : fieldList) {
//            // 用表格中的title匹配类的属性，匹配成功则将title中属性替换替换为空
//            for (int i = 0; i < titleCount; i++) {
//                String reg = field.getName();
//                String cellValue = row.getCell(index).getStringCellValue();
//                if (cellValue.contains(reg)) {
//                    cellValue = cellValue.replaceAll(reg, "");
//                    row.getCell(index).setCellValue(cellValue);
//                    marked[index] = true;
//                    index = (index + 1) % titleCount;
//                    break;
//                }
//                index = (index + 1) % titleCount;
//            }
//        }

        for (int i = 0; i < titleCount; i++) {
            if (marked[i] == false) {
                row.getCell(i).setCellValue("-");
            }
        }
    }

    /**
     * 创建标题行
     *
     * @param firstRowTitle 标题列表
     * @param row           标题行（第一行）
     * @param cellStyle     单元格格式
     */
    private void createFirstRowTitle(Sheet sheet, List<String> firstRowTitle, Row row, CellStyle cellStyle) {
        for (int i = 0; i < firstRowTitle.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(firstRowTitle.get(i));
            sheet.setColumnWidth(i, 5 * 2 * 512);
        }
    }

    /**
     * 向sheet插入数据
     *
     * @param sheet         sheet页
     * @param fieldList     实体类属性名
     * @param dataList      具体数据
     * @param firstRowTitle 第一行标题名称
     * @param rowIndex      行数计数
     * @param cellStyle     样式
     */
    private void insertData(Sheet sheet, List<Field> fieldList, List<T> dataList,
                            List<String> firstRowTitle, int rowIndex, CellStyle cellStyle) throws Exception {

        // 属性个数
        int fieldCount = fieldList.size();

        Row hRow;
        Cell hCell;

        // 获得属性在excel表中对应的位置（优化）
        Map<String, Integer> fieldPlace = getFieldPlace(fieldList, firstRowTitle);

        for (T data : dataList) {
            hRow = sheet.createRow(rowIndex++);
            // 获取该类
            Class clazz = data.getClass();
            for (int i = 0; i < fieldCount; i++) {
                // 属性名
                Field field = fieldList.get(i);
                String fieldName = field.getName();
                // 构造方法名，获取方法
                //String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method method = clazz.getMethod(methodName);
                // 执行方法
                Object result = method.invoke(data);


                // 匹配成功
                int matchPlace = fieldPlace.get(fieldName);
                if (matchPlace != -1) {

                    // 在匹配成功位置创建一个单元格
                    hCell = hRow.createCell((short) matchPlace);
                    if (result == null)
                        hCell.setCellValue(new HSSFRichTextString("-"));
                    else {
                        // 是否是时间类型
                        if (result.getClass().isAssignableFrom(Date.class)) {
                            result = this.simpleDateFormat.format(result);
                        }
                        // 向单元格设置数据
                        hCell.setCellValue(new HSSFRichTextString(result.toString()));

                    }
                    // 设置单元格样式
                    hCell.setCellStyle(cellStyle);
                }

            }
        }
    }

    /**
     * 查找属性在excel表中对应的位置并返回
     *
     * @param fieldList     属性名列表
     * @param firstRowTitle 标题列表
     * @return 存有属性名对应位置的map
     */
    private Map<String, Integer> getFieldPlace(List<Field> fieldList, List<String> firstRowTitle) {
        Map<String, Integer> fieldPlace = new HashMap<>();
        // excel列数
        int columnSize = firstRowTitle.size();
        // 属性个数
        int fieldCount = fieldList.size();
        // 标记标题行遍历到的位置
        int firstRowPlace = 0;
        // 标记属性是否找到对应excel列
        boolean isMatch = false;

        for (int i = 0; i < fieldCount; i++) {
            // 属性全名
            Field field = fieldList.get(i);

            int count = 0;
            while (count < columnSize) {
                // 判断是否有标题名与属性名匹配
                if (firstRowTitle.get(firstRowPlace).contains(field.getName())) {
                    fieldPlace.put(field.getName(), firstRowPlace);
                    break;
                }
                firstRowPlace = (firstRowPlace + 1) % columnSize;
                count++;
            }

            // 未匹配成功
            if (count >= columnSize)
                fieldPlace.put(field.getName(), -1);

        }


        return fieldPlace;
    }

    /**
     * 功能 :设置excel表格默认样式
     *
     * @param workbook   需导出Excel数据
     * @param fontHeight 字体粗度
     * @param boldWeight 表格线的粗度
     * @return
     */
    public CellStyle getCellStyle(Workbook workbook, short fontHeight, short boldWeight) {
        CellStyle cellStyle;
        Font font;
        cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        font = workbook.createFont();
        font.setFontHeight(fontHeight);
        font.setBoldweight(boldWeight);
        font.setFontName("宋体");
        cellStyle.setFont(font);
//        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

}
