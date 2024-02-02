# poi 公式失效解决方法



## 现象

使用java poi写入excel文件后，excel文件中的计算公式失效



## 解决方法

### 方法1

```java
workbook.setForceFormulaRecalculation(true);
```

`在office excel下计算公式有效，但是在永中表格下计算公式依然失效`



### 方法2

```java
private static void updateFormula(Workbook wb, Sheet s, int row) {
    Row r = s.getRow(row);
    Cell c = null;
    FormulaEvaluator eval = null;
    if(wb instanceof HSSFWorkbook) {
        eval = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
    } else if(wb instanceof XSSFWorkbook) {
        eval = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
    }

    for(int i = r.getFirstCellNum(); i < r.getLastCellNum(); i++){
        c = r.getCell(i);
        if(c.getCellType() == CellType.FORMULA) {
            eval.evaluateFormulaCell(c);
        }
    }
}
```

`在office excel下计算公式有效，在永中表格下计算公式有效`

