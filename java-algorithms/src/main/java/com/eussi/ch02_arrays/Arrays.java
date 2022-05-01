package com.eussi.ch02_arrays;

import com.eussi.ch02_arrays.util.OrdArray;
import static com.eussi.util.PrintUtil.*;

/**
 * @author wangxueming
 * @create 2019-10-17 22:16
 * @description
 */
public class Arrays {
    public static void main(String[] args) {
        /**
         * 数组操作平均比较次数和移动次数（N是数据项个数，插入一个数据为一次移动）
         *
         *                          不允许重复                           允许重复
         * 查找                       N/2次比较                          N次比较
         * 插入（简单插入一个空位上）   无比较,一次移动 （指无序数组插入）   无比较,一次移动
         * 删除                       N/2次比较,N/2次移动                 N次比较,多于N/2次移动
         *
         * 通常认为N和N/2之间的差异不很重要,除非正在对程序进行微调以达到最优。
         * 更重要的是操作是否需要执行一步、N步、log(N)步或N²步
         *
         */

        /**
         * 有序数组常用操作，重点在二分查找
         *
         */
        testOrdArray();//有序数组测试

        /**
         * 有序数组的优点
         *      使用有序数组会给我们带来什么好处?最主要的好处是查找的速度比无序数组快多了。不好的
         * 方面是在插入操作中由于所有靠后的数据都需移动以腾开空间,所以速度较慢。有序数组和无序数
         * 组中的删除操作都很慢,这是因为数据项必须向前移动来填补已删除数据项的洞
         *      有序数组在查找频繁的情况下十分有用,但若是插入与删除较为频繁时,则无法高效工作。例
         * 如,有序数组适合于公司雇员的数据库。雇用和解雇雇员同读取一个已存在雇员的有关信息或更改
         * 薪水、住址等信息相比,前两者是不经常发生的。
         *      另一方面,零售商店的存货清单不适合用有序数组来实现,这是由于与频繁的进货和出货相应
         * 的插入与删除操作都会执行得很慢
         *
         *
         * 二分查找所需次数比较：
         *      范围            所需比较次数
         *      10              4
         *      100             7
         *      1000            10
         *      10000           14
         *      100000          17
         * 设s为表示查询步数，r表示查询的范围，有下面方程：
         *      r = 2^s
         * 所以：
         *      s = log₂(r)
         *
         * 对数有一个特点，当一个数(r)变大时，他的对数(s)增长得很缓慢。后面大O表示法会再次提到
         * 所以二分查找除了对于特别小的数组以外，二分查找的表现都是极为优秀的
         *
         */


        /**
         * 大O表示法
         *      汽车按尺寸被分为若干类:微型,小型,中型等等。在不提及具体尺寸的情况下,这些分类可
         * 以为我们所涉及到车的大小提供一个大致概念。我们同样也需要一种快捷的方法来评价计算机算法
         * 的效率。在计算机科学中,这种粗略的度量方法被称作“大O”表示法
         *      在比较算法时似乎应该说一些类似“算法A比算法B快两倍”之类的话,但实际上这类陈述
         * 并没有多大意义。为什么?这是由于当数据项个数变化时,对应的比例也会发生根本改变。有可能
         * 数据项增加了50%,则A就比B快了三倍。或有可能只有一半的数据项,但现在A和B的速度是
         * 相同的。
         *      **我们所需的是一个可以描述算法的速度是如何与数据项的个数相联系的比较。**
         *
         * 下面是我们目前所见过的算法的大O表示：
         * 无序数组的插入:常数
         *      无序数组的插入是我们到现在为止所见过的算法中惟一一个与数组中的数据项个数无关的算
         * 法。新数据项总是被放在下一个有空的地方,a[nElems],然后nElems增加。无论数组中的数据项
         * 个数N有多大,一次插入总是用相同的时间。我们可以说向一个无序数组中插入一个数据项的时间
         * T是一个常数K:
         *      T=K
         *      在现实情况中,插入所需的实际时间(不管是微秒还是其他单位)与以下这些因素有关:微处
         * 理器,编译程序生成程序代码的效率,等等。上面等式中的常数K包含了所有这些因素。在现实情
         * 况中要得到K的值,需要测量次插入所花费的时间。(软件就是为了这个目的而存在的。)K就等
         * 于这个时间。
         * 线性查找:与N成正比
         *      在数组数据项的线性查找中,我们已经发现寻找特定数据项所需的比较次数平均为数据项总数
         * 的一半。因此设N为数据项总数,搜索时间T与N的一半成正比
         *      T=K*N/2
         * 同插入一样,若要得到方程中K的值,首先需要对某个N值(有可能很大)的查找进行计时,
         * 然后用T来计算K。当得到K后便可对任意N的值来计算T
         *      将2并入K可以得到一个更方便的公式。新K值等于原先的K除以2。新公式为:
         *      T=K*N
         * 这个方程说明平均线性查找时间与数组的大小成正比。即如果一个数组增大两倍,则所花费的
         * 查找时间也会相应地增长两倍。
         * 二分查找:与log(N)成正比
         *      同样,我们可以为二分查找制定出一个与T和N有关的公式
         *      T=K*log₂(N)
         *      正如前面所提到的,时间T与以2为底N的对数成正比。实际上,由于所有的对数都和其他
         * 对数成比例(从底数为2转换到底数为10需乘以3.322),我们可以将这个为常数的底数也并入K
         * 由此不必指定底数
         *      T=K*log(N)
         * 不要常数
         *      大O表示法同上面的公式比较类似,但它省去了常数K。当比较算法时,并不在乎具体的微处
         * 理器芯片或编译器;真正需要比较的是对应不同的N值,T是如何变化的,而不是具体的数字。因
         * 此不需要常数
         *      大O表示法使用大写字母O,可以认为其含义是“order of”(大约是)。我们可以使用大O表示
         * 法来描述线性查找使用了O(N)级时间,二分查找使用了O(logN)级时间。向一个无序数组中的插入
         * 使用了O(1),或常数级时间。(小括号中是数字1)。
         *
         * 下面是目前为止讨论过的算法的运行时间：
         *      算法              大0表示法表示的运行时间
         *      线性查找             O(N)
         *      二分查找             O(logN)
         *      无序数组的插入        O(1)
         *      有序数组的插入        O(N)
         *      无序数组的删除        O(N)
         *      有序数组的删除        O(N)
         *
         * 不同的大O值大致可以分为：
         *      O(1)是优秀,O(logN)是良好,O(N)是还可以,O(N²)则差一些了。O(N²)会在后面的冒泡
         * 排序和其他某些算法中出现
         *      **大O表示法的实质并不是对运行时间给出实际值,而是表达了运行时间是如何受数据项个数所
         * 影响的。除了实际安装后真正去测量一次算法的运行时间之外,这可能是对算法进行比较的最有意
         * 义的方法了**
         *
         *
         * 为什么不用数组表示一切?
         *      仅使用数组似乎就可以完成所有工作,为什么不用它们来进行所有的数据存储呢?我们已经见
         * 到了许多关于数组的缺点。在一个无序数组中可以很快进行插入(O(1)时间),但是查找却要花费较
         * 慢的O(N)时间。在一个有序数组中可以查找得很快,用O(logN)的时间,但插入却花费了O(N)时间
         * 对于这两种数组而言,由于平均半数的数据项为了填补“空洞”必须移动,所以删除操作平均需要
         * O(N)时间。
         *      如果有一种数据结构进行任何如插入、删除和查找的操作都很快(理想的O(1)或是O(logN)时
         * 间),那就好了。在后面可以见到我们离这种理想是多么近,但是这是以程序的复杂度做为
         * 代价的。
         *      数组的另一个问题便是它们被new创建后,大小尺寸就被固定住了。但通常在开始设计程序时
         * 并不知道会有多少数据项将会被放入数组中,所以需要猜它的大小。如果猜的数过大,会使数组中
         * 的某些单元永远不会被填充而浪费空间。如果猜得过小,会发生数组的溢出,最好的情况下会向程
         * 序的用户发出警告消息,最坏的情况则会导致程序崩溃
         *      有些更加灵活的数据结构可以随插入数据项而扩展大小。如链表就是这样的结构
         *      Java中有一个被称作 Vector的类,使用起来很像数组,但是它可以扩展。这些附加的功能是以
         * 效率作为代价的。
         *      你可能想尝试创建自己的向量(vector)类。当类用户使用类中的内部数组将要溢出时,插入算法
         * 创建一个大一点的数组,把旧数组中的内容复制到新数组中,然后再插入新数据项。整个过程对于
         * 类用户来说是不可见的。
         *
         *
         */
    }

    private static void testOrdArray() {
        println("Order Array:");
        int maxSize = 100;             // array size
        OrdArray arr;                  // reference to array
        arr = new OrdArray(maxSize);   // create the array
        // insert 10 items
        arr.insert(77);
        arr.insert(99);
        arr.insert(44);
        arr.insert(55);
        arr.insert(22);
        arr.insert(88);
        arr.insert(11);
        arr.insert(0);
        arr.insert(66);
        arr.insert(33);
        print("After insert: ");
        arr.display();
        // search for item
        int searchKey = 55;
        println("SearchKey:" + searchKey);
        if( arr.find(searchKey) != arr.size() )
            println("Found " + searchKey);
        else
            println("Can't find " + searchKey);
        // delete 3 items
        arr.delete(0);
        arr.delete(55);
        arr.delete(99);
        // display items again
        print("Delete 0,55,99, after: ");
        arr.display();
    }
}
