package main

import "./linkedList"

func main(){
	list := linkedList.List{}

	list.Append(1)
	list.Append(2)
	list.Append(3)

	list.PrintList()
}