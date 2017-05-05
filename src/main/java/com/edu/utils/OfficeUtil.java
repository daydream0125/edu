package com.edu.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OfficeUtil {

    //suffix 为后缀类型,POI-HSSF处理97~2007版本的xls,XSSF处理07之后的xlsx
    public static List<String> readExcel(InputStream inputStream, String suffix) throws IOException {
        List<String> list = new ArrayList<>();
        Workbook workbook = suffix.equals("xls") ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            list.add(row.getCell(0).getStringCellValue());
        }
        return list;
    }
}
