# @MockSetup

```
import { MockKit, when, MockSetup } from '@ohos/hamock';
```

* @MockSetup用于修饰Mock方法，仅支持声明式范式的组件。
* @MockSetup修饰的方法仅在预览场景会自动触发，并先于组件的aboutToAppear执行。