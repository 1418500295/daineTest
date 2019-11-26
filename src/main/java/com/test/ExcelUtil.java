package com.test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelUtil {






        private Workbook workbook;
        private Sheet sheet;
        private Cell cell;
        int rows;
        int columns;
        private String fileName;
        private String caseName;
        private List<String> list = new ArrayList<String>();
        String sourceFile;

        public ExcelUtil(String fileName,String caseName){
            super();
            this.fileName =fileName;
            this.caseName=caseName;

        }
        public Object[][] getExcelData() throws IOException, BiffException {

            workbook = Workbook.getWorkbook(new File(getpath()));
            sheet = workbook.getSheet(caseName);
            rows = sheet.getRows();
            columns = sheet.getColumns();
            //定义一个多行单列的二维数组
            HashMap<String,String> map[][] =new HashMap[rows-1][];
            //对数组中的所有元素进行初始化
            if (rows > 1){
                for (int i =0;i <rows;i++){
                    map[1][0] = new HashMap<String, String>();

                }
            }else {
                System.out.println("excel中没有数据");
            }
            //获取首行的列名，作为hashmap的key值
            for (int c = 0; c < columns;c++){
                String cellvalue = sheet.getCell(c,0).getContents();
                list.add(cellvalue);

            }
            //遍历所有单元格的值到hashmao中
            for (int r = 1;r< rows;r++){
                for (int c =0;c <columns;c++){
                    String cellvalue = sheet.getCell(c,r).getContents();
                    map[r-1][0].put(list.get(c),cellvalue);
                }
            }
            return map;

        }

        private String getpath() throws IOException {
            File directory = new File(".");
            sourceFile = directory.getCanonicalPath()+"\\src\\resources\\"+fileName+".xlsx";

            return sourceFile;
        }


    }


