package com.rain.wanandroidkotlin.test

import com.rain.wanandroidkotlin.mvp.model.entity.SystemBean

/**
 * Author:rain
 * Date:2019/1/9 10:44
 * Description:
 * 用于kotlin语法的各种测试
 */

private const val Tag = "KotlinTest"

object KotlinTest {
    @JvmStatic
    fun main(args: Array<String>) {
//        foo()

//        foo1()

//        Persons("xiaohong", 19)

//        val t = Teacher("小王")
//        t.data = listOf(1, 2, 3)
//        println("isEmpty:${t.isEmpty()}")

//        val b = BaseImpl(10)
//        Derived(b).print()
//        Derived(b).foo {
//            println("hello")
//        }

        /*解构声明*/
//        val p:Persons = Persons("meizu",19)
//        val(name,age) = p
//        println("name:$name,age:$age")

//        val list = listOf(1,2,3)
        // 复制当前的list集合，相当于快照
//        list.toList()


//        val isLast = (1..12 step 4).last == 9
//        println("isLast:$isLast")

        val numbers = listOf(1, 2, 3)
        println(numbers.filter(::isOdd)) // 引用到 isOdd(x: Int)
        numbers.filter {
            it % 2 != 0
        }

    }

    fun foo() {
        listOf(1, 2, 3).forEach {
            if (it == 2) {
                println("it:$it")
                return@forEach // @forEach 表示跳出当前foreach循环，不加此标签表示跳出当前方法
            }
        }
        println("done")
    }

    fun foo1() {
        for (i in 1..10) {
            for (j in 1..10) {
                if (j == 5) break
                println("i:$i,j:$j")
            }
        }
    }

    /**
     * 主构造函数与次级构造函数
     * 次级构造函数必须委托主构造函数
     * 一个类class、属性、方法（抽象类接口除外）如果要被继承，
     * 必须加open，因为默认为final
     */
    open class Person(name: String) {

        open val Tag: String = "Persons"

        init {
            println("初始化")
        }

        constructor(name: String, age: Int) : this(name) {
            println("name:$name,age:$age")
        }

        constructor(name: String, age: Int, sex: Int) : this(name, age) {
            println("name:$name,age:$age,sex:$sex")
        }
    }

    class Teacher(name: String) : Person(name) {
        override var Tag = "Teacher"
        var data: List<Int>? = null
        // 为属性自定义的访问器
//        val isEmpty: Boolean get() = (data?.size ?: 0) == 0
        // 与上边实质是一样的
        fun isEmpty(): Boolean {
            return (data?.size ?: 0) == 0
        }
    }

    /**
     * 接口中声明的属性要么是抽象的，要么提供访问器的实现
     */
    interface MyInterface {
        val prop: Int // 抽象的
        val propWithImplementation: String
            get() = "foo"
    }

    class Child : MyInterface {
        override val prop = 20
    }

    /*修饰符的作用域*/
    open class Outer {
        private val a = 1
        protected open val b = 2
        internal open val c = 3
        val d = 4  // 默认 public

        protected class Nested {
            public val e: Int = 5
        }
    }

    class Subclass : Outer() {
        // a 不可见
        // b、c、d 可见
        // Nested 和 e 可见

        override val b = 5   // “b”为 protected
    }

    class Unrelated(val o: Outer) {
        // o.a、o.b 不可见
        // o.c 和 o.d 可见（相同模块）
        // Outer.Nested 不可见，Nested::e 也不可见
        fun foo() {
            o.c
        }
    }

    /*委托*/
    interface Base {
        fun print()
    }

    class BaseImpl(val x: Int) : Base {
        override fun print() {
            print(x)
        }
    }

    class Derived(b: Base) : Base by b {
        fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
            println("bar:$bar,baz:$baz")
        }
    }

    fun isOdd(x: Int): Boolean = x % 2 != 0


}