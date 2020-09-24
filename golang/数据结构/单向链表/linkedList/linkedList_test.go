package linkedList

import (
	"testing"
)

// 判空方法的单元测试方法
func TestIsEmpty(t *testing.T) {
	list := List{}

	list.Append(1)
	list.Append(2)
	list.Append(3)

	if list.IsEmpty() {
		//t.Fatalf("IsEmpty == false")
		t.Error("IsEmpty result failed！")
	}
}