# javascript异步代码写法

## no-async-promise-executor  
不建议将 async 函数传递给 new Promise 的构造函数。

```js
// ❌
new Promise(async (resolve, reject) => {});
 
// ✅
new Promise((resolve, reject) => {});
```

## no-await-in-loop   
不建议在循环里使用 await，有这种写法通常意味着程序没有充分利用 JavaScript 的事件驱动。

```js
// ❌
for (const url of urls) {
  const response = await fetch(url);
}

// ✅
const responses = [];
for (const url of urls) {
  const response = fetch(url);
  responses.push(response);
}
 
await Promise.all(responses);
```

## no-promise-executor-return   
不建议在 Promise 构造函数中返回值，Promise 构造函数中返回的值是没法用的，并且返回值也不会影响到 Promise 的状态。

```js
// ❌
new Promise((resolve, reject) => {
  return result;
});

// ✅
new Promise((resolve, reject) => {
  resolve(result);
});
```

## require-atomic-updates  
不建议将赋值操作和 await 组合使用，这可能会导致条件竞争。

```js
// ❌
let totalPosts = 0;
 
async function getPosts(userId) {
  const users = [{ id: 1, posts: 5 }, { id: 2, posts: 3 }];
  await sleep(Math.random() * 1000);
  return users.find((user) => user.id === userId).posts;
}
 
async function addPosts(userId) {
  totalPosts += await getPosts(userId);
}
 
await Promise.all([addPosts(1), addPosts(2)]);
console.log('Post count:', totalPosts);

// ✅
let totalPosts = 0;
 
async function getPosts(userId) {
  const users = [{ id: 1, posts: 5 }, { id: 2, posts: 3 }];
  await sleep(Math.random() * 1000);
  return users.find((user) => user.id === userId).posts;
}
 
async function addPosts(userId) {
  const posts = await getPosts(userId);
  totalPosts += posts; // variable is read and immediately updated
}
 
await Promise.all([addPosts(1), addPosts(2)]);
console.log('Post count:', totalPosts);
```

## max-nested-callbacks  
防止回调地狱，避免大量的深度嵌套。

```js
/* eslint max-nested-callbacks: ["error", 3] */
 
// ❌
async1((err, result1) => {
  async2(result1, (err, result2) => {
    async3(result2, (err, result3) => {
      async4(result3, (err, result4) => {
        console.log(result4);
      });
    });
  });
});
 
// ✅
const result1 = await asyncPromise1();
const result2 = await asyncPromise2(result1);
const result3 = await asyncPromise3(result2);
const result4 = await asyncPromise4(result3);
console.log(result4);
```

## no-return-await  
返回异步结果时不一定要写 await ，如果你要等待一个 Promise，然后又要立刻返回它，这可能是不必要的。

```js
// ❌
async () => {
  return await getUser(userId);
}

// ✅
async () => {
  return getUser(userId);
}

// 👎
async () => {
  try {
    return await getUser(userId);
  } catch (error) {
    // Handle getUser error
  }
}
 
// 👍
async () => {
  try {
    const user = await getUser(userId);
    return user;
  } catch (error) {
    // Handle getUser error
  }
}
```

## prefer-promise-reject-errors  
建议在 reject Promise 时强制使用 Error 对象，这样可以更方便的追踪错误堆栈。

```js
// ❌
Promise.reject('An error occurred');
 
// ✅
Promise.reject(new Error('An error occurred'));
```

## node/handle-callback-err  
强制在 Node.js 的异步回调里进行异常处理。  
在 Node.js 中，通常将异常作为第一个参数传递给回调函数。忘记处理这些异常可能会导致你的应用程序出现不可预知的问题。

```js
// ❌
function callback(err, data) {
  console.log(data);
}
 
// ✅
function callback(err, data) {
  if (err) {
    console.log(err);
    return;
  }
 
  console.log(data);
}
```

## node/no-sync  
不建议在存在异步替代方案的 Node.js 核心 API 中使用同步方法。

```js
// ❌
const file = fs.readFileSync(path);
 
// ✅
const file = await fs.readFile(path);
```

## @typescript-eslint/await-thenable  
不建议 await 非 Promise 函数或值。

```js
// ❌
function getValue() {
  return someValue;
}
 
await getValue();
 
// ✅
async function getValue() {
  return someValue;
}
 
await getValue();
```

## @typescript-eslint/no-floating-promises  
建议 Promise 附加异常处理的代码。

```js
// ❌
myPromise()
  .then(() => {});
 
// ✅
myPromise()
  .then(() => {})
  .catch(() => {});
```

## @typescript-eslint/no-misused-promises  
不建议将 Promise 传递到并非想要处理它们的地方。

```js
// ❌
if (getUserFromDB()) {}
 
// ✅ 👎
if (await getUserFromDB()) {}

// ✅ 👍
const user = await getUserFromDB();
if (user) {}
```

