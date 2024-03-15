# holiday_jp

Japanese holiday

## Install

```shell
npm install @holiday-jp/holiday_jp
```

## Usage
```typescript
import * as holiday_jp from '@holiday-jp/holiday_jp';

const holidays = holiday_jp.between(new Date('2010-09-14'), new Date('2010-09-21'));

console.log(holidays[0]['name']); // 敬老の日  
```