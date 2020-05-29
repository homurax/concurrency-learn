# 并发设计原理

## 基本的并发概念

### 并发与并行

**并行**：系统中同时运行了多个任务。

**并发**：将任务和它们对共享资源的访问同步的不同技术和机制的方法。

并发和并行是非常相似的概念，而且这种相似性随着多核处理器的发展也在不断增强。



### 同步

同步可以理解为一种协调两个或更多任务以获得预期结果的机制。

- **控制同步**

  当一个任务的开始依赖于另一个任务的结束时，第二个任务不能在第一个任务完成之前开始。

- **数据访问同步**

  当两个或更多任务访问共享变量时，在任意时间里，只有一个任务可以访问该变量。



**临界段**是一段代码，由于它可以访问共享资源，因此在任何给定时间内，只能够被一个任务执行。**互斥**是用来保证这一要求的机制，而且可以采用不同的方式来实现。



流行的同步机制：

- **信号量（semaphore）**

  一种用于控制对一个或多个单位资源进行访问的机制。它有一个用于存放可用资源数量的变量，并且可以采用两种原子操作来管理该变量的值。互斥（mutex，mutual exclusion）是一种特殊类型的信号量，它只能取两个值（即资源空闲和资源忙）， 而且只有将互斥设置为忙的那个进程才可以释放它。互斥可以通过保护临界段来避免出现竞争条件。

- **监视器**

  一种在共享资源之上实现互斥的机制。它有一个互斥、一个条件变量、两种操作（等待条件和通报条件）。一旦通报了该条件，在等待它的任务中只有一个会继续执行。



如果共享数据的所有用户都受到同步机制的保护，那么代码（或方法、对象）就是**线程安全**的。数据的非阻塞的 **CAS**（compareand-swap）原语是不可变的，这样就可以在并发应用程序中使用该代码而不会出任何问题。



### 原子操作和原子变量

**原子操作**是一种发生在瞬间的操作。在并发应用程序中，可以通过一个临界段来实现原子操作，以便对整个操作采用同步机制。

**原子变量**是一种通过原子操作来设置和获取其值的变量。可以使用某种同步机制来实现一个原子变量，或者也可以使用 CAS 以无锁方式来实现一个原子变量，而这种方式并不需要任何同步机制。



## 并发应用程序中可能出现的问题

### 数据竞争

如果有两个或者多个任务在临界段之外对一个共享变量进行写入操作，也就是说没有使用任何同步机制，那么应用程序可能存在**数据竞争**（也叫作**竞争条件**）。



### 死锁

当多个任务正在等待必须由另一线程释放的某个共享资源，而该线程又正在等待必须由前述任务之一释放的另一共享资源时，并发应用程序就出现了死锁。当系统中同时出现如下四种条件时，就会导致这种情形。一般将其称为 Coffman 条件。

- 互斥

  死锁中涉及的资源必须是不可共享的。一次只有一个任务可以使用该资源。

- 占有并等待条件

  一个任务在占有某一互斥的资源时又请求另一互斥的资源。当它在等待时， 不会释放任何资源。

- 不可剥夺

  资源只能被那些持有它们的任务释放。

- 循环等待

  任务 1 正等待任务 2 所占有的资源， 而任务 2 又正在等待任务 3 所占有的资源， 以此类推，最终任务 n 又在等待由任务 1 所占有的资源，这样就出现了循环等待。

避免死锁的机制：

- 忽略

  假设自己的系统绝不会出现死锁，而如果发生死锁， 结果就是只能停止应用程序并且重新执行它。

- 检测

  系统中有一项专门分析系统状态的任务，可以检测是否发生了死锁。如果它检测到了死锁，可以采取一些措施来修复该问题，例如，结束某个任务或者强制释放某一资源。

- 预防

  预防 Coffman 条件中的一条或多条出现。

- 规避

  当一个任务要开始执行时，可以对系统中空闲的资源和任务所需的资源进行分析， 这样就可以判断任务是否能够开始执行。



### 活锁

如果系统中有两个任务，它们总是因对方的行为而改变自己的状态，那么就出现了活锁。最终结果是它们陷入了状态变更的循环而无法继续向下执行。

例如，有两个任务：任务 1 和任务 2，它们都需要用到两个资源：资源 1 和资源 2。假设任务 1 对资源 1 加了一个锁，而任务 2 对资源 2 加了一个锁。当它们无法访问所需的资源时，就会释放自己的资源并且重新开始循环。这种情况可以无限地持续下去，所以这两个任务都不会结束自己的执行过程。



### 资源不足

当某个任务在系统中无法获取维持其继续执行所需的资源时，就会出现资源不足。当有多个任务在等待某一资源且该资源被释放时，系统需要选择下一个可以使用该资源的任务。如果系统中没有设计良好的算法，那么系统中有些线程很可能要为获取该资源而等待很长时间。

要解决这一问题就要确保公平原则。所有等待某一资源的任务必须在某一给定时间之内占有该资源。



### 优先权反转

当一个低优先权的任务持有了一个高优先级任务所需的资源时，就会发生优先权反转。这样的话， 低优先权的任务就会在高优先权的任务之前执行。



## 设计并发算法的方法论




















