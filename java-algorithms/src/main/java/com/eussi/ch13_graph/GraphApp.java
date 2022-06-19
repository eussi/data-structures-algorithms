package com.eussi.ch13_graph;

import com.eussi.data._13.Graph;
import com.eussi.data._13.GraphD;

import static com.eussi.util.PrintUtil.*;

/**
 * @author wangxueming
 * @create 2020-03-04 19:01
 * @description
 */
public class GraphApp {
    /**
     * 图
     *      在计算机程序设计中,图是最常用的结构之一。一般来说,用图来帮助解决的问题类型与
     * 本书中已经讨论过的问题类型有很大差别。如果处理一般的数据存储问题,可能用不到图,但对
     * 某些问题(经常是一些有趣的问题),图是必不可少的。
     *      对图的讨论分成两章。本章讨论不带权的图,展示这些图的算法。下一章讨论关于带权图
     * 的更复杂的算法。
     */
    public static void main(String[] args) {
        /**
         * 图简介
         *      图是一种与树有些相像的数据结构。实际上,从数学意义上说,树是图的一种。然而,在计算
         * 机程序设计中,图的应用方式与树不同。
         *      本书前面讨论的数据结构都有一个框架,这个框架都是由相应的算法规定的。例如,二叉树是
         * 那样一个形状,就是因为那样的形状使它容易搜索数据和插入新数据。树的边表示了从一个节点到
         * 另一个节点的快捷方式。
         *      另一方面,图通常有一个固定的形状,这是由物理或抽象的问题所决定的。例如,图中节点表
         * 示城市,而边可能表示城市间的班机航线。另一个更抽象的例子是一个代表了几个单独任务的图,
         * 这些任务是完成一个项目所必需的。在图中,节点可能代表任务,有向边(在一端带一个箭头)指
         * 示某个任务必须在另一个任务前完成。在这种情形下,图的形状取决于真实世界的具体情况。
         *      在继续讨论下去之前,必须说明,当讨论图时,节点通常叫做顶点。可能是因为图的命名在几
         * 个世纪前的数学领域就有了,所以它比树的命名要早。树和计算机科学联系得更紧密一些。但是,
         * 这些术语或多或少会交替使用
         *
         * 定义
         *      下图是模拟美国加利福尼亚 San Jose周边地区的简化的高速公路网的图。
         *      在图中,字母代表高速公路的交汇点,连接字母的直线代表高速公路段。字母是顶点,线是边。
         * 顶点通常用一些方法来标识——正如图中显示的那样,用字母表中的字母来表示。每条边由两个顶
         * 点作为两端。
         *      图并不是要试图反映地图上的地理位置;它只是显示了顶点和边的关系——即,哪些边连接着
         * 哪些顶点。它本身不涉及物理的远近和方向。而且一条边可能代表几条不同的公路,两个交叉点间
         * 的连通性(或不连通性)是重要的,而实际的路线并不重要。
         *
         *         I——————————————————J
         *         |\                / \
         *        |  \             /    \
         *        |   G————F——————E—————D
         *       |    /     \    /    /
         *       |  /          A     /
         *       | /         |  \   /
         *       H——————————B—————C
         *
         * 邻接
         *      如果两个顶点被同一条边连接,就称这两个顶点是邻接的。上图中,顶点I和G是邻接的
         * 但I和F就不是。和某个指定顶点邻接的顶点有时叫做它的邻居。例如G的邻居是I、H和F
         *
         * 路径
         *      路径是边的序列。上图显示了一条从顶点B到顶点J的路径,这条路径通过了顶点A和顶
         * 点E。这条路径称作BAEJ。这两个顶点之间还有其他路径:从B到J的另一条路径是BCDJ。
         *
         * 连通图
         *      如果至少有一条路径可以连接起所有的顶点,那么这个图被称作连通的,例如上图。然而,
         * 如果“不能从这里到那里”,那么这个图就是非连通的。
         *      非连通图包含几个连通子图。简便起见,本章讨论的算法都是应用在连通图或非连通图的
         * 连通子图中。如果需要,进行一些小的修正就可以使它们应用在非连通图中。
         *
         * 有向图和带权图
         *      上图图是无向图。这说明图中的边没有方向;可以从任意一边到另一边。所以,可以从顶点A到顶
         * 点B,也可以从顶点B到顶点A,两者是等价的。(无向图很好地模拟了高速公路网,因为在一条公路上可
         * 以按两个方向行驶。）
         *      然而,图还经常用来模拟另一种情况,即只能沿着边朝一个方向移动——只能从A到B,而不
         * 能从B到A,就像单行道一样。这样的图被称作是有向的。允许移动的方向在图中通常用边一端的
         * 箭头表示。
         *      在某些图中,边被赋予一个权值,权值是一个数字,它能代表两个顶点间的物理距离,或者从
         * 个顶点到另一个顶点的时间,或者是两点间的花费(例如飞机航线)。这样的图叫做带权图。下
         * 章将讨论它们。
         *      本章开始我们将讨论简单的无向、无权图,后面将探讨有向无权图
         *      我们无法覆盖所有应用于图的定义,但我们会尽可能多介绍一些
         *
         * 历史的笔记
         *      研究图最早的数学家之一是18世纪的欧拉( Leonhard euler)。他解决了一个著名的难题,关于
         * 波兰 Konigsberg镇的桥的问题。这个镇有一个小岛和七座桥，简化如下图：
         *                                ——————-----C
         *                              /   /         \
         *                           c/    /           \ g
         *                         /      /d            \
         *                      /       /                \
         *                    A  ———————————————————————— D
         *                    |   \          e           /
         *                     \   \                    /
         *                      \   \                 /
         *                       \   \ b            / f
         *                        \   \           /
         *                         \   \         /
         *                          ————-------B
         *
         *      这个问题已经被市民讨论很多次了,它要寻找到一条走遍所有桥的路线,但不允许包含任何交
         * 叉路线。在这里不打算叙述 Euler的解决方案,它的结果是没有这样一条通路。然而,解决问题的
         * 关键所在是把这个问题用图表示,其中把岛的区域作为顶点,桥作为边,如上图所示。这可能是把图用
         * 于表示真实世界的一个具体问题的第一个例子。
         *
         * 在程序中表示图
         *      计算机发明以前,以抽象的方式考虑图是非常恰当的,就像 Euler和其他数学家做的那样,但
         * 是现在需要用计算机来描述图。什么样的软件结构适合于模拟图?首先来看顶点的情况,然后是边
         * 的情况。
         *  - 顶点
         *      在非常抽象的图的问题中,只是简单地把顶点编号,从0到N-1(这里N是顶点数)。不需要
         * 任何变量类型存储顶点,因为它们的用处来自于它们之间的相互关系。
         *      然而在大多数情况下,顶点表示某个真实世界的对象,这个对象必须用数据项来描述。例如,
         * 如果在一个飞机航线模拟程序中,顶点代表城市,那么它需要存储城市名字、海拔高度、地理位置
         * 和其他相关信息。因此,通常用一个顶点类的对象来表示一个顶点。示例程序仅存储了一个字母(例
         * 如A),用来标识顶点,同时还有一个标志位,后面将会看到它在搜索算法中的作用。
         *      顶点对象能放在数组中,然后用下标指示。在本例中,用数组 vertexList存储顶点对象。顶点
         * 也可以放在链表或其他数据结构中。不论使用什么结构,存储只为了使用方便。这与边如何连接点
         * 没有关系。要达到这个目的,还需要其他机制。
         *  - 边
         *      在第9章“红黑树”中,可以看到计算机程序能用几种方式表示树。大多数情况下讨论的树都
         * 是每个节点包含它的子节点的引用,但也提到过用数组表示树,数组中的节点位置决定了它和其他
         * 节点的关系。在第12章“堆”中,就用数组表示了一种树的类型,叫做“堆”。
         *      然而,图并不像树,拥有几种固定的结构。二叉树中,每个节点最多有两个子节点,但图的每
         * 个顶点可以与任意多个顶点连接。例如,上图中,顶点A与三个顶点连接,而C只与一个顶点
         * 连接。
         *      为了模拟这种自由形式的组织结构,需要用一种不同的方法表示边,比树的表示方法更合适些。
         * 一般用两个方法表示图:即邻接矩阵和邻接表。(记住,如果一条边连接两个顶点,这两个顶点就
         * 是邻接的。)
         *  - 邻接矩阵
         *      邻接矩阵是一个二维数组,数据项表示两点间是否存在边。如果图有N个顶点,邻接矩阵就是
         * N*N的数组。下表显示了上图中图的邻接矩阵
         *               A            B            C             D
         *        A      0            1            1             1
         *        B      1            0            0             1
         *        C      1            0            0             0
         *        D      1            1            0             0
         *
         *      顶点被用作行和列的标题。两个顶点间有边则标识为1,没有边则标识为0(也可以使用布尔
         * 变量的true/false值来标识)。如表所示,顶点A和另外三个顶点邻接,B和A、D邻接,C只与A
         * 邻接,而D与A和B邻接。在本例中,顶点与其自身的“连接”设为0,从A-A到D-D称为主(同一）
         * 对角线,所以这条从左上角到右下角的对角线全部是0。主对角线上的实体不代表任何真实世
         * 界的信息,所以为了方便,也可以把主对角线的值设为1
         *      注意,这个矩阵的上三角是下三角的镜像:两个三角包含了同样的信息。这个冗余信息看似低
         * 效,但在大多数计算机语言中,创造一个三角形数组比较困难,所以只好求其次接受这个冗余。这
         * 也要求当增加一条边时,必须更新邻接矩阵的两部分,而不是一部分
         *
         *  - 邻接表
         *      表示边的另一种方法是邻接表。邻接表中的表指的是第5章“链表”中讨论的那种链表。实际
         * 上,邻接表是一个链表数组(或者是链表的链表)。每个单独的链表表示了有哪些顶点与当前顶点
         * 邻接。下表显示了上图中图的邻接表。
         *              顶点                        包含邻接顶点的链表
         *              A                           B->C->D
         *              B                           A->D
         *              C                           A
         *              D                           A->B
         *
         *      在这个表中,符号->表示链表中的一个节点。链表中每个节点都是一个顶点。在这里的每个
         * 链表中,顶点按字母顺序排列,不过这并不是必需的。不要把邻接表的内容与路径混淆。邻接表表
         * 示了当前顶点与哪些顶点连接——即两个顶点间存在边,而不是表示顶点间的路径。
         *      后面会看到什么时候使用邻接矩阵,而什么时候使用邻接表。
         *
         * 在图中添加顶点和边
         *      为了向图中添加顶点,必须用new保留字生成一个新的顶点对象,然后插入到顶点数组
         * vertexList中。在模拟真实世界的程序中,顶点可能包含许多数据项,但是为了简便,这里假定
         * 顶点只包含单一的字符。因此顶点的创建用下面的代码
         *          vertexList[nVerts++] = new Vertex('F')
         *      这就插入了顶点F, nverts变量是图中当前顶点数
         *      怎样添加边取决于用邻接矩阵还是用邻接表表示图。假定使用邻接矩阵并考虑在顶点1和顶点
         * 3之间加一条边。这些数字对应vertexList数组的下标,顶点存储在数组的对应位置。首次创建邻接
         * 矩阵 adjMat时,初值为0。添加边的代码如下
         *          adjMat[1][3] = 1;
         *          adjMat[3][1] = 1;
         *      如果使用邻接表,就把1加到3的链表中,然后把3加到1的链表中。
         *
         *      邻接矩阵(或者邻接表)提供了关于当前顶点的位置信息。特别是,当前顶点通过边与哪些顶
         * 点相连。为了回答关于顶点序列的更一般问题,就必须求助于其他的算法。下面从搜索开始
         *
         * 搜索
         *      在图中实现的最基本的操作之一就是搜索从一个指定顶点可以到达哪些顶点。例如,可以想像
         * 要找出美国有多少个城市可以从 Kansas城乘坐旅行列车到达(假定中途不换车)。一些城市可以直
         * 达,而有些城市因为没有旅行列车服务而不能直达。有些地方即使有列车服务也不能到达,因为他
         * 们的铁轨系统(例如 Hayfork-Hicks ville RR用窄铁轨标准)不能和出发地或者沿途的标准铁轨系统
         * 相连
         *      还有另外一种情形可能需要找到所有当前顶点可到达的顶点。设想需要设计一个印刷电路板
         * 就像在计算机中使用的这一个。(打开机箱,看看它!)不同的部分[大多数是集成电路块(IC)]放
         * 置在电路板上,集成电路的针脚插在电路板的预留孔中。IC芯片焊接在恰当位置,它们的针脚通过
         * 连线( trace)与其他针脚保持电路连接。连线是在电路板表面的细小金属线。
         *      在图中,顶点代表每个针脚,边代表每条连线。在电路板上,有许多线路是互不相连的,所以
         * 图绝不是连通图。因此,设计过程中,创建一个图,利用它找到哪些针脚连接到同一电路中,真是
         * 太有用了。
         *      假设已经创建了这么一个图。现在需要一种算法来提供系统的方法,从某个特定顶点开始,沿
         * 首边移动到其他顶点。移动完毕后,要保证访问了和起始点相连的每一个顶点。正如在第8章“二
         * 叉树”中讨论的二叉树一样,访问意味着在顶点上的某种操作,例如显示操作。
         *      有两种常用的方法可用来搜索图:即深度优先搜索(DFS)和广度优先搜索(BFS)它们最终
         * 都会到达所有连通的顶点。深度优先搜索通过栈来实现,而广度优先搜索通过队列实现。正如我们
         * 即将看到的那样,不同的机制导致了搜索的不同方式。
         *
         * 深度优先搜索
         *      在搜索到尽头的时候,深度优先搜索用栈记住下一步的走向。这里展示了一个例子,最好实验
         * 一下,最后考察一下执行搜索的具体代码。
         * 一个示例
         *      下面下图所示讨论深度优先搜索思想。图中的数字显示了顶点被访问的顺序。
         *              (2)    (3)    (4)
         *           /   B ———— F ————H
         *          /
         *         /  /  C(5)
         *          /
         *      A(1)
         *          \   (6)    (7)    (8)
         *        \  \   D ———— G ———— I
         *         \
         *          \    E
         *              (9)
         *
         *      为了实现深度优先搜索,找一个起始点——本例为顶点A。需要做三件事:首先访问该顶点,然后
         * 把该点放入栈中,以便记住它,最后标记该点,这样就不会再访问它。
         *      下面可以访问任何与顶点A相连的顶点,只要还没有访问过它。假设顶点按字母顺序访问,所以
         * 下面访问顶点B，然后标记它,并放入栈中。
         *      现在已经访问了B,做相同的事情:找下一个未访问的顶点,也就是F。这个过程称作规则1
         *          规则1：
         *              如果可能,访问一个邻接的未访问顶点,标记它,并把它放入栈中。
         *      再次应用规则1,这次访问顶点H。然而,这里还需要做一些事情,因为没有和H邻接的未访
         * 问顶点。下面是规则2。
         *          规则2
         *              当不能执行规则1时,如果栈不空,就从栈中弹出一个顶点
         *      根据这条规则,从栈中弹出H,这样就又回到了顶点F。F也没有与之邻接且未访问的顶点了。
         * 那么再弹出F,这回到顶点B。这时只有顶点A在栈中。
         *      然而A还有未访问的邻接点,所以访问下一个顶点C。但是C又是这一条路线的终点,所以从
         * 栈中弹出它,再次回到A点。接着访问D、G和Ⅰ。当到达I时,把它们都弹出栈。现在回到A,
         * 然后访问E,最后再次回到A。
         *      然而这次A也没有未访问的邻接点,所以把它也弹出栈。现在栈中已无顶点。下面是规则3。
         *          规则3
         *              如果不能执行规则1和规则2,就完成了整个搜索过程。
         *
         *      深度优先搜索算法要得到距离起始点最远的顶点,然后在不能继续前进的时候返回。使用深度
         * 这个术语表示与起始点的距离,便可以理解“深度优先搜索”的意义
         *
         * 模拟问题
         *      深度优先搜索与迷宫问题类似。迷宫在英国很流行,可以由一方给另一方设置障碍,由另一方
         * 想办法通过。迷宫由狭窄的过道(认为是边)和过道的交汇点(顶点)组成。
         *      假设有个人在迷宫中迷路。她知道有一个出口,并且计划系统地搜索迷宫找到出口。幸运的是
         * 她有一团线和一支笔。她从某个交汇点开始,任意选择一个通路,从线团上退下一些线。在下一个
         * 交汇点,她继续随机选择一条通路,再退下一些线,直到最后她到达死胡同。
         *      到达死胡同时,她按原路返回,把线再绕上,直到到达前一个交汇点。她标记了以前走过的路
         * 径,所以不会重复走那些通路,而选择未走过的通路。当她标记了这个交汇点的所有通路,就会再
         * 回到上一个交汇点,并且重复这个过程
         *      线代表栈:它“记住”了走向某个特定点的路径
         */
        dfs();
        sep();

        /**
         * 深度优先搜索和游戏仿真
         *      深度优先搜索通常用在游戏仿真中(以及真实世界中与游戏相似的情况)。在一般的游戏中
         * 可以在几个可能的动作中选择一个。每个选择导致更进一步的选择,这些选择又产生了更多的选择
         * 这样就形成了一个代表可能性的不断伸展的树形图。一个选择点代表一个顶点,采取的特定选择代
         * 表边,由它可以到达下一个选择顶点。
         *      想像一个名为tic-tac-toe的游戏。如果你先走,可以在九种可能的移动中选择一种。而对手只
         * 能在八种可能的移动中选择一种,依此类推。每次移动都导致了对手可以对当时的情况做出选择,
         * 而她的选择又影响到你下一步的走向,直到最后一个方块被填充。
         *      当考虑如何走时,一种方法是在心中默想一步移动,然后对手可能的对策,然后是自己的对策,一
         * 直进行下去。可以通过考察哪一步移动能产生最好的结果来选择下一步怎么走。在 tic-tac-toe这
         * 种简单游戏中,可能的移动选择很有限,所以可以很轻松地沿每种路径考虑到游戏的最后一步。当
         * 分析了全部的路径后,就可以知道哪条路径最佳。这可以通过图来表示。图中的一个顶点代表首次
         * 移动,它连接着八个顶点,代表对手的八种移动可能,它们每一个又连接着七个顶点,代表相应的
         * 移动可能性,依此类推。从起始点到终点的所有路径都包含九个顶点。要做一个完整的分析,就需
         * 要画九张图,每张图代表不同的起始点。
         *      即使在这种简单的游戏中,搜索路径的数量也是出奇的大。如果可根据忽略所作的简化,九张
         * 图就会有9*8*7*6*5*4*3*2*1条路径。这是9的阶乘(9!),或362880。在国际象棋这种游戏中
         * 可能的移动数量还要大得多,因此,即使最强大的计算机(例如IBM的“深藍”)也不能“看到”
         * 比赛的结果。它们只能沿某条路径到达一定的深度,然后估算棋盘上的形式,来判断是否这个选择
         * 比其他的选择要好。
         *      用计算机考察这种问题,通常的方法是使用深度优先搜索。在每个顶点决定下一步的移动,就
         * 像 dfs.java程序中 getAdjUnvisitedVertex方法执行的那样。如果还有未访问的顶点(选
         * 择点),就把当前顶点入栈,然后继续选择。如果发现在某一点已经不能再作出选择
         * ( getAdjUnvisitedVertex()返回-1),就从栈中弹出一个顶点,并回到这个顶点处,然后看这里是否
         * 还有未试过的选择。
         *      可以把游戏中移动的序列考虑成一棵树,节点代表移动。首次移动是根节点。在tic- tac-toe游
         * 戏中,首次移动后只有八种可能的移动,那么有八个第二层节点与根节点连接。接下来只有七种可
         * 能的移动,那么每个第二层节点就有七个第三层节点与之相连。从根节点到叶节点,为构造这棵树
         * 需要有9!条路径。这叫作决策树。
         *      实际上,决策树中的每一支的数量可以缩减,因为在所有方格填满前,游戏可能已经结束了
         * 然而, tic-tac-toe游戏的决策树还是非常庞大和复杂,不过和国际象棋等游戏比起来,它还算是比较
         * 简单的游戏
         *      决策树中只有几条路径有成功解。例如,有些会导致对手获胜。当到达叶节点时,必须回复到,
         * 或者说回溯到上一个节点,尝试另一条路径。用这样的方法探索整棵树,直到找到获得成功解的路
         * 径。那么就可以根据这条路径作出第一步选择
         */

        /**
         * 广度优先搜索
         *      正如深度优先搜索中看到的,算法表现得好像要尽快地远离起始点似的。相反,在广度优先搜
         * 索中,算法好像要尽可能地靠近起始点。它首先访问起始顶点的所有邻接点,然后再访问较远的区域。这
         * 种搜索不能用栈,而要用队列来实现。
         *
         * 一个例子
         *      下图与上一个图相同,但是这里应用广度优先搜索策略。号码依旧表明访问顶点的顺序。
         *              (2)    (6)    (8)
         *           /   B ———— F ————H
         *          /
         *         /  /  C(3)
         *          /
         *      A(1)
         *          \   (4)    (7)    (9)
         *        \  \   D ———— G ———— I
         *         \
         *          \    E
         *              (5)
         *
         *      A是起始点,所以访问它,并标记为当前顶点。然后应用下面几条规则:
         *          规则1
         *              访问下一个未来访问的邻接点(如果存在),这个顶点必须是当前顶点的邻接点,标记它,并
         *          把它插入到队列中
         *          规则2
         *              如果因为已经没有未访问顶点而不能执行规则1,那么从队列头取一个顶点(如果存在),并使
         *          其成为当前顶点
         *          规则3
         *              如果因为队列为空而不能执行规则2,则搜索结東
         *      因此,需要首先访问所有与A邻接的顶点,并在访问的同时把它们插入到队列中。现在已经访
         * 问了A、B、C、D和E。这时队列(从头到尾)包含BCDE
         *      已经没有未访问的且与顶点A邻接的顶点了,所以从队列中取出B,寻找那些和B邻接的顶点。
         * 这时找到F,所以把F插入到队列中。已经没有未访问的且与顶点B邻接的顶点了,所以从队列中
         * 取出C,它没有未访问的邻接点,因此取出D并访问G。D没有未访问的邻接点,所以取出E。现
         * 在队列中有FG。再取出F,访问H。然后取出G,访问I
         * 现在队列中有H,但当取出它们时,发现没有其他的未访问顶点,这时队列为空,所以从过程
         * 中退出。
         *      在每一时刻,队列所包含的顶点是那些本身已经被访问,而它的邻居还有未被访问的顶点。
         * (对比深度优先搜索,栈的内容是起始点到当前顶点经过的所有顶点。)顶点访问的顺序为
         * ABCDEFGHI。
         *      注意广度优先搜索和深度优先搜索的异同点。
         *      可以认为广度优先搜索就像往水中投入石块时,水波纹扩展的过程——对于喜爱流行病学的人
         * 来说,就好比流感细菌通过航空旅客从一个城市传播到另一个城市。首先是相距起始点只有一条边
         * (飞机航线)的所有顶点被访问,然后是相距两条边的所有顶点被访问,依此类推。
         */
        bfs();
        sep();

        /**
         *      广度优先搜索有一个有趣的属性:它首先找到与起始点相距一条边的所有顶点,然后是与起始
         * 点相距两条边的顶点,依此类推。如果要寻找起始顶点到指定顶点的最短距离,那么这个属性非常
         * 有用。首先执行BFS,当找到指定顶点时,就可以说这条路径是到这个顶点的最短路径。如果有更
         * 短的路径,BFS算法就应该已经找到过它了。
         */

        /** 最小生成树
         *      假设我们设计了一个的印刷电路板，可能需要确定是否使用了最少的连线。也就
         * 是说，针脚之间不要有多余的连接；多余的连接必定占用多余的空间，使布线变得更加困难。
         *      对于针脚和连线的联通部分（用图中的术语说就是顶点和边)，如果有一种算法可以去
         * 掉多余的连线，那真是太好了。执行这种算法的结果就是产生了一种图，它用最少的边连接
         * 了所有的顶点。顶点之间只有最少数量的边保证它们彼此连通。这组成了最小生成树MST(minimum spanning tree)
         *      对给定的一组顶点，可能有很多种最小生成树。注意，最小生成树的边E的数量总比顶点V
         * 的数量小1：
         *          E=V-1
         *      记住，不必关心边的长度。并不需要找到一条最短路径，而是要找最少数量的边。(在下一章
         * 讨论带权图时，这点就会发生改变。)
         *      创建最小生成树的算法与搜索的算法几乎是相同的。它同样可以基于**广度优先搜索或深度优先
         * 搜索**，本例中使用深度优先搜索。
         *      在执行深度优先搜索过程中，记录走过的边，就可以创建一棵最小生成树，这可能会使人感到
         * 有些奇怪。下面要看到的最小生成树算法mst() 和前面看到的深度优先搜索算法dfs() 之间的惟一区
         * 别就是mst() 方法必须记录走过的边。
         */
        mst();
        sep();

        /**
         *      注意，这里仅是能够创建的众多最小生成树中的一种可能。例如，用不同的起始点，可以产生
         * 不同的树。所以可以在代码中作些小的调整， 例如在getAdjUnvisitedVertex()方法中从数组vertexList[]
         * 的最大下标开始，而不是从0下标开始。
         *      最小生成树比较容易从深度优先搜索得到， 这是因为DFS访问所有顶点，但只访问一次。它绝
         * 不会两次访问同一个顶点。当它看到某条边将到达一个己访问的顶点，它就不会走这条边。它从来
         * 不遍历那些不可能的边。因此， DFS算法走过整个图的路径必定是最小生成树。
         *
         * 有向图的拓扑排序
         *      拓扑排序是可以用图模拟的另一种操作。它可用于表示一种情况，即某些项目或事件必须按特
         * 定的顺序排列或发生。下面看一个例子。
         *
         * 实例：课程的优先关系
         *      在高中或大学中，学生们发现（他们会很沮丧）不能随心所欲地选择课程。一些课程有先修课
         * 程——这些课程必须先学。确实，修够必要的课程也是得到相关专业学位的“先决条件”。如下图：
         *
         *         A.代数 → D.高等代数
         *                             ↘
         *              ↘                 G.高级研讨会
         *                             ↗               ↘
         *     B.几何  →    E.解析几何                     H.学位
         *                                            ↗
         *                   C.英文写作  →  F.比较文学
         *
         * 有向图
         *      图可以表示以上的这类关系。然而，图需要有一种前面没有涉及过的特性：边有方向。这时，
         * 图叫做有向图。在有向图中，只能沿着边指定的方向移动。图中的箭头表示了边的方向。
         *      在程序中，有向图和无向图的区别是有向图的边在邻接矩阵中 只有一项。如下图：
         *                                     A
         *                                   ↙
         *                                 B  -→  C
         *      表示上图邻接矩阵
         *                A               B               C
         *           A    0               1               0
         *           B    0               0               1
         *           C    0               0               0
         *
         *      1代表一条边。行标表示边从哪里开始，列标表示边到哪里结束。因此，行A列B的值为1表
         * 示从A到B的边。如果边的方向是相反的，即从B到A，那么行B列A的值就是1。
         *      对于前面讨论的无向图，邻接矩阵的上下三角是对称的，所以一半的信息是冗余的。而有
         * 向图的邻接矩阵中所有行列值都包含必要的信息。它的上下三角不是对称的。
         *      对于有向图，增加边的方法只需要一条语句：
         *          public void addEdge(int start, int end){ //directed graph
         *              adjMat[start][end] = 1;
         *          }
         *      而在无向图中要两条语句。
         *      如果用邻接表表示图，那么A在它的链表中有B，但不同于无向图的是，B的链表中不包
         * 含A。
         *
         * 拓扑排序
         *      假设存在一个课程列表，包含了要得到学位必修的所有课程，下面按课程的先后关系排列它
         * 们。得到学位是列表的最后一项，这就得到下面的序列：
         *      BAEDGCFH
         *      这种方式的排列，叫做为图进行拓扑排序。那些在某些课程前面学习的课程在列表中相应地排
         * 在前面。
         *      实际上，许多可能的排序都符合课程的优先关系。例如可以先修C和F:
         *      CFBAEDGH
         *      这也满足优先关系。而且还有许多其他的排序关系也符合。当用算法生成一个拓扑序列时，使
         * 用的方法和代码的细节决定了产生哪种拓扑序列。
         *      除了课程优先关系以外，拓扑排序还可以对其他情况进行建模。工作进度是一个重要的例子。
         * 如果正在制造小汽车，就需要对工序进行排列，使得刹车片在安装车轮前安装，以及在上底盘前组装
         * 好发动机。汽车制造商用图来模拟制造过程中的上千个操作。这样可以保证每件事按恰当的顺序进行。
         *      用图对工作进度建模叫做关键路径分析。尽管这里没有提到带权图，但是会用到它（下一章讨
         * 论）。带权图允许图带有时间的信息，这个时间是项目中完成不同任务所需的时间。图会指出完成整
         * 个工程的最短时间。
         *
         *      拓扑排序算法的思想虽然不寻常，但是很简单。有两个步骤是必需的：
         *          步骤1
         *              找到一个没有后继的顶点。
         *          顶点的后继也是一些顶点，它们是该节点的直接“下游”——即，该节点与它们由一条边相连，并
         * 且边的方向指向它们。如果有一条边从A指向B,那么B是A的后继。上面课程图中，惟一没有后继的顶
         * 点是H。
         *          步驟2
         *              从图中删除这个顶点，在列表的前面插入顶点的标记。
         *          重复步骒1和步骤2,直到所有顶点都从图中删除。这时，列表显示的顶点顺序就是拓扑排序的结果。
         *
         *      删除顶点似乎是一个极端步骤，但是它是算法的核心。如果第一个顶点不处理，算法就不能计算出要处
         * 理的第二个顶点。如果需要，也可以在其他地方存储图的数据（顶点列表或邻接矩阵）， 然后在排序完成后
         * 恢复它们。
         *      算法能够执行是因为，如果一个顶点没有后继，那么它肯定是拓扑序列中的最后一个。一旦删除它，剩
         * 下的顶点中必然有一个没有后继，所以它成为下一个拓扑序列中的最后一个，依此类推。
         *      拓扑排序算法既可以用于连通图，也可以用于非连通图。这可以模拟另外一种有两个互不相关的目标的
         * 情况，例如同时得到数学学位和急教资格的证书。
         *
         * 环和树
         *      有一种图是拓扑排序算法不能处理的，那就是有环图。什么是环？它是一条路径，路径的起点和终点都
         * 是同一个顶点。下图的路径 B-C-D-B就形成一个环。（注意A-B-C-A不是环，因为不能从C到A。）
         *                                           B
         *                                       ↗     ↖
         *                                     A     ↓    D
         *                                       ↘     ↗
         *                                           C
         *      环模拟了令人左右为难的情况（这个情况是某些学生提出在现行制度中有矛盾的地方），这种情况下，
         * 课程B是课程C的先修课程，C是 D的先修课程，D是B的先修课程。
         *      不包含环的图叫做树。本书前面讨论的二叉树和多叉树就是这个意义上的树。然而在图中提出的树比二
         * 叉树和多叉树更具有一般意义，因为二叉树和多叉树定死了子节点的最大个数。在图中，树的顶点可以连接任
         * 意数量的顶点，只要不存在环即可。
         *      要计算出无向图是否存在环也很简单。如果有N个顶点的图有超过N-1条边，那么它必定存在环。可以尝
         * 试着画一个没有环而有N个顶点，N条边的图，这样就可以理解这个问题了。
         *      拓扑排序必须在无环的有向图中进行。这样的图叫做有向无环图，缩写为DAG(directed acyclic graph)。
         */
        topo();

        /**
         * 主要工作在GraphD类中的topo方法中的while循环中进行。这个循环直到顶点数目为0时才退出。下面是步骤：
         *          1.	调用noSuccessors()找到任意一个没有后继的顶点。
         *          2.	如果找到一个这样的顶点，把顶点放入数组sortedArray[]，并且从图中删除顶点。
         *          3.	如果没有这样的顶点，则图必然存在环。
         *      最后一个被删除的顶点出现在列表的开头，所以，随着nVerts(图中顶点个数）逐渐变小，顶点从sortedArray
         * 数组的最后开始，依次向前排列。
         *      如果有顶点还在图中，但它们都有后继，那么图必然存在环，算法会显示一条信息并退出。如果没有环，
         * 则while循环退出，显示sortedArray数组中的数据，这时顶点是按拓扑有序排列。
         *      noSuccessors()方法使用邻接矩阵找到没有后继的顶点。在外层for循环中，沿着每一行考察每个顶点。在每行
         * 中，用内层for循环扫描列，査找值为1的顶点。如果找到一个，就说明顶点有后继，因为从这个顶点到其他点有边存在。
         * 当找到一个1时，跳出内层循环，考察下一个顶点。
         *      只有整个一行都没有1存在，才说明有一个顶点没有后继；这时，就返回它的行号。如果没有这样的顶点，就返回
         * -1。
         *      除了一些细节外，删除一个顶点很简单。顶点从vertexList[]数组删除，后面的顶点向前移动填补空位。同样的，
         * 顶点的行列从邻接矩阵中删除，下面的行和右面的列移动来填补空位。这些任务由deleteVertex()、moveRowUp()和
         * moveColLeft()方法来完成。对于这个算法，用邻接表表示图效率更高，但要使用更多空间。
         *
         * 有向图的连通性
         *      前面己经看到在无向图中，如何利用深度优先搜索或广度优先搜索找到所有相互连通的点。当试图找到有向图中所
         * 有的连通点时，事情变得更加复杂了。因为不能从任意一个顶点开始，并期望找到所有其他的连通的顶点。
         *
         *                                        B
         *                                     ↙    \
         *                                   A       \      D
         *                                     ↘       ↘  ↙
         *                                       C  ←-- E
         *
         *      考虑上图。如果从A开始，可以到达C,但是不能到达其他任何顶点。如果从B开始，就不能到达D，如果从C开始，更不
         * 能到达其他顶点。关于连通性的问题是：如果从一个指定顶点出发，能够到达哪些顶点？
         *
         * 连通性表
         *      我们可以很容易地改变dfs()程序,依次从每个顶点开始进行搜索。对于上图所示的图，输出如下所示：
         *          AC
         *          BACE
         *          C
         *          DEC
         *          EC
         *      这是有向图的连通性表，第一个字母是起始点，接下来的字母显示能够到达的顶点（直接或者通过其他顶点）。
         *
         * Warshall算法
         *      在有些应用中，需要快速地找出是否一个顶点可以从其他顶点到达。例如想沿Hubris航线
         * 从 Athens到Murmansk，如果不考虑中间要转多少次站，那么可能到达吗？
         *      可以检索连通性表，但是那要查看指定行的所有表项，需要O(N)的时间（这里N是指定顶点
         * 可到达的顶点数目平均值）。如果时间紧迫，可否有更快的方式？
         *      可以构造一个表，这个表将立即（即，O(1)的复杂度）告知一个顶点对另一个点是否是可达
         * 的。 这样的表可以通过系统地修改图的邻接矩阵得到。由这种修正过的邻接矩阵表示的图，叫做原
         * 图的传递闭包。
         *      记住，在原来的邻接矩阵中，行号代表边从哪里开始，列号代表边到哪里结束。（在连通表中
         * 排列是类似的。）行C列D的交叉点为1，表示从顶点C到顶点D有一条边。可以一步从一个顶点到另一
         * 个顶点。（当然，在有向图中，从D到C不能通过这条从C到D的边。)下表是上图图的邻接矩阵：
         *                      A       B       C       D       E
         *                 A    0       0       1       0       0
         *                 B    1       0       0       0       1
         *                 C    0       0       0       0       0
         *                 D    0       0       0       0       1
         *                 E    0       0       1       0       0
         *      可以使用 Warshall 算法把邻接矩阵变成图的传递闭包。算法用有限的几行做了很多工作。它基
         * 于一个简单的思想：
         *      如果能从顶点L到M，并且能从顶点M到N，那么可以从L到N。
         *      这里已经从两个一步路径得到个两步路径。邻接矩阵显示了所有的一步路径，所以它可以很好地
         * 应用这个规则。
         *      你可能想知道这个算法是否可以找到比两步还长的路径。毕竟规则只讨论了从两个一步路径合并
         * 成一个两步路径。在它执行时，算法建立在用前面发现的多步路径来创建任意长度的路径的基础上。将
         * 要描述的实现保证了这个结果，但是它的证明超出了本书的范围。
         *      下面是它的实现过程。这里使用上表作为例子，要检査邻接矩阵的每个单元，一次一行。
         *  - 行A
         *      从行A开始。列A和列B为0,但列C为1，所以在这里停下来。
         *      现在，1说明从A到C有一条路径。如果这时知道从某个顶点X到A有一条路径，那么就知道有条路径
         * 从顶点X到C。以A为终点的边（如果存在）在哪里？它只可能在列A中出现：结果 发现行B是1。这说明从
         * B到A有一条路径。所以就知道有一条边从B到A，另一条（前面开始的那条）从A到C。那么这就暗示可以
         * 通过两步从B到C。通过上图可以检验这个过程的正确性。为了记录这个结果，把行B和列C的交叉点置为1。
         * 结果如下表所示。
         *                      A       B       C       D       E
         *                 A    0       0       1       0       0
         *                 B    1       0       1       0       1
         *                 C    0       0       0       0       0
         *                 D    0       0       0       0       1
         *                 E    0       0       1       0       0
         *      行A余下的单元为空。
         *  - 行B、C和D
         *      下面来看行B。在列A,也就是第一个单元，值就是1,表示有一条边从B到A。有没有边是以B为结尾的？
         * 那么看列B,发现它是空的，所以就知道，列B没有1存在，这样就没有发现更长的路径，因为没有以B为终点
         * 的边。
         *      行C没有1存在，所以直接到行D。这时发现有一条边从D到E。然而，列D是空的，所有没有其他边以D
         * 结尾。
         *  - 行E
         *      在行E，有一条边从E到C。考察列E，E的第一个条目是从B到E的边，所以根据B到E和 E到C,可以知道
         * 有一条路径从B到C。然而，它已经被发现了，所以相应的位置己经被置为1。
         *      列E还有一个1，在行D。这条从D到E的边加上从E到C的边说明有一条路径从D到C， 所以把相应单元置
         * 1。结果如下表所示：
         *                      A       B       C       D       E
         *                 A    0       0       1       0       0
         *                 B    1       0       1       0       1
         *                 C    0       0       0       0       0
         *                 D    0       0       1       0       1
         *                 E    0       0       1       0       0
         *      Warshall算法现在结束。由于向邻接矩阵增加了两个1，现在邻接矩阵可以显示某个顶点通过任意步可
         * 以从另一个顶点到达，如果基于新矩阵画一个图，它就是上图所示的图的传递闭包。
         *
         * Warshall算法的实现
         *      实现Warshall算法的一个方法是用三层嵌套的循环（就像Sedgewick建议的那样)。外层循环考察每一行；
         * 称它为变量y。它里面的一层循环考察行中的每个单元，它使用变量X。如果在单元(x,y)发现1，那么表明有一
         * 条边从y到x,这时执行最里层循环，它使用变量z。
         *      第三个循环检查列y的每个单元，看是否有边以y为终点。（注意y在第一个循环中作为行，在这个循环中
         * 作为列。）如果行z列y值为1,说明有一条边从z到y。一条边从z到y,另一条从y到X，就可以说有一条路径从z到X，
         * 所以把(z，x)置为1。至于算法的细节将留作练习。
         */

        /**
         * 小结
         *  - 图包含由边连接的顶点。
         *  - 图可以表示许多真实世界的情况，包括飞机航线、电子线路和工作调度。
         *  - 搜索算法以一种系统的方式访问图中的每个顶点。搜索是其他行为的基础。
         *  - 两个主要的搜索算法是深度优先搜索（DFS)和广度优先搜索（BFS)。
         *  - 深度优先搜索通过栈实现；广度优先搜索通过队列实现。
         *  - 最小生成树（MST)包含连接图中所有顶点所需要的最少数量的边。
         *  - 在不带权的图中，简单修改深度优先搜索算法就可以生成它的最小生成树。
         *  - 在有向图中，边有方向（通常用箭头表示)。
         *  - 拓扑排序算法创建这样一个顶点的排列列表：如果从A到B有一条边，那么在列表中顶点A在顶点B的前面。
         *  - 拓扑排序只能在DAG中执行，DAG表示有向无环（没有环存在）图。
         *  - 拓扑排序的典型应用是复杂项目的调度，它包含了任务和任务之间的前后关系。
         *  - Warshall算法能找到任意顶点和另外顶点之间是否有连接，不管是通过一步还是任意步到达。
         */
    }

