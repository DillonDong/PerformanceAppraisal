package cn.edu.fjnu.towide.util;

import cn.edu.fjnu.towide.util.ExcelUtils.ExcelTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by willim on 2018/8/1.
 */
public class ToExcelUtil<T> {

    private ToExcelUtil() {
    }

    // 基于枚举的单例模式
    private enum Singleton {
        INSTANCE;

        @SuppressWarnings("rawtypes")
		private ToExcelUtil singleton;

        @SuppressWarnings("rawtypes")
		Singleton() {
            singleton = new ToExcelUtil();
        }

        @SuppressWarnings("rawtypes")
		public ToExcelUtil getSingleton() {
            return singleton;
        }
    }

    @SuppressWarnings("rawtypes")
	public static ToExcelUtil getInstance() {
        return Singleton.INSTANCE.getSingleton();
    }

    public void toExcel(String filePath, List<String> columnList, List<Map<String, String>> dataListMap)
            throws Exception {

        int rowIndex = 0;
        CellStyle cellStyle;
        // 创建excel
        Workbook book = new HSSFWorkbook();
        // sheet名
        String sheetName = "sheet1";
        Sheet sheet = book.createSheet(sheetName);
        // 生成第一行
        Row row = sheet.createRow(rowIndex++);
        // 获取title
        List<String> firstRowTitle = columnList;

        // 给第一行赋值
        cellStyle = getCellStyle(book, (short) 240, (short) 600);
        createFirstRowTitle(sheet, firstRowTitle, row, cellStyle);
        // 创建数据
        cellStyle = getCellStyle(book, (short) 220, (short) 500);
        insertData(sheet, dataListMap, firstRowTitle, rowIndex, cellStyle);
        rowIndex++;

        File file = new File(filePath);
        if (file.exists())
            file.delete();
        FileOutputStream fos = new FileOutputStream(file);

        book.write(fos);
        fos.close();
    }


    @SuppressWarnings("resource")
	public List<String> getExcelRow(String filePath) throws Exception {

        List<String> rowData = new ArrayList<>();

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
        
        Sheet hssfSheet = wb.getSheetAt(startSheetNum);
        Row hssfRow = hssfSheet.getRow(0);
        if (hssfRow == null) {
            return null;
        }
        boolean flag = false;
        for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
            if (hssfRow.getCell(i) != null && !("").equals(hssfRow.getCell(i).toString().trim())) {
                flag = true;
            }
        }
        if (!flag) {
            return null;
        }
        for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {

            Cell hssfCell = hssfRow.getCell(cellNum);
            if (hssfCell == null) {
                continue;
            }

            String tempCellValue = hssfSheet.getRow(0).getCell(cellNum).getStringCellValue();

            tempCellValue = StringUtils.remove(tempCellValue, (char) 160);
            tempCellValue = tempCellValue.trim();

            rowData.add(tempCellValue);


        }
        is.close();
        return rowData;
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
     * @param dataListMap   具体数据
     * @param sheet         sheet页
     * @param dataListMap
     * @param firstRowTitle 第一行标题名称
     * @param rowIndex      行数计数
     * @param cellStyle     样式
     */
    private void insertData(Sheet sheet, List<Map<String, String>> dataListMap, List<String> firstRowTitle, int rowIndex, CellStyle cellStyle)
            throws Exception {

        // 列数
        int colCount = firstRowTitle.size();

        Row hRow;
        Cell hCell;

        for (Map<String, String> data : dataListMap) {
        	if (data == null) {
				continue;
			}
            hRow = sheet.createRow(rowIndex++);
            for (int i = 0; i < colCount; i++) {

                // 创建一个单元格
                hCell = hRow.createCell((short) i);
                String value = data.get(firstRowTitle.get(i));
                if (value == null)
                    hCell.setCellValue(new HSSFRichTextString("-"));
                else {
                    // 向单元格设置数据
                    hCell.setCellValue(new HSSFRichTextString(value));

                }
                // 设置单元格样式
                hCell.setCellStyle(cellStyle);
            }
        }
    }


    /**
     * 功能 :设置excel表格默认样式
     *
     * @param workbook   需导出Excel数据
     * @param fontHeight 字体粗度
     * @param boldWeight 表格线的粗度
     * @return
     */
    @SuppressWarnings("deprecation")
	private CellStyle getCellStyle(Workbook workbook, short fontHeight, short boldWeight) {
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
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }
}
