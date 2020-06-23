package com.springboot.common.excelComomon;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ExportExcel {
    public static void excelExport(int colunmNum, String headTitle, List<String> headTitleList, List<List<String>> dataList, String fileName, HttpServletResponse response){
        //1-创建一个HSSFWorkbook
        ExcelObject excel = new ExcelObject("系统日志");
        //2-写入头标题
        excel.createHeadTile(colunmNum, headTitle);//头标默认写在第一行
        //3-写入行标题
        excel.createRowTitle(headTitleList, 1);
        //4-写入具体数据
        excel.createDataByRow(2, dataList);//因为没有行标题，所以从第二行开始
        //5-生成excel文件
        //根据情况决定生成不生成文件
        // excel.buildExcelFile(fileName);
        //6-浏览器下载excel
        try {
            excel.buildExcelDocument(fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
