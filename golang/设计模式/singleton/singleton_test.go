package singleton

import (
	"testing"
)

func TestSingleton(t *testing.T) {
	obj1 := GetInstance()
	obj2 := GetInstance()
	if obj1 != obj2 {
		t.Error("instance is not equal")
	}
}