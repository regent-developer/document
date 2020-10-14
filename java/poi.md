# POI操作Excel
## 自定义cell的颜色
```java
//设置自定义颜色
HSSFWorkbook wb = new HSSFWorkbook();
HSSFPalette customPalette = wb.getCustomPalette(); 
customPalette.setColorAtIndex(HSSFColor.LIGHT_GREEN.index, (byte) 0, (byte) 204, (byte) 153);
customPalette.setColorAtIndex(HSSFColor.LIGHT_BLUE.index, (byte) 203, (byte) 236, (byte) 222); 
customPalette.setColorAtIndex(HSSFColor.LIGHT_YELLOW.index, (byte) 231, (byte) 246, (byte) 239); 

// 创建一个单元的样式
HSSFCellStyle styleheader = wb.createCellStyle();

//设置背景颜色
style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);

// 设置Cell的样式
HSSFCell cell;
cell.setCellStyle(style);
```

## 删除指定列(POI未提供删除列的方法)
```java
public void deleteColumn(HSSFSheet sheet, int delColumnNum) {
		
    int maxColumn = 0;
    for (int r = 0; r < sheet.getLastRowNum() + 1; r++) {
        HSSFRow row = sheet.getRow(r);

        // if no row exists here; then nothing to do; next!
        if (row == null)
            continue;

        // if the row doesn't have this many columns then we are good; next!
        int lastColumn = row.getLastCellNum();
        if (lastColumn > maxColumn)
            maxColumn = lastColumn;

        if (lastColumn < delColumnNum)
            continue;

        for (int x = delColumnNum + 1; x < lastColumn + 1; x++) {
            Cell oldCell = row.getCell(x - 1);
            if (oldCell != null)
                row.removeCell(oldCell);

            Cell nextCell = row.getCell(x);
            if (nextCell != null) {
                Cell newCell = row.createCell(x - 1, nextCell.getCellType());
                cloneCell(newCell, nextCell);
            }
        }
    }	
}

public void cloneCell(Cell newCell, Cell oldCell) {

    // Use old cell style
    newCell.setCellStyle(oldCell.getCellStyle());

    // If there is a cell comment, copy
    if (newCell.getCellComment() != null) {
        newCell.setCellComment(oldCell.getCellComment());
    }

    // If there is a cell hyperlink, copy
    if (oldCell.getHyperlink() != null) {
        newCell.setHyperlink(oldCell.getHyperlink());
    }

    // Set the cell data type
    newCell.setCellType(oldCell.getCellType());

    // Set the cell data value
    switch (oldCell.getCellType()) {
    case Cell.CELL_TYPE_BLANK:
        break;
    case Cell.CELL_TYPE_BOOLEAN:
        newCell.setCellValue(oldCell.getBooleanCellValue());
        break;
    case Cell.CELL_TYPE_ERROR:
        newCell.setCellErrorValue(oldCell.getErrorCellValue());
        break;
    case Cell.CELL_TYPE_FORMULA:
        newCell.setCellFormula(oldCell.getCellFormula());
        break;
    case Cell.CELL_TYPE_NUMERIC:
        newCell.setCellValue(oldCell.getNumericCellValue());
        break;
    case Cell.CELL_TYPE_STRING:
        newCell.setCellValue(oldCell.getRichStringCellValue());
        break;
    }

}
```

## 合并单元格
```java
HSSFWorkbook wb = new HSSFWorkbook();
HSSFSheet sheet = wb.createSheet("Sheet 1");
CellRangeAddress region = new CellRangeAddress(startRow, endRow, startCol, endCol);
sheet.addMergedRegion(region);
```
##获取指定excel的真实行数
```java
public static int getVaildRows(String path) throws IOException, InvalidFormatException {
        FileInputStream excelFileInputStream = new FileInputStream(path);
        Workbook wb = WorkbookFactory.create(excelFileInputStream);
        Sheet sheet = wb.getSheetAt(0);
        CellReference cellReference = new CellReference("A4");
        boolean flag = false;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {//如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum())//如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                else//如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
            }
        }
        return sheet.getLastRowNum() + 1;
    }
```
