//package com.huangjicheng.top.vepjob.jobhandler;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.text.SimpleDateFormat;
//
///**
// * 读取excle，包括合并单元格
// */
//public class DealExcle  {
//
//
//
//    public static void main(String[] args) {
//        getAllByExcel("D:\\Desktop\\test.xlsx");
//
//    }
//    public static void getAllByExcel(String filepath) {
//        try {
//            // 同时支持Excel 2003、2007
//            File excelFile = new File(filepath); // 创建文件对象
//            FileInputStream is = new FileInputStream(excelFile); // 文件流
//            Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
//
//            String[] res = readExcel(workbook, 0, 0, 0);
//            for (int i = 0; i < res.length; i++) {
//                System.out.println(res[i]);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static String[] readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
//        Sheet sheet = wb.getSheetAt(sheetIndex);
//        Row row = null;
//        String[] res = new String[sheet.getLastRowNum() - tailLine + 1];
//
//        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
//
//            row = sheet.getRow(i);
//            res[i] = "";
//            for (Cell c : row) {
//                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
//                // 判断是否具有合并单元格
//                if (isMerge) {
//                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
//                    //System.out.print(rs + "_"+ row.getRowNum()+"_"+c.getColumnIndex() +"_");
//                    res[i] += rs+ " ";
//                } else {
//                    //System.out.print(c.getRichStringCellValue() + "");
//                    res[i] += getCellValue(c)+ " ";
//                }
//            }
//            //System.out.println();
//        }
//
//        if(startReadLine > 0){
//            String[] result = new String[res.length - startReadLine];
//            for (int i = 0; i < startReadLine; i++) {
//                for (int j = 0; j < res.length; j++) {
//                    if(j == res.length - 1)
//                        continue;
//                    res[j] = res[j+1];
//                }
//            }
//            for (int i = 0; i < result.length; i++) {
//                result[i] = res[i];
//            }
//            return result;
//        }else{
//            return res;
//        }
//    }
//
//    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        for (int i = 0; i < sheetMergeCount; i++) {
//            CellRangeAddress range = sheet.getMergedRegion(i);
//            int firstColumn = range.getFirstColumn();
//            int lastColumn = range.getLastColumn();
//            int firstRow = range.getFirstRow();
//            int lastRow = range.getLastRow();
//            if (row >= firstRow && row <= lastRow) {
//                if (column >= firstColumn && column <= lastColumn) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
//        int sheetMergeCount = sheet.getNumMergedRegions();
//
//        for (int i = 0; i < sheetMergeCount; i++) {
//            CellRangeAddress ca = sheet.getMergedRegion(i);
//            int firstColumn = ca.getFirstColumn();
//            int lastColumn = ca.getLastColumn();
//            int firstRow = ca.getFirstRow();
//            int lastRow = ca.getLastRow();
//
//            if (row >= firstRow && row <= lastRow) {
//
//                if (column >= firstColumn && column <= lastColumn) {
//                    Row fRow = sheet.getRow(firstRow);
//                    Cell fCell = fRow.getCell(firstColumn);
//                    return getCellValue(fCell);
//                }
//            }
//        }
//
//        return null;
//    }
//
//    private static String getCellValue(Cell cell) {
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//        String cellValue = "";
//        int cellType = cell.getCellType();
//        switch (cellType) {
//            case Cell.CELL_TYPE_STRING: // 文本
//                cellValue = cell.getStringCellValue();
//                break;
//            case Cell.CELL_TYPE_NUMERIC: // 数字、日期
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    cellValue = fmt.format(cell.getDateCellValue()); // 日期型
//                } else {
//                    cellValue = String.valueOf((int) cell.getNumericCellValue()); // 数字
//                }
//                break;
//            case Cell.CELL_TYPE_BOOLEAN: // 布尔型
//                cellValue = String.valueOf(cell.getBooleanCellValue());
//                break;
//            case Cell.CELL_TYPE_BLANK: // 空白
//                cellValue = cell.getStringCellValue();
//                break;
//            case Cell.CELL_TYPE_ERROR: // 错误
//                cellValue = "错误";
//                break;
//            case Cell.CELL_TYPE_FORMULA: // 公式
//                cellValue = "错误";
//                break;
//            default:
//                cellValue = "错误";
//        }
//        return cellValue;
//    }
//
//}
