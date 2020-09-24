package circleLinkedList

import "fmt"

// 数据域类型（任意类型）
type Object interface{}
 
// 定义节点
type Node struct{
	// 数据域
	Data Object
	// 地址域（指向下一个地址）
	Next *Node
}