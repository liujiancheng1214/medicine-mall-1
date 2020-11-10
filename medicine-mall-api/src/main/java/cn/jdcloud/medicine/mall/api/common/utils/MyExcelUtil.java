package cn.jdcloud.medicine.mall.api.common.utils;

import cn.jdcloud.framework.utils.StringUtils;
import cn.jdcloud.medicine.mall.api.annotation.ExcelHead;
import cn.jdcloud.medicine.mall.api.common.exception.CustomException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel导出工具类，
 *
 * @param <T>
 * @author HXN
 */
public class MyExcelUtil<T> {

    /**
     * 导出数据
     *
     * @param out     输出流
     * @param dataset 数据集合
     * @throws Exception
     */
    public void exportExcel(OutputStream out, List<T> dataset,Map<String,Map> map) throws Exception {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        CellStyle style2 = workbook.createCellStyle();
        //创建表头
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 11);//设置字体大小
        style.setFont(font);//选择需要用到的字体格式

        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style2.cloneStyleFrom(style);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        if (dataset == null || dataset.size() == 0){ return;}
        T tempT = dataset.get(0);
        Class<?> tCls = tempT.getClass();
        Field[] heads = tCls.getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        List<String> headList = new ArrayList<>();
        //获取字段注解的表头
        for (int i = 0; i < heads.length; i++) {
            ExcelHead excelHead = heads[i].getAnnotation(ExcelHead.class);
            if (excelHead.isExport()) {
                String head = excelHead.value();
                headList.add(head);
                fields.add(heads[i]);
            }
        }
        int size = headList.size();
        Map<Integer,Integer> maxWidth = new HashMap<>(size);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 480);
        for (int i = 0; i < size; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            String head = headList.get(i);
            HSSFRichTextString text = new HSSFRichTextString(head);
            cell.setCellValue(text);
            maxWidth.put(i, head.getBytes().length * 256);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            row.setHeight((short) 480);
            T t = it.next();
            int cIndex=0;
            for (Field field : fields) {
                HSSFCell cell = row.createCell(cIndex);
                cell.setCellStyle(style2);
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), tCls);
                Method get = pd.getReadMethod();
                Object value = get.invoke(t, new Object[]{});
                // 判断值的类型后进行强制类型转换
                String textValue = null;
                ExcelHead annotation = field.getAnnotation(ExcelHead.class);
                if (value == null) {
                    cell.setCellValue("");
                }else if(annotation.isOption()){
                    cell.setCellValue((String) map.get(field.getName()).get(value));
                }else if (value instanceof Integer) {
                    int intValue = (Integer) value;
                    cell.setCellValue(intValue);
                } else if (value instanceof Float) {
                    float fValue = (Float) value;
                    cell.setCellValue(fValue);
                } else if (value instanceof Double) {
                    double dValue = (Double) value;
                    cell.setCellValue(dValue);
                } else if (value instanceof Long) {
                    long longValue = (Long) value;
                    cell.setCellValue(longValue);
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    SimpleDateFormat sdf = new SimpleDateFormat(annotation.datePattern());
                    textValue = sdf.format(date);
                    cell.setCellValue(textValue);
                } else {
                    // 其它数据类型都当作字符串简单处理
                    textValue = value == null ? "" : value.toString();
                    cell.setCellValue(textValue);
                }
                int length = 0;
                if (cell.getCellType()== Cell.CELL_TYPE_STRING &&cell.getStringCellValue() != null) {
                    length = cell.getStringCellValue().getBytes().length * 256;
                }
                //这里把宽度最大限制到12000
                length = length > 12000 ? 12000 : length;
                maxWidth.put(cIndex, Math.max(length, maxWidth.get(cIndex) == null ? 4000 : maxWidth.get(cIndex)));
                cIndex++;
            }
        }
        for(int i=0;i<fields.size();i++){
            sheet.setColumnWidth(i, maxWidth.get(i));
        }
        sheet.createFreezePane(0, 1, 0, 1);
        workbook.write(out);
        out.flush();
        out.close();
    }

    /**
     * 导入模板生成
     *
     * @param out        输出流
     * @param optionsMap 下拉选项map
     * @param t          导出类
     * @throws IOException
     */
    public void importTemplate(OutputStream out, Map<String, String[]> optionsMap, Class<T> t) throws IOException {
        String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet("items");
        HSSFSheet sheet2 = workbook.createSheet("options");
        workbook.setSheetHidden(workbook.getSheetIndex(sheet2), true);
        CellStyle style = workbook.createCellStyle();
        CellStyle style1 = workbook.createCellStyle();
        //创建表头
        Font font = workbook.createFont();
        Font font1 = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 11);//设置字体大小
        font1.setFontName("微软雅黑");
        font1.setFontHeightInPoints((short) 11);
        font1.setColor(HSSFColor.RED.index);
        style.setFont(font);//选择需要用到的字体格式

        style.setFillForegroundColor(HSSFColor.YELLOW.index);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style1.cloneStyleFrom(style);
        style1.setFont(font1);
        Field[] heads = t.getDeclaredFields();
        List<String> headList = new ArrayList<>();
        List<String> types = new ArrayList<>();
        //获取字段注解的表头
        List<Integer> notNull = new ArrayList<>();
        for (int i = 0; i < heads.length; i++) {
            ExcelHead excelHead = heads[i].getAnnotation(ExcelHead.class);
            String type = heads[i].getType().toString();
            if (excelHead.isImport()) {
                if(excelHead.notNull()){
                    notNull.add(headList.size());
                }
                headList.add(excelHead.value());
                if(excelHead.isOption()){
                    type="选项";
                }else if(type.endsWith("String")){
                    type = "字符类型";
                }else if(type.endsWith("Integer")||type.endsWith("int")||type.endsWith("Long")||type.endsWith("long")){
                    type = "整数";
                }else if(type.endsWith("Double")||type.endsWith("double")||type.endsWith("BigDecimal")){
                    type = "小数";
                }else if(type.endsWith("Date")){
                    type = "日期时间";
                }
                types.add(type);
            }
        }
        // 产生表格标题行
        HSSFRow rowFirst = sheet.createRow(0);
        HSSFRow Row = sheet.createRow(1);
        Row.setHeight((short) 480);
        int index = 0;
        for (int i = 0; i < headList.size(); i++) {
            HSSFCell typeCell = rowFirst.createCell(i);
            typeCell.setCellValue(types.get(i));
            HSSFCell cell = Row.createCell(i);
            String head = headList.get(i);
            if(notNull.contains(i)){
                cell.setCellStyle(style1);
                typeCell.setCellStyle(style1);
            }else {
                cell.setCellStyle(style);
                typeCell.setCellStyle(style);
            }
            HSSFRichTextString text = new HSSFRichTextString(head);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, head==null?4000:Math.max(head.getBytes().length * 256, 4000));
            if (optionsMap.containsKey(head)) {
                //设置下拉框数据
                HSSFRow row = null;
                String[] dlData = optionsMap.get(head);//获取下拉对象
                if (dlData.length < 255) { //255以内的下拉
                    //255以内的下拉,参数分别是：作用的sheet、下拉内容数组、起始行、终止行、起始列、终止列
                    sheet.addValidationData(setDataValidation(sheet, dlData, 2, 5000, i, i)); //超过255个报错
                } else { //255以上的下拉，即下拉列表元素很多的情况
                    //1、设置有效性
                    //String strFormula = "Sheet2!$A$1:$A$5000" ; //Sheet2第A1到A5000作为下拉列表来源数据
                    String strFormula = "options!$" + arr[index] + "$1:$" + arr[index] + "$" + dlData.length;
                    //设置数据有效性加载在哪个单元格上,参数分别是：从sheet2获取A1到A5000作为一个下拉的数据、起始行、终止行、起始列、终止列
                    sheet.addValidationData(SetDataValidation(strFormula, 2, 5000, i, i)); //下拉列表元素很多的情况
                    //2、生成sheet2内容
                    for (int j = 0; j < dlData.length; j++) {
                        if (index == 0) { //第1个下拉选项，直接创建行、列
                            row = sheet2.createRow(j); //创建数据行
                            row.createCell(0).setCellValue(dlData[j]); //设置对应单元格的值
                        } else { //非第1个下拉选项
                            int rowCount = sheet2.getLastRowNum();
                            if (j <= rowCount) { //前面创建过的行，直接获取行，创建列
                                //获取行，创建列
                                sheet2.getRow(j).createCell(index).setCellValue(dlData[j]); //设置对应单元格的值
                            } else { //未创建过的行，直接创建行、创建列
                                sheet2.setColumnWidth(j, 4000); //设置每列的列宽
                                //创建行、创建列
                                sheet2.createRow(j).createCell(index).setCellValue(dlData[j]); //设置对应单元格的值
                            }
                        }
                    }
                    index++;
                }
            }
        }
        sheet.createFreezePane(0, 2, 0, 2);
        workbook.write(out);
        out.flush();
        out.close();
    }

    public List<T> excelToEntity(MultipartFile file, Class<T> t,Map<String,Map> map) throws ReflectiveOperationException {
        List<T> list = new ArrayList<>();
        StringBuffer msg = new StringBuffer();
        Workbook workBook = null;
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 判断Excel类型，是Excel2003还是Excel2007，通过文件名后缀判断
        try {
            InputStream stream = file.getInputStream();
            if (fileName.endsWith("xls")) {
                workBook = new HSSFWorkbook(stream);
            } else if (fileName.endsWith("xlsx")) {
                workBook = new XSSFWorkbook(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workBook != null) {
            Sheet sheet = workBook.getSheetAt(0);      //获取数据标签页
            int maxRownum = sheet.getPhysicalNumberOfRows();        //最大行数
            if (maxRownum > 2) {
                Field[] fields = t.getDeclaredFields();
                List<String> headList = new ArrayList<>();
                //获取字段注解的表头
                Class<ExcelHead> excelHeadClass = ExcelHead.class;
                Map<String, Field> heads = Arrays.stream(fields).filter(field -> field.getAnnotation(excelHeadClass).isImport())
                        .collect(Collectors.toMap(
                                field -> field.getAnnotation(excelHeadClass).value(),
                                field -> field));
                Row firstRow = sheet.getRow(1);     //获取表头行
                int maxColnum = firstRow.getPhysicalNumberOfCells();    //最大列数
                Field[] setFields = new Field[maxColnum];
                for (int i = 0; i < maxColnum; i++) {
                    setFields[i] = heads.get(firstRow.getCell(i).getStringCellValue()); //字段对照表头排序
                }
                boolean wrap = true;
                for (int i = 2; i < maxRownum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        T t1 = t.newInstance();
                        list.add(t1);
                        wrap = true;
                        for (int j = 0; j < maxColnum; j++) {
                            Field field = setFields[j];
                            Cell cell = row.getCell(j);
                            wrap = this.setAttr(t1, field, cell, t,map);
                            if (!wrap) {
                                break;
                            }
                        }
                        if (!wrap) {
                            msg.append("第").append(i + 1).append("行数据有误;<br/>");
                        }
                    } else {
                        break;
                    }
                }
                String s = msg.toString();
                if(!"".equals(s)){
                    throw new CustomException(550,s);
                }
            }
        }
        return list;
    }

    private boolean setAttr(T t, Field field, Cell cell, Class<T> claz,Map<String,Map> map) {
        try {
            ExcelHead annotation = field.getAnnotation(ExcelHead.class);

            if(cell==null){
                if (annotation.notNull()) {
                    return false;
                }else {
                    return true;
                }
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            if (StringUtils.isBlank(value)) {
                if (annotation.notNull()) {
                    return false;
                }else {
                    return true;
                }
            }
            String type = field.getType().toString();
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), claz);
            Method wM = pd.getWriteMethod();//获得写方法
            if(annotation.isOption()){
                Object o = map.get(field.getName()).get(value);
                wM.invoke(t, o);
            }else if (type.endsWith("String")) {
                wM.invoke(t, value);
            } else if (type.endsWith("Integer") || type.endsWith("int")) {
                wM.invoke(t, Integer.parseInt(value));
            } else if (type.endsWith("BigDecimal")) {
                wM.invoke(t, new BigDecimal(value));
            } else if (type.endsWith("Date")) {
                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(value));
                wM.invoke(t,date);
            } else if (type.endsWith("Long") || type.endsWith("long")) {
                wM.invoke(t, Long.parseLong(value));
            } else if (type.endsWith("Double") || type.endsWith("double")) {
                wM.invoke(t, Double.parseDouble(value));
            } else if (type.endsWith("Byte") || type.endsWith("Byte")) {
                wM.invoke(t, Byte.parseByte(value));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param @param  strFormula
     * @param @param  firstRow   起始行
     * @param @param  endRow     终止行
     * @param @param  firstCol   起始列
     * @param @param  endCol     终止列
     * @param @return
     * @return HSSFDataValidation
     * @throws
     * @Title: SetDataValidation
     * @Description: 下拉列表元素很多的情况 (255以上的下拉)
     */
    private HSSFDataValidation SetDataValidation(String strFormula, int firstRow, int endRow, int firstCol, int endCol) {
        // 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        dataValidation.createErrorBox("Error", "Error");
        dataValidation.createPromptBox("", null);
        return dataValidation;
    }

    /**
     * @param @param  sheet
     * @param @param  textList
     * @param @param  firstRow
     * @param @param  endRow
     * @param @param  firstCol
     * @param @param  endCol
     * @param @return
     * @return DataValidation
     * @throws
     * @Title: setDataValidation
     * @Description: 下拉列表元素不多的情况(255以内的下拉)
     */
    private DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        //加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
        constraint.setExplicitListValues(textList);
        //设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);
        //数据有效性对象
        DataValidation data_validation = helper.createValidation(constraint, regions);
        return data_validation;
    }
}
