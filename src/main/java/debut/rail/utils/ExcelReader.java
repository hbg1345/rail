package debut.rail.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import debut.rail.domain.Schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ExcelReader {

    public static int getColIndex(char colChar){
        return colChar - 'A';
    }
    public static Cell getCell(int row, int col, Sheet sheet){
        try {
            return sheet.getRow(row).getCell(col);
        }
        catch (Exception e)
        {  return null;}
    }

    public static int getEndCol(int row, int col, Sheet sheet){
        for(int j=col+2; ; j++){
            Cell __cell = getCell(row, j, sheet);
          
            if (__cell == null || __cell.getCellType() == CellType.BLANK)
                return j-1;
        }
    }

    public static List<Schedule> getSchedule(char colChar, int chainRow, Sheet sheet) {
        int chainCol = getColIndex(colChar);
        int beginRow = chainRow + 4; // 첫 기차 행(0-based index)
        int beginCol = chainCol; //  (0-based index)
        
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        String prevStation;
        LocalTime departureTime;
        prevStation = null;
        departureTime = null;
        int endCol = getEndCol(beginRow, beginCol, sheet);   
        String chainName = String.valueOf(getCell(chainRow, chainCol, sheet));
        
        List<String> stationList = new ArrayList<String>();
        
        for(int j=beginCol+2; j<endCol; j++){
            stationList.add(String.valueOf(getCell(beginRow-3, j, sheet)));
            // System.out.println(String.valueOf(getCell(j, beginRow-3, sheet)));
        }
        
        System.out.println(stationList);

        for(int i=beginRow; ; i++){
            Cell _cell = getCell(i, beginCol, sheet);
            if (_cell ==null || _cell.getCellType() == CellType.BLANK)
                break;
            String day = getCell(i, endCol, sheet).getStringCellValue();
            String trainNum = String.valueOf((int) getCell(i, beginCol, sheet).getNumericCellValue());
            String trainName = getCell(i, beginCol+1, sheet).getStringCellValue();
            
            System.out.println(trainNum + trainName);
            for(int j=beginCol+2; j<endCol; j++){
                Cell cell = getCell(i, j, sheet);

                LocalTime arrivalTime = cell.
                    getDateCellValue().toInstant().atZone(
                        ZoneOffset.of("+09:00")).toLocalTime();
                if (!arrivalTime.equals(LocalTime.of(0, 0))){
                    String nowStation = stationList.get(j-beginCol-2);
                    
                    if (departureTime != null){
                        Schedule schedule = new Schedule(chainName, trainName, trainNum, 
                        prevStation, nowStation, day, departureTime, arrivalTime);
                        scheduleList.add(schedule);
                        System.out.println(schedule);
                    }
                    prevStation = nowStation;
                    departureTime = arrivalTime;
                }
                // LocalTime arrivalTime = cell.getStringCellValue();
            }
        }
    
        for(Schedule sc: scheduleList){
            System.out.println(sc);
        }
        return scheduleList;
    }
}
