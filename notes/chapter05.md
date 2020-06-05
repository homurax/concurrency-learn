# 从任务获取数据：Callable 与 Future 接口

执行器框架也允许执行其他基于 Callable 接口和 Future 接口返回值的任务。

Callable 是一种函数接口，它定义了 `call()` 方法。`call()` 方法可以抛出一种与 Runnable 接口不同的校验异常。

Callable 接口的处理结果要用 Future 接口来包装，而 Future 接口则描述了异步计算的结果。



## Callable 和 Future 接口简介

在执行器中，你可以执行两种任务。

- **基于 Runnable 接口的任务**

  这些任务实现了不返回任何结果的 `run()` 方法。

- **基于 Callable 接口的任务**

  这些任务实现了返回某个对象作为结果的 `call()` 方法。返回的具体类型由 Callable 接口的泛型参数指定。为了获取该任务返回的结果，执行器会为每个任务返回一个 Future 接口的实现。

  

### Callable 接口

Callable 接口的主要特征如下。

- 有一个简单类型参数，与 `call()` 方法的返回类型相对应。
- 声明了 `call()` 方法。执行器运行任务时，该方法会被执行器执行。它必须返回声明中指定类型的对象。
- `call()` 方法可以抛出任何一种校验异常。可以实现自己的执行器并重载 `afterExecute()` 方法来处理这些异常。



### Future 接口

Future 接口的实现可以用来控制任务的执行和任务状态，以及能够获取结果。该接口的主要特征如下。

- 可以使用 `cancel()` 方法来撤销任务的执行。该方法有一个布尔型参数，用于指定是否需要在任务运行期间中断任务。

- 可以校验任务是否已被撤销（`isCancelled()`）或者是否已经结束（`isDone() `）。

- 可以使用 `get()` 方法获取任务返回的值。该方法有两个重载。

  第一个变体不带有参数，当任务完成执行后，该变体将返回任务所返回的值。如果任务并没有完成执行，它将挂起执行线程直到任务执行完毕。

  第二个变体带有两个参数：时间周期和该周期的 TimeUnit（时间单位）。区别在于将线程等待的时间周期作为参数来传递。如果这一周期结束后任务仍未结束执行，该方法就会抛出一个 TimeoutException 异常。



## 单词最佳匹配算法

用于评估两个单词之间相似度的指标：使用 Levenshtein 距离来度量两个字符序列的差异。

Levenshtein 距离是指，将第一个字符串转换成第二个字符串所需进行的最少的插入、删除或替换操作次数。

### ★ 小结

`invokeAny()` ：有任意一个 Task 成功执行了（无异常），则返回成功完成的 Task 的结果。正常或者异常返回时，尚未完成的 Task 会被取消。

所以 Task 类实现 Callable 接口，`run()` 方法中在指定的范围内查询是否存在目标 string，存在则返回 true，否则抛出异常。

`submit()` 接收一 个 Callable 对象作为参数，并立即返回一个 Future 对象。

`invokeAll()` 方法接收一个 Callable 对象集合作为参数，并且返回一个 Future 对象集合。Future 与 Callable  按照顺序关联。`invokeAll()` 方法仅当所有 Callable 任务都终止执行时才返回。










