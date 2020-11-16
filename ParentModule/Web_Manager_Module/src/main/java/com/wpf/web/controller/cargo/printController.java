package com.wpf.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wpf.service.cargo.ContractService;
import com.wpf.vo.ContractProductVo;
import com.wpf.web.controller.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 创建时间：2020/11/15
 * SassExport项目-Web层-print控制器
 * 用于处理输出文件的相关请求
 * @author wpf
 */
@Controller
public class printController extends BaseController {

    @Reference
    ContractService contractService;

    /**
     * 打印出货表
     * @param inputDate 需要导出的合同船期
     */
    @RequestMapping("/cargo/contract/printExcel")
//    @ResponseBody //此注解可加可不加，主要用于不让页面跳转
    public void printProductTable(String inputDate) throws IOException {
        //查询表格中所需要的数据
        List<ContractProductVo> tableList = contractService.findTableVoByShipTime(inputDate);
        //利用Apache的POI组件创建表格
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //输出第一行内容
        Row row1 = sheet.createRow(0);
        //设置行高
        row1.setHeightInPoints(36);
        //合并单元格,四个参数分别代表合的所有单元格的起始行、结束行、起始列、结束列
        sheet.addMergedRegion(new CellRangeAddress(0 , 0, 1, 8));
        //设置单元格内容
        Cell cell = row1.createCell(1);
        String tableTitle = inputDate.replace("-0", "-").replace("-", "年")
                + "月份出货表";
        cell.setCellValue(tableTitle);
        //设置标题样式
        cell.setCellStyle(this.bigTitle(workbook));

        //输出第二行内容
        Row row2 = sheet.createRow(1);
        //设置列宽
        sheet.setColumnWidth(1, 256 * 27);
        sheet.setColumnWidth(2, 256 * 13);
        sheet.setColumnWidth(3, 256 * 30);
        sheet.setColumnWidth(4, 256 * 14);
        sheet.setColumnWidth(5, 256 * 16);
        sheet.setColumnWidth(6, 256 * 11);
        sheet.setColumnWidth(7, 256 * 11);
        sheet.setColumnWidth(8, 256 * 10);
        //设置单元格内容
        //直接插入内容的方法
//        Cell cell21 = row2.createCell(1);
//        Cell cell22 = row2.createCell(2);
//        Cell cell23 = row2.createCell(3);
//        Cell cell24 = row2.createCell(4);
//        Cell cell25 = row2.createCell(5);
//        Cell cell26 = row2.createCell(6);
//        Cell cell27 = row2.createCell(7);
//        Cell cell28 = row2.createCell(8);
//        cell21.setCellValue("客户");
//        cell22.setCellValue("订单号");
//        cell23.setCellValue("货号");
//        cell24.setCellValue("数量");
//        cell25.setCellValue("工厂");
//        cell26.setCellValue("工厂交期");
//        cell27.setCellValue("船期");
//        cell28.setCellValue("贸易条款");
//        cell21.setCellStyle(this.title(workbook));
//        cell22.setCellStyle(this.title(workbook));
//        cell23.setCellStyle(this.title(workbook));
//        cell24.setCellStyle(this.title(workbook));
//        cell25.setCellStyle(this.title(workbook));
//        cell26.setCellStyle(this.title(workbook));
//        cell27.setCellStyle(this.title(workbook));
//        cell28.setCellStyle(this.title(workbook));
        //遍历插入内容的方法
        String[] columnNames = {"客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        for (int i = 0; i < columnNames.length; i++) {
            Cell columnNameCell = row2.createCell(i + 1);
            columnNameCell.setCellValue(columnNames[i]);
            //设置表格属性行d的单元格样式
            columnNameCell.setCellStyle(this.title(workbook));
        }
        //设置行高
        row2.setHeightInPoints(26);

        //输出数据行
        if (tableList != null && tableList.size() > 0) {
            //遍历数据集合
            for (int i = 0; i < tableList.size(); i++) {
                ContractProductVo tableVo = tableList.get(i);
                //创建行
                Row row3 = sheet.createRow(i + 2);
                row3.setHeightInPoints(24);
                //创建单元格
                Cell cell31 = row3.createCell(1);
                Cell cell32 = row3.createCell(2);
                Cell cell33 = row3.createCell(3);
                Cell cell34 = row3.createCell(4);
                Cell cell35 = row3.createCell(5);
                Cell cell36 = row3.createCell(6);
                Cell cell37 = row3.createCell(7);
                Cell cell38 = row3.createCell(8);
                //写入第1个单元格
                cell31.setCellValue(tableVo.getCustomName() == null ? "" : tableVo.getCustomName());
                //写入第2个单元格
                cell32.setCellValue(tableVo.getContractNo() == null ? "" : tableVo.getContractNo());
                //写入第3个单元格
                cell33.setCellValue(tableVo.getProductNo() == null ? "" : tableVo.getProductNo());
                //写入第4个单元格
                cell34.setCellValue(tableVo.getCnumber() == null ? 0 : tableVo.getCnumber());
                //写入第5个单元格
                cell35.setCellValue(tableVo.getFactoryName() == null ? "" : tableVo.getFactoryName());
                //写入第6个单元格
                if (tableVo.getDeliveryPeriod() != null) {
                    cell36.setCellValue(tableVo.getDeliveryPeriod());
                } else {
                    cell36.setCellValue("");
                }
                //写入第7个单元格
                if (tableVo.getShipTime() != null) {
                    cell37.setCellValue(tableVo.getShipTime());
                } else {
                    cell37.setCellValue("");
                }
                //写入第8个单元格
                cell38.setCellValue(tableVo.getTradeTerms() == null ? "" : tableVo.getTradeTerms());
                //设置样式
                cell31.setCellStyle(this.text(workbook));
                cell32.setCellStyle(this.text(workbook));
                cell33.setCellStyle(this.text(workbook));
                cell34.setCellStyle(this.text(workbook));
                cell35.setCellStyle(this.text(workbook));
                cell36.setCellStyle(this.text(workbook));
                cell37.setCellStyle(this.text(workbook));
                cell38.setCellStyle(this.text(workbook));
            }
        }

        //输出内存中的表格数据到磁盘
        //设置相应头
        response.setHeader("content-disposition", "attachment;fileName=export.xlsx");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        //释放资源
        workbook.close();
        out.close();
    }

