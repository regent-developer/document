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

//  定义链表
type List struct{
	// 数量
	size uint64

	// 头节点
	headNode *Node
}

// 初始化链表
func (this *List) Init() {
	list := *this
	list.size = 0
	list.headNode = nil
}

// 添加数据到链表尾部
func (this *List) Append(data Object) {
	// 生成节点
	node := new(Node)

	node.Data = data

	if this.GetSize() == 0 {
		// 没有节点时
		(*this).headNode = node
	} else {
		item := this.GetHead()

		for ; (*item).Next != this.GetHead(); item = (*item).Next {}

		(*item).Next = node
	}

	(*node).Next = (*this).headNode
	(*this).size++
}

// 指定节点后插入
func (this *List) Insert(node *Node, data Object) bool {
	if node == nil {
		return false
	}

	item := new(Node)
	(*item).Data = data

	(*node).Next = (*node).GetNext()
	(*node).Next = item

	this.size++

	return true
}

// 删除节点
func (this *List) Remove(node *Node) Object {
	if node == nil {
		return nil
	}

	item := (*this).GetHead()

	for ; (*item).Next != this.GetHead(); item = (*item).Next {}

	(*item).Next = (*node).Next
	(*this).GetSize()--

	return (*node).GetData()
}

// 获取头节点
func (this *List) GetHead() *Node {
	return (*this).headNode
}

// 节点数量
func (this *List) GetSize() uint64 {
	return (*this).size
}

// 获取节点数据
func (node *Node) GetData() Object {
	return (*node).Data
}

// 获取下个节点
func (node *Node) GetNext() *Node {
	return (*node).Next
}
