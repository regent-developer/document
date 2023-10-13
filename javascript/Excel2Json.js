// npm install xlsx

const fs = require('fs')
const XLSX = require('xlsx')

const getJson = {}

//读取excel文件
const workBook = XLSX.readFile('./test.xlsx')
// 表名称
const sheetName = workBook.SheetNames[0]
//读取表格内容
const worksheet = workBook.Sheets[sheetName]

// 拿到转化为json的内容
const jsonData = XLSX.utils.sheet_to_json(worksheet)

jsonData.forEach(item => {
  //去重复数据
  if (!(item['键'] in getJson)) {
    getJson[item['键']] = item['值']
  }
})

fs.writeFileSync('./json.json',JSON.stringify(getJson))
// console.log(workBook);
