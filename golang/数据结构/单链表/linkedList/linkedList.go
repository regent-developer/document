package linkedList

import "fmt"

type Object interface{}
 
type Node struct{
	// 数据域
	Data Object
	// 地址域（指向下一个地址）
	Next *Node
}

type List struct{
	// 头节点
	headNode *Node
}

// 判断链表是否为空
func (this * List) IsEmpty() bool{
	if this.headNode == nil{
		return true
	}else{
		return false
	}
}

// 获取链表长度
func (this *List) Length() int{
	// 头节点
	head := this.headNode

	count := 0

	for head != nil {
		count++
		head = head.Next
	}

	return count
}

// 链表头部添加元素
func (this *List) Add(data Object) *Node {
	// 追加节点
	node := &Node{Data: data}

	// 头节点的Next赋值给新节点的Next
	node.Next = this.headNode

	// 头节点指向新节点
	this.headNode = node

	return node
 }

 // 链表尾部追加
 func (this *List) Append(data Object){
	 // 创建新元素
	 node := &Node{Data: data}

	 if this.IsEmpty() {
		this.headNode = node
	 }else{
		 // 存储头节点
		 current := this.headNode
		 // 判断是否为尾节点，找到尾节点
		 for current.Next != nil{
			 current = current.Next
		 }

		 // 尾节点的地址域指向新节点
		 current.Next = node
	 }
 }

 // 链表指定位置添加元素
 func (this *List) Insert(index int, data Object){
	 if index < 0{
		 // 头部插入
		 this.Add(data)
	 }else if index > this.Length() {
		 // 尾部插入
		this.Append(data)
	 }else{
		pre := this.headNode
		count := 0

		for count < (index -1){
			pre = pre.Next
			count++
		}

		// pre指向index - 1的位置
		node := &Node{Data:data}
		node.Next = pre.Next
		pre.Next = node
	 }
 }

 // 删除链表指定值的元素
 func (this *List) Remove(data Object){
	 pre := this.headNode
	 if pre.Data == data{
		 // 如果该节点就是头节点的场合，删除头节点，第二个节点就是头节点
		 this.headNode = pre.Next
	 }else{
		 for pre.Next != nil{
			 if pre.Next != nil{
				 pre.Next = pre.Next.Next
			 }else{
				 pre = pre.Next 
			 }
		 }
	 }
 }

 // 删除指定位置的元素
 func (this *List) RemoveAtIndex(index int){
	 pre := this.headNode

	 if index <= 0{
		this.headNode = pre.Next 
	 }else if index > this.Length() {
		// 退出程序
		return
	 }else{
		 count := 0
		 for count != (index - 1) && pre.Next != nil{
			 count++
			 pre = pre.Next 
		 }

		 pre.Next = pre.Next.Next
	 }
 }

 // 查看链表是否包含某个元素
 func (this *List) Contain(data Object) bool {
	 cur := this.headNode

	 for cur != nil {
		 if cur.Data == data {
			 return true
		 }

		 cur = cur.Next
	 }

	 return false
 }

 // 遍历链表所有节点
func (this *List) PrintList(){
	if !this.IsEmpty() {
		cur := this.headNode
		for {
			fmt.Printf("\t%v", cur.Data)

			if cur.Next != nil{
				cur = cur.Next 
			}else{
				break
			}
		}
	}
}
