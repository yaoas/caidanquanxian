package com.springboot.common.excelComomon;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {

    //excel表对象
    private Workbook workbook;
    //工作表对象
    private Sheet sheet;
    //标题
    private String title;
    //sheet各个列的表头
    private String[] headList;
    //各个列的元素key值
    private String[] headKey;
    //sheet需要填充的数据信息
    private List<Map> data;
    //工作表名称
//    private String sheetName = "sheet1";
    //工作表列宽
    private Integer columnWidth=20;
    //工作表行高
    private Integer rowHeight=10;
    //字体大小
    private int fontSize = 14;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Integer getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Integer rowHeight) {
        this.rowHeight = rowHeight;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public String[] getHeadKey() {
        return headKey;
    }

    public void setHeadKey(String[] headKey) {
        this.headKey = headKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getHeadList() {
        return headList;
    }

    public void setHeadList(String[] headList) {
        this.headList = headList;
    }

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }





    /**
     * @return *
     * @Author
     * @Description //TODO 插入数据到表格（body）
     * @Date 2019/7/26 17:28
     * @Param startRow: 开始插入数据的行
     */
    public void writeMainData(Integer startRow) throws IOException {
        //设置每格数据的样式 （字体红色）
        CellStyle fontRed = ExcelStyleUtilFor2003.getFontStyle(this.workbook, this.fontSize, HSSFColor.RED.index);
        //设置每格数据的样式2（字体蓝色）
        CellStyle fontBlue = ExcelStyleUtilFor2003.getFontStyle(this.workbook, this.fontSize, HSSFColor.BLUE.index);
        //设置body样式
        CellStyle bodyStyle = ExcelStyleUtilFor2003.getBodyStyle(this.workbook, this.fontSize);

        //开始写入实体数据信息
        if (data.size() > 0 && null != data.get(0)) {
            for (int i = 0; i < data.size(); i++) {
                Row row = this.sheet.createRow(startRow);
                Map map = data.get(i);
                map.put("index",i);
                map.put("des","无");
                for (int j = 0; j < headKey.length; j++) {
                    Cell cell = row.createCell(j);
                    //设置单个单元格的字体颜色
                    if (headKey[j].equals("ddNum") || headKey[j].equals("sjNum")) {
                        if (null != map.get("ddNum") && Integer.parseInt(map.get("ddNum").toString()) != 0) {
                            if (null == map.get("sjNum") || Integer.parseInt(map.get("sjNum").toString()) == 0) {
                                cell.setCellStyle(fontRed);
                            } else if (Integer.parseInt(map.get("ddNum").toString()) != Integer.parseInt(map.get("sjNum").toString())) {
                                if (Integer.parseInt((String) map.get("ddNum")) > Integer.parseInt((String) map.get("sjNum"))) {
                                    cell.setCellStyle(fontRed);
                                } else {
                                    cell.setCellStyle(fontBlue);
                                }
                            } else {
                                cell.setCellStyle(bodyStyle);
                            }
                        } else {
                            if (Integer.parseInt(map.get("ddNum").toString()) < Integer.parseInt(map.get("sjNum").toString())) {
                                cell.setCellStyle(fontBlue);
                            } else {
                                cell.setCellStyle(bodyStyle);
                            }
                        }
                        cell.setCellValue(Integer.parseInt(map.get(headKey[j]).toString()));
                    } else {
                        Object value = map.get(headKey[j]);
                        if (null == value) {
                            String valueN = "";
                            cell.setCellValue(valueN);
                        } else if (value instanceof Integer) {
                            Integer valueInt = Integer.valueOf(value.toString());
                            cell.setCellValue(valueInt);
                        } else if (value instanceof String) {
                            String valueStr = String.valueOf(value);
                            cell.setCellValue(valueStr);
                        }
                    }
                }
                startRow++;
            }
        }


    }


    /**
     * @return * @param null
     * @Author
     * @Description //TODO 添加表格标题
     * @Date 2019/7/26 17:58
     * @Param
     */
    public void writeTitle() throws IOException {
        checkConfig();
        //设置默认行宽
        this.sheet.setDefaultColumnWidth(20);
        //在第0行创建rows  (表标题)
        Row title = this.sheet.createRow(0);
        title.setHeightInPoints(this.rowHeight);//行高
        Cell cell = title.createCell(0);
        cell.setCellValue(this.title);
        CellStyle cellStyle = ExcelStyleUtilFor2003.getTitleStyle(this.workbook,true,16);
        cell.setCellStyle(cellStyle);
        ExcelStyleUtilFor2003.mergeCell(sheet,0,0,0, (this.headList.length - 1));
    }


    /**
     * @Author
     * @Description //TODO 添加表头
     * @Date 2019/7/26 15:41
     * @Param headRowNum: 添加表头所在行数
     * @return  *
     */

    public void writeTableHead(String[] head, CellStyle cellStyle, Integer headRowNum) {
        Row row = this.sheet.createRow(headRowNum);
        if (head.length > 0) {
            for (int i = 0; i < head.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(head[i]);
                cell.setCellStyle(cellStyle);
            }
        }
    }


    /**
     * 检查数据配置问题
     *
     * @throws IOException 抛出数据异常类
     */
    protected void checkConfig() throws IOException {
        if (headKey == null || headList.length == 0) {
            throw new IOException("列名数组不能为空或者为NULL");
        }
        if (fontSize < 0) {
            throw new IOException("字体不能为负值");
        }
    }

    /**
     * @Author
     * @Description //TODO 写入拼接好的数据，不需要通过表头key值来对应
     * @Date 2019/7/27 11:01
     * @Param
     * @return  * @param null
     */
    public void writeMainData(List<List<String>> datas,Integer startRow){
        if(datas.size()>0){
            for(List<String> data : datas){
                Row row = this.sheet.createRow(startRow);
                for(int i =0; i<data.size(); i++){
                    Cell cell = row.createCell(i);
                    cell.setCellValue(data.get(i));
                    CellStyle cellStyle = ExcelStyleUtilFor2003.setCellBorder(this.workbook);
                    cell.setCellStyle(cellStyle);
                }
                startRow++;
            }
        }
    }



    public static void setResponseInfo(HttpServletResponse response, Workbook wb) throws IOException {
        //导出数据
        try {
            //设置Http响应头告诉浏览器下载这个附件
            response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + ".xls");
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IOException("导出Excel出现严重异常，异常信息：" + ex.getMessage());
        }
    }

}