    public static void topo() {
        GraphD theGraph = new GraphD();
        theGraph.addVertex('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H');    // 7

        //上面课程关系拓扑图
        theGraph.addEdge(0, 3);     // AD
        theGraph.addEdge(0, 4);     // AE
        theGraph.addEdge(1, 4);     // BE
        theGraph.addEdge(2, 5);     // CF
        theGraph.addEdge(3, 6);     // DG
        theGraph.addEdge(4, 6);     // EG
        theGraph.addEdge(5, 7);     // FH
        theGraph.addEdge(6, 7);     // GH

        theGraph.topo();            // do the sort
    }

    public static void mst() {
        Graph theGraph = new Graph();
        theGraph.addVertex('A', 'B', 'C', 'D', 'E');    // 4

        theGraph.addEdge(0, 1);     // AB
        theGraph.addEdge(0, 2);     // AC
        theGraph.addEdge(0, 3);     // AD
        theGraph.addEdge(0, 4);     // AE
        theGraph.addEdge(1, 2);     // BC
        theGraph.addEdge(1, 3);     // BD
        theGraph.addEdge(1, 4);     // BE
        theGraph.addEdge(2, 3);     // CD
        theGraph.addEdge(2, 4);     // CE
        theGraph.addEdge(3, 4);     // DE

        print("Minimum spanning tree(dfs): ");
        theGraph.mstByDfs();             // minimum spanning tree
        print("\nMinimum spanning tree(bfs): ");
        theGraph.mstByBfs();
        println();
    }

    public static void bfs() {
        Graph theGraph = new Graph();
        theGraph.addVertex('A', 'B', 'C', 'D', 'E');    // 4

        theGraph.addEdge(0, 1);     // AB
        theGraph.addEdge(1, 2);     // BC
        theGraph.addEdge(0, 3);     // AD
        theGraph.addEdge(3, 4);     // DE

        print("BFS(breadth-first search) Visits: ");
        theGraph.bfs();             // breadth-first search
        println();
    }

    public static void dfs() {
        Graph theGraph = new Graph();
        theGraph.addVertex('A', 'B', 'C', 'D', 'E');

        theGraph.addEdge(0, 1);     // AB
        theGraph.addEdge(1, 2);     // BC
        theGraph.addEdge(0, 3);     // AD
        theGraph.addEdge(3, 4);     // DE

        print("DFS(depth-first search) Visits: ");
        theGraph.dfs();             // depth-first search
        println();
    }
}
