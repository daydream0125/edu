import com.alibaba.fastjson.JSON;
import com.edu.dao.CourseDAO;
import com.edu.model.Course;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppTest {
    @Test
    public void test() {
        System.out.println(new Date());
    }

    @Test
    public void testReadByExtractor() throws Exception {
        InputStream is = new FileInputStream("/Users/hanhao0125/Desktop/test.doc");
        WordExtractor extractor = new WordExtractor(is);
        //输出word文档所有的文本
        String s = extractor.getText();
        //System.out.println(extractor.getTextFromPieces());
        String[] a = s.split("(\n\r)+");
        int i = 1;
        for (String z : a) {
            System.out.println(z);
            i++;
        }
    }

    @Test
    public  void importExcel() throws IOException {
        FileInputStream in = null;
        List<String> result = null;
        String file = "/Users/hanhao0125/Desktop/工作簿1.xlsx";
        try {
            in = new FileInputStream(file);
            result = new ArrayList<String>();
            Workbook wb = new XSSFWorkbook(in);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
//                if (row.getRowNum() < 1) {
//                    continue;
//                }
                result.add(row.getCell(0).getStringCellValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        for (String s: result) {
            System.out.println(s);
        }
    }

    @Test
    public void testTime() {
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }


    @Test
    public void testLog() {
        final Logger logger = LoggerFactory.getLogger(AppTest.class);
        logger.warn("fuck you");
        logger.error("abc");
    }

    @Test
    public void testJson() {
        System.out.println(JSON.toJSONString("123"));
    }




}
