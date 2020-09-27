package circleLinkedList

import (
	"testing"
)

// 判空方法的单元测试方法
func TestGetSize(t *testing.T) {
	list := List{}

	if list.GetSize() != 0 {
		t.Error("GetSize result failed！")
	}
}