    /**
     * 按模板打印出货表，可以减少设置样式的代码
     * @param inputDate 需要导出的合同船期
     */
    //@RequestMapping("/cargo/contract/printExcel")
    public void printProductTableByTemplate(String inputDate) throws IOException {
        //读取模板文件
        Workbook workbook = new XSSFWorkbook(
                request.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx"));
        //获取工作表
        Sheet sheet = workbook.getSheetAt(0);
        //设置第一行数据
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        String tableTitle = inputDate.replace("-0", "-").replace("-", "年")
                + "月份出货表";
        cell.setCellValue(tableTitle);

        //获取数据行的样式
        CellStyle[] columnNameStyles = new CellStyle[8];
        for (int i = 0; i < columnNameStyles.length; i++) {
            row = sheet.getRow(2);
            cell = row.getCell(i + 1);
            columnNameStyles[i] = cell.getCellStyle();
        }

        //输出数据行
        List<ContractProductVo> tableList = contractService.findTableVoByShipTime(inputDate);
        if (tableList != null && tableList.size() > 0) {
            int index = 2;
            for (ContractProductVo tableVo : tableList) {
                //获取对应行
                row = sheet.createRow(index);
                //写入单元格的数据并设置样式
                cell = row.createCell(1);
                cell.setCellValue(tableVo.getCustomName() == null ? "" : tableVo.getCustomName());
                cell.setCellStyle(columnNameStyles[0]);
                cell = row.createCell(2);
                cell.setCellValue(tableVo.getContractNo() == null ? "" : tableVo.getContractNo());
                cell.setCellStyle(columnNameStyles[1]);
                cell = row.createCell(3);
                cell.setCellValue(tableVo.getProductNo() == null ? "" : tableVo.getProductNo());
                cell.setCellStyle(columnNameStyles[2]);
                cell = row.createCell(4);
                cell.setCellValue(tableVo.getCnumber() == null ? 0 : tableVo.getCnumber());
                cell.setCellStyle(columnNameStyles[3]);
                cell = row.createCell(5);
                cell.setCellValue(tableVo.getFactoryName() == null ? "" : tableVo.getFactoryName());
                cell.setCellStyle(columnNameStyles[4]);
                cell = row.createCell(6);
                if (tableVo.getDeliveryPeriod() != null) {
                    cell.setCellValue(tableVo.getDeliveryPeriod());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnNameStyles[5]);
                cell = row.createCell(7);
                if (tableVo.getShipTime() != null) {
                    cell.setCellValue(tableVo.getShipTime());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnNameStyles[6]);
                cell = row.createCell(8);
                cell.setCellValue(tableVo.getTradeTerms() == null ? "" : tableVo.getTradeTerms());
                cell.setCellStyle(columnNameStyles[7]);
                //索引+1
                index++;
            }
        }

        //将表格输出到磁盘
        //设置响应头
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        //输出到响应的文件流
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        //释放资源
        out.close();
        workbook.close();
    }

    /**
     * 模拟百万级数据导出
     * @param inputDate 需要导出的合同船期
     */
    //@RequestMapping("/cargo/contract/printExcel")
    public void printProductTableAsBigData(String inputDate) throws IOException {
        //查询表格中所需要的数据
        List<ContractProductVo> tableList = contractService.findTableVoByShipTime(inputDate);
        //利用Apache的POI组件创建表格，需要使用到SXSSFWorkbook类
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //输出第一行内容
        Row row1 = sheet.createRow(0);
        //设置行高
        row1.setHeightInPoints(36);
        //合并单元格,四个参数分别代表合的所有单元格的起始行、结束行、起始列、结束列
        sheet.addMergedRegion(new CellRangeAddress(0 , 0, 1, 8));
        //设置单元格内容
        Cell cell = row1.createCell(1);
        String tableTitle = inputDate.replace("-0", "-").replace("-", "年")
                + "月份出货表";
        cell.setCellValue(tableTitle);

        cell.setCellStyle(this.bigTitle(workbook));

        //输出第二行内容
        Row row2 = sheet.createRow(1);
        //设置列宽
        sheet.setColumnWidth(1, 256 * 27);
        sheet.setColumnWidth(2, 256 * 13);
        sheet.setColumnWidth(3, 256 * 30);
        sheet.setColumnWidth(4, 256 * 14);
        sheet.setColumnWidth(5, 256 * 16);
        sheet.setColumnWidth(6, 256 * 11);
        sheet.setColumnWidth(7, 256 * 11);
        sheet.setColumnWidth(8, 256 * 10);

        //遍历插入内容的方法
        String[] columnNames = {"客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        for (int i = 0; i < columnNames.length; i++) {
            Cell columnNameCell = row2.createCell(i + 1);
            columnNameCell.setCellValue(columnNames[i]);
            //设置表格属性行d的单元格样式
            columnNameCell.setCellStyle(this.title(workbook));
        }
        //设置行高
        row2.setHeightInPoints(26);

        //输出数据行
        if (tableList != null && tableList.size() > 0) {
            //遍历数据集合
            for (int i = 0; i < tableList.size(); i++) {
                ContractProductVo tableVo = tableList.get(i);
                for (int j = 0; j < 6000; j++) { //使用循环模拟百万级数据

                    //创建行
                    Row row3 = sheet.createRow(i * 6000 + j + 2);
                    row3.setHeightInPoints(24);
                    //创建单元格
                    Cell cell31 = row3.createCell(1);
                    Cell cell32 = row3.createCell(2);
                    Cell cell33 = row3.createCell(3);
                    Cell cell34 = row3.createCell(4);
                    Cell cell35 = row3.createCell(5);
                    Cell cell36 = row3.createCell(6);
                    Cell cell37 = row3.createCell(7);
                    Cell cell38 = row3.createCell(8);
                    //写入第1个单元格
                    cell31.setCellValue(tableVo.getCustomName() == null ? "" : tableVo.getCustomName());
                    //写入第2个单元格
                    cell32.setCellValue(tableVo.getContractNo() == null ? "" : tableVo.getContractNo());
                    //写入第3个单元格
                    cell33.setCellValue(tableVo.getProductNo() == null ? "" : tableVo.getProductNo());
                    //写入第4个单元格
                    cell34.setCellValue(tableVo.getCnumber() == null ? 0 : tableVo.getCnumber());
                    //写入第5个单元格
                    cell35.setCellValue(tableVo.getFactoryName() == null ? "" : tableVo.getFactoryName());
                    //写入第6个单元格
                    if (tableVo.getDeliveryPeriod() != null) {
                        cell36.setCellValue(tableVo.getDeliveryPeriod());
                    } else {
                        cell36.setCellValue("");
                    }
                    //写入第7个单元格
                    if (tableVo.getShipTime() != null) {
                        cell37.setCellValue(tableVo.getShipTime());
                    } else {
                        cell37.setCellValue("");
                    }
                    //写入第8个单元格
                    cell38.setCellValue(tableVo.getTradeTerms() == null ? "" : tableVo.getTradeTerms());
                    //不能设置样式，因为SXSSFWorkbook中，样式最多只能设置64000行
                }
            }
        }

        //输出内存中的表格数据到磁盘
        //设置相应头
        response.setHeader("content-disposition", "attachment;fileName=export.xlsx");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        //释放资源
        workbook.close();
        out.close();
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }



}
