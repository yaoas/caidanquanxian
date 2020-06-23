package com.springboot.common.poiSecond;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * excel导出
 */
public class ExcelObject {

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;


    public ExcelObject() {
        this.workbook = new HSSFWorkbook();
        this. sheet = this.workbook.createSheet("sheet1");
    }

    public ExcelObject(String sheetName) {
        this.workbook = new HSSFWorkbook();
        this. sheet = this.workbook.createSheet(sheetName);
        sheet.setDefaultRowHeight((short) (2 * 256)); //设置默认行高，表示2个字符的高度
        sheet.setDefaultColumnWidth(17);    //设置默认列宽，实际上回多出2个字符，不知道为什么
    }

    /**
     * @description:设置头标题格式
     * @param:[workbook, sheet]
     * @return:org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    private  HSSFCellStyle getHeadTitleStyle(){
        //设置为居中加粗
        HSSFCellStyle style = this.workbook.createCellStyle();
        HSSFFont font = this.workbook.createFont();
        font.setFontHeightInPoints((short)24);
        font.setBold(true);
        style.setAlignment( HorizontalAlignment.CENTER);
        style.setFont(font);
        style.setWrapText(true);//设置自动换行
        return style;
    }
    /**
     * @description:设置行标题格式
     * @param:[workbook, sheet]
     * @return:org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    private  HSSFCellStyle getRowTitleStyle(){
        //设置为居中加粗
        HSSFCellStyle style = this.workbook.createCellStyle();
        HSSFFont font = this.workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBold(true);
        style.setAlignment( HorizontalAlignment.CENTER);
        style.setFont(font);
        style.setWrapText(true);//设置自动换行
        return style;
    }
    /**
     * @description:设置普通单元格格式
     * @param:[workbook, sheet]
     * @return:org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    private  HSSFCellStyle getNormalCellTitleStyle(){
        //设置为居中加粗
        HSSFCellStyle style = this.workbook.createCellStyle();
        HSSFFont font = this.workbook.createFont();
        font.setFontHeightInPoints((short)12);
        style.setAlignment( HorizontalAlignment.CENTER);
        style.setFont(font);
        style.setWrapText(true);//设置自动换行
        return style;
    }

    /**
     * @description:1-写入头标题
     * @param:[workbook, sheet, column, headTitle]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    public  void createHeadTile(int column, String headTitle){
        //创建行(默认第一行)
        HSSFRow row = sheet.createRow(0);
        //合并单元格
        int lastColIndex = column > 0 ?  (column-1):0;
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,lastColIndex));
        //单元格居中加粗格式
        HSSFCellStyle style = getHeadTitleStyle();
        //写入标题
        HSSFCell cell  = row.createCell(0);//获取当前行的第一列
        cell.setCellValue(headTitle);//写入数据
        cell.setCellStyle(style);//设置单元格格式
    }

    /**
     * @description:2-写入行标题
     * @param:[workbook, sheet, rowTitleList, firstRowIndex]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    public  void createRowTitle(List<String> rowTitleList,int firstRowIndex){
        //创建行
        HSSFRow row = sheet.createRow(firstRowIndex);
        //设置为居中加粗
        HSSFCellStyle style = getRowTitleStyle();
        HSSFCell cell;
        for(int i=0; i< rowTitleList.size(); i++){
            cell = row.createCell(i);//获取当前列
            cell.setCellValue(rowTitleList.get(i));//写入数据
            cell.setCellStyle(style);//设置单元格格式
        }
    }
    /**
     * @description:3-写入具体的单元格数据
     * @param:[firstRowIndex, lineDataList]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    public  void createDataByRow(int firstRowIndex,List<List<String>> lineDataList){
        //i代表行，j代表列
        HSSFCellStyle style = getNormalCellTitleStyle();
        for(int i=0; i< lineDataList.size(); i++){
            List<String> lineDatas = lineDataList.get(i);
            HSSFRow row = sheet.createRow(firstRowIndex + i);//行
            for(int j=0; j<lineDatas.size(); j ++){
                HSSFCell cell = row.createCell(j);//列
                cell.setCellValue(lineDatas.get(j));//写入数据
                cell.setCellStyle(style);//设置单元格格式
            }
        }
    }

    /**
     * @description:4-生成excel文件
     * @param:[filename, workbook]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    public void buildExcelFile(String filename) throws Exception{
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        File file = new File(filename);
        file.delete();
        fos.flush();
        fos.close();
    }

    /**
     * @description:5-浏览器下载excel
     * @param:[filename, workbook, response]
     * @return:void
     * @date:2019/5/11
     * @author:tangyj
     * @remark:
     * */
    public void buildExcelDocument(String filename,HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


}
