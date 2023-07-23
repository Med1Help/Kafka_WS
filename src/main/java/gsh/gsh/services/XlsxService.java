package gsh.gsh.services;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class XlsxService {
    public List<String> upload(String excelFile) throws IOException{
        File file = new File(excelFile);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workBook = new XSSFWorkbook(fis);
        int numberOfSheet = workBook.getNumberOfSheets();
        List<String> urls = new ArrayList<>();
        //for(int i = 0 ; i < numberOfSheet ; i++){
            //Getting sheet at index i
            Sheet sheet = workBook.getSheetAt(0);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = sheet.rowIterator(); //to oterate over evry row
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String url = String.valueOf(row.getCell(0));
                if(!(url.contains("web site"))) urls.add("https://"+url);
                System.out.println("url: "+url);
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.println(cellValue+"\t");
                }
                System.out.println("");
            }
        //}
        return urls;
    }
}
