# vue中各种deep的区别

## ::v-deep
::v-deep 是 Vue 2 中引入的深度选择器，在 Vue 3 中仍然可用，但不再是推荐的写法。它用于穿透作用域，将样式应用到子组件的元素上。

```css
.parent ::v-deep .child-class {
  color: red;
}
```

## ::deep
::deep 是 Vue 3 中推荐的深度选择器。它与 ::v-deep 功能相同，但语法更简洁。

```css
.parent ::deep .child-class {
  color: red;
}
```

## :deep()
:deep() 是 Vue 3 中推荐的另一种深度选择器。它是一个伪类，可以嵌套在其他选择器中使用，提供了更灵活的语法。

```css
.parent :deep(.child-class) {
  color: red;
}
```

## >>>
在早期版本的Vue.js中，>>> 也被用来作为深度选择器。它的用法与 ::v-deep 类似，但是现在已经被 ::v-deep 或 ::deep 所取代。

```css
.parent-class >>> .child-class {
  color: blue;
}
```

## /deep/
/deep/ 也是早期Vue.js版本中的一个深度选择器。不过，随着Vue 2.6.0的发布，官方建议使用 ::v-deep 替代 /deep/。在某些情况下，特别是在使用某些构建工具或配置时，/deep/ 可能仍然有效，但并不推荐继续使用。

```css
.parent-class /deep/ .child-class {
  color: green;
}
